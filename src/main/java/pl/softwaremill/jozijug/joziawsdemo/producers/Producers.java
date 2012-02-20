package pl.softwaremill.jozijug.joziawsdemo.producers;

import com.google.common.collect.ImmutableMap;
import pl.softwaremill.common.conf.Configuration;
import pl.softwaremill.common.conf.PropertiesProvider;
import pl.softwaremill.jozijug.joziawsdemo.impl.hibernate.HibernateMessageAdder;
import pl.softwaremill.jozijug.joziawsdemo.impl.hibernate.HibernateMessageLister;
import pl.softwaremill.jozijug.joziawsdemo.impl.jms.JMSQueueService;
import pl.softwaremill.jozijug.joziawsdemo.impl.sdb.AwsAccessKeys;
import pl.softwaremill.jozijug.joziawsdemo.impl.sdb.MessagesDomainProvider;
import pl.softwaremill.jozijug.joziawsdemo.impl.sdb.SDBMessageAdder;
import pl.softwaremill.jozijug.joziawsdemo.impl.sdb.SDBMessageLister;
import pl.softwaremill.jozijug.joziawsdemo.impl.sqs.SQSQueueService;
import pl.softwaremill.jozijug.joziawsdemo.service.MessageAdder;
import pl.softwaremill.jozijug.joziawsdemo.service.MessagesLister;
import pl.softwaremill.jozijug.joziawsdemo.service.QueueService;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * User: szimano
 */
public class Producers {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private MessagesDomainProvider messagesDomainProvider;

    @Produces
    private MessageAdder createMessageAdder() {
        if (System.getProperty("local") == null) {
            return new SDBMessageAdder(messagesDomainProvider);
        } else {
            return new HibernateMessageAdder(entityManager);
        }
    }

    @Produces
    private MessagesLister createMessageLister() {
        if (System.getProperty("local") == null) {
            return new SDBMessageLister(messagesDomainProvider);
        } else {
            return new HibernateMessageLister(entityManager);
        }
    }

    @Produces
    private QueueService createQueueService() {
        if (System.getProperty("local") == null) {
            return new SQSQueueService();
        } else {
            return new JMSQueueService();
        }
    }
}
