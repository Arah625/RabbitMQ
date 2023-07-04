package RabbitMQ.Methods.QueueArguments;

import java.util.HashMap;
import java.util.Map;

public class DefaultForVirtualHostQueueArgumentsBuilder {
    private final Map<String, Object> arguments;

    public DefaultForVirtualHostQueueArgumentsBuilder() {
        this.arguments = new HashMap<>();
        this.arguments.put("x-queue-type", "classic");
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withAutoExpire(int autoExpire) {
        arguments.put("x-expires", autoExpire);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withMessageTtl(int messageTtl) {
        arguments.put("x-message-ttl", messageTtl);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withOverflow(String overflow) {
        arguments.put("x-overflow", overflow);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withSingleActiveConsumer(boolean singleActiveConsumer) {
        arguments.put("x-single-active-consumer", singleActiveConsumer);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withDeadLetterExchange(String deadLetterExchange) {
        arguments.put("x-dead-letter-exchange", deadLetterExchange);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withDeadLetterRoutingKey(String deadLetterRoutingKey) {
        arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withMaxLength(int maxLength) {
        arguments.put("x-max-length", maxLength);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withMaxLengthBytes(int maxLengthBytes) {
        arguments.put("x-max-length-bytes", maxLengthBytes);
        return this;
    }

    public DefaultForVirtualHostQueueArgumentsBuilder withLeaderLocator(String leaderLocator) {
        arguments.put("x-queue-master-locator", leaderLocator);
        return this;
    }

    public Map<String, Object> build() {
        return arguments;
    }
}
