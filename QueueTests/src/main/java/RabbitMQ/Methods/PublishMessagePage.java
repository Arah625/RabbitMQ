package RabbitMQ.Methods;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class PublishMessagePage {

    private Channel channel; // RabbitMQ channel

    public PublishMessagePage(Channel channel) {
        this.channel = channel;
    }

    public void publishMessageWithProperties(String queueName, AMQP.BasicProperties messageProperties, String messageBody) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.basicPublish("", queueName, messageProperties, messageBody.getBytes());
        }
    }

    public void publishMessageWithProperties(String queueName, AMQP.BasicProperties messageProperties, byte[] messageBody) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.basicPublish("", queueName, messageProperties, messageBody);
            System.out.println("Message to QUEUE: '" + queueName + "' with PROPERTIES: '" + messageProperties + "' and BODY: " + Arrays.toString(messageBody) + "' was sent");
        }
    }

}
