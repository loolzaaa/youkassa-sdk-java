package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.PersonalData;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersonalDataProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    PersonalDataProcessor personalDataProcessor;

    @BeforeEach
    void setUp() {
        personalDataProcessor = new PersonalDataProcessor(client);
    }

    @Test
    void executeFindById() {
        personalDataProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/personal_data/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> personalDataProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> personalDataProcessor.findById(""));
    }

    @Test
    void executeCreate() {
        PersonalData personalDataParams = PersonalData.builder()
                .type(PersonalData.Type.PAYOUT_STATEMENT_RECIPIENT)
                .lastName("test")
                .firstName("test")
                .build();
        personalDataProcessor.create(personalDataParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/personal_data", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(personalDataParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        PersonalData personalDataParams = PersonalData.builder()
                .type(PersonalData.Type.PAYOUT_STATEMENT_RECIPIENT)
                .lastName("test")
                .firstName("test")
                .build();
        personalDataProcessor.create(personalDataParams, null);

        assertEquals("POST", client.method);
        assertEquals("/personal_data", client.path);
        assertEquals(personalDataParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> personalDataProcessor.create(null, null));
    }
}