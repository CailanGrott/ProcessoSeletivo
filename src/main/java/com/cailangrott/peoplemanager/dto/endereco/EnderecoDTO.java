package com.cailangrott.peoplemanager.dto.endereco;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoDTO {

    @Nullable
    private Integer idEndereco;

    private String logradouro;

    private String cep;

    private Integer numero;

    private String cidade;

    private Boolean enderecoPrincipal;
}
