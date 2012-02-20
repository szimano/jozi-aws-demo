package pl.softwaremill.jozijug.joziawsdemo.impl.hibernate;

import org.jboss.seam.solder.core.Veto;
import pl.softwaremill.jozijug.joziawsdemo.entity.Message;
import pl.softwaremill.jozijug.joziawsdemo.service.MessagesLister;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * User: szimano
 */
@Veto
public class HibernateMessageLister implements MessagesLister {

    private EntityManager entityManager;

    public HibernateMessageLister(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Message> listRecentMessages(String room) {
        //noinspection unchecked
        return entityManager.createQuery("select m from Message m where m.room = :room order by m.date desc")
                .setParameter("room", room).setMaxResults(10).getResultList();
    }
}
