package de.ck35.monitoring.request.tagging.demo.usecases;

import org.junit.Rule;
import org.junit.Test;

import de.ck35.monitoring.request.tagging.core.DefaultRequestTaggingStatus.StatusCode;
import de.ck35.monitoring.request.tagging.testing.ExpectedStatus;
import de.ck35.monitoring.request.tagging.testing.ExpectedStatusRule;

/**
 * Demonstrates how to test your application and ensure that request-tagging has
 * been invoked with the expected arguments.
 * 
 * @author Christian Kaspari
 */
public class DefaultUsecaseTest {

    @Rule public ExpectedStatusRule rule = new ExpectedStatusRule();

    public DefaultUsecase defaultUsecase() {
        return new DefaultUsecase();
    }

    @Test
    @ExpectedStatus(resourceName = "my-default-usecase", statusCode = StatusCode.SUCCESS, metaData = { "my-extra-data", "any-value" })
    public void testInvoke() {
        defaultUsecase().invoke();
    }

}