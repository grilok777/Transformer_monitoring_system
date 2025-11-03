package com.example.items

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import kotlinx.coroutines.*
import org.bson.Document
import org.slf4j.LoggerFactory
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document as MongoDoc
import java.time.LocalDateTime
import kotlin.random.Random

@MongoDoc(collection = "Transformers")
data class Transformer(
    @Id
    val id: Long,
    val manufacturer: String,
    val modelType: String,
    val ratedPowerKVA: Double,
    var primaryVoltageKV: Int = 35,
    var secondaryVoltageKV: Int = 10,
    var frequencyHz: Double = 50.0,
    var transformerCondition: Boolean = true,
    var remoteMonitoring: Boolean = false,
    var currentPower: Double = 0.0,
    var currentTemperature: Double = 0.0,
    var currentVoltage: Double = 0.0
) {
    // --- внутрішні поля ---
    @Transient
    private val logger = LoggerFactory.getLogger(Transformer::class.java)

    @Transient
    private var mongoClient: MongoClient? = null

    @Transient
    private var database: MongoDatabase? = null

    @Transient
    private var collection: MongoCollection<Document>? = null

    @Transient
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // --- підключення до MongoDB ---
    fun connectToMongoDB(uri: String, dbName: String, collectionName: String) {
        try {
            mongoClient = MongoClients.create(uri)
            database = mongoClient!!.getDatabase(dbName)
            collection = database!!.getCollection(collectionName)
            logger.info("✅ Connected to MongoDB Cloud successfully.")
        } catch (e: Exception) {
            logger.error("❌ MongoDB connection error: ${e.message}")
        }
    }

    // --- створення документа трансформатора ---
    fun initTransformerRecord() {
        val existing = collection?.find(Document("id", id))?.firstOrNull()
        if (existing == null) {
            val transformerDoc = Document("id", id)
                .append("manufacturer", manufacturer)
                .append("modelType", modelType)
                .append("ratedPowerKVA", ratedPowerKVA)
                .append("primaryVoltageKV", primaryVoltageKV)
                .append("secondaryVoltageKV", secondaryVoltageKV)
                .append("frequencyHz", frequencyHz)
                .append("transformerCondition", transformerCondition)
                .append("remoteMonitoring", remoteMonitoring)
                .append("dataLogs", mutableListOf<Document>())

            collection?.insertOne(transformerDoc)
            logger.info("📄 Created new transformer record in MongoDB.")
        } else {
            logger.info("ℹ️ Transformer record already exists in MongoDB.")
        }
    }

    // --- 2️⃣ Завантаження ВСІХ трансформаторів із бази ---
    fun loadAllFromMongoDB(): List<Transformer> {
        val transformers = mutableListOf<Transformer>()
        try {
            if (collection == null) {
                logger.warn("⚠️ MongoDB collection not initialized. Call connectToMongoDB() first.")
                return transformers
            }

            val cursor = collection!!.find()
            for (doc in cursor) {
                val t = Transformer(
                    id = doc.getLong("id"),
                    manufacturer = doc.getString("manufacturer"),
                    modelType = doc.getString("modelType"),
                    ratedPowerKVA = doc.getDouble("ratedPowerKVA"),
                    primaryVoltageKV = doc.getInteger("primaryVoltageKV", 35),
                    secondaryVoltageKV = doc.getInteger("secondaryVoltageKV", 10),
                    frequencyHz = doc.getDouble("frequencyHz") ?: 50.0,
                    transformerCondition = doc.getBoolean("transformerCondition", true),
                    remoteMonitoring = doc.getBoolean("remoteMonitoring", false),
                    currentPower = 0.0,
                    currentTemperature = 0.0,
                    currentVoltage = 0.0
                )
                transformers.add(t)
            }

            logger.info("✅ Loaded ${transformers.size} transformers from MongoDB.")
        } catch (e: Exception) {
            logger.error("❌ Error loading transformers: ${e.message}")
        }
        return transformers
    }

    // --- розрахунки ---
    private fun calculateEfficiency(loadPercent: Double): Double =
        95.0 - (100 - loadPercent) * 0.05

    private fun checkSafetyStatus(): String {
        return if (currentTemperature > 120 || currentVoltage > secondaryVoltageKV * 1.1) {
            transformerCondition = false
            "⚠️ Transformer overheating or overvoltage!"
        } else {
            transformerCondition = true
            "✅ Transformer operating within safe limits."
        }
    }

    private fun updateOperatingData(power: Double, temp: Double, voltage: Double) {
        currentPower = power
        currentTemperature = temp
        currentVoltage = voltage
        logger.debug(
            "Updated data -> Power: %.2f kW, Temp: %.2f°C, Voltage: %.2f kV".format(power, temp, voltage)
        )
    }

    // --- запис у MongoDB ---
    private fun saveGeneratedData(power: Double, temp: Double, voltage: Double, efficiency: Double, status: String) {
        val newData = Document("timestamp", LocalDateTime.now().toString())
            .append("power_kW", power)
            .append("temperature_C", temp)
            .append("voltage_kV", voltage)
            .append("efficiency_percent", efficiency)
            .append("status", status)

        collection?.updateOne(
            Document("id", id),
            Document("\$push", Document("dataLogs", newData))
        )

        logger.info("💾 Data saved to MongoDB: $newData")
    }

    // --- короткий опис ---
    fun getTechnicalSummary(): String =
        "Transformer $modelType ($manufacturer): %.1f kVA, %.1f/%.1f kV, Condition: %s".format(
            ratedPowerKVA,
            primaryVoltageKV.toDouble(),
            secondaryVoltageKV.toDouble(),
            if (transformerCondition) "Operational" else "Faulty"
        )

    // --- симуляція ---
    fun simulateOperation() {
        coroutineScope.launch {
            val random = Random(System.currentTimeMillis())

            while (isActive) {
                val load = 50 + random.nextDouble() * 50 // навантаження 50–100%
                val power = ratedPowerKVA * load / 100
                val temp = 60 + random.nextDouble() * 70  // 60–130°C
                val voltage = secondaryVoltageKV * (0.95 + random.nextDouble() * 0.1)
                val efficiency = calculateEfficiency(load)

                updateOperatingData(power, temp, voltage)
                val status = checkSafetyStatus()

                logger.info("Efficiency: %.2f%% | %s".format(efficiency, status))

                // збереження в MongoDB
                saveGeneratedData(power, temp, voltage, efficiency, status)

                delay(30_000L) // повторюємо кожні 30 секунд
            }
        }
    }

    fun stopSimulation() {
        coroutineScope.cancel()
        mongoClient?.close()
        logger.info("🛑 Simulation stopped and MongoDB connection closed.")
    }
}
