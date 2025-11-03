package com.example.repository

import com.example.items.Transformer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TransformerRepository : MongoRepository<Transformer, Long> {
    fun findByManufacturer(manufacturer: String): List<Transformer>
}