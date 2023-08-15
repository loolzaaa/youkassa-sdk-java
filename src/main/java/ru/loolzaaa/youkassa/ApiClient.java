package ru.loolzaaa.youkassa;

import lombok.RequiredArgsConstructor;
import ru.loolzaaa.youkassa.model.Constants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.lang.String.*;

@RequiredArgsConstructor
public class ApiClient {

    private final String accountId;
    private final String secretKey;

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient(System.getenv("YOU_ID"), System.getenv("YOU_KEY"));
        apiClient.getAccountInfo();
    }

    public void getAccountInfo() {
        String credentials = Base64.getUrlEncoder()
                .encodeToString(format("%s:%s", accountId, secretKey).getBytes(StandardCharsets.UTF_8));

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(Constants.API_ENDPOINT + "payments"))
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            System.out.println(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
