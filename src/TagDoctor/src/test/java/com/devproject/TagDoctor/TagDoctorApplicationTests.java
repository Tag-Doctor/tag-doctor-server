package com.devproject.TagDoctor;

import com.devproject.TagDoctor.dto.GeminiRequest;
import com.devproject.TagDoctor.dto.GeminiResponse;
import com.devproject.TagDoctor.service.GeminiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagDoctorApplicationTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GeminiService geminiService;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @BeforeEach
    public void setUp() {
        // setUp() 메서드를 사용하여 필요한 설정을 수행합니다.
        // 주의: @Value 어노테이션의 값을 설정하는 방법은 다를 수 있습니다.
    }

    @Test
    public void testGetContents() {
        // given: 설정할 데이터
        String prompt = "안녕! 너는 누구야?";

        // Mock 응답 데이터 생성
        GeminiResponse mockResponse = new GeminiResponse();
        // (예시 데이터 설정)
        // mockResponse.setCandidates(Collections.singletonList(new Candidate(new Content(new Part(expectedMessage)))));

        // RestTemplate 의 postForObject 메서드 호출 시 Mock 데이터 반환 설정
        when(restTemplate.postForObject(
                apiUrl + "?key=" + geminiApiKey,
                new GeminiRequest(prompt),
                GeminiResponse.class
        )).thenReturn(mockResponse);
    }
}
