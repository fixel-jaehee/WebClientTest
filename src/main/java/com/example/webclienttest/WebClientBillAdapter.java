package com.example.webclienttest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientBillAdapter {

    private final WebClient webClient;

    public CreateCodeResponse createBillingCode(Long hotelId){

        URI uri = UriComponentsBuilder.fromPath("/posts")
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .port(443)
                .build(false)
                .encode()
                .toUri();

        CreateCodeRequest createCodeRequest = new CreateCodeRequest("foo", "bar", hotelId.intValue());

        ParameterizedTypeReference<CreateCodeResponse> TYPE_REFERENCE = new ParameterizedTypeReference<>() {};

        return webClient.mutate()
                .build()
                .method(HttpMethod.POST)
                .uri(uri)
                .bodyValue(createCodeRequest)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("error")))
                .bodyToMono(TYPE_REFERENCE)
                .flux().toStream().findFirst()
                .orElseThrow(()->new RuntimeException("Empty Response"));
    }
}
