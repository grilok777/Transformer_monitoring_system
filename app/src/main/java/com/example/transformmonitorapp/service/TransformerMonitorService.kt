package com.example.transformmonitorapp.service

import com.example.transformmonitorapp.model.Transformer
import com.example.transformmonitorapp.model.TransformerStatus
import com.example.transformmonitorapp.model.User


class TransformerMonitorService(
    users : List<User>,
    transformers: List<Transformer>
) {
    fun addUser(user: User) {}
    fun addTransformer(transformer: Transformer) {
    }

    fun getTransformerStatus(id: Long): TransformerStatus? {
        return null
    }

    fun generateReport() {}
}