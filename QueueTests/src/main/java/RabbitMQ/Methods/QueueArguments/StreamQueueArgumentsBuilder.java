package RabbitMQ.Methods.QueueArguments;

import java.util.HashMap;
import java.util.Map;

public class StreamQueueArgumentsBuilder {
    private final Map<String, Object> arguments;

    public StreamQueueArgumentsBuilder() {
        this.arguments = new HashMap<>();
        this.arguments.put("x-queue-type", "stream");
    }

    public StreamQueueArgumentsBuilder withMaxLengthBytes(int maxLengthBytes) {
        arguments.put("x-max-length-bytes", maxLengthBytes);
        return this;
    }

    public StreamQueueArgumentsBuilder withMaxTimeRetention(int maxTimeRetention) {
        arguments.put("x-max-age", maxTimeRetention);
        return this;
    }

    public StreamQueueArgumentsBuilder withMaxSegmentSizeInBytes(int maxSegmentSizeInBytes) {
        arguments.put("x-stream-max-segment-size-bytes", maxSegmentSizeInBytes);
        return this;
    }

    public StreamQueueArgumentsBuilder withInitialClusterSize(int clusterSize) {
        arguments.put("x-initial-cluster-size", clusterSize);
        return this;
    }

    public StreamQueueArgumentsBuilder withLeaderLocator(String leaderLocator) {
        arguments.put("x-queue-leader-locator", leaderLocator);
        return this;
    }

    public Map<String, Object> build() {
        return arguments;
    }
}
