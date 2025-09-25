package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.SelfEmployed;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SelfEmployedProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    SelfEmployedProcessor selfEmployedProcessor;

    @BeforeEach
    void setUp() {
        selfEmployedProcessor = new SelfEmployedProcessor(client);
    }

    @Test
    void executeFindById() {
        selfEmployedProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/self-employed/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> selfEmployedProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> selfEmployedProcessor.findById(""));
    }

    @Test
    void executeCreate() {
        SelfEmployed selfEmployedParams = SelfEmployed.builder()
                .phone("1234")
                .build();
        selfEmployedProcessor.create(selfEmployedParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/self-employed", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(selfEmployedParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        SelfEmployed selfEmployedParams = SelfEmployed.builder()
                .phone("1234")
                .build();
        selfEmployedProcessor.create(selfEmployedParams, null);

        assertEquals("POST", client.method);
        assertEquals("/self-employed", client.path);
        assertEquals(selfEmployedParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> selfEmployedProcessor.create(null, null));
    }
}