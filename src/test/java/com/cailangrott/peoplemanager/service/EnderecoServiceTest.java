package com.cailangrott.peoplemanager.service;

import com.cailangrott.peoplemanager.dto.endereco.EnderecoCreateDTO;
import com.cailangrott.peoplemanager.dto.endereco.EnderecoUpdateDTO;
import com.cailangrott.peoplemanager.exception.RecursoNaoEncontradoException;
import com.cailangrott.peoplemanager.model.Endereco;
import com.cailangrott.peoplemanager.model.Pessoa;
import com.cailangrott.peoplemanager.repository.EnderecoRepository;
import com.cailangrott.peoplemanager.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    private EnderecoService enderecoService;


    @BeforeEach
    void setup() {
        enderecoService = new EnderecoService(enderecoRepository, pessoaRepository);
    }

    @Test
    void deveAdicionarNovoEnderecoSecundarioComSucesso() throws RecursoNaoEncontradoException {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(buildPessoaStub()));
        when(enderecoRepository.saveAndFlush(any(Endereco.class))).thenReturn(buildEnderecoStub());
        var enderecoDTO = enderecoService.adicionaNovoEndereco(1, buildEnderecoCreateDTOStub());

        assertEquals(1, enderecoDTO.getIdEndereco());
        assertEquals("91410-000", enderecoDTO.getCep());
        assertEquals("Porto Alegre", enderecoDTO.getCidade());
        assertEquals("Rua Professor Cristiano Fischer", enderecoDTO.getLogradouro());
        assertEquals(1238, enderecoDTO.getNumero());
        assertEquals(false, enderecoDTO.getEnderecoPrincipal());

        verify(pessoaRepository, times(1)).findById(1);
        verify(enderecoRepository, times(1)).saveAndFlush(any(Endereco.class));
    }

    @Test
    void deveBuscaEnderecoPorIdComSucesso() {
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(buildEnderecoStub()));
        var enderecoDTO = enderecoService.buscaEnderecoPorId(1);

        assertEquals(1, enderecoDTO.getIdEndereco());
        assertEquals("91410-000", enderecoDTO.getCep());
        assertEquals("Porto Alegre", enderecoDTO.getCidade());
        assertEquals("Rua Professor Cristiano Fischer", enderecoDTO.getLogradouro());
        assertEquals(1238, enderecoDTO.getNumero());
        assertEquals(false, enderecoDTO.getEnderecoPrincipal());

        verify(enderecoRepository, times(1)).findById(1);
    }

    @Test
    void deveRetornarRecursoNaoEncontradoException_quandoAdicionarEnderecoComIdInexistente() {
        when(pessoaRepository.findById(any())).thenReturn(Optional.empty());

        //assertThrows(Tipo da excessao, () -> chamada_metodo que joga a excessao);
        var excessao = assertThrows(RecursoNaoEncontradoException.class, () -> enderecoService.adicionaNovoEndereco(1, buildEnderecoCreateDTOStub()));
        assertEquals("Recurso não encontrado!", excessao.getMessage());

        verify(pessoaRepository, times(1)).findById(any());
    }

    @Test
    void deveAtualizarEnderecoPorIdComSucesso() throws RecursoNaoEncontradoException {
        var endereco = buildEnderecoStub();
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));

        var enderecoUpdate = buildEnderecoUpdateDTOStub();
        enderecoService.atualizaEnderecoPorId(1, enderecoUpdate);

        verify(enderecoRepository, times(1)).updateEnderecoById(enderecoUpdate.getLogradouro(),
                enderecoUpdate.getCep(), enderecoUpdate.getNumero(), enderecoUpdate.getCidade(), enderecoUpdate.getPrincipal(), 1);
    }

    @Test
    void deveDeletarEnderecoPorIdComSucesso() throws RecursoNaoEncontradoException {
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(buildEnderecoStub()));
        enderecoService.deletaEnderecoPorId(1);

        verify(enderecoRepository,times(1)).findById(1);
        verify(enderecoRepository, times(1)).deleteEnderecoById(1);
    }

    private Pessoa buildPessoaStub() {
        return Pessoa.builder()
                .idPessoa(1)
                .nome("Cailan")
                .dataNascimento(LocalDate.of(2002, 8, 28))
                .enderecos(Set.of(buildEnderecoStub()))
                .build();
    }

    private Pessoa buildPessoaStubSemEndereco() {
        return Pessoa.builder()
                .idPessoa(1)
                .nome("Cailan")
                .dataNascimento(LocalDate.of(2002, 8, 28))
                .build();
    }

    private Endereco buildEnderecoStub() {
        return Endereco.builder()
                .idEndereco(1)
                .cep("91410-000")
                .cidade("Porto Alegre")
                .logradouro("Rua Professor Cristiano Fischer")
                .numero(1238)
                .enderecoPrincipal(false)
                .pessoa(buildPessoaStubSemEndereco())
                .build();
    }

    private EnderecoUpdateDTO buildEnderecoUpdateDTOStub() {
        return EnderecoUpdateDTO.builder()
                .logradouro("Rua Sardinha")
                .numero(74)
                .cidade("Capão da Canoa")
                .cep("95555-000")
                .principal(true)
                .build();
    }

    private EnderecoCreateDTO buildEnderecoCreateDTOStub() {
        return EnderecoCreateDTO.builder()
                .cep("91410-000")
                .cidade("Porto Alegre")
                .logradouro("Rua Professor Cristiano Fischer")
                .numero(1238)
                .enderecoPrincipal(false)
                .build();

    }
}