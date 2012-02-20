package pl.softwaremill.jozijug.joziawsdemo.impl.sdb;

import com.xerox.amazonws.simpledb.Domain;
import com.xerox.amazonws.simpledb.SDBException;
import com.xerox.amazonws.simpledb.SimpleDB;
import pl.softwaremill.jozijug.joziawsdemo.MessageMappingConstants;

import javax.inject.Inject;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class MessagesDomainProvider {
    private final AwsAccessKeys awsAccessKeys;

    @Inject
    public MessagesDomainProvider(AwsAccessKeys awsAccessKeys) {
        this.awsAccessKeys = awsAccessKeys;
    }

    public Domain getDomain() throws SDBException {
        SimpleDB simpleDB = new SimpleDB(awsAccessKeys.getAccessKeyId(), awsAccessKeys.getSecretAccessKey(),
                true, "sdb.eu-west-1.amazonaws.com");
        return simpleDB.getDomain(MessageMappingConstants.MESSAGES_DOMAIN);
    }
}
