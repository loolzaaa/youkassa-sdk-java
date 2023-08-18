package ru.loolzaaa.youkassa.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaginatedRequestTest {

    PaginatedRequest underTest;

    @Test
    void shouldCorrectConvertParams() {
        underTest = new FakePaginatedRequest(10, null, null);
        String queryString = underTest.toQueryString();
        assertEquals("?limit=10", queryString);

        underTest = new FakePaginatedRequest(10, "1234", null);
        queryString = underTest.toQueryString();
        assertEquals("?limit=10&cursor=1234", queryString);

        underTest = new FakePaginatedRequest(10, "1234", "test");
        queryString = underTest.toQueryString();
        assertEquals("?limit=10&cursor=1234&testParam=test", queryString);

        underTest = new FakePaginatedRequest(null, "1234", "test");
        queryString = underTest.toQueryString();
        assertEquals("?cursor=1234&testParam=test", queryString);

        underTest = new FakePaginatedRequest(null, null, "test");
        queryString = underTest.toQueryString();
        assertEquals("?testParam=test", queryString);

        underTest = new FakePaginatedRequest(20, null, "test");
        queryString = underTest.toQueryString();
        assertEquals("?limit=20&testParam=test", queryString);

        underTest = new FakePaginatedRequest(null, null, null);
        queryString = underTest.toQueryString();
        assertEquals("", queryString);
    }

    static class FakePaginatedRequest extends PaginatedRequest {

        private final String testParam;

        public FakePaginatedRequest(Integer limit, String cursor, String testParam) {
            super(limit, cursor);
            this.testParam = testParam;
        }

        @Override
        public String getQuery() {
            return testParam != null ? "testParam=" + testParam : "";
        }
    }
}