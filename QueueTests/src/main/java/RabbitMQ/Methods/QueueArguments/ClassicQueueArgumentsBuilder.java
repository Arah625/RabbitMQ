package RabbitMQ.Methods.QueueArguments;

import java.util.HashMap;
import java.util.Map;

public class ClassicQueueArgumentsBuilder {
    private final Map<String, Object> arguments;

    public ClassicQueueArgumentsBuilder() {
        this.arguments = new HashMap<>();
        this.arguments.put("x-queue-type", "classic");
    }

    public ClassicQueueArgumentsBuilder withAutoExpire(int autoExpire) {
        arguments.put("x-expires", autoExpire);
        return this;
    }

    public ClassicQueueArgumentsBuilder withMessageTtl(int messageTtl) {
        arguments.put("x-message-ttl", messageTtl);
        return this;
    }

    public ClassicQueueArgumentsBuilder withOverflow(String overflow) {
        arguments.put("x-overflow", overflow);
        return this;
    }

    public ClassicQueueArgumentsBuilder withSingleActiveConsumer(boolean singleActiveConsumer) {
        arguments.put("x-single-active-consumer", singleActiveConsumer);
        return this;
    }

    public ClassicQueueArgumentsBuilder withDeadLetterExchange(String deadLetterExchange) {
        arguments.put("x-dead-letter-exchange", deadLetterExchange);
        return this;
    }

    public ClassicQueueArgumentsBuilder withDeadLetterRoutingKey(String deadLetterRoutingKey) {
        arguments.put("x-dead-letter-routing-key", deadLetterRoutingKey);
        return this;
    }

    public ClassicQueueArgumentsBuilder withMaxLength(int maxLength) {
        arguments.put("x-max-length", maxLength);
        return this;
    }

    public ClassicQueueArgumentsBuilder withMaxLengthBytes(int maxLengthBytes) {
        arguments.put("x-max-length-bytes", maxLengthBytes);
        return this;
    }

    public ClassicQueueArgumentsBuilder withMaximumPriority(int maxPriority) {
        arguments.put("x-max-priority", maxPriority);
        return this;
    }

    public ClassicQueueArgumentsBuilder withQueueVersion(int queueVersion) {
        arguments.put("x-queue-version", queueVersion);
        return this;
    }

    public ClassicQueueArgumentsBuilder withMasterLocator(String masterLocator) {
        arguments.put("x-queue-master-locator", masterLocator);
        return this;
    }

    public Map<String, Object> build() {
        return arguments;
    }
}
