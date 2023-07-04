package RabbitMQ.Methods.HTTP_API;

import RabbitMQ.Methods.Configuration.Configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpApiMethods extends Configuration {


    public String getResponse(String apiPath) {
        String url = getBaseUrl() + apiPath + getVirtualHost();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", getBasicAuthHeader())
                .build();

        String responseBody = null;
        try (Response response = client.newCall(request).execute()) {
            int statusCode = response.code();
            responseBody = response.body().string();

            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + formatJson(responseBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    public String requestDelete(String apiPath) {
        String url = getBaseUrl() + apiPath + getVirtualHost();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", getBasicAuthHeader())
                .delete()
                .build();

        String responseBody = null;
        try (Response response = client.newCall(request).execute()) {
            int statusCode = response.code();
            responseBody = response.body().string();

            System.out.println("Status Code: " + statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

//    public String getResponse(String apiPath1, String apiPath2) {
//        String url = getBaseUrl() + apiPath1 + getVirtualHost() + "/" + ;
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .header("Authorization", getBasicAuthHeader())
//                .build();
//
//        String responseBody = null;
//        try (Response response = client.newCall(request).execute()) {
//            int statusCode = response.code();
//            responseBody = response.body().string();
//
//            System.out.println("Status Code: " + statusCode);
//            System.out.println("Response Body: " + formatJson(responseBody));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return responseBody;
//    }


    private String formatJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object jsonNode = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

}
