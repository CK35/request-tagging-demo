package de.ck35.monitoring.request.tagging.demo;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.ck35.monitoring.request.tagging.demo.DemoUtilities.ChaosMonkey;
import de.ck35.monitoring.request.tagging.demo.DemoUtilities.RandomDelay;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final Random random;
    
    @Autowired ChaosMonkey chaosMonkey;
    @Autowired RandomDelay randomDelay;
    @Autowired List<Usecase> usecases;

    public DemoController() {
        this.random = new Random();
    }
    
    @ResponseBody
    @RequestMapping("/invoke")
    public void invoke() throws InterruptedException {
        randomDelay.delay();
        usecases.get(random.nextInt(usecases.size()))
                .invoke();
        chaosMonkey.invoke();
    }

}