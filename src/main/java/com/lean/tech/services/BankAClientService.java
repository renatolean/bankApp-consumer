package com.lean.tech.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAClientService {

    @Qualifier("bankA-service")
    private final WebClient webClient;

    private Mono<AccountDetails> getAccount(UUID accountId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/banks/BankA/accounts/{account_id}")
                        .build(accountId))
                .retrieve()
                .bodyToMono(AccountDetails.class)
                .doOnError(error -> log.error("Exception occurred when retrieving account details"));
    }

    record AccountDetails(UUID id, String iban, String accountNumber) {

    }
}
