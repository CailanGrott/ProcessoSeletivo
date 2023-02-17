package com.cailangrott.peoplemanager.service;

import com.cailangrott.peoplemanager.dto.endereco.EnderecoCreateDTO;
import com.cailangrott.peoplemanager.dto.pessoa.PessoaCreateDTO;
import com.cailangrott.peoplemanager.dto.pessoa.PessoaUpdateDTO;
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
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private PessoaService pessoaService;

    @BeforeEach
    void setup() {
        pessoaService = new PessoaService(pessoaRepository, enderecoRepository);
    }

    @Test
    void deveAdicionarNovaPessoaComSucesso() {
        when(pessoaRepository.saveAndFlush(any(Pessoa.class))).thenReturn(buildPessoaStub());
        var pessoaDTO = pessoaService.adicionaNovaPessoa(buildPessoaCreateDTOStub());

        assertEquals(1, pessoaDTO.getIdPessoa());
        assertEquals("Cailan", pessoaDTO.getNome());
        assertEquals(LocalDate.of(2002, 8, 28), pessoaDTO.getDataNascimento());

        verify(pessoaRepository, times(1)).saveAndFlush(any(Pessoa.class));
    }

    @Test
    void deveBuscarPessoaPorIdComSucesso() throws RecursoNaoEncontradoException {
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(buildPessoaStub()));
        var pessoaDTO = pessoaService.buscaPessoaPorId(1);

        assertEquals(1, pessoaDTO.getIdPessoa());
        assertEquals("Cailan", pessoaDTO.getNome());
        assertEquals(LocalDate.of(2002, 8, 28), pessoaDTO.getDataNascimento());

        verify(pessoaRepository, times(1)).findById(1);
    }

    @Test
    void deveRetornarRecursoNaoEncontradoException_quandoBuscarPorIdInexistente() {
        when(pessoaRepository.findById(any())).thenReturn(Optional.empty());

        //assertThrows(Tipo da excessao, () -> chamada_metodo que joga a excessao);
        var excessao = assertThrows(RecursoNaoEncontradoException.class, () -> pessoaService.buscaPessoaPorId(1));
        assertEquals("Recurso não encontrado!", excessao.getMessage());

        verify(pessoaRepository, times(1)).findById(any());
    }

    @Test
    void deveBuscarTodasPessoasComSucesso() {
        var pessoa = buildPessoaStub();
        when(pessoaRepository.consultaTodasPessoasExistentes()).thenReturn(List.of(pessoa));
        var pessoaRetorno = pessoaService.buscaTodasPessoas();

        assertEquals(pessoaRetorno.iterator().next().getIdPessoa(), pessoa.getIdPessoa());

        verify(pessoaRepository, times(1)).consultaTodasPessoasExistentes();
    }

    @Test
    void deveEditarPessoaPorIdComSucesso() {
        var pessoa = buildPessoaStub();
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));

        var pessoaUpdate = buildPessoaUpdateDTOStub();
        pessoaService.editaPessoaPorId(1, buildPessoaUpdateDTOStub());

        verify(pessoaRepository, times(1)).findById(1);
        verify(pessoaRepository, times(1)).updatePessoaById(pessoaUpdate.getNome(), pessoaUpdate.getDataNascimento(), 1);
    }

    @Test
    void deveDeletarPessoaPorIdComSucesso() throws RecursoNaoEncontradoException {
        var pessoa = buildPessoaStub();
        when(pessoaRepository.findById(1)).thenReturn(Optional.of(pessoa));
        pessoaService.deletaPessoaPorId(1);

        verify(pessoaRepository, times(1)).findById(1);
        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    private Pessoa buildPessoaStub() {
        return Pessoa.builder()
                .idPessoa(1)
                .nome("Cailan")
                .dataNascimento(LocalDate.of(2002, 8, 28))
                .enderecos(Set.of(buildEnderecoStub()))
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
                .build();
    }

    private PessoaCreateDTO buildPessoaCreateDTOStub() {
        return PessoaCreateDTO.builder()
                .nome("Cailan")
                .dataNascimento(LocalDate.of(2002, 8, 28))
                .enderecos(Set.of(buildEnderecoCreateDTOStub()))
                .build();

    }

    private PessoaUpdateDTO buildPessoaUpdateDTOStub() {
        return PessoaUpdateDTO.builder()
                .nome("Jhon")
                .dataNascimento(LocalDate.of(2002, 10, 10))
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
