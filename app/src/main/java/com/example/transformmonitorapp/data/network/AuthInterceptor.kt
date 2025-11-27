package com.example.transformmonitorapp.data.network

import com.example.transformmonitorapp.data.network.api.AuthApi
import com.example.transformmonitorapp.domain.dto.request.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class AuthInterceptor(
    private val tokenManager: TokenManager,
    private val authApi: AuthApi
) : Interceptor {

    private val lock = ReentrantLock()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originalAccess = tokenManager.getAccessToken()

        if (!originalAccess.isNullOrEmpty()) {
            request = request.newBuilder()
                .header("Authorization", "Bearer $originalAccess")
                .build()
        }

        val response = chain.proceed(request)

        if (response.code == 401) {
            response.close()

            return lock.withLock {
                val currentToken = tokenManager.getAccessToken()
                if (!currentToken.isNullOrEmpty() && currentToken != originalAccess) {
                    val newReq = request.newBuilder()
                        .header("Authorization", "Bearer $currentToken")
                        .build()
                    return@withLock chain.proceed(newReq)
                }

                val refreshToken = tokenManager.getRefreshToken() ?: return@withLock response

                val refreshResponse = runBlocking {
                    authApi.refreshToken(RefreshTokenRequest(refreshToken))
                }

                return@withLock if (refreshResponse.isSuccessful && refreshResponse.body() != null) {
                    val body = refreshResponse.body()!!
                    tokenManager.saveTokens(body.accessToken, body.refreshToken)

                    val newReq = request.newBuilder()
                        .header("Authorization", "Bearer ${body.accessToken}")
                        .build()
                    chain.proceed(newReq)
                } else {
                    tokenManager.clear()
                    response
                }
            }
        }

        return response
    }
}