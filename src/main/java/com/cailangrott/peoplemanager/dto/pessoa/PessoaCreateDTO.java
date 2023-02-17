package com.cailangrott.peoplemanager.dto.pessoa;

import com.cailangrott.peoplemanager.dto.endereco.EnderecoCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class PessoaCreateDTO {

    @Schema(example = "Cailan")
    private String nome;

    @Schema(example = "2002-08-28")
    private LocalDate dataNascimento;

    private Set<EnderecoCreateDTO> enderecos;
}
