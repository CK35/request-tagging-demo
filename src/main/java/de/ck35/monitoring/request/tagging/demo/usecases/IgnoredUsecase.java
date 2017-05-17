package de.ck35.monitoring.request.tagging.demo.usecases;

import org.springframework.stereotype.Component;

import de.ck35.monitoring.request.tagging.RequestTagging;
import de.ck35.monitoring.request.tagging.demo.Usecase;

/**
 * Sometimes you reach a point inside your application which is experimental and
 * you don't really care about any kind of error. By calling ignore all
 * meta-data attached to this request will be ignored. No counters will be
 * incremented and no measurements will be stored.
 * <p>
 * If you have the situation that you reach a point inside your code where the
 * request data is important and should not be ignored after ignore has been
 * called, you can call the heed method to remove the ignore state from the
 * request.
 * 
 * @author Christian Kaspari
 */
@Component
public class IgnoredUsecase implements Usecase {

    @Override
    public void invoke() {
        RequestTagging.get()
                      .withResourceName("an-ignored-usecase")
                      .ignore();
        
        //Can be called to remove ignore state later on.
        //RequestTagging.get().heed();
    }

}