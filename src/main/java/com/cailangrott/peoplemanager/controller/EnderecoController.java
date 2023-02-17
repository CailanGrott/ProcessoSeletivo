package com.cailangrott.peoplemanager.controller;

import com.cailangrott.peoplemanager.dto.endereco.EnderecoCreateDTO;
import com.cailangrott.peoplemanager.dto.endereco.EnderecoDTO;
import com.cailangrott.peoplemanager.dto.endereco.EnderecoUpdateDTO;
import com.cailangrott.peoplemanager.exception.RecursoNaoEncontradoException;
import com.cailangrott.peoplemanager.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Adiciona um novo endereço ao sistema",
               description = "Use este endpoint para adicionar um endereço ao sistema usando o id de uma pessoa já cadastrada.")
    @PostMapping("/{id}")
    public ResponseEntity<EnderecoDTO> criarNovoEndereco(@PathVariable("id") Integer idPessoa,
                                                        @RequestBody EnderecoCreateDTO endereco) throws RecursoNaoEncontradoException {
        EnderecoDTO enderecoDTO = enderecoService.adicionaNovoEndereco(idPessoa, endereco);
        return new ResponseEntity<>(enderecoDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna o endereço com o respectivo id",
               description = "Use este endpoint para listar um endereço com um id especifico.")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable("id") Integer idEndereco) {
        EnderecoDTO enderecoDTO = enderecoService.buscaEnderecoPorId(idEndereco);
        return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de um endereço existente",
               description = "Use este endpoint para atualizar os dados de um endereço do sistema usando o id.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") Integer idEndereco,
                                       @RequestBody EnderecoUpdateDTO enderecoUpdateDTO) throws RecursoNaoEncontradoException {
        enderecoService.atualizaEnderecoPorId(idEndereco, enderecoUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta um endereço do sistema",
               description = "Use este endpoint para deletar um endereço do sistema usando o id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer idEndereco) throws RecursoNaoEncontradoException {
        enderecoService.deletaEnderecoPorId(idEndereco);
        return ResponseEntity.noContent().build();
    }
}
