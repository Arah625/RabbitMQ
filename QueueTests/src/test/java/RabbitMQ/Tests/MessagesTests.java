package RabbitMQ.Tests;

import RabbitMQ.Methods.HTTP_API.HttpApiMethods;
import RabbitMQ.Methods.HTTP_API.HttpApiPaths;
import RabbitMQ.Methods.PublishMessage;
import RabbitMQ.Methods.QueuePage;
import RabbitMQ.TestUtils.Service;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessagesTests {
    private Channel channel;
    private QueuePage queuePage;
    private PublishMessage publishMessagePage;
    private Connection connection;
    private HttpApiPaths httpApiPaths;
    private HttpApiMethods httpApiMethods;
    Service service;

    @BeforeMethod
    public void setUp() throws IOException, TimeoutException {
        // Create RabbitMQ connection and channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        // Create an instance of the QueuePage class
        queuePage = new QueuePage(channel);
        publishMessagePage = new PublishMessage(channel);
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

        }
    }

//    @Test
//    public void testQueueExistence() throws IOException, InterruptedException, TimeoutException {
//        String queueName = "myQueue12es343253234 " + Math.random();
//
//        List<String> petNames = Arrays.asList("Max", "Bella", "Leo", "Luna", "Charlie", "Sadie", "Cooper", "Stella", "Milo", "Lucy", "Buddy", "Daisy", "Rocky", "Lily", "Teddy", "Zoe", "Duke", "Lola", "Mossberg", "Bailey");
//        queuePage.abc(petNames.toArray(new String[0]));
//   //     queuePage.abc("a", "b");
//        // Check if the queue exists
//        boolean queueExists = queuePage.isQueueExists(queueName);
//        System.out.println("Czy istnieje: " + queueExists);
//
//        queuePage.createExchange("Test_Exchange_" + Math.random());
//
//        if (!queueExists) {
//            // Create the queue
//            queuePage.createQueue(queueName);
//        }
//
//
//        AMQP.BasicProperties properties = new MessagePropertiesBuilder()
//                .withHeader("Request_ID", "headerValue1")
//                .withHeader("Signature", "headerValue2")
//                .withMessageId("unique-message-id")
//                .withAppId("TPP-API")
//                .withExpiration("60000") // Set expiration time in milliseconds so message will automatically expire from queue
//                .withPriority(5)
//                .build();
//
//        // Create message body
//        String message = "Hello, RabbitMQ!";
//        byte[] messageBody = message.getBytes();
//
//        // Publish the message with properties and headers
//        publishMessagePage.publishMessageWithProperties( queueName, properties, messageBody);
//        System.out.println("Sent: '" + message + "'");
//
//                int messagesInQueue = queuePage.getMessageCountInQueue(queueName);
//        int expectedMessagesAmount = 0;
//        Assert.assertEquals(messagesInQueue, expectedMessagesAmount, "Amount of messages in queue does not equals to expected '" + expectedMessagesAmount + "'");
//
//    }


//    @Test
//    public void testQueueExistence() throws IOException, InterruptedException, TimeoutException {
//        String queueName = service.timestamp("TestingQueue");
//
//        // Check if the queue exists
//        boolean queueExists = queuePage.isQueueExists(queueName);
//        System.out.println("Czy istnieje: " + queueExists);
//
//        queuePage.createExchange("Test_Exchange_" + Math.random());
//
//        if (!queueExists) {
////             Create the queue
//                 queuePage.createQueue(queueName);
//        }
//
//        String requestId = service.timestamp(UUID.randomUUID().toString());
//        String signature = "SIGNATURE-" + UUID.randomUUID();
//        String messageId = UUID.randomUUID().toString();
//        String appId = "LI-2313";
//        String expiration = service.timeInMillis(1, "m");
//        int priority = service.randomNumberGenerator(1, 5);
//
//        //Message properties
//        AMQP.BasicProperties properties = new MessagePropertiesBuilder()
//                .withDeliveryMode(2)
//                .withHeader("Request_ID", requestId)
//                .withHeader("Signature", signature)
//                .withMessageId(messageId)
//                .withAppId(appId)
//                .withExpiration(expiration) // Set expiration time in milliseconds so message will automatically expire from queue
//                .withPriority(priority)
//                .build();
//
//        // Create message body
//        String message = "Hello, RabbitMQ!";
//        byte[] messageBody = message.getBytes();
//
//        // Publish the message with properties and headers
//        publishMessagePage.publishMessageWithProperties(queueName, properties, messageBody);
//        System.out.println("Sent: '" + message + "'");
//
//        int messagesInQueue = queuePage.getMessageCountInQueue(queueName);
//        System.out.println("Messages in queue: " + messagesInQueue);
//        Assert.assertEquals(messagesInQueue, 1);
//
////        queuePage.deleteQueue(queueName);
////        Assert.assertFalse(queueExists);
//
//    }



}
