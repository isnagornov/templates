package ru.isnagornov.templates.test.util;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestPostgresqlContainer extends PostgreSQLContainer<TestPostgresqlContainer> {

    private TestPostgresqlContainer() {
    }

    public static TestPostgresqlContainer getInstance() {
        return Holder.TEST_POSTGRESQL_CONTAINER;
    }

    private static class Holder {
        private static final TestPostgresqlContainer TEST_POSTGRESQL_CONTAINER = new TestPostgresqlContainer();
    }
}
