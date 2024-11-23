package com.wagnerleodoro.forecastwaves.services.impl;

import com.wagnerleodoro.forecastwaves.model.Cidade;
import com.wagnerleodoro.forecastwaves.repository.CidadeRepository;
import com.wagnerleodoro.forecastwaves.services.localidadeServices.LocalidadeService;
import com.wagnerleodoro.forecastwaves.utils.BuscaLocalidadeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalidadeImpl implements LocalidadeService {
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private BuscaLocalidadeClient buscaLocalidadeClient;


    @Override
    public Iterable<Cidade> buscarTodos() {
        List<Cidade> cidades = cidadeRepository.findAll();
        if (cidades.isEmpty()) {
            return null;
        } else {
            return cidades;
        }
    }

    @Override
    public Cidade buscarPorId(Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        return cidade.orElse(null);
    }

    @Override
    public Optional<Cidade> buscarPorNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }

    @Override
    public void inserir(Cidade cidade) {
        salvarCidadeComCodigo(cidade);
    }

    @Override
    public void atualizar(Long id, Cidade cidade) {
        Optional<Cidade> cidadeBd = cidadeRepository.findById(id);
        if (cidadeBd.isPresent()) {
            salvarCidadeComCodigo(cidade);
        }
    }

    @Override
    public void deletar(Long id) {
        cidadeRepository.deleteById(id);
    }

    private void salvarCidadeComCodigo(Cidade cidade) {
        String nome = cidade.getNome();
        Cidade response = cidadeRepository.findByNome(nome).orElseGet(() -> {
            List<Cidade> cidades = buscaLocalidadeClient.getLocalidade(nome).getBody();

            assert cidades != null;

            if (cidades.isEmpty()) {
                throw new RuntimeException("Nenhuma cidade encontrada para o nome: " + nome);
            }

            Cidade novaCidade = cidades.get(0);
            cidadeRepository.save(novaCidade);
            return novaCidade;
        });
        cidade.setNome(response.getNome());
        cidade.setEstado(response.getEstado());
        cidade.setCodigo(response.getId());
        cidadeRepository.save(cidade);
    }


}
