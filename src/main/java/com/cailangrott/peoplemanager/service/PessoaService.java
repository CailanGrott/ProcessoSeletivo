package com.cailangrott.peoplemanager.service;

import com.cailangrott.peoplemanager.dto.pessoa.PessoaCreateDTO;
import com.cailangrott.peoplemanager.dto.pessoa.PessoaDTO;
import com.cailangrott.peoplemanager.dto.pessoa.PessoaUpdateDTO;
import com.cailangrott.peoplemanager.exception.RecursoNaoEncontradoException;
import com.cailangrott.peoplemanager.mapper.EnderecoMapper;
import com.cailangrott.peoplemanager.mapper.PessoaMapper;
import com.cailangrott.peoplemanager.model.Endereco;
import com.cailangrott.peoplemanager.model.Pessoa;
import com.cailangrott.peoplemanager.repository.EnderecoRepository;
import com.cailangrott.peoplemanager.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.cailangrott.peoplemanager.mapper.PessoaMapper.mapDtoToPessoa;

@Service

public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public PessoaDTO adicionaNovaPessoa(PessoaCreateDTO pessoaCreateDTO) {
        Pessoa pessoa = pessoaRepository.saveAndFlush(mapDtoToPessoa(pessoaCreateDTO));

        adicionaEnderecoPessoa(pessoa, pessoa.getEnderecos().stream()
                .map(Endereco::getIdEndereco)
                .collect(Collectors.toSet()));

        return PessoaMapper.mapPessoaToDto(pessoa);
    }

    public PessoaDTO buscaPessoaPorId(Integer id) throws RecursoNaoEncontradoException {
        var pessoaRetorno = pessoaRepository.findById(id).orElseThrow(RecursoNaoEncontradoException::new);

        return PessoaDTO.builder()
                .idPessoa(pessoaRetorno.getIdPessoa())
                .nome(pessoaRetorno.getNome())
                .dataNascimento(pessoaRetorno.getDataNascimento())
                .enderecos(EnderecoMapper.mapEnderecoToDto(pessoaRetorno.getEnderecos()))
                .build();
    }

    public Iterable<PessoaDTO> buscaTodasPessoas() {
        var pessoasConsultadas = pessoaRepository.consultaTodasPessoasExistentes();

        return pessoasConsultadas.stream()
                .map(PessoaMapper::mapPessoaToDto)
                .collect(Collectors.toList());
    }

    public void editaPessoaPorId(Integer id, PessoaUpdateDTO model) {
        var updatePessoa = validaUpdateAtributosPessoa(model, pessoaRepository.findById(id).orElseThrow());
        pessoaRepository.updatePessoaById(updatePessoa.getNome(), updatePessoa.getDataNascimento(), id);
    }

    public void deletaPessoaPorId(Integer idPessoa) throws RecursoNaoEncontradoException {
        var pessoaRetorno = pessoaRepository.findById(idPessoa).orElseThrow(RecursoNaoEncontradoException::new);
        pessoaRepository.delete(pessoaRetorno);
    }

    //region private methods
    //Método responsável por relacionar endereços a uma pessoa que está sendo adicionada ao sistema
    private void adicionaEnderecoPessoa(Pessoa pessoa, Set<Integer> idEnderecos) {
        enderecoRepository.updatePersonAddressById(pessoa, idEnderecos);
    }

    private Pessoa validaUpdateAtributosPessoa(PessoaUpdateDTO model, Pessoa pessoa) {
        return Pessoa.builder()
                .nome(model.getNome().isBlank() ? pessoa.getNome() : model.getNome())
                .dataNascimento(model.getDataNascimento() == null ? pessoa.getDataNascimento() : model.getDataNascimento())
                .build();
    }
    //endregion
}
