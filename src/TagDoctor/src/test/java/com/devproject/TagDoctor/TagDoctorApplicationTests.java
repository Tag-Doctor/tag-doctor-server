package com.devproject.TagDoctor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.devproject.TagDoctor.dto.GeminiRequest;
import com.devproject.TagDoctor.dto.GeminiResponse;
import com.devproject.TagDoctor.dto.RequestDto;
import com.devproject.TagDoctor.service.GeminiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

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
        String expectedMessage = "응답 메시지";

        // Mock 응답 데이터 생성
        GeminiResponse mockResponse = new GeminiResponse();
        GeminiResponse.Candidate candidate = new GeminiResponse.Candidate();
        GeminiResponse.Content content = new GeminiResponse.Content();
        GeminiResponse.Parts part = new GeminiResponse.Parts();
        part.setText(expectedMessage);
        content.setParts(Collections.singletonList(part));
        candidate.setContent(content);
        mockResponse.setCandidates(Collections.singletonList(candidate));

        // RestTemplate의 postForObject 메서드 호출 시 Mock 데이터 반환 설정
        when(restTemplate.postForObject(
                eq(apiUrl + "?key=" + geminiApiKey),
                any(GeminiRequest.class),
                eq(GeminiResponse.class)
        )).thenReturn(mockResponse);

        // when: 서비스 메서드 호출
        String actualMessage = geminiService.getContents(new RequestDto());

        // then: 반환된 값 검증
        assertEquals(expectedMessage, actualMessage);
    }
}
