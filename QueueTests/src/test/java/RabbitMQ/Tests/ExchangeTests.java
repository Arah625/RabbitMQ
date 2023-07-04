package RabbitMQ.Tests;

import RabbitMQ.Methods.HTTP_API.HttpApiMethods;
import RabbitMQ.Methods.HTTP_API.HttpApiPaths;
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
import java.util.concurrent.TimeoutException;

public class ExchangeTests {
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

        }
    }

    @Test
    public void deleteMultipleExchangesByRegex() throws IOException, InterruptedException, TimeoutException {
        service.testCaseName("Create multiple exchanges and then delete them");
        String regex = "^Test_Exchange_.*";

        int numberOfExchanges = 10;
        //create n-queues
        for (int i = 0; i < numberOfExchanges; i++) {
            String exchangeName = service.timestamp("Test_Exchange_");
            // Check if the exchange exists
            if (!queuePage.isExchangeExists(exchangeName)) {
                // Create the exchange
                queuePage.createDirectExchange(exchangeName);
            }
            Assert.assertTrue(queuePage.isExchangeExists(exchangeName), "Exchange '" + exchangeName + "' does not exist");
        }
        String responseBody = httpApiMethods.getResponse(httpApiPaths.getExchangesPath());

        queuePage.deleteExchangesByRegex(regex, responseBody);

    }
}
