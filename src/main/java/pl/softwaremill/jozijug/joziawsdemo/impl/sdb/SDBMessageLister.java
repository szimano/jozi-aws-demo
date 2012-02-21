package pl.softwaremill.jozijug.joziawsdemo.impl.sdb;

import com.xerox.amazonws.simpledb.Item;
import com.xerox.amazonws.simpledb.SDBException;
import pl.softwaremill.jozijug.joziawsdemo.entity.Message;
import pl.softwaremill.jozijug.joziawsdemo.service.AWS;
import pl.softwaremill.jozijug.joziawsdemo.service.MessagesLister;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pl.softwaremill.jozijug.joziawsdemo.MessageMappingConstants.*;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@AWS
public class SDBMessageLister implements MessagesLister {
    private final MessagesDomainProvider messagesDomainProvider;

    private DateFormatter dateFormatter;

    @Inject
    public SDBMessageLister(MessagesDomainProvider messagesDomainProvider, DateFormatter dateFormatter) {
        this.messagesDomainProvider = messagesDomainProvider;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public List<Message> listRecentMessages(String room) {
        StringBuilder sb = new StringBuilder();
        // We should use constants for the domain and attribute names, but
        // that's more readable for the demo
        sb.append("SELECT * FROM messages WHERE room = '")
                .append(escapeValue(room))
                .append("' AND date IS NOT NULL ORDER BY date DESC LIMIT 10");

        String query = sb.toString();
        System.out.println("Executing query: " + query);

        List<Item> items;
        try {
            items = messagesDomainProvider.getDomain().selectItems(query, null, false).getItems();
        } catch (SDBException e) {
            throw new RuntimeException(e);
        }
        List<Message> messages = new ArrayList<Message>();

        for (Item item : items) {
            messages.add(convertItemToMessage(item));
        }

        return messages;
    }

    private String escapeValue(String value) {
        return value.replaceAll("'", "''").replaceAll("\"", "\"\"");
    }

    private Message convertItemToMessage(Item item) {
        try {
            return new Message(
                    UUID.fromString(item.getIdentifier()),
                    item.getAttribute(ROOM),
                    item.getAttribute(CONTENT),
                    dateFormatter.getDateFormat().parse(item.getAttribute(DATE)),
                    dateFormatter.getDateFormat().parse(item.getAttribute(SAVE_DATE))
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
