package com.example.openfeign.demo.service;

import com.example.openfeign.demo.dto.PapagoDTO;

public interface PapagoService {

    /**
     * 네이버 파파고 API 를 호출하여 입력된 언어가 어느나라 언어인지 찾기
     * @param pDTO
     * @return
     */
    PapagoDTO detectLangs(PapagoDTO pDTO);

    /**
     * 네이버 PAPAGO API 를 호출하여 언어감지 후, 번역하기
     * @param pDTO
     * @return
     */
    PapagoDTO translate(PapagoDTO pDTO);
}
