package com.example;

import com.example.instant.Customer;
import com.example.instant.CustomerRepository;
import com.example.offset.Account;
import com.example.offset.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
class ApplicationTest {

    @Container
    private static final PostgreSQLContainer<?> DATABASE_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @DynamicPropertySource
    private static void setDatabaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATABASE_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", DATABASE_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DATABASE_CONTAINER::getPassword);
    }

    @Test
    void momentUtc(@Autowired CustomerRepository customerRepository) {
        final var customer = new Customer();
        customer.setName("John Smith");
        customer.setCreatedAt(Instant.parse("2022-10-29T22:52:33.205183929Z"));
        customerRepository.save(customer);

        final var customerSaved = customerRepository.findById(customer.getId())
                .orElseThrow();

        assertNotNull(customerSaved.getId(), "Database should generate ID for new aggregates");
        assertEquals(customer.getName(), customerSaved.getName());
        assertEquals("2022-10-29T22:52:33.205184Z", customerSaved.getCreatedAt().toString());
    }

    @Test
    void momentOffsetUtc(@Autowired AccountRepository accountRepository) {
        final var account = new Account();
        account.setBalance(new BigDecimal("750.00"));
        account.setCreatedAt(OffsetDateTime.parse("2022-10-30T00:03:50.785061364Z"));
        accountRepository.save(account);

        final var accountSaved = accountRepository.findById(account.getId())
                .orElseThrow();

        assertNotNull(accountSaved.getId(), "Database should generate ID for new aggregates");
        assertEquals(account.getBalance(), accountSaved.getBalance());
        assertEquals("2022-10-30T00:03:50.785061Z", accountSaved.getCreatedAt().toString());
    }

    @Test
    void momentOffsetUtcBrazil(@Autowired AccountRepository accountRepository) {
        final var account = new Account();
        account.setBalance(new BigDecimal("750.00"));
        account.setCreatedAt(OffsetDateTime.parse("2022-10-29T21:01:41.372759031-03:00"));
        accountRepository.save(account);

        final var accountSaved = accountRepository.findById(account.getId())
                .orElseThrow();

        assertNotNull(accountSaved.getId(), "Database should generate ID for new aggregates");
        assertEquals(account.getBalance(), accountSaved.getBalance());
        assertEquals("2022-10-30T00:01:41.372759Z", accountSaved.getCreatedAt().toString());
    }
}
