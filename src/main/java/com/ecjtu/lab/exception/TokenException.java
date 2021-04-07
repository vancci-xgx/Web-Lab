package com.ecjtu.lab.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenException extends RuntimeException {
    private String  code;
    private String message;


}
