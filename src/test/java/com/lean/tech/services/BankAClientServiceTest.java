package com.lean.tech.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.test.StepVerifier;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureStubRunner(ids = "com.lean.tech:bankA-producer:+:stubs:8090", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BankAClientServiceTest {

    private static final UUID KNOWN_ACCOUNT_ID = UUID.fromString("111c6065-a3e9-30bc-be6b-2e59da52bf2a");

    @Autowired
    private BankAClientService underTest;

    @DynamicPropertySource
    static void setBankAUrl(DynamicPropertyRegistry registry) {
        registry.add("bankA-service.url", () -> "http://localhost:8090");
    }

    @Test
    void getAccount_whenFound_returnSuccess() {
        StepVerifier.create(underTest.getAccount(KNOWN_ACCOUNT_ID))
                .assertNext(accountDetails -> assertThat(accountDetails)
                        .hasNoNullFieldsOrProperties()
                        .returns(KNOWN_ACCOUNT_ID, BankAClientService.AccountDetails::id)
                        .extracting("iban", "name")
                        .allSatisfy(s -> assertThat(s).asString().isNotBlank())
                )
                .verifyComplete();
    }

    @Test
    void getAccount_whenNotFound_returnEmpty() {
        StepVerifier.create(underTest.getAccount(UUID.randomUUID()))
                .verifyComplete();
    }

}
