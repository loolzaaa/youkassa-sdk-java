package ru.loolzaaa.youkassa.helper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiHelperTest {

    @Test
    void shouldCorrectCheckField() {
        TestClass testClass = new TestClass(TestClass.Type.TEST_TYPE);

        ApiHelper.checkObjectType(testClass, "type", String.class, "Type");
    }

    @Test
    void shouldThrowExceptionWhenCheckField() {
        TestClass testClass = new TestClass("error");

        assertThrows(IllegalArgumentException.class, () -> ApiHelper.checkObjectType(testClass, "type", String.class, "Type"));
    }

    @AllArgsConstructor
    public static class TestClass {
        String type;

        public static class Type {
            public static final String TEST_TYPE = "test";
        }
    }
}