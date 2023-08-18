package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.Deal;
import ru.loolzaaa.youkassa.pojo.list.DealList;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DealProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    DealProcessor dealProcessor;

    @BeforeEach
    void setUp() {
        dealProcessor = new DealProcessor(client);
    }

    @Test
    void executeFindById() {
        dealProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/deals/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> dealProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> dealProcessor.findById(""));
    }

    @Test
    void executeFindAll() {
        DealList dealList = DealList.builder().createdAtGt("2023-01-01T10:10:10.100Z").limit(10).build();
        dealProcessor.findAll(dealList);

        assertEquals("GET", client.method);
        assertEquals("/deals?limit=10&created_at.gt=2023-01-01T10:10:10.100Z", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void executeCreate() {
        Deal dealParams = Deal.builder().type("test").feeMoment("test").build();
        dealProcessor.create(dealParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/deals", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(dealParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        Deal dealParams = Deal.builder().type("test").feeMoment("test").build();
        dealProcessor.create(dealParams, null);

        assertEquals("POST", client.method);
        assertEquals("/deals", client.path);
        assertEquals(dealParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> dealProcessor.create(null, null));
    }
}