package com.cailangrott.peoplemanager.mapper;

import com.cailangrott.peoplemanager.dto.pessoa.PessoaCreateDTO;
import com.cailangrott.peoplemanager.dto.pessoa.PessoaDTO;
import com.cailangrott.peoplemanager.model.Pessoa;

public class PessoaMapper {
    public static PessoaDTO mapPessoaToDto(Pessoa pessoa) {
        return PessoaDTO.builder()
                .idPessoa(pessoa.getIdPessoa())
                .nome(pessoa.getNome())
                .dataNascimento(pessoa.getDataNascimento())
                .enderecos(EnderecoMapper.mapEnderecoToDto(pessoa.getEnderecos()))
                .build();
    }

    public static Pessoa mapDtoToPessoa(PessoaCreateDTO pessoaCreateDTO) {
        return Pessoa.builder()
                .nome(pessoaCreateDTO.getNome())
                .dataNascimento(pessoaCreateDTO.getDataNascimento())
                .enderecos(EnderecoMapper.mapDtoToEndereco(pessoaCreateDTO.getEnderecos()))
                .build();
    }

    public static Pessoa mapDtoToPessoa(PessoaDTO pessoaDTO) {
        return Pessoa.builder()
                .idPessoa(pessoaDTO.getIdPessoa())
                .nome(pessoaDTO.getNome())
                .dataNascimento(pessoaDTO.getDataNascimento())
                .build();
    }
}
