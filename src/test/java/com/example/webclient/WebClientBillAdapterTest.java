package com.example.webclient;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
class WebClientBillAdapterTest {

    @Autowired
    WebClientBillAdapter webClientBillAdapter;


    @Test
    void webClientTest(){

        CreateCodeResponse billingCode = webClientBillAdapter.createBillingCode(1L);

        assertThat(billingCode).isNotNull();
        log.info("billingCode : {}", billingCode);
        assertThat(billingCode).extracting("title").isEqualTo("foo");



    }



}