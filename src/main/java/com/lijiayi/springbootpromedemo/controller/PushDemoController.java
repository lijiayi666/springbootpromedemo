package com.lijiayi.springbootpromedemo.controller;

import com.lijiayi.springbootpromedemo.dto.PushResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class PushDemoController {

    @GetMapping("/pushTest")
    public PushResultDTO test() {
        int random = new Random().nextInt(1000);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return PushResultDTO.getResultDTO(random);
    }
}
