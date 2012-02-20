package pl.softwaremill.jozijug.joziawsdemo.producers;

import com.google.common.collect.ImmutableMap;
import pl.softwaremill.common.conf.Configuration;
import pl.softwaremill.common.conf.PropertiesProvider;
import pl.softwaremill.jozijug.joziawsdemo.impl.sdb.AwsAccessKeys;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * User: szimano
 */
public class AWSKeysProducer {

    private void setupSQSPropertiesProvider(final AwsAccessKeys awsAccessKeys) {
        Configuration.registerPropertiesProvider(new PropertiesProvider() {
            @Override
            public ImmutableMap<String, String> lookupProperties(String name) {
                if ("sqs".equals(name)) {
                    return ImmutableMap.of("sqsServer", "sqs.eu-west-1.amazonaws.com",
                            "AWSAccessKeyId", awsAccessKeys.getAccessKeyId(),
                            "SecretAccessKey", awsAccessKeys.getSecretAccessKey());
                }

                return null;
            }

            @Override
            public boolean providerAvailable() {
                return false;
            }
        });
    }

    @Produces
    @Singleton
    public AwsAccessKeys createAwsKeys() {
        AwsAccessKeys awsAccessKeys;
        try {
            awsAccessKeys = AwsAccessKeys.createFromResources();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupSQSPropertiesProvider(awsAccessKeys);

        return awsAccessKeys;
    }
}
