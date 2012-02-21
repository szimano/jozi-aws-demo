package pl.softwaremill.jozijug.joziawsdemo.impl.hibernate;

import org.jboss.seam.solder.core.Veto;
import pl.softwaremill.common.cdi.transaction.Transactional;
import pl.softwaremill.jozijug.joziawsdemo.entity.Message;
import pl.softwaremill.jozijug.joziawsdemo.service.Local;
import pl.softwaremill.jozijug.joziawsdemo.service.MessageAdder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.*;
import java.util.Date;

/**
 * User: szimano
 */
@Local
public class HibernateMessageAdder implements MessageAdder {

    @PersistenceContext
    private EntityManager entityManager;

    public HibernateMessageAdder() {
    }

    @Transactional
    public void addMessage(Message msg) {
        entityManager.joinTransaction();

        try {
            if (msg.getSaveDate() == null)
                msg.setSaveDate(new Date());

            entityManager.merge(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Message added");
    }
}
