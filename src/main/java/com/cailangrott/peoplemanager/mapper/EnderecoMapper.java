package com.cailangrott.peoplemanager.mapper;

import com.cailangrott.peoplemanager.dto.endereco.EnderecoCreateDTO;
import com.cailangrott.peoplemanager.dto.endereco.EnderecoDTO;
import com.cailangrott.peoplemanager.model.Endereco;
import com.cailangrott.peoplemanager.model.Pessoa;

import java.util.Set;
import java.util.stream.Collectors;

public class EnderecoMapper {
    public static Set<EnderecoDTO> mapEnderecoToDto(Set<Endereco> enderecos) {
        return enderecos.stream().map(endereco -> EnderecoDTO.builder()
                        .idEndereco(endereco.getIdEndereco())
                        .cidade(endereco.getCidade())
                        .cep(endereco.getCep())
                        .logradouro(endereco.getLogradouro())
                        .numero(endereco.getNumero())
                        .enderecoPrincipal(endereco.getEnderecoPrincipal())
                        .build())
                .collect(Collectors.toSet());
    }

    public static Set<Endereco> mapDtoToEndereco(Set<EnderecoCreateDTO> enderecosDTO) {
        return enderecosDTO.stream().map(enderecoDTO -> Endereco.builder()
                .cidade(enderecoDTO.getCidade())
                .cep(enderecoDTO.getCep())
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .enderecoPrincipal(enderecoDTO.getEnderecoPrincipal())
                .build())
                .collect(Collectors.toSet());
    }

    public static Endereco mapDtoToEndereco(EnderecoCreateDTO endereco, Pessoa pessoa) {
        return Endereco.builder()
                .enderecoPrincipal(endereco.getEnderecoPrincipal())
                .cidade(endereco.getCidade())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .pessoa(pessoa)
                .build();
    }

    public static EnderecoDTO mapEnderecoToDto(Endereco endereco) {
        return EnderecoDTO.builder()
                .idEndereco(endereco.getIdEndereco())
                .cidade(endereco.getCidade())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .cep(endereco.getCep())
                .enderecoPrincipal(endereco.getEnderecoPrincipal())
                .build();
    }
}
