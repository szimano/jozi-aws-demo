package pl.softwaremill.jozijug.joziawsdemo.impl.hibernate;

import org.jboss.seam.solder.core.Veto;
import org.joda.time.DateTime;
import pl.softwaremill.common.cdi.transaction.Transactional;
import pl.softwaremill.jozijug.joziawsdemo.entity.Message;
import pl.softwaremill.jozijug.joziawsdemo.service.MessageAdder;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * User: szimano
 */
@Veto
public class HibernateMessageAdder implements MessageAdder {

    private EntityManager entityManager;

    public HibernateMessageAdder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void addMessage(Message msg) {
        if (msg.getSaveDate() == null)
            msg.setSaveDate(new DateTime());

        entityManager.merge(msg);
    }
}
