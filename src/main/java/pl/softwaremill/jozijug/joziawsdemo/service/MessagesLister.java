package pl.softwaremill.jozijug.joziawsdemo.service;

import pl.softwaremill.jozijug.joziawsdemo.entity.Message;

import java.util.List;

/**
 * User: szimano
 */
public interface MessagesLister {
    List<Message> listRecentMessages(String room);
}
