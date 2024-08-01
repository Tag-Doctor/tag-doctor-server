package com.devproject.TagDoctor.dto;

import lombok.*;

import java.util.List;

// REST API 호출시 사용되는 데이터의 출력 클래스
// 서비스와 컨트롤러 사이에서 데이터를 주고받을 때 사용
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponse {

    private List<Candidate> candidates; // Candidate 객체의 리스트를 저장하는 필드, 여러 개의 후보 결과를 저장
    private PromptFeedback promptFeedback; // 프롬프트에 대한 피드백을 저장하는 필드

    @Getter
    @Setter
    public static class Candidate {
        private Content content; // Content 객체를 저장하는 필드, 후보의 콘텐츠 정보를 나타냄
        private String finishReason; // 후보의 생성 종료 이유를 저장하는 필드
        private int index; // 후보의 인덱스를 저장하는 필드
        private List<SafetyRating> safetyRatings; // SafetyRating 객체의 리스트를 저장하는 필드, 후보에 대한 안전 등급 정보를 나타냄

    }

    @Getter @Setter
    @ToString
    public static class Content {
        private List<Parts> parts; // Parts 객체의 리스트를 저장하는 필드, 콘텐츠의 구성 요소를 나타냄
        private String role; // 콘텐츠의 역할을 저장하는 필드

    }

    @Getter @Setter
    @ToString
    public static class Parts {
        private String text; // 텍스트 내용을 저장하는 필드

    }

    @Getter @Setter
    public static class SafetyRating {
        private String category; // 안전 등급의 카테고리를 저장하는 필드
        private String probability; // 안전 등급의 확률을 저장하는 필드
    }

    @Getter @Setter
    public static class PromptFeedback {
        // SafetyRating 객체의 리스트를 저장하는 필드, 프롬프트에 대한 안전 등급 정보를 나타냄
        private List<SafetyRating> safetyRatings;

    }
}

// 이 클래스는 REST API 의 응답 데이터를 구조화하여 쉽게 주고받을 수 있도록 하는 역할
// 예를 들어, 프론트엔드에서 AI 모델의 텍스트 생성 결과를 요청하면, 이 클래스가 응답 데이터를 포맷팅하여 서비스와 컨트롤러 간의 데이터 전달을 용이하게 함
// 각 응답 데이터는 후보 텍스트(Candidate), 콘텐츠(Content), 안전 등급(SafetyRating), 프롬프트 피드백(PromptFeedback) 등의 정보를 포함합니다.
