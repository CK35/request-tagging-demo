package de.ck35.monitoring.request.tagging.demo.usecases;

import org.springframework.stereotype.Component;

import de.ck35.monitoring.request.tagging.RequestTagging;
import de.ck35.monitoring.request.tagging.demo.Usecase;

/**
 * Demonstrates the default usage of request-tagging. The current usecase or
 * resource is identified by calling the withResourceName Method. Optionally any
 * other meta data can be attached by calling the withMetaData Method.
 * 
 * @author Christian Kaspari
 */
@Component
public class DefaultUsecase implements Usecase {

    @Override
    public void invoke() {
        RequestTagging.get()
                      .withResourceName("my-default-usecase")
                      .withMetaData("my-extra-data", "any-value");
    }

}