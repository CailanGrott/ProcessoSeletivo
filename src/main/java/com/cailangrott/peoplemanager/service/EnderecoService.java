package com.cailangrott.peoplemanager.service;

import com.cailangrott.peoplemanager.dto.endereco.EnderecoCreateDTO;
import com.cailangrott.peoplemanager.dto.endereco.EnderecoDTO;
import com.cailangrott.peoplemanager.dto.endereco.EnderecoUpdateDTO;
import com.cailangrott.peoplemanager.exception.RecursoNaoEncontradoException;
import com.cailangrott.peoplemanager.mapper.EnderecoMapper;
import com.cailangrott.peoplemanager.model.Endereco;
import com.cailangrott.peoplemanager.model.Pessoa;
import com.cailangrott.peoplemanager.repository.EnderecoRepository;
import com.cailangrott.peoplemanager.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.cailangrott.peoplemanager.util.ValorOuDefault.*;
import static com.cailangrott.peoplemanager.mapper.EnderecoMapper.mapDtoToEndereco;
import static com.cailangrott.peoplemanager.mapper.EnderecoMapper.mapEnderecoToDto;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaRepository pessoaRepository;

    public EnderecoDTO adicionaNovoEndereco(Integer idPessoa, EnderecoCreateDTO endereco) throws RecursoNaoEncontradoException {
        var pessoa = buscaPessoaExistente(idPessoa);
        validaEnderecosPrincipaisPorIdPessoa(idPessoa, endereco.getEnderecoPrincipal());

        var enderecoAdicionado = enderecoRepository.saveAndFlush(mapDtoToEndereco(endereco, pessoa));
        return mapEnderecoToDto(enderecoAdicionado);
    }

    public EnderecoDTO buscaEnderecoPorId(Integer idEndereco) {
        return EnderecoMapper.mapEnderecoToDto(enderecoRepository.findById(idEndereco).orElseThrow());
    }

    public void atualizaEnderecoPorId(Integer idEndereco, EnderecoUpdateDTO model) throws RecursoNaoEncontradoException {
        var enderecoExistente = buscaEnderecoExiste(idEndereco);
        var updateEntidade = validaUpdateDeCamposEndereco(enderecoExistente, model);
        validaEnderecosPrincipaisPorIdPessoa(updateEntidade.getPessoa().getIdPessoa(), updateEntidade.getEnderecoPrincipal());

        enderecoRepository.updateEnderecoById(updateEntidade.getLogradouro(),
                updateEntidade.getCep(),
                updateEntidade.getNumero(),
                updateEntidade.getCidade(),
                updateEntidade.getEnderecoPrincipal(),
                idEndereco);
    }

    private Endereco buscaEnderecoExiste(Integer idEndereco) throws RecursoNaoEncontradoException {
        return enderecoRepository.findById(idEndereco)
                .orElseThrow(RecursoNaoEncontradoException::new);
    }

    public void deletaEnderecoPorId(Integer idEndereco) throws RecursoNaoEncontradoException {
        var enderecoRetorno = enderecoRepository.findById(idEndereco).orElseThrow(RecursoNaoEncontradoException::new);
        enderecoRepository.deleteEnderecoById(idEndereco);
    }

    //region private methods
    private Endereco validaUpdateDeCamposEndereco(Endereco endereco, EnderecoUpdateDTO model) {
        return Endereco.builder()
                .idEndereco(endereco.getIdEndereco())
                .cidade(getValorOuDefault(endereco.getCidade(), model.getCidade()))
                .logradouro(getValorOuDefault(endereco.getLogradouro(), model.getLogradouro()))
                .cep(getValorOuDefault(endereco.getCep(), model.getCep()))
                .numero(getValorOuDefault(endereco.getNumero(), model.getNumero()))
                .enderecoPrincipal(getValorOuDefault(endereco.getEnderecoPrincipal(), model.getPrincipal()))
                .pessoa(endereco.getPessoa())
                .build();
    }

    private void validaEnderecosPrincipaisPorIdPessoa(Integer idPessoa, boolean principal) {
        if (principal) {
            enderecoRepository.updateEnderecoPrincipalPessoa(idPessoa);
        }
    }

    private Pessoa buscaPessoaExistente(Integer id) throws RecursoNaoEncontradoException {
        Optional<Pessoa> pessoaRetorno = pessoaRepository.findById(id);

        return pessoaRetorno.orElseThrow(RecursoNaoEncontradoException::new);
    }
    //endregion
}
