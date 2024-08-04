package com.devproject.TagDoctor.controller;

import com.devproject.TagDoctor.dto.GeminiRequest;
import com.devproject.TagDoctor.dto.RequestDto;
import com.devproject.TagDoctor.service.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

// 이 컨트롤러는 /gemini 경로에 대한 HTTP 요청을 처리
@RestController // 이 어노테이션이 붙은 클래스는 JSON 또는 XML 형태의 데이터를 반환
@RequiredArgsConstructor // final 로 선언된 필드에 대해 생성자를 자동으로 생성, 생성자 주입 가능
@RequestMapping("/gemini") // 이 컨트롤러의 모든 엔드포인트는 /gemini 로 시작
@Slf4j
public class GeminiController { // Controller 에서는 Get /gemini/chat 으로 요청만 받아옴

    private final GeminiService geminiService;

    @PostMapping("/chat")
    public ResponseEntity<?> handleRequest(@RequestBody RequestDto request) {
        try {
            // 요청 데이터의 내용을 로깅
            log.info("Received request: {}", request);

            // GeminiService를 호출하여 API에 요청을 보내고 응답을 받아옴
            String result = geminiService.getContents(request);

            // 응답을 JSON 형식으로 변환하여 반환
            Map<String, String> response = new HashMap<>();
            response.put("message", result);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException e) {
            // 오류 발생 시 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/test/log")
    public ResponseEntity<Void> logTest(){
        log.error("에러로그 발생");
        log.debug("디버그로그 발생");
        log.info("Info로그 발생");
        log.warn("경고로그 발생");

        return ResponseEntity.ok().build();
    }
}

// 클라이언트가 /gemini/chat 경로로 GET 요청을 보냅니다.
// GeminiController 의 gemini() 메서드가 호출됩니다.
// geminiService 의 getContents("안녕! 너는 누구야?") 메서드를 호출하여 서비스 로직을 실행합니다.
// 서비스 로직이 성공하면, 결과를 포함한 HTTP 200 OK 응답을 반환합니다.
// 예외가 발생하면, 예외 메시지를 포함한 HTTP 400 Bad Request 응답을 반환합니다
