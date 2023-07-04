package RabbitMQ.Tests;

import RabbitMQ.Methods.HTTP_API.HttpApiMethods;
import RabbitMQ.Methods.HTTP_API.HttpApiPaths;
import RabbitMQ.Methods.QueueArguments.ClassicQueueArgumentsBuilder;
import RabbitMQ.Methods.QueueArguments.QuorumQueueArgumentsBuilder;
import RabbitMQ.Methods.QueueArguments.StreamQueueArgumentsBuilder;
import RabbitMQ.Methods.QueuePage;
import RabbitMQ.TestUtils.Service;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class QueueTests {
    private Channel channel;
    private QueuePage queuePage;
    private HttpApiPaths httpApiPaths;
    private HttpApiMethods httpApiMethods;
    private Connection connection;
    private Service service;

    @BeforeMethod
    public void setUp() throws IOException, TimeoutException {
        // Create RabbitMQ connection and channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        // Create an instance of the QueuePage class
        queuePage = new QueuePage(channel);
        service = new Service();
        httpApiPaths = new HttpApiPaths();
        httpApiMethods = new HttpApiMethods();
    }

    @AfterTest
    public void tearDown() {
        try {
            // Close the RabbitMQ channel and connection
            channel.close();
            connection.close();
        } catch (Exception exception) {
            System.out.println("Error occurred");
        }
    }

    @Test(priority = 1)
    public void createClassicQueue() throws IOException, TimeoutException {
        service.testCaseName("Create classic queue");

        String queueName = service.timestampWithMillis("Test_Classic_Queue_");
        // Check if the queue exists
        if (!queuePage.isQueueExists(queueName)) {
            // Create the queue
            queuePage.createQueue(queueName);
        }
        // Check if queue exists
        Assert.assertTrue(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' does not exist");

        // Delete queue
        queuePage.deleteQueue(queueName);
        // Check if queue not exists
        Assert.assertFalse(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' exists");
    }

    @Test(priority = 2)
    public void createQuorumQueue() throws IOException, TimeoutException {
        service.testCaseName("Create quorum queue");

        String queueName = service.timestampWithMillis("Test_Quorum_Queue_");
        // Check if the queue exists
        if (!queuePage.isQueueExists(queueName)) {
            // Create the queue
            Map<String, Object> arguments = new QuorumQueueArgumentsBuilder()
                    .build();

            queuePage.createQueueWithArguments(queueName, arguments);
        }
        // Check if queue exists
        Assert.assertTrue(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' does not exist");

        // Delete queue
        queuePage.deleteQueue(queueName);
        // Check if queue not exists
        Assert.assertFalse(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' exists");
    }

    @Test(priority = 3)
    public void createStreamQueue() throws IOException, TimeoutException {
        service.testCaseName("Create stream queue");

        String queueName = service.timestampWithMillis("Test_Stream_Queue_");
        // Check if the queue exists
        if (!queuePage.isQueueExists(queueName)) {
            // Create the queue
            Map<String, Object> arguments = new StreamQueueArgumentsBuilder()
                    .build();

            queuePage.createQueueWithArguments(queueName, arguments);
        }
        // Check if queue exists
        Assert.assertTrue(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' does not exist");

        // Delete queue
        queuePage.deleteQueue(queueName);
        // Check if queue not exists
        Assert.assertFalse(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' exists");
    }


    @Test(priority = 4)
    public void createClassicQueueWithArguments() throws IOException, InterruptedException, TimeoutException {
        service.testCaseName("Create classic queue with arguments");

        String queueName = service.timestamp("Test_Queue_");

        boolean queueExists = queuePage.isQueueExists(queueName);
        System.out.println("Czy istnieje: " + queueExists);

        String exchangeName = service.timestamp("Test_Exchange_");
        queuePage.createDirectExchange(exchangeName);

        if (!queueExists) {

            Map<String, Object> arguments = new ClassicQueueArgumentsBuilder()
                    .withAutoExpire(3600000) // 1 hour
                    .withMessageTtl(60000) // 1 minute
                    .withOverflow("drop-head")
                    .withSingleActiveConsumer(true)
                    .withDeadLetterExchange("myDeadLetterExchange")
                    .withDeadLetterRoutingKey("myDeadLetterRoutingKey")
                    .withMaxLength(1000)
                    .withMaxLengthBytes(1048576) // 1 megabyte
                    .withMasterLocator("client-local")
                    .build();

            queuePage.createQueueWithArguments(queueName, arguments);


            Assert.assertTrue(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' does not exist");
        }
        // Delete queue
        queuePage.deleteQueue(queueName);
        // Check if queue not exists
        Assert.assertFalse(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' exists");
    }

    @Test(priority = 5)
    public void testQueueExistence() throws IOException, InterruptedException, TimeoutException {
        service.testCaseName("Create direct exchange with queue and binding - check for existence");
        String exchangeName = service.timestamp("Test_Exchange_");
        // Check if the queue exists
        if (!queuePage.isExchangeExists(exchangeName)) {
            // Create the queue
            queuePage.createDirectExchange(exchangeName);
        }
        Assert.assertTrue(queuePage.isExchangeExists(exchangeName), "Exchange '" + exchangeName + "' does not exist");

        String routingKey = service.timestamp("Test_Routing_Key_");

        String queueName = service.timestamp("Test_Queue_");
        // Check if the queue exists
        if (!queuePage.isQueueExists(queueName)) {
            // Create the queue
            queuePage.createQueueWithBinding(queueName, exchangeName, routingKey);
        }
        Assert.assertTrue(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' does not exist");

        int messagesInQueue = queuePage.getMessageCountInQueue(queueName);
        int expectedMessagesAmount = 0;
        Assert.assertEquals(messagesInQueue, expectedMessagesAmount, "Amount of messages in queue does not equals to expected '" + expectedMessagesAmount + "'");

        String responseBody = httpApiMethods.getResponse(httpApiPaths.getBindingsPath());
        Assert.assertTrue(queuePage.bindingExists(queueName, exchangeName, routingKey, responseBody), "Binding between queue '" + queueName + "' and exchange '" + exchangeName + "' with routing key '" + routingKey + "' does not exist");
    }

    @Test(priority = 6)
    public void createMultipleQueuesAndDeleteThemByRegex() throws IOException, TimeoutException {
        service.testCaseName("Create multiple queues and then delete them");
        String regex = "^Test_Queue_.*";

        int numberOfQueues = 10;
        //create n-queues
        for (int i = 0; i < numberOfQueues; i++) {
            String queueName = service.timestampWithMillis("Test_Queue_");
            // Check if the queue exists
            if (!queuePage.isQueueExists(queueName)) {
                // Create the queue
                queuePage.createQueue(queueName);
            }
            Assert.assertTrue(queuePage.isQueueExists(queueName), "Queue '" + queueName + "' does not exist");
        }
        String responseBody = httpApiMethods.getResponse(httpApiPaths.getQueuesPath());
        queuePage.deleteQueuesByRegex(regex, responseBody);
        responseBody = httpApiMethods.getResponse(httpApiPaths.getQueuesPath());
        Assert.assertTrue(queuePage.areAllQueuesDeletionByRegex(regex, responseBody));
    }
}
