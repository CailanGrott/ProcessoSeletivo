package com.cailangrott.peoplemanager.repository;

import com.cailangrott.peoplemanager.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update pessoa p set p.nome = :nome, p.data_nascimento = :dataNascimento where p.id_pessoa = :idPessoa", nativeQuery = true)
    void updatePessoaById(@Nullable @Param("nome") String nome, @Nullable @Param("dataNascimento") LocalDate dataNascimento, @Param("idPessoa") Integer idPessoa);

    @Query("select p from pessoa p")
    List<Pessoa> consultaTodasPessoasExistentes();
}
