package com.example.openfeign.demo.configuration;

import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "naver.papago")
public class OpenFeignConfig {

    private String clientId;
    private String clientSecret;

    /**
     * API 접속을 위해 접속 방법은 기본값으로 설정함(반드시 설정되어야 함)
     * @return
     */
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", clientId);
            requestTemplate.header("X-Naver-Client-Secret", clientSecret);
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {

        /**
         * OpenFeign 통해 전송 및 전달받는 모든 과정에 대해 로그찍기 설정
         *
         * NONE: 로깅하지 않음 (기본값)
         * BASIC: 요청 메소드와 URI 와 응답 상태와 실행시간 로깅함
         * HEADERS: 요청과 응답 헤더와 함께 기본 정보들을 남김
         * FULL: 요청과 응답에 대한 헤더와 바디, 메타 데이터를 남김
         */
        return Logger.Level.FULL;
    }
}
