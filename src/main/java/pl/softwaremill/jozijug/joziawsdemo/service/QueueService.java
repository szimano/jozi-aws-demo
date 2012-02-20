package pl.softwaremill.jozijug.joziawsdemo.service;


import pl.softwaremill.jozijug.joziawsdemo.entity.Message;

/**
 * User: szimano
 */
public interface QueueService {

    void sendMessage(Message message);

    Message readMessage();
}
