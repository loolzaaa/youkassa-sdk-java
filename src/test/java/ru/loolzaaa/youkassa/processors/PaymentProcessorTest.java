package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.Payment;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.Currency;
import ru.loolzaaa.youkassa.pojo.list.PaymentList;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    PaymentProcessor paymentProcessor;

    @BeforeEach
    void setUp() {
        paymentProcessor = new PaymentProcessor(client);
    }

    @Test
    void executeFindById() {
        paymentProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/payments/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.findById(""));
    }

    @Test
    void executeFindAll() {
        PaymentList paymentList = PaymentList.builder().createdAtGt("2023-01-01T10:10:10.100Z").limit(10).build();
        paymentProcessor.findAll(paymentList);

        assertEquals("GET", client.method);
        assertEquals("/payments?limit=10&created_at.gt=2023-01-01T10:10:10.100Z", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void executeCreate() {
        Payment paymentParams = Payment.builder()
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .build();
        paymentProcessor.create(paymentParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/payments", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(paymentParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        Payment paymentParams = Payment.builder()
                .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                .build();
        paymentProcessor.create(paymentParams, null);

        assertEquals("POST", client.method);
        assertEquals("/payments", client.path);
        assertEquals(paymentParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.create(null, null));
    }

    @Test
    void executeCapture() {
        Payment paymentParams = Payment.builder().build();
        paymentProcessor.capture("1111", paymentParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/payments/1111/capture", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(paymentParams, client.body);
    }

    @Test
    void executeCaptureWithRandomIdempotenceKey() {
        Payment paymentParams = Payment.builder().build();
        paymentProcessor.capture("1111", paymentParams, null);

        assertEquals("POST", client.method);
        assertEquals("/payments/1111/capture", client.path);
        assertEquals(paymentParams, client.body);
    }

    @Test
    void throwsCapture() {
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.capture(null, null, null));
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.capture("", null, null));
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.capture("test", null, null));
    }

    @Test
    void executeCancel() {
        paymentProcessor.cancel("1234", "1111");

        assertEquals("POST", client.method);
        assertEquals("/payments/1234/cancel", client.path);
        assertEquals(Map.of("Idempotence-Key", "1111"), client.headers);
        assertNull(client.body);
    }

    @Test
    void executeCancelWithRandomIdempotenceKey() {
        paymentProcessor.cancel("1234", null);

        assertEquals("POST", client.method);
        assertEquals("/payments/1234/cancel", client.path);
        assertNull(client.body);
    }

    @Test
    void throwsCancel() {
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.cancel(null, null));
        assertThrows(IllegalArgumentException.class, () -> paymentProcessor.cancel("", null));
    }
}