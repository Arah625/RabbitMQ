package RabbitMQ.Methods.QueueArguments;

import java.util.HashMap;
import java.util.Map;

public class QuorumQueueArgumentsBuilder {
    private final Map<String, Object> arguments;

    public QuorumQueueArgumentsBuilder() {
        this.arguments = new HashMap<>();
        this.arguments.put("x-queue-type", "quorum");
    }

    public QuorumQueueArgumentsBuilder withAutoExpire(int autoExpire) {
        arguments.put("x-expires", autoExpire);
        return this;
    }

    public QuorumQueueArgumentsBuilder withMessageTtl(int messageTtl) {
        arguments.put("x-message-ttl", messageTtl);
        return this;
    }

    public QuorumQueueArgumentsBuilder withOverflow(String overflow) {
        arguments.put("x-overflow", overflow);
        return this;
    }

    public QuorumQueueArgumentsBuilder withSingleActiveConsumer(boolean singleActiveConsumer) {
        arguments.put("x-single-active-consumer", singleActiveConsumer);
        return this;
    }

    public QuorumQueueArgumentsBuilder withDeadLetterExchange(String deadLetterExchange) {
        arguments.put("x-dead-letter-exchange", deadLetterExchange);
        return this;
    }

    public QuorumQueueArgumentsBuilder withDeadLetterRoutingKey(String deadLetterRoutingKey) {
        arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);
        return this;
    }

    public QuorumQueueArgumentsBuilder withMaxLength(int maxLength) {
        arguments.put("x-max-length", maxLength);
        return this;
    }

    public QuorumQueueArgumentsBuilder withMaxLengthBytes(int maxLengthBytes) {
        arguments.put("x-max-length-bytes", maxLengthBytes);
        return this;
    }

    public QuorumQueueArgumentsBuilder withDeliveryLimit(int deliveryLimit) {
        arguments.put("x-delivery-limit", deliveryLimit);
        return this;
    }

    public QuorumQueueArgumentsBuilder withInitialClusterSize(int clusterSize) {
        arguments.put("x-quorum-initial-group-size", clusterSize);
        return this;
    }

    public QuorumQueueArgumentsBuilder withDeadLetterStrategy(String deadLetterStrategy) {
        arguments.put("x-dead-letter-strategy", deadLetterStrategy);
        return this;
    }

    public QuorumQueueArgumentsBuilder withLeaderLocator(String leaderLocator) {
        arguments.put("x-queue-leader-locator", leaderLocator);
        return this;
    }

    public Map<String, Object> build() {
        return arguments;
    }
}
