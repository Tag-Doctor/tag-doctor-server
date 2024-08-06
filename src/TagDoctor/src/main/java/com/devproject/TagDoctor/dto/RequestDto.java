package com.devproject.TagDoctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private String selectedAge;
    private String selectedPart;
    private String selectedSubPart;
    private String selectedDetail;
    private String additionalInfo;
}
