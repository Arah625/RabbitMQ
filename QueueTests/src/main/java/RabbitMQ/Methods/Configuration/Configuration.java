package RabbitMQ.Methods.Configuration;

import java.util.Base64;

public class Configuration {

    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    private static final String VIRTUAL_HOST = "/";
    private static final String VIRTUAL_HOST_ENCODED = "%2F";
    private static final String HOST = "localhost";
    private static final String PORT = "15672";
    private static final String BASE_URL = "http://" + HOST + ":" + PORT;


    protected static String getBaseUrl() {
        return BASE_URL;
    }

    protected static String getVirtualHost() {
        return VIRTUAL_HOST;
    }

    protected static String getVirtualHostEncoded() {
        return VIRTUAL_HOST_ENCODED;
    }

    protected static String getBasicAuthHeader() {
        String credentials = USERNAME + ":" + PASSWORD;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encodedCredentials;
    }


}