package RabbitMQ.Tests;

import RabbitMQ.Methods.QueuePage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class QueueTests {
    private Channel channel;
    private QueuePage queuePage;
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

            // Wait for a few seconds before checking the queue existence again

        }

 //       Assert.assertTrue(queueExists, "Queue was not created successfully");
    }
}
