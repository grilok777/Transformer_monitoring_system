package com.example.service.interfaces;

public interface SimulatorService {

    /**
     * Запуск симуляції для трансформатора за його ID.
     *
     * @param transformerId ID трансформатора
     */
    void startSimulation(Long transformerId);
}

