package de.ck35.monitoring.request.tagging.demo.usecases;

import java.io.IOException;

import org.springframework.stereotype.Component;

import de.ck35.monitoring.request.tagging.RequestTagging;
import de.ck35.monitoring.request.tagging.demo.Usecase;

/**
 * Demonstrates a situation where a custom exception handler is used but the
 * server error should be tagged (counted).
 * 
 * @author Christian Kaspari
 */
@Component
public class ExceptionUsecase implements Usecase {

    @Override
    public void invoke() {
        RequestTagging.get()
                      .withResourceName("my-exception-usecase");
        methodWithExceptionHandling();
    }

    public void methodWithExceptionHandling() {
        try {
            anyMethod();
        } catch (IOException e) {
            // Exception will not be re-thrown and thus not handled by
            // request-tagging context.
            RequestTagging.get()
                          .serverError()
                          .withMetaData("serverErrorCause", e.getClass()
                                                             .getName());
        }
    }

    public void anyMethod() throws IOException {
        throw new IOException("A test exception.");
    }

}