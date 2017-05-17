package de.ck35.monitoring.request.tagging.demo.usecases;

import org.springframework.stereotype.Component;

import de.ck35.monitoring.request.tagging.RequestTagging;
import de.ck35.monitoring.request.tagging.core.RequestTaggingContextConfigurer;
import de.ck35.monitoring.request.tagging.demo.Usecase;

/**
 * Demonstrates the usage of hashed meta-data. The method withHashedMetaData
 * should be used when sensitive data must be added to the current request.
 * <p>
 * The underlying hash algorithm can be configured with the algorithmName key.
 * 
 * @author Christian Kaspari
 * @see RequestTaggingContextConfigurer.ConfigKey#algorithmName
 */
@Component
public class HashedMetaDataUsecase implements Usecase {

    @Override
    public void invoke() {
        RequestTagging.get()
                      .withResourceName("")
                      .withHashedMetaData("hashedCustomerNumber", getCustomerNumber());
    }

    public String getCustomerNumber() {
        return "ABC";
    }

}