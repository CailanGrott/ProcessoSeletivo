package com.cailangrott.peoplemanager.dto.pessoa;

import com.cailangrott.peoplemanager.dto.endereco.EnderecoDTO;
import com.cailangrott.peoplemanager.model.Endereco;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
public class PessoaDTO {

    private Integer idPessoa;

    private String nome;

    private LocalDate dataNascimento;

    private Set<EnderecoDTO> enderecos;
}
