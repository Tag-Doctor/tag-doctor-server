package com.devproject.TagDoctor.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

// 프론트엔드에서 오는 요청 데이터를 담는 클래스
// 프론트엔드에서 요청이 어떻게 오는지 봐야할 것 같음
// 이 클래스를 통해 프론트엔드에서 전달된 데이터를 쉽게 받아서 처리할 수 있도록 객체로 변환
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // 빌더 패턴을 사용하여 객체를 생성
public class GeminiRequest {

    private List<Content> contents; // Content 객체의 리스트를 저장하는 필드, 프론트엔드에서 여러 개의 콘텐츠를 요청할 수 있다!!
    private GenerationConfig generationConfig; // 생성 설정을 저장하는 필드, 이 설정은 텍스트 생성 관련 매개변수를 포함

    @Getter @Setter
    public static class Content {
        // Parts 객체를 저장하는 필드, 콘텐츠의 구성 요소를 나타냄
        private Parts parts;
    }

    @Getter @Setter
    public static class Parts {
        // 텍스트 내용을 저장하는 필드, 프론트엔드에서 요청된 텍스트가 여기에 저장
        private String text;
    }

    @Getter @Setter
    public static class GenerationConfig {
        private int candidate_count; // 생성될 텍스트의 후보 수를 나타내는 필드
        private int max_output_tokens; // 생성될 텍스트의 최대 토큰 수를 나타내는 필드
        private double temperature; // 텍스트 생성의 랜덤성을 조절하는 매개변수입니다. 값이 높을수록 출력이 더 다양
    }

    // 프론트엔드에서 텍스트 프롬프트를 받아서 GeminiRequest 객체를 생성하는 생성자
    // contents 리스트를 초기화하고, Content 객체와 Parts 객체를 생성하여 프롬프트 텍스트를 설정
    // generationConfig 를 초기화하고 기본 설정 값을 설정
    public GeminiRequest(String prompt) {
        this.contents = new ArrayList<>();
        Content content = new Content();
        Parts parts = new Parts();

        parts.setText(prompt);
        content.setParts(parts);

        this.contents.add(content);
        this.generationConfig = new GenerationConfig();
        this.generationConfig.setCandidate_count(1); // 생성될 텍스트의 후보수를 1로 설정
        this.generationConfig.setMax_output_tokens(1000); // 최대 토큰 수 설정
        this.generationConfig.setTemperature(0.7); // 값이 높을수록 출력 다양
    }
}
