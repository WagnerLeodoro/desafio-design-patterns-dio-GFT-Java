package com.wagnerleodoro.forecastwaves.utils;

import com.wagnerleodoro.forecastwaves.model.DadosOndas;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "previsaoClient", url = "https://brasilapi.com.br/api/cptec/v1/ondas")
public interface PrevisaoClient {
    @GetMapping("/{cityCode}")
    ResponseEntity<DadosOndas> getPrevisao(@PathVariable("cityCode") Optional<Long> codigo);
}
