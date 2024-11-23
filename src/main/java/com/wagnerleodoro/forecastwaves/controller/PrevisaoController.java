package com.wagnerleodoro.forecastwaves.controller;


import com.wagnerleodoro.forecastwaves.model.DadosOndas;
import com.wagnerleodoro.forecastwaves.repository.CidadeRepository;
import com.wagnerleodoro.forecastwaves.services.previsaoServices.PrevisaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/previsao")
public class PrevisaoController {
    @Autowired
    private PrevisaoService previsaoService;
    @Autowired
    private CidadeRepository cidadeRepository;

    @GetMapping("/{id}")
    public DadosOndas getOndas(@PathVariable Long id) {
        return previsaoService.getDadosFromDB(id);
    }

    @GetMapping("/buscar")
    public Optional<DadosOndas> buscarPrevisao(@RequestParam Long cityCode) {
        return previsaoService.fetchAndSavePrevisao(cityCode);
    }
}
