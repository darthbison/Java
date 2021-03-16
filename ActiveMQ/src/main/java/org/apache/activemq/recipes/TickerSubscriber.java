package org.apache.activemq.recipes;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TickerSubscriber {

    private final String connectionUri = "tcp://localhost:61616";
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
   

    public void before() throws Exception {
        connectionFactory = new ActiveMQConnectionFactory(connectionUri);
        connection = connectionFactory.createConnection();
        connection.setClientID("TickerGetter");
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        
    }

    public void after() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public void run() throws Exception {
       
        MessageConsumer consumer = session.createDurableSubscriber(session.createTopic("EVENTS.QUOTES"),"DurableConsumer");
        consumer.setMessageListener(new EventListener());
        TimeUnit.MINUTES.sleep(5);
        connection.stop();
        consumer.close();
    }

    public static void main(String[] args) {
        TickerSubscriber producer = new TickerSubscriber();
        System.out.print("\n\n\n");
        System.out.println("Starting example Stock Ticker Subscriber now...");
        try {
            producer.before();
            producer.run();
            producer.after();
        } catch (Exception e) {
            System.out.println("Caught an exception during the example: " + e.getMessage());
        }
        System.out.println("Finished running the sample Stock Ticker Subscriber app.");
        System.out.print("\n\n\n");
    }

}