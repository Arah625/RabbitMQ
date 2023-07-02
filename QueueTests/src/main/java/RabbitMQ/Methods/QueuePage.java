package RabbitMQ.Methods;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueuePage {
    private Channel channel; // RabbitMQ channel
    private final static String EXCHANGE_NAME_DIRECT = "ex_demo";
    private final static String ROUTING_KEY = "Placek";

    public QueuePage(Channel channel) {
        this.channel = channel;
    }

    public boolean isQueueExists(String queueName) throws IOException {
        try {
            // Check if the queue exists
            channel.queueDeclarePassive(queueName);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void createQueue(String queueName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME_DIRECT, ROUTING_KEY);
            System.out.println("Queue created: " + queueName);
        }
    }

    public void createExchange(String exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
            System.out.println("Exchange created: " + exchangeName);
        }
    }

    public void createQueueToExchangeBinding(String queueName, String exchangeName, String routingKey) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueBind(queueName, exchangeName, routingKey);
        }
    }

    public int getMessageCountInQueue(String queueName) throws IOException, TimeoutException {
        int messageCount;
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive(queueName);
            messageCount = declareOk.getMessageCount();
            System.out.println("Number of messages in the queue: " + messageCount);
        }
        return messageCount;
    }


    public void deleteQueue(String queueName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDelete(queueName);
            System.out.println("Queue: '" + queueName + "' has been successfully deleted");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    public void abc(String... strings) {
        for (String value : strings) {
            System.out.println(value);
        }
    }

}
