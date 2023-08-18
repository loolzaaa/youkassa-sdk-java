package ru.loolzaaa.youkassa.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(httpPort = 8080)
class ApiClientTest {

    ApiClient client;

    @BeforeEach
    void setUp() {
        client = new ApiClient("http://localhost:8080/api", () -> "Basic dXNlcjpwYXNzd29yZA==");
    }

    @Test
    void shouldThrowExceptionWhileCreation() {
        assertThrows(AssertionError.class, () -> new ApiClient(null, null));
        assertThrows(AssertionError.class, () -> new ApiClient("test", null));
        assertThrows(AssertionError.class, () -> new ApiClient(null, () -> "test"));
    }

    @Test
    void shouldSentCorrectHeadersWhenOneEntity() {
        stubFor(get("/api/test")
                .withHeader("Content-Type", containing("application/json"))
                .withHeader("Test-Header", containing("testValue"))
                .withBasicAuth("user", "password")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{}")));
        Map<String, String> headers = Map.of("Test-Header", "testValue");

        JsonNode answer = client.sendRequest("GET", "/test", headers, null, JsonNode.class);

        assertEquals("{}", answer.toString());
    }

    @Test
    void shouldSentCorrectHeadersWhenListOfEntities() {
        stubFor(get("/api/test")
                .withHeader("Content-Type", containing("application/json"))
                .withHeader("Test-Header", containing("testValue"))
                .withBasicAuth("user", "password")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"type\":\"list\",\"items\":[\"test1\",\"test2\"],\"next_cursor\":\"1111\"}")));
        Map<String, String> headers = Map.of("Test-Header", "testValue");

        PaginatedResponse<String> answer = client.sendRequest("GET", "/test", headers, null, new TypeReference<>() {});

        assertEquals("list", answer.getType());
        assertEquals(2, answer.getItems().size());
        assertEquals(List.of("test1", "test2"), answer.getItems());
        assertEquals("1111", answer.getNextCursor());
    }

    @Test
    void shouldSentCorrectHeadersAndBodyWhenOneEntity() {
        stubFor(get("/api/test")
                .withHeader("Content-Type", containing("application/json"))
                .withBasicAuth("user", "password")
                .withRequestBody(equalTo("{\"test\":true}"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{}")));

        assertDoesNotThrow(() -> client.sendRequest("GET", "/test", null, new TestBody(true), JsonNode.class));
    }

    @Test
    void shouldThrowExceptionIfStatusNot200() {
        stubFor(get("/api/test")
                .withHeader("Content-Type", containing("application/json"))
                .withBasicAuth("user", "password")
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("{\"type\":\"error\"}")));

        assertThrows(RuntimeException.class, () -> client.sendRequest("GET", "/test", null, null, JsonNode.class));
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestBody implements RequestBody {
        private boolean test;
    }
}