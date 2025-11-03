package com.example;

import com.example.items.Transformer;
import com.example.repository.TransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import java.util.List;

@SpringBootApplication
public class MonitorTransformerServerApplication {

	@Autowired
	private TransformerRepository transformerRepository;

	public static void main(String[] args) {
		SpringApplication.run(MonitorTransformerServerApplication.class, args);
	}

	/**
	 * Після запуску:
	 * - підключається до MongoDB
	 * - завантажує всі трансформатори з бази
	 * - якщо є — підключається й запускає симуляцію для кожного
	 * - якщо нема — створює нового, додає в базу, запускає симуляцію
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void startSimulation() {
		String uri = "mongodb+srv://troianvitalii_db_user:e9cdpdOcz8pA708o@cluster0.wfs1ajx.mongodb.net/?appName=Cluster0";
		String dbName = "TransformersDB";
		String collection = "Transformers";

		// тимчасовий об’єкт для ініціалізації з’єднання
		Transformer connectionHelper = new Transformer(
				0L, "System", "Loader", 0.0,
				35, 10, 50.0, true, false, 0.0, 0.0, 0.0
		);
		connectionHelper.connectToMongoDB(uri, dbName, collection);

		// завантаження всіх існуючих трансформаторів
		List<Transformer> transformers = connectionHelper.loadAllFromMongoDB();

		if (transformers.isEmpty()) {
			System.out.println("⚠️ No transformers found in MongoDB. Creating a new one...");

			Transformer newTransformer = new Transformer(
					1L,
					"Siemens",
					"TX-500",
					1000.0,
					35,
					10,
					50.0,
					true,
					false,
					0.0,
					0.0,
					0.0
			);

			newTransformer.connectToMongoDB(uri, dbName, collection);
			newTransformer.initTransformerRecord();
			transformerRepository.save(newTransformer);

			System.out.println("✅ Created and saved new transformer in MongoDB Atlas.");
			newTransformer.simulateOperation();
			System.out.println("▶️ Simulation started for the new transformer.");

		} else {
			System.out.println("✅ Found " + transformers.size() + " transformers in MongoDB. Connecting...");
			for (Transformer t : transformers) {
				t.connectToMongoDB(uri, dbName, collection);
				System.out.println("🔌 Connected to transformer: " + t.getTechnicalSummary());
				t.simulateOperation();
			}
			System.out.println("▶️ Simulations started for all existing transformers.");
		}
	}
}