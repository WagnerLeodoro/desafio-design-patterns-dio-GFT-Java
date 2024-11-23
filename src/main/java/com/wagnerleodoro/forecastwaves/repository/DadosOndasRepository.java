package com.wagnerleodoro.forecastwaves.repository;

import com.wagnerleodoro.forecastwaves.model.DadosOndas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosOndasRepository extends JpaRepository<DadosOndas, Long> {

}
