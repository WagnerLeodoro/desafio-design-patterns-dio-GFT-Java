package com.wagnerleodoro.forecastwaves.controller;

import com.wagnerleodoro.forecastwaves.model.Cidade;
import com.wagnerleodoro.forecastwaves.services.localidadeServices.LocalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cidade")
public class CidadeController {
    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping
    public ResponseEntity<Iterable<Cidade>> buscarTodos() {
        return ResponseEntity.ok(localidadeService.buscarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(localidadeService.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<Cidade> inserir(@RequestBody Cidade cidade) {
        localidadeService.inserir(cidade);
        return ResponseEntity.ok(cidade);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        localidadeService.atualizar(id, cidade);
        return ResponseEntity.ok(cidade);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        localidadeService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
