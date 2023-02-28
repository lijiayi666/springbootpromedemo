package com.lijiayi.springbootpromedemo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PushResultDTO {

    private String code;

    public static PushResultDTO getResultDTO(int random) {
        return new PushResultDTO().setCode(random <= 500 ? "000000" : "999999");
    }
}
