package com.brunogdev.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {
    // DTO = data to object
    private String message;
    private String field;
}
