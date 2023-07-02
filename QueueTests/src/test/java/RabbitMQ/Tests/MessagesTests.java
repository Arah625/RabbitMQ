package RabbitMQ.Tests;

import RabbitMQ.Methods.MessagePropertiesBuilder;
import RabbitMQ.Methods.PublishMessagePage;
import RabbitMQ.Methods.QueuePage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MessagesTests {
    private Channel channel;
    private QueuePage queuePage;
    private PublishMessagePage publishMessagePage;
    private Connection connection;

    @BeforeMethod
    public void setUp() throws IOException, TimeoutException {
        // Create RabbitMQ connection and channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        // Create an instance of the QueuePage class
        queuePage = new QueuePage(channel);
        publishMessagePage = new PublishMessagePage(channel);
    }

    @AfterTest
    public void tearDown(){
        try {
            // Close the RabbitMQ channel and connection
            channel.close();
            connection.close();
        } catch (Exception exception) {

        }
    }

    @Test
    public void testQueueExistence() throws IOException, InterruptedException, TimeoutException {
        String queueName = "myQueue12es343253234 " + Math.random();

        List<String> petNames = Arrays.asList("Max", "Bella", "Leo", "Luna", "Charlie", "Sadie", "Cooper", "Stella", "Milo", "Lucy", "Buddy", "Daisy", "Rocky", "Lily", "Teddy", "Zoe", "Duke", "Lola", "Mossberg", "Bailey");
        queuePage.abc(petNames.toArray(new String[0]));
   //     queuePage.abc("a", "b");
        // Check if the queue exists
        boolean queueExists = queuePage.isQueueExists(queueName);
        System.out.println("Czy istnieje: " + queueExists);

        queuePage.createExchange("Test_Exchange_" + Math.random());

        if (!queueExists) {
            // Create the queue
            queuePage.createQueue(queueName);
        }


        AMQP.BasicProperties properties = new MessagePropertiesBuilder()
                .withHeader("Request_ID", "headerValue1")
                .withHeader("Signature", "headerValue2")
                .withMessageId("unique-message-id")
                .withAppId("TPP-API")
                .withExpiration("60000") // Set expiration time in milliseconds so message will automatically expire from queue
                .withPriority(5)
                .build();

        // Create message body
        String message = "Hello, RabbitMQ!";
        byte[] messageBody = message.getBytes();

        // Publish the message with properties and headers
        publishMessagePage.publishMessageWithProperties( queueName, properties, messageBody);
        System.out.println("Sent: '" + message + "'");

        int messagesInQueue = queuePage.getMessageCountInQueue(queueName);
        System.out.println("Messages in queue: " + messagesInQueue);
        Assert.assertEquals(messagesInQueue, 1);

    }
}
