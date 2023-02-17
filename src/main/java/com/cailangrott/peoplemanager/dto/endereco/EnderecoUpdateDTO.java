package com.cailangrott.peoplemanager.dto.endereco;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoUpdateDTO {

    private  String cidade;
    private  String logradouro;
    private  String cep;
    private  Integer numero;
    private  Boolean principal;

}
