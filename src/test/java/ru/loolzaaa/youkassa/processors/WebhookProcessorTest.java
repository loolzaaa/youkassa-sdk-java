package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.Webhook;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebhookProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    WebhookProcessor webhookProcessor;

    @BeforeEach
    void setUp() {
        webhookProcessor = new WebhookProcessor(client);
    }

    @Test
    void executeFindAll() {
        webhookProcessor.findAll();

        assertEquals("GET", client.method);
        assertEquals("/webhooks", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void executeCreate() {
        Webhook webhookParams = Webhook.builder().event("test").url("test").build();
        webhookProcessor.create(webhookParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/webhooks", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(webhookParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        Webhook webhookParams = Webhook.builder().event("test").url("test").build();
        webhookProcessor.create(webhookParams, null);

        assertEquals("POST", client.method);
        assertEquals("/webhooks", client.path);
        assertEquals(webhookParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> webhookProcessor.create(null, null));
    }

    @Test
    void executeRemoveById() {
        webhookProcessor.removeById("1111", "1234");

        assertEquals("DELETE", client.method);
        assertEquals("/webhooks/1111", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertNull(client.body);
    }

    @Test
    void executeRemoveByIdWithRandomIdempotenceKey() {
        webhookProcessor.removeById("1111", null);

        assertEquals("DELETE", client.method);
        assertEquals("/webhooks/1111", client.path);
        assertNull(client.body);
    }

    @Test
    void throwsRemoveById() {
        assertThrows(IllegalArgumentException.class, () -> webhookProcessor.removeById(null, null));
        assertThrows(IllegalArgumentException.class, () -> webhookProcessor.removeById("", null));
    }
}