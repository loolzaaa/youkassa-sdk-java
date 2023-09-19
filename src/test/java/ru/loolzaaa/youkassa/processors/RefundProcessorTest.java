package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.Refund;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.Currency;
import ru.loolzaaa.youkassa.pojo.list.RefundList;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RefundProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    RefundProcessor refundProcessor;

    @BeforeEach
    void setUp() {
        refundProcessor = new RefundProcessor(client);
    }

    @Test
    void executeFindById() {
        refundProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/refunds/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> refundProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> refundProcessor.findById(""));
    }

    @Test
    void executeFindAll() {
        RefundList refundList = RefundList.builder().createdAtGt("2023-01-01T10:10:10.100Z").limit(10).build();
        refundProcessor.findAll(refundList);

        assertEquals("GET", client.method);
        assertEquals("/refunds?limit=10&created_at.gt=2023-01-01T10:10:10.100Z", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void executeCreate() {
        Refund refundParams = Refund.builder()
                .paymentId("1111")
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .build();
        refundProcessor.create(refundParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/refunds", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(refundParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        Refund refundParams = Refund.builder()
                .paymentId("1111")
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .build();
        refundProcessor.create(refundParams, null);

        assertEquals("POST", client.method);
        assertEquals("/refunds", client.path);
        assertEquals(refundParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> refundProcessor.create(null, null));
    }
}