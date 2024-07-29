package com.devproject.TagDoctor.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// REST API 호출시 사용되는 데이터의 출력 클래스
// 서비스와 컨트롤러 사이에서 데이터를 주고받을 때 사용
@NoArgsConstructor
@Getter
public class GeminiResponse {


    @Getter
    public static class Candidate {
        private Content content;
        private String finishReason;
        private int index;
        List<SafetyRating> safetyRatings;
    }

    @Getter
    public static class Content {
        private List<TextPart> parts;
        private String role;
    }

    @Getter
    public static class TextPart {
        private String text;
    }

    @Getter
    public static class SafetyRating {
        private String category;
        private String probability;
    }

}
