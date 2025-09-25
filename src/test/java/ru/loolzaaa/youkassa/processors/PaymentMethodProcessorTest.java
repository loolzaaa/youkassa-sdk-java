package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.PaymentMethod;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    PaymentMethodProcessor paymentMethodProcessor;

    @BeforeEach
    void setUp() {
        paymentMethodProcessor = new PaymentMethodProcessor(client);
    }

    @Test
    void executeFindById() {
        paymentMethodProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/payment_methods/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> paymentMethodProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> paymentMethodProcessor.findById(""));
    }

    @Test
    void executeCreate() {
        PaymentMethod paymentMethodParams = PaymentMethod.builder().type("test").build();
        paymentMethodProcessor.create(paymentMethodParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/payment_methods", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(paymentMethodParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        PaymentMethod paymentMethodParams = PaymentMethod.builder().type("test").build();
        paymentMethodProcessor.create(paymentMethodParams, null);

        assertEquals("POST", client.method);
        assertEquals("/payment_methods", client.path);
        assertEquals(paymentMethodParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> paymentMethodProcessor.create(null, null));
    }
}