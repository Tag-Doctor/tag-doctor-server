package com.devproject.TagDoctor.controller;

import com.devproject.TagDoctor.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

// 이 컨트롤러는 /gemini 경로에 대한 HTTP 요청을 처리
@RestController // 이 어노테이션이 붙은 클래스는 JSON 또는 XML 형태의 데이터를 반환
@RequiredArgsConstructor // final 로 선언된 필드에 대해 생성자를 자동으로 생성, 생성자 주입 가능
@RequestMapping("/gemini") // 이 컨트롤러의 모든 엔드포인트는 /gemini 로 시작
public class GeminiController { // Controller 에서는 Get /gemini/chat 으로 요청만 받아옴

    private final GeminiService geminiService;

    // HTTP GET 요청이 /gemini/chat 경로로 들어오면 gemini() 메서드가 호출
    @GetMapping("/chat")
    public ResponseEntity<?> gemini() { // 이 메서드는 ResponseEntity 객체를 반환
        try {
            // 이 호출이 성공하면, ResponseEntity.ok().body(...)를 사용하여 HTTP 200 OK 응답과 함께 서비스에서 반환된 결과를 응답 본문으로 보냄
            return ResponseEntity.ok().body(geminiService.getContents("안녕! 너는 누구야?"));
        } catch (HttpClientErrorException e) {
            // 만약 HttpClientErrorException 예외가 발생하면, ResponseEntity.badRequest().body(e.getMessage())를 사용하여 HTTP 400 Bad Request 응답과 함께 예외 메시지를 응답 본문으로 보냄
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

// 클라이언트가 /gemini/chat 경로로 GET 요청을 보냅니다.
// GeminiController 의 gemini() 메서드가 호출됩니다.
// geminiService 의 getContents("안녕! 너는 누구야?") 메서드를 호출하여 서비스 로직을 실행합니다.
// 서비스 로직이 성공하면, 결과를 포함한 HTTP 200 OK 응답을 반환합니다.
// 예외가 발생하면, 예외 메시지를 포함한 HTTP 400 Bad Request 응답을 반환합니다
