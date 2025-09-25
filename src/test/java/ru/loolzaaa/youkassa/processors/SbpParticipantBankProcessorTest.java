package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SbpParticipantBankProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    SbpParticipantBankProcessor sbpParticipantBankProcessor;

    @BeforeEach
    void setUp() {
        sbpParticipantBankProcessor = new SbpParticipantBankProcessor(client);
    }

    @Test
    void executeFindSbpParticipants() {
        sbpParticipantBankProcessor.findSbpParticipants();

        assertEquals("GET", client.method);
        assertEquals("/sbp_banks", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }
}