package com.example.openfeign.demo.rest.controller;

import com.example.openfeign.demo.dto.PapagoDTO;
import com.example.openfeign.demo.service.PapagoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequestMapping("papago")
@RequiredArgsConstructor
@RestController
public class PapagoRestController {

    private final PapagoService papagoService;

    @PostMapping("/detectLangs")
    public PapagoDTO detectLangs(HttpServletRequest request) {

        String text = request.getParameter("text");

        log.trace("text -> {}", text);

        PapagoDTO pDTO = new PapagoDTO();
        pDTO.setText(text);

        PapagoDTO rDTO = Optional.ofNullable(papagoService.detectLangs(pDTO)).orElseGet(PapagoDTO::new);

        return rDTO;
    }
}
