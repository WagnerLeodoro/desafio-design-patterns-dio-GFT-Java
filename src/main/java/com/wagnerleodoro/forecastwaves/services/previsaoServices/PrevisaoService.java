package com.wagnerleodoro.forecastwaves.services.previsaoServices;

import com.wagnerleodoro.forecastwaves.model.DadosOndas;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PrevisaoService {
    Optional<DadosOndas> fetchAndSavePrevisao(Long cityCode);
    DadosOndas getDadosFromDB(Long id);
    Optional<String> informacoes(Long cityCode);
}
