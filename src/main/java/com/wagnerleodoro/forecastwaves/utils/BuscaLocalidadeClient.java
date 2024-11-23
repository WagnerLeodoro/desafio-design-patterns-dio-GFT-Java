package com.wagnerleodoro.forecastwaves.utils;

import com.wagnerleodoro.forecastwaves.model.Cidade;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "buscaLocalidade", url = "https://brasilapi.com.br/api/cptec/v1/cidade")
public interface BuscaLocalidadeClient {
    @GetMapping("/{cityName}")
    ResponseEntity<List<Cidade>> getLocalidade(@PathVariable("cityName") String cityName);
}
