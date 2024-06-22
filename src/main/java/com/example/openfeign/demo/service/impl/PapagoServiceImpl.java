package com.example.openfeign.demo.service.impl;

import com.example.openfeign.demo.dto.PapagoDTO;
import com.example.openfeign.demo.service.INaverAPIService;
import com.example.openfeign.demo.service.PapagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PapagoServiceImpl implements PapagoService {

    private final INaverAPIService naverAPIService;

    /**
     * 네이버 파파고 API 를 호출하여 입력된 언어가 어느나라 언어인지 찾기
     *
     * @param pDTO
     * @return
     */
    @Override
    public PapagoDTO detectLangs(PapagoDTO pDTO) {
        log.trace("detectLangs START!!!!");

        String text = pDTO.getText();

        // PapagoAPI 호출하기
        // 결과 : 예 : {"langCode":"ko"}
        PapagoDTO rDTO = naverAPIService.detectLangs(text);

        // 언어감지를 위한 원문 저장하기
        rDTO.setText(text);

        log.trace("END!!");
        return rDTO;
    }

    /**
     * 네이버 PAPAGO API 를 호출하여 언어감지 후, 번역하기
     *
     * @param pDTO
     * @return
     */
    @Override
    public PapagoDTO translate(PapagoDTO pDTO) {
        log.trace(".translate START!!");

        // 언어 종류 찾기, 위에 개발한 public PapagoDTO detectLangs(PapagoDTO pDTO) 호출
        PapagoDTO rDTO = this.detectLangs(pDTO);

        // 찾은 언어 종류
        String langCode = rDTO.getLangCode();

        log.trace("langCode -> {}", langCode);

        rDTO = null;

        String source = ""; // 원문 언어(한국어 : ko / 영어 : en)
        String target = ""; // 번역할 언어

        if (langCode.equals("ko")) {
            source = "ko";
            target = "en";
        } else if (langCode.equals("en")) {
            source = "en";
            target = "ko";
        } else {
            // 한국어와 영어가 아니면 에러 발생시키기
            throw new RuntimeException("한국어와 영어만 번역됩니다");
        }

        String text = pDTO.getText();

        rDTO = naverAPIService.translate(source, target, text);

        log.info("rDTO -> {}", rDTO);

        // 네이버 결과 데이터 구조는 Map 구조에 Map 구조에 Map 구조로 3중 Map 구조 되어있음
        Map<String, String> result = (Map<String, String>) rDTO.getMessage().get("result");

        String srcLangType = result.get("srcLangType");
        log.trace("srcLangType -> {}", srcLangType);
        String tarLangType = result.get("tarLangType");
        log.trace("tarLangType -> {}", tarLangType);
        String translatedText = result.get("translatedText");
        log.trace("translatedText -> {}", translatedText);

        // API 호출 결과를 기반으로 HTML 에서 사용하기 쉽게 새롭게 데이터 구조 정의하기
        rDTO = new PapagoDTO();
        rDTO.setText(text);
        rDTO.setTranslatedText(translatedText);
        rDTO.setSrcLangType(srcLangType);
        rDTO.setTarLangType(tarLangType);

        return rDTO;
    }
}
