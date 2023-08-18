package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    MeProcessor meProcessor;

    @BeforeEach
    void setUp() {
        meProcessor = new MeProcessor(client);
    }

    @Test
    void executeFindMe() {
        meProcessor.findMe();

        assertEquals("GET", client.method);
        assertEquals("/me", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }
}