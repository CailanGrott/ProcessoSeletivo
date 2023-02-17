package com.cailangrott.peoplemanager.dto.endereco;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoCreateDTO {

    @Schema(example = "Rua Professor Cristiano Fischer")
    private String logradouro;

    @Schema(example = "91410-000")
    private String cep;

    @Schema(example = "1238")
    private Integer numero;

    @Schema(example = "Porto Alegre")
    private String cidade;

    private Boolean enderecoPrincipal;
}
