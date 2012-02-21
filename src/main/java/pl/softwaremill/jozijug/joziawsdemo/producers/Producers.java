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
import pl.softwaremill.jozijug.joziawsdemo.service.*;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.IOException;

/**
 * User: szimano
 */
public class Producers {

    @Inject
    @Local
    Instance<MessagesLister> localMessageLister;

    @Inject
    @AWS
    Instance<MessagesLister> awsMessageLister;

    @Inject
    @Local
    Instance<MessageAdder> localMessageAdder;

    @Inject
    @AWS
    Instance<MessageAdder> awsMessageAdder;

    @Inject
    @Local
    Instance<QueueService> localQueueService;

    @Inject
    @AWS
    Instance<QueueService> awsQueueService;

    @Produces
    private MessageAdder createMessageAdder() {
        if (System.getProperty("local") != null) {
            return localMessageAdder.get();
        } else {
            return awsMessageAdder.get();
        }
    }

    @Produces
    private MessagesLister createMessageLister() {
        if (System.getProperty("local") != null) {
            return localMessageLister.get();
        } else {
            return awsMessageLister.get();
        }
    }

    @Produces
    private QueueService createQueueService() {
        if (System.getProperty("local") != null) {
            return localQueueService.get();
        } else {
            return awsQueueService.get();
        }
    }
}
