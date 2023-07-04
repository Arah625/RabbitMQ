package RabbitMQ.Methods.HTTP_API;

import RabbitMQ.Methods.Configuration.Configuration;

public class HttpApiPaths extends Configuration {

    public String getOverviewPath() {
        return "/api/overview";
    }

    public String getClusterNamePath() {
        return "/api/cluster-name";
    }

    public String getNodesPath() {
        return "/api/nodes";
    }

    public String getExtensionsPath() {
        return "/api/extensions";
    }

    public String getConnectionsPath() {
        return "/api/connections";
    }

    public String getExchangesPath() {
        return "/api/exchanges";
    }

    public String getQueuesPath() {
        return "/api/queues";
    }

    public String getBindingsPath() {
        return "/api/bindings";
    }

    public String getVirtualHostsPath() {
        return "/api/vhosts";
    }

    public String getCurrentlyAuthenticatedUserPath() {
        return "/api/whoami";
    }

    public String getExchangeBindingsPath(String exchangeName) {
        return "/api/exchanges/" + getVirtualHostEncoded() + "/" + exchangeName + "/bindings/source";
    }

    public String getQueueBindingsPath(String queueName) {
        return "/api/queues/" + getVirtualHostEncoded() + "/" + queueName + "/bindings";
    }

    public String getBindingsBetweenExchangeAndQueuePath(String exchangeName, String queueName) {
        return "/api/bindings/" + getVirtualHostEncoded() + "/e/" + exchangeName + "/q/" + queueName;
    }

    public String deleteExchangePath(String exchangeName) {
        return "/api/exchanges/" + getVirtualHostEncoded() + "/" + exchangeName;
    }

    public String deleteQueuePath(String queueName) {
        return "/api/queues/" + getVirtualHostEncoded() + "/" + queueName;
    }


}
