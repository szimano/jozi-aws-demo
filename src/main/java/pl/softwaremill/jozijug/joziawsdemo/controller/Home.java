package pl.softwaremill.jozijug.joziawsdemo.controller;

import org.joda.time.DateTime;
import pl.softwaremill.asamal.controller.AsamalContext;
import pl.softwaremill.asamal.controller.ControllerBean;
import pl.softwaremill.asamal.controller.annotation.Controller;
import pl.softwaremill.asamal.controller.annotation.Get;
import pl.softwaremill.asamal.controller.annotation.Json;
import pl.softwaremill.asamal.controller.annotation.Post;
import pl.softwaremill.jozijug.joziawsdemo.entity.Message;
import pl.softwaremill.jozijug.joziawsdemo.service.MessagesLister;
import pl.softwaremill.jozijug.joziawsdemo.service.QueueService;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

/**
 * Home page controller
 *
 * User: szimano
 */
@Controller("home")
public class Home extends ControllerBean implements Serializable {

    @Inject
    private MessagesLister messagesLister;

    @Inject
    private QueueService queueService;

    @Get
    public void index() {

        List<Message> messages = messagesLister.listRecentMessages(getParameter("room"));

        setParameter("messages", messages);
    }

    @Post
    public void addMessage() {
        Message message = new Message(
                UUID.randomUUID(),
                getParameter("room"),
                getParameter("content"),
                new DateTime(),
                null
        );

        queueService.sendMessage(message);
    }

    @Json
    public Object getMessages() {
        return messagesLister.listRecentMessages(getParameter("room"));
    }
}
