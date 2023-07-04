package RabbitMQ.Methods;

import RabbitMQ.Methods.HTTP_API.HttpApiMethods;
import RabbitMQ.Methods.HTTP_API.HttpApiPaths;
import com.rabbitmq.client.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class QueuePage {
    private Channel channel; // RabbitMQ channel
    HttpApiMethods httpApiMethods;
    HttpApiPaths httpApiPaths;

    public QueuePage(Channel channel) {
        this.channel = channel;
    }

    public boolean isQueueExists(String queueName) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // Check if the queue exists
            channel.queueDeclarePassive(queueName);
            System.out.println("Queue '" + queueName + "' does exist");
            return true;
        } catch (IOException | TimeoutException e) {
            System.out.println("Queue '" + queueName + "' does not exist");
            return false;
        }
    }

    public boolean isQueueExists(String queueName, String responseBody) {
        JSONArray queues = new JSONArray(responseBody);

        // Iterate over the queues
        for (int i = 0; i < queues.length(); i++) {
            JSONObject queue = queues.getJSONObject(i);
            String currentQueueName = queue.getString("name");

            // If the queue name matches the input queueName, return true
            if (currentQueueName.equals(queueName)) {
                return true;
            }
        }
        // If we didn't find a match in the list, the queue doesn't exist
        return false;
    }


    public boolean isExchangeExists(String exchangeName) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // Check if the exchange exists
            channel.exchangeDeclarePassive(exchangeName);
            System.out.println("Exchange '" + exchangeName + "' does exist");
            return true;
        } catch (IOException | TimeoutException e) {
            System.out.println("Exchange '" + exchangeName + "' does not exist");
            return false;
        }
    }

    public void createQueueWithBinding(String queueName, String exchangeName, String routingKey) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            System.out.println("Queue created: " + queueName);
        }
    }

    public void createQueue(String queueName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            System.out.println("Queue created: " + queueName);
        }
    }

    public void createQuorumQueue(String queueName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            System.out.println("Queue created: " + queueName);
        }
    }

    public void createQueueWithArguments(String queueName, Map<String, Object> queueArguments) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, true, false, false, queueArguments);
            System.out.println("Queue created: " + queueName);
        }
    }

    public void createDirectExchange(String exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
            System.out.println("Direct exchange '" + exchangeName + "' has been created");
        }
    }

    public void createFanoutExchange(String exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true);
            System.out.println("Fanout exchange '" + exchangeName + "' has been created");
        }
    }

    public void createHeadersExchange(String exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS, true);
            System.out.println("Headers exchange '" + exchangeName + "' has been created");
        }
    }

    public void createTopicExchange(String exchangeName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true);
            System.out.println("Topic exchange '" + exchangeName + "' has been created");
        }
    }

    public void bindExchangeToQueue(String queueName, String exchangeName, String routingKey) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueBind(queueName, exchangeName, routingKey);
            System.out.println("Exchange: '" + exchangeName + "' has been successfully bound to queue: '" + queueName + "' by routing key:'" + routingKey + "'");
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
        //Delete queue regardless of if is not empty or used
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDelete(queueName);
            System.out.println("Queue: '" + queueName + "' has been successfully deleted");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void deleteQueueIfIsEmpty(String queueName) throws IOException, TimeoutException {
        //Delete queue if it is empty
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDelete(queueName, false, true);
            System.out.println("Empty queue: '" + queueName + "' has been successfully deleted");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void deleteQueueIfUnused(String queueName) throws IOException, TimeoutException {
        //Delete queue if it is not used (does not have any consumers)
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDelete(queueName, true, false);
            System.out.println("Not used queue: '" + queueName + "' has been successfully deleted");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void deleteExchange(String exchangeName) throws IOException, TimeoutException {
        //Delete exchange regardless of if is not empty or used
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDelete(exchangeName);
            System.out.println("Exchange: '" + exchangeName + "' has been successfully deleted");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void deleteExchangeIfUnused(String exchangeName) throws IOException, TimeoutException {
        //Delete exchange regardless of if is not empty or used
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDelete(exchangeName, true);
            System.out.println("Exchange: '" + exchangeName + "' has been successfully deleted");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void deleteExchangeWithoutServerConfirmation(String exchangeName) throws IOException, TimeoutException {
        // Deleting an exchange regardless of whether it is used or unused, without waiting for a server confirmation.
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeleteNoWait(exchangeName, true);
            System.out.println("Exchange: '" + exchangeName + "' has been successfully deleted");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void unbindExchangeFromQueue(String queueName, String exchangeName, String routingKey) throws IOException, TimeoutException {
        // Unbinding an exchange from a queue
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueUnbind(queueName, exchangeName, routingKey);
            System.out.println("Exchange: '" + exchangeName + "' has been successfully unbound from queue: '" + queueName + "' by routing key:'" + routingKey + "'");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllMessagesFromQueue(String queueName) throws IOException, TimeoutException {
        //Delete queue if it is not used (does not have any consumers)
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queuePurge("queue-name");
            System.out.println("Queue: '" + queueName + "' has been successfully purged of all messages");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public boolean bindingExists(String queueName, String exchangeName, String routingKey, String responseBody) {
        JSONArray bindings = new JSONArray(responseBody);
        boolean bindingExists = false;
        for (int i = 0; i < bindings.length(); i++) {
            JSONObject binding = bindings.getJSONObject(i);
            if (exchangeName.equals(binding.getString("source")) &&
                    queueName.equals(binding.getString("destination")) &&
                    routingKey.equals(binding.getString("routing_key"))) {
                bindingExists = true;
                break;
            }
        }
        if (bindingExists) {
            System.out.println("Binding between queue '" + queueName + "' and exchange '" + exchangeName + "' with routing key '" + routingKey + "' exists");
        } else {
            System.out.println("Binding between queue '" + queueName + "' and exchange '" + exchangeName + "' with routing key '" + routingKey + "' does not exist");
        }
        return bindingExists;
    }

    public void bindExchangeToQueue(String queueName, String exchangeName, String... routingKeys) {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            for (String routingKey : routingKeys) {
                channel.queueBind(queueName, exchangeName, routingKey);
                System.out.println("Exchange: '" + exchangeName + "' has been successfully bound to queue: '" + queueName + "' by routing key:'" + routingKey + "'");
            }
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExchangesByRegex(String exchangesRegex, String responseBody) {
        JSONArray exchanges = new JSONArray(responseBody);
        // Iterate over the exchanges
        for (int i = 0; i < exchanges.length(); i++) {
            JSONObject exchange = exchanges.getJSONObject(i);
            String exchangeName = exchange.getString("name");

            // If the exchange name matches the regex, delete it
            if (exchangeName.matches(exchangesRegex)) {
                // Delete the exchange
                httpApiMethods = new HttpApiMethods();
                httpApiPaths = new HttpApiPaths();
                httpApiMethods.requestDelete(httpApiPaths.deleteExchangePath(exchangeName));
                System.out.println("Exchange " + exchangeName + " deleted");
            }
        }
    }

    public void deleteQueuesByRegex(String queuesRegex, String responseBody) {
        JSONArray exchanges = new JSONArray(responseBody);
        // Iterate over the queues
        for (int i = 0; i < exchanges.length(); i++) {
            JSONObject exchange = exchanges.getJSONObject(i);
            String queueName = exchange.getString("name");

            // If the queue name matches the regex, delete it
            if (queueName.matches(queuesRegex)) {
                // Delete the queue
                httpApiMethods = new HttpApiMethods();
                httpApiPaths = new HttpApiPaths();
                httpApiMethods.requestDelete(httpApiPaths.deleteQueuePath(queueName));
                System.out.println("Queue " + queueName + " deleted");
            }
        }
    }

    public boolean areAllQueuesDeletionByRegex(String queuesRegex, String responseBody) {
        JSONArray queues = new JSONArray(responseBody);
        boolean allQueuesDeleted = true;

        // Iterate over the queues
        for (int i = 0; i < queues.length(); i++) {
            JSONObject queue = queues.getJSONObject(i);
            String queueName = queue.getString("name");

            // If the queue name matches the regex, check if it was deleted
            if (queueName.matches(queuesRegex)) {
                if (isQueueExists(queueName, responseBody)) {
                    allQueuesDeleted = false;
                    System.out.println("Queue " + queueName + " was not deleted");
                } else {
                    System.out.println("Queue " + queueName + " was deleted");
                }
            }
        }

        return allQueuesDeleted;
    }
}