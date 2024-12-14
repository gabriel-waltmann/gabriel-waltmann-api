import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class APIHandler {
    class Response<T> {
        T data;

        public Response(T data) {
            this.data = data;
        }
    }

    public <T> Response<T> GET(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .build();

            String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            ObjectMapper objectMapper = new ObjectMapper();

            T data = objectMapper.readValue(response, (Class<T>) User[].class);

            return new Response<>(data);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
