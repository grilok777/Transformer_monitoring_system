package com.example.transformmonitorapp.domain.dto.request

data class ChangePasswordRequest(val userId : Long,
                                 val oldPassword : String,
                                 val newPassword: String)
