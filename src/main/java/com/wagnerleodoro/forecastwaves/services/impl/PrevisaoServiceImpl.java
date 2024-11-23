package com.wagnerleodoro.forecastwaves.services.impl;

import com.wagnerleodoro.forecastwaves.model.Cidade;
import com.wagnerleodoro.forecastwaves.model.DadosOndas;
import com.wagnerleodoro.forecastwaves.repository.CidadeRepository;
import com.wagnerleodoro.forecastwaves.repository.DadosOndasRepository;
import com.wagnerleodoro.forecastwaves.services.previsaoServices.PrevisaoService;
import com.wagnerleodoro.forecastwaves.utils.PrevisaoClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrevisaoServiceImpl implements PrevisaoService {
    @Autowired
    private PrevisaoClient previsaoClient;
    @Autowired
    private DadosOndasRepository dadosOndasRepository;
    @Autowired
    private CidadeRepository cidadeRepository;


    public Optional<DadosOndas> fetchAndSavePrevisao(Long cityCode) {
        try {
            ResponseEntity<DadosOndas> response = previsaoClient.getPrevisao(Optional.ofNullable(cityCode));
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                System.err.println("Erro ao obter a previsão para a cidade com código: " + cityCode);
                return Optional.empty(); // Retorna vazio se a resposta não for bem-sucedida
            }
            DadosOndas dadosOndas = previsaoClient.getPrevisao(Optional.ofNullable(cityCode)).getBody();

            Optional<Cidade> cidadeOptional = cidadeRepository.findById(cityCode);
            if (cidadeOptional.isEmpty()) {
                Cidade cidade = new Cidade();
                assert dadosOndas != null;
                cidade.setNome(dadosOndas.getCidade());
                cidade.setEstado(dadosOndas.getEstado());
                cidade.setCodigo(cityCode);
                cidadeRepository.save(cidade);
                dadosOndasRepository.save(dadosOndas);
                informacoes(cityCode);
                return Optional.of(dadosOndas);
            }

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao buscar e salvar a previsão: " + e.getMessage());

        }
        return Optional.empty();
    }


    @Override
    public DadosOndas getDadosFromDB(Long id) {
        return dadosOndasRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<String> informacoes(Long cityCode) {
        DadosOndas dadosOndas = previsaoClient.getPrevisao(Optional.ofNullable(cityCode)).getBody();

        StringBuilder informacoes = new StringBuilder();
        informacoes.append("Previsão para ").append(dadosOndas.getCidade()).append(", ")
                .append(dadosOndas.getEstado()).append(":\n");

        for (DadosOndas.Onda onda : dadosOndas.getOndas()) {
            for (DadosOndas.Onda.DadosHora dadosHora : onda.getDados_ondas()) {
                informacoes.append(String.format("Hora: %s - Altura da Onda: %.1f m - Agitação: %s\n",
                        dadosHora.getHora(),
                        dadosHora.getAltura_onda(),
                        dadosHora.getAgitation()));
            }
        }

        return Optional.of(informacoes.toString());
    }
}
