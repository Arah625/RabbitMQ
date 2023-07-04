package RabbitMQ.Methods;

import com.rabbitmq.client.AMQP;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessagePropertiesBuilder {
    private Map<String, Object> headers;
    private Integer deliveryMode;
    private String messageId;
    private Integer priority;
    private String contentType;
    private String contentEncoding;
    private String correlationId;
    private String replyTo;
    private String expiration;
    private Date timestamp;
    private String type;
    private String userId;
    private String appId;
    private String clusterId;



    public MessagePropertiesBuilder() {
        headers = new HashMap<>();
    }

    public MessagePropertiesBuilder withDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
        return this;
    }

    public MessagePropertiesBuilder withHeader(String key, Object value) {
        headers.put(key, value);
        return this;
    }

    public MessagePropertiesBuilder withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessagePropertiesBuilder withPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public MessagePropertiesBuilder withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public MessagePropertiesBuilder withContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
        return this;
    }

    public MessagePropertiesBuilder withCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public MessagePropertiesBuilder withReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public MessagePropertiesBuilder withExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    public MessagePropertiesBuilder withTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MessagePropertiesBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public MessagePropertiesBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public MessagePropertiesBuilder withAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public MessagePropertiesBuilder withClusterId(String clusterId) {
        this.clusterId = clusterId;
        return this;
    }

    public AMQP.BasicProperties build() {
        AMQP.BasicProperties.Builder propertiesBuilder = new AMQP.BasicProperties.Builder()
                .headers(headers);

        if (deliveryMode != null) {
            propertiesBuilder = propertiesBuilder.deliveryMode(deliveryMode);
        }
        if (messageId != null) {
            propertiesBuilder = propertiesBuilder.messageId(messageId);
        }
        if (priority != null) {
            propertiesBuilder = propertiesBuilder.priority(priority);
        }
        if (contentType != null) {
            propertiesBuilder = propertiesBuilder.contentType(contentType);
        }
        if (contentEncoding != null) {
            propertiesBuilder = propertiesBuilder.contentEncoding(contentEncoding);
        }
        if (correlationId != null) {
            propertiesBuilder = propertiesBuilder.correlationId(correlationId);
        }
        if (replyTo != null) {
            propertiesBuilder = propertiesBuilder.replyTo(replyTo);
        }
        if (expiration != null) {
            propertiesBuilder = propertiesBuilder.expiration(expiration);
        }
        if (timestamp != null) {
            propertiesBuilder = propertiesBuilder.timestamp(timestamp);
        }
        if (type != null) {
            propertiesBuilder = propertiesBuilder.type(type);
        }
        if (userId != null) {
            propertiesBuilder = propertiesBuilder.userId(userId);
        }
        if (appId != null) {
            propertiesBuilder = propertiesBuilder.appId(appId);
        }
        if (clusterId != null) {
            propertiesBuilder = propertiesBuilder.clusterId(clusterId);
        }

        return propertiesBuilder.build();
    }
}
