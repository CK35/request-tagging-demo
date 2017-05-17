package de.ck35.monitoring.request.tagging.demo.usecases;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import de.ck35.monitoring.request.tagging.RequestTagging;
import de.ck35.monitoring.request.tagging.demo.Usecase;

/**
 * Demonstrates asynchronous request processing. The request-tagging data will
 * be handed over to a different thread. The final tagging result will be
 * provided by the async thread. The request-tagging data of the initial
 * thread will be ignored inside this example.
 * 
 * @author Christian Kaspari
 */
@Component
public class HandoverUsecase implements Usecase {

    private final ExecutorService executorService;

    public HandoverUsecase() {
        this.executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void invoke() {
        RequestTagging.get()
                      .withResourceName("my-handover-usecase")
                      .withMetaData("my-meta-data", "before-async");
        executorService.submit(RequestTagging.get()
                                             .handover(this::doAsync));
        // We will ignore this request because the
        // final result will be provided by the async
        // thread.
        RequestTagging.get()
                      .ignore();
    }

    public void doAsync() {
        RequestTagging.get()
                      .withMetaData("mode", "async");
    }

}