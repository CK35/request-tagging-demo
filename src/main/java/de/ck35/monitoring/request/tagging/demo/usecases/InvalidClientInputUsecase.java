package de.ck35.monitoring.request.tagging.demo.usecases;

import org.springframework.stereotype.Component;

import de.ck35.monitoring.request.tagging.RequestTagging;
import de.ck35.monitoring.request.tagging.demo.Usecase;

/**
 * Demonstrates a situation where an invalid request is detected and the status
 * is set by calling clientError. In this case the caller is blamed and not this
 * application.
 * 
 * @author Christian Kaspari
 */
@Component
public class InvalidClientInputUsecase implements Usecase {

    @Override
    public void invoke() {
        RequestTagging.get()
                      .withResourceName("resource-with-client-input")
                      .clientError();
    }

}