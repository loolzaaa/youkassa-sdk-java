package ru.loolzaaa.youkassa.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.loolzaaa.youkassa.model.Receipt;
import ru.loolzaaa.youkassa.pojo.*;
import ru.loolzaaa.youkassa.pojo.list.ReceiptList;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptProcessorTest {

    FakeApiClient client = new FakeApiClient("", () -> "");

    ReceiptProcessor receiptProcessor;

    @BeforeEach
    void setUp() {
        receiptProcessor = new ReceiptProcessor(client);
    }

    @Test
    void executeFindById() {
        receiptProcessor.findById("1234");

        assertEquals("GET", client.method);
        assertEquals("/receipts/1234", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void throwsFindById() {
        assertThrows(IllegalArgumentException.class, () -> receiptProcessor.findById(null));
        assertThrows(IllegalArgumentException.class, () -> receiptProcessor.findById(""));
    }

    @Test
    void executeFindAll() {
        ReceiptList receiptList = ReceiptList.builder().createdAtGt("2023-01-01T10:10:10.100Z").limit(10).build();
        receiptProcessor.findAll(receiptList);

        assertEquals("GET", client.method);
        assertEquals("/receipts?limit=10&created_at.gt=2023-01-01T10:10:10.100Z", client.path);
        assertNull(client.headers);
        assertNull(client.body);
    }

    @Test
    void executeCreate() {
        Receipt receiptParams = Receipt.builder()
                .type("test")
                .customer(Customer.builder().build())
                .items(List.of(Item.builder()
                        .description("test")
                        .quantity("1")
                        .vatCode(1)
                        .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                        .build()))
                .send(true)
                .settlements(List.of(Settlement.builder()
                        .type("test")
                        .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                        .build()))
                .build();
        receiptProcessor.create(receiptParams, "1234");

        assertEquals("POST", client.method);
        assertEquals("/receipts", client.path);
        assertEquals(Map.of("Idempotence-Key", "1234"), client.headers);
        assertEquals(receiptParams, client.body);
    }

    @Test
    void executeCreateWithRandomIdempotenceKey() {
        Receipt receiptParams = Receipt.builder()
                .type("test")
                .customer(Customer.builder().build())
                .items(List.of(Item.builder()
                        .description("test")
                        .quantity("1")
                        .vatCode(1)
                        .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                        .build()))
                .send(true)
                .settlements(List.of(Settlement.builder()
                        .type("test")
                        .amount(Amount.builder().value("100.00").currency(Currency.RUB).build())
                        .build()))
                .build();
        receiptProcessor.create(receiptParams, null);

        assertEquals("POST", client.method);
        assertEquals("/receipts", client.path);
        assertEquals(receiptParams, client.body);
    }

    @Test
    void throwsCreate() {
        assertThrows(IllegalArgumentException.class, () -> receiptProcessor.create(null, null));
    }
}