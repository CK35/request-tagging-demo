package de.ck35.monitoring.request.tagging.demo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import de.ck35.monitoring.request.tagging.core.RequestTaggingContextConfigurer;
import de.ck35.monitoring.request.tagging.demo.DemoUtilities.ChaosMonkey;
import de.ck35.monitoring.request.tagging.demo.DemoUtilities.RandomDelay;
import de.ck35.monitoring.request.tagging.demo.DemoUtilities.TrafficGenerator;
import de.ck35.monitoring.request.tagging.integration.filter.RequestTaggingFilter;

/**
 * Demo Spring Boot Application which demonstrates the usage and opportunities of request-tagging.
 * <p>
 * Please see implemntations of {@link Usecase} interface inside the usecases package.
 * 
 * @author Christian Kaspari
 * @see Usecase
 */
public class DemoApplication extends SpringApplication {

    public DemoApplication() {
        super(RootConfiguration.class);
    }
    
    public static void main(String[] args) {
        new DemoApplication().run(args).getBean(TrafficGenerator.class).start();
    }
    
    @Configuration
    @EnableAutoConfiguration
    @PropertySource("classpath:demo.properties")
    @ComponentScan("de.ck35.monitoring.request.tagging.demo.usecases")
    public static class RootConfiguration {

        @Autowired Environment env;

        @Bean
        public DemoController demoController() {
            return new DemoController();
        }
        
        @Bean
        public FilterRegistrationBean requestTaggingFilterRegistration() {
            Map<String, String> initParameters = new HashMap<>();
            RequestTaggingContextConfigurer.load(env::getProperty, initParameters::put);
            FilterRegistrationBean bean = new FilterRegistrationBean(new RequestTaggingFilter());
            bean.setInitParameters(initParameters);
            return bean;
        }

        @Bean
        public TrafficGenerator trafficGenerator() {
            return new TrafficGenerator(env.getProperty("threads", Integer.TYPE, 5), url());
        }
        
        @Bean
        public URL url() {
            try {
                return new URL("http", "localhost", 8080, "/demo/invoke");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        
        @Bean
        public RandomDelay randomDelay() {
            int delay = env.getProperty("delay", Integer.TYPE, 5);
            TimeUnit delayUnit = env.getProperty("delayUnit", TimeUnit.class, TimeUnit.SECONDS);
            return new RandomDelay(delay, delayUnit);
        }
        
        @Bean
        public ChaosMonkey chaosMonkey() {
            return new ChaosMonkey();
        }
    }
}