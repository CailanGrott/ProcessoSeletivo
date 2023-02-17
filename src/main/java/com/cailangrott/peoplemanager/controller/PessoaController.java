package com.cailangrott.peoplemanager.controller;

import com.cailangrott.peoplemanager.dto.pessoa.PessoaCreateDTO;
import com.cailangrott.peoplemanager.dto.pessoa.PessoaDTO;
import com.cailangrott.peoplemanager.dto.pessoa.PessoaUpdateDTO;
import com.cailangrott.peoplemanager.exception.RecursoNaoEncontradoException;
import com.cailangrott.peoplemanager.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @Operation(summary = "Adiciona uma pessoa ao sistema",
               description = "Use este endpoint para adicionar uma pessoa ao sistema juntamente com seu(s) endereço(s).")
    @PostMapping
    public ResponseEntity<PessoaDTO> criarNovaPessoa(@RequestBody PessoaCreateDTO pessoaCreateDTO) {
        PessoaDTO pessoaDTO = pessoaService.adicionaNovaPessoa(pessoaCreateDTO);
        return new ResponseEntity<>(pessoaDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna uma lista com todas as pessoas",
               description = "Use este endpoint para listar todas as pessoas do sistema.")
    @GetMapping
    public ResponseEntity<Iterable<PessoaDTO>> findAll() {
        Iterable<PessoaDTO> pessoasDTO = pessoaService.buscaTodasPessoas();
        return new ResponseEntity<>(pessoasDTO, HttpStatus.OK);
    }

    @Operation(summary = "Retorna a pessoa com o respectivo id",
               description = "Use este endpoint para listar uma pessoa com um id especifico.")
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> findById(@PathVariable("id") Integer idPessoa) throws RecursoNaoEncontradoException {
        PessoaDTO pessoaDTO = pessoaService.buscaPessoaPorId(idPessoa);
        return new ResponseEntity<>(pessoaDTO, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de uma pessoa existente",
               description = "Use este endpoint para atualizar os dados de uma pessoa do sistema usando o id.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") Integer idPessoa,
                                       @RequestBody PessoaUpdateDTO pessoaUpdateDTO) {
        pessoaService.editaPessoaPorId(idPessoa, pessoaUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta uma pessoa do sistema",
               description = "Use este endpoint para deletar uma pessoa do sistema juntamente com seu(s) endereço(s).")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer idPessoa) throws RecursoNaoEncontradoException {
        pessoaService.deletaPessoaPorId(idPessoa);
        return ResponseEntity.noContent().build();
    }
}
