package com.cailangrott.peoplemanager.repository;

import com.cailangrott.peoplemanager.model.Endereco;
import com.cailangrott.peoplemanager.model.Pessoa;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Override
    Optional<Endereco> findById(Integer integer);

    @Transactional
    @Modifying
    @Query("delete from endereco e where e.idEndereco = :idEndereco")
    void deleteEnderecoById(@NonNull Integer idEndereco);

    @Transactional
    @Modifying
    @Query("update endereco e set e.pessoa = :pessoa where e.idEndereco in :id")
    void updatePersonAddressById(@Param("pessoa") Pessoa pessoa, @Param("id") Set<Integer> id);

    @Transactional
    @Modifying
    @Query("update endereco e set e.enderecoPrincipal = false where e.pessoa.idPessoa = :id")
    void updateEnderecoPrincipalPessoa(@Nullable @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("""
            update endereco e set e.logradouro = :logradouro, e.cep = :cep, e.numero = :numero, e.cidade = :cidade, e.enderecoPrincipal = :enderecoPrincipal
            where e.idEndereco = :idEndereco""")
    void updateEnderecoById(@Param("logradouro") String logradouro,
                            @Param("cep") String cep,
                            @Param("numero") Integer numero,
                            @Param("cidade") String cidade,
                            @Param("enderecoPrincipal") Boolean enderecoPrincipal,
                            @Param("idEndereco") Integer idEndereco);
    
    @Transactional
    @Modifying
    @Query(value = """
            update endereco e set e.logradouro = :logradouro, e.cep = :cep, e.numero = :numero, e.cidade = :cidade, e.enderecoPrincipal = :enderecoPrincipal
            where e.idEndereco = :idEndereco""")
    void updateAddress(@Nullable @Param("logradouro") String logradouro,
                       @Nullable @Param("cep") String cep,
                       @Nullable @Param("numero") Integer numero,
                       @Nullable @Param("cidade") String cidade,
                       @Nullable @Param("enderecoPrincipal") Boolean enderecoPrincipal, @org.springframework.lang.NonNull
                       @Param("idEndereco") Integer idEndereco);
}
