package de.ck35.monitoring.request.tagging.demo;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoUtilities {

    public static class TrafficGenerator implements Closeable {
        
        private static final Logger LOG = LoggerFactory.getLogger(TrafficGenerator.class);
        
        private final int numberOfThreads;
        private final ExecutorService executor;
        private final URL url;
        
        public TrafficGenerator(int numberOfThreads, URL url) {
            this.numberOfThreads = numberOfThreads;
            this.url = url;
            this.executor = Executors.newFixedThreadPool(numberOfThreads);
        }

        public void start() {
            IntStream.range(0, numberOfThreads).forEach(x -> executor.submit(this::loop));
        }
        
        public void loop() {
            while(!Thread.interrupted()) {
                try {                     
                    invoke();
                } catch(RuntimeException e) {
                    LOG.error("Error while invoking demo!", e);
                }
            }
        }
        
        public void invoke() {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                
                connection.setConnectTimeout(0);
                connection.setReadTimeout(0);
                
                connection.connect();
                try {
                    connection.getResponseCode();
                } finally {
                    connection.disconnect();
                }
            } catch(IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        
        @Override
        public void close() {
            this.executor.shutdownNow();
        }

    }
    
    public static class RandomDelay {

        private final Random random;
        private final int maxMillis;
        
        public RandomDelay(int delay, TimeUnit delayUnit) {
            this.maxMillis = (int) TimeUnit.MILLISECONDS.convert(delay, delayUnit);
            this.random = new Random();
        }
        
        public void delay() {
            try {
                Thread.sleep(random.nextInt(maxMillis));
            } catch (InterruptedException e) {
                return;
            }
        }
    }
    
    public static class ChaosMonkey {
        
        private final Random random;
        
        public ChaosMonkey() {
            this.random = new Random();
        }
        
        public void invoke() {
            if (random.nextInt(11) < 9) {
                return;
            }
            List<Supplier<RuntimeException>> exceptions = Arrays.<Supplier<RuntimeException>>asList(IllegalArgumentException::new,
                                                                                                    IllegalStateException::new,
                                                                                                    NumberFormatException::new,
                                                                                                    IndexOutOfBoundsException::new,
                                                                                                    ClassCastException::new);
            throw exceptions.get(random.nextInt(exceptions.size()))
                            .get();
        }
        
    }
    
}