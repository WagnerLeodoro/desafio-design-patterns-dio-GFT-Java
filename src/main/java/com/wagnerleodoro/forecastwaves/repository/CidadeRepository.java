package com.wagnerleodoro.forecastwaves.repository;

import com.wagnerleodoro.forecastwaves.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String nome);
    Optional<Cidade> findByCodigo(Long codigo);
}
