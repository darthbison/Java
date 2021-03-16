package com.gps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;



public class GPSConsumer {

    private ActiveMQConnection connection;
    private Session session;
    private Destination destination;
   private Properties properties = new Properties();

    public void before() throws JMSException {
        
        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("job.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            Logger.getRootLogger().error(e);
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Logger.getRootLogger().error(e);
            }
        }
        
        
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getProperty("connectionURI"));
        connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("GPSQ.Work");
    }

    public void after() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }

    public void run() throws JMSException {
        MessageConsumer consumer = session.createConsumer(destination);
        
        Set<ActiveMQQueue> amqs = connection.getDestinationSource().getQueues(); 
        Iterator<ActiveMQQueue> queues = amqs.iterator(); 

        StringBuilder sb = new StringBuilder();
        
        while ( queues.hasNext() ) 
        { 
            ActiveMQQueue queue = (ActiveMQQueue)queues.next();
           if ("GPSQ.Work".equals(queue.getQueueName()))
           {
               QueueBrowser browser = session.createBrowser(queue);
               Enumeration<?> messagesInQueue = browser.getEnumeration();
               while (messagesInQueue.hasMoreElements()) {
                   TextMessage queueMessage = (TextMessage) messagesInQueue.nextElement();
                   String coordinates = queueMessage.getText();
                   
                   sb.append(coordinates + "\n");
                   consumer.receive();
               }
               
               break;
           }
          
        }
       
//        CSVCreation csv = new CSVCreation();
//        try {
//            csv.generateCSV(sb.toString());
//        } catch (IOException e) {
//           Logger.getRootLogger().error(e);
//        }
        
        try {
            TimeUnit.MINUTES.sleep(3);
        } catch (InterruptedException e) {
            Logger.getRootLogger().error(e);
            Thread.currentThread().interrupt();
        }
        connection.stop();
        consumer.close();
    }

    public static void main(String[] args) throws Exception {

        GPSConsumer consumer = new GPSConsumer();
        Logger.getRootLogger().info("Starting example Consumer now...");
        try {
            consumer.before();
            consumer.run();
            consumer.after();
        } catch (Exception e) {
            Logger.getRootLogger().error(e);
        }
        Logger.getRootLogger().info("Finished running the sample Consumer app.");
       
    }
    
}
