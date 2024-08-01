package com.devproject.TagDoctor.service;

import com.devproject.TagDoctor.dto.GeminiRequest;
import com.devproject.TagDoctor.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

// 이 클래스는 Gemini API 에 요청을 보내고 응답을 처리하는 기능을 담당
@Service
@RequiredArgsConstructor
public class GeminiService {
    @Qualifier("geminiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String getContents(String prompt) {

        // Gemini 에 요청 전송, apiUrl 과 geminiApiKey 를 결합하여 요청 URL 을 생성
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        try {
            GeminiRequest request = new GeminiRequest(prompt); // 요청 객체 생성
            GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class); // API 호출, RestTemplate 의 postForObject 메서드를 사용하여 POST 요청을 보냄

            // GeminiResponse 객체에서 응답 데이터를 추출, 응답에서 첫 번째 후보(Candidate)의 내용(Content)을 가져오고, 그 안의 첫 번째 파트(Parts)의 텍스트(Text)를 추출
            String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString(); // 응답 처리

            return message; // 추출한 메시지를 반환
        } catch (HttpClientErrorException e) {
            // Log error details
            System.err.println("Error response: " + e.getResponseBodyAsString());
            return "Error occurred: " + e.getMessage();
        }
    }
}

// getContents 메서드가 호출되면, Gemini API 에 요청을 보냅니다.
// 요청 URL 은 apiUrl 과 geminiApiKey 를 기반으로 생성됩니다.
// 요청 본문으로는 GeminiRequest 객체가 사용됩니다.
// RestTemplate 을 통해 POST 요청이 전송되고, GeminiResponse 로 응답이 반환됩니다.
// 응답에서 필요한 데이터를 추출하여 결과를 반환합니다.
