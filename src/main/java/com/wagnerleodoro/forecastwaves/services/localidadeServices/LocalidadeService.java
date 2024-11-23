package com.wagnerleodoro.forecastwaves.services.localidadeServices;

import com.wagnerleodoro.forecastwaves.model.Cidade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LocalidadeService {
    Iterable<Cidade> buscarTodos();
    Cidade buscarPorId(Long id);
    Optional<Cidade> buscarPorNome(String nome);
    void inserir(Cidade cidade);
    void atualizar(Long id, Cidade cidade);
    void deletar(Long id);
}
