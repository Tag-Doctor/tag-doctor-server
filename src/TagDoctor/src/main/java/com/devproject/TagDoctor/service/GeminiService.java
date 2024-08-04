package com.devproject.TagDoctor.service;

import com.devproject.TagDoctor.dto.GeminiRequest;
import com.devproject.TagDoctor.dto.GeminiResponse;
import com.devproject.TagDoctor.dto.RequestDto;
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

    public String getContents(RequestDto requestDto) {
        // Gemini API에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        try {
            // RequestDto를 GeminiRequest로 변환
            GeminiRequest geminiRequest = convertToGeminiRequest(requestDto);

            // API 호출, RestTemplate의 postForObject 메서드를 사용하여 POST 요청을 보냄
            GeminiResponse response = restTemplate.postForObject(requestUrl, geminiRequest, GeminiResponse.class);

            // GeminiResponse 객체에서 응답 데이터 추출
            if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
                // 첫 번째 후보의 콘텐츠에서 텍스트 추출
                String message = response.getCandidates().get(0).getContent().getParts().get(0).getText();
                return message;
            } else {
                return "No response from API.";
            }
        } catch (HttpClientErrorException e) {
            // 오류 발생 시 처리
            System.err.println("Error response: " + e.getResponseBodyAsString());
            return "Error occurred: " + e.getMessage();
        }
    }

    // RequestDto를 GeminiRequest로 변환하는 메서드
    private GeminiRequest convertToGeminiRequest(RequestDto requestDto) {
        // GeminiRequest 객체를 생성하고 RequestDto에서 데이터를 설정
        String prompt = String.format(
                "Health Information: 나이는 %s살이고, 신체 부위는 %s 중에 %s에서 %s가 아파요. 추가 정보: %s",
                requestDto.getSelectedAge(),
                requestDto.getSelectedPart(),
                requestDto.getSelectedSubPart(),
                requestDto.getSelectedDetail(),
                requestDto.getAdditionalInfo()
        );

        // GeminiRequest 객체 생성
        return new GeminiRequest(prompt);
    }
}

// getContents 메서드가 호출되면, Gemini API 에 요청을 보냅니다.
// 요청 URL 은 apiUrl 과 geminiApiKey 를 기반으로 생성됩니다.
// 요청 본문으로는 GeminiRequest 객체가 사용됩니다.
// RestTemplate 을 통해 POST 요청이 전송되고, GeminiResponse 로 응답이 반환됩니다.
// 응답에서 필요한 데이터를 추출하여 결과를 반환합니다.
