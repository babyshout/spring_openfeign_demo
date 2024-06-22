package com.example.openfeign.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PapagoDTO {
    private String langCode;
    private String text;

    private String translatedText;
    private String srcLangType;
    private String tarLangType;

    private Map<String, Object> message;
}
