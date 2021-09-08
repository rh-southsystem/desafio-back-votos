package br.com.southsystem.cooperative.service;

public interface MensageriaProducerService {
    void sendMessage(String topic, String message);
}
