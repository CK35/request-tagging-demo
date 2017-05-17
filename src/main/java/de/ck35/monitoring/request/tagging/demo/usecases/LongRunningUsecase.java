package de.ck35.monitoring.request.tagging.demo.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ck35.monitoring.request.tagging.RequestTagging;
import de.ck35.monitoring.request.tagging.core.RequestTaggingContextConfigurer;
import de.ck35.monitoring.request.tagging.demo.Usecase;
import de.ck35.monitoring.request.tagging.demo.DemoUtilities.RandomDelay;

/**
 * Demonstrates the measurement of certain code points by calling startTimer and
 * stopTimer with the same key.
 * <p>
 * Before measured durations are transfered, the max number of durations per
 * node must be configured. The default value is zero which will discard all
 * measured durations at the end of the request. The consumed extra memory for
 * holding all measurements depends on the number of meta data keys included
 * within the running request.
 * 
 * @author Christian Kaspari
 * @see RequestTaggingContextConfigurer.ConfigKey#maxDurationsPerNode
 */
@Component
public class LongRunningUsecase implements Usecase {

    private static final String TIMER_KEY = "expensive_operation";

    @Autowired RandomDelay randomDelay;

    @Override
    public void invoke() {
        RequestTagging.get()
                      .withResourceName("long-running-usecase")
                      .startTimer(TIMER_KEY);

        randomDelay.delay();

        RequestTagging.get()
                      .stopTimer(TIMER_KEY);
    }

}