package com.devproject.TagDoctor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Gemini API 에 요청을 보내기 위해 RestTemplate 를 설정하는 클래스
// 애플리케이션 내에서 "geminiRestTemplate" 빈을 주입받아 사용할 수 있게 됨
@Configuration // @Bean 메서드를 포함할 수 있으며, 애플리케이션 컨텍스트에 빈을 정의하고 제공
@RequiredArgsConstructor // final 필드나 @NonNull 이 붙은 필드에 대해 생성자를 자동으로 생성
public class GeminiRestTemplateConfig {

    @Bean // 해당 메서드가 반환하는 객체가 Spring 컨텍스트에 의해 빈으로 등록
    @Qualifier("geminiRestTemplate") // geminiRestTemplate"이라는 이름으로 RestTemplate 빈을 등록하고, 필요할 때 이 이름을 사용하여 해당 빈을 주입받을 수 있음
    public RestTemplate geminiRestTemplate() { // RestTemplate 빈을 생성하여 반환
        RestTemplate restTemplate = new RestTemplate(); // RestTemplate 은 Spring 에서 제공하는 HTTP 통신을 위한 템플릿 클래스
        restTemplate.getInterceptors().add(((request, body, execution) -> execution.execute(request, body))); // RestTemplate 에 인터셉터를 추가하는 코드입니다. 인터셉터는 HTTP 요청을 가로채서 추가적인 로직을 실행할 수 있도록 함
        return restTemplate;
    }
}
