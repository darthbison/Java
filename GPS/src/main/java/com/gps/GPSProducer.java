package com.gps;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;


public class GPSProducer {

    private static final String connectionUri = "tcp://localhost:61616";
    private Connection connection;
    private Session session;
    private Destination destination;

    public void before() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectionUri);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("GPSQ.Work");
    }

    public void after() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public void run(String contents) throws JMSException {
        MessageProducer producer = session.createProducer(destination);

        if (!contents.isEmpty()) {
        } else {
            TextMessage message = session.createTextMessage(contents);
            producer.send(message);
        }

        producer.close();
    }

    public static void main(String[] args) throws Exception {

        StringBuilder contents = new StringBuilder();
        
        GPSProducer producer = new GPSProducer();
       
        Logger.getRootLogger().info("Starting example GPS Producer now...");
        try {
            producer.before();
            producer.run(contents.toString());
            producer.after();
        } catch (Exception e) {
            Logger.getRootLogger().error("Caught an exception during the example: " + e);
        }
        Logger.getRootLogger().info("Finished running GPS Producer.");
        
    }
    
    
}
