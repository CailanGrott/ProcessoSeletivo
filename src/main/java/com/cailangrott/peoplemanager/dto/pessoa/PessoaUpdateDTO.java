package com.cailangrott.peoplemanager.dto.pessoa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PessoaUpdateDTO {

    @Schema(example = "Jhon")
    private String nome;
    private LocalDate dataNascimento;
}
