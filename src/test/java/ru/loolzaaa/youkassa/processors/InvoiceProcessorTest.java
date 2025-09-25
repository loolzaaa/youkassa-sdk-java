package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.Invoice;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.Cart;
import ru.loolzaaa.youkassa.pojo.Currency;
import ru.loolzaaa.youkassa.pojo.PaymentData;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    InvoiceProcessor invoiceProcessor;

    @BeforeEach
    void setUp() {
        invoiceProcessor = new InvoiceProcessor(client);
    }

    @Test
    void executeFindById() {
        invoiceProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/invoices/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> invoiceProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> invoiceProcessor.findById(""));
    }

    @Test
    void executeCreate() {
        Invoice invoiceParams = Invoice.builder()
                .paymentData(PaymentData.builder()
                        .amount(Amount.builder()
                                .value("100.00")
                                .currency(Currency.RUB)
                                .build())
                        .build())
                .cart(List.of(Cart.builder()
                        .description("test")
                        .price(Amount.builder()
                                .value("100.00")
                                .currency(Currency.RUB)
                                .build())
                        .quantity("1.0")
                        .build()))
                .build();
        invoiceProcessor.create(invoiceParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/invoices", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(invoiceParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        Invoice invoiceParams = Invoice.builder()
                .paymentData(PaymentData.builder()
                        .amount(Amount.builder()
                                .value("100.00")
                                .currency(Currency.RUB)
                                .build())
                        .build())
                .cart(List.of(Cart.builder()
                        .description("test")
                        .price(Amount.builder()
                                .value("100.00")
                                .currency(Currency.RUB)
                                .build())
                        .quantity("1.0")
                        .build()))
                .build();
        invoiceProcessor.create(invoiceParams, null);

        assertEquals("POST", client.method);
        assertEquals("/invoices", client.path);
        assertEquals(invoiceParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> invoiceProcessor.create(null, null));
    }
}