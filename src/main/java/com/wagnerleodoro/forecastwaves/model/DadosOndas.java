package com.wagnerleodoro.forecastwaves.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Data
@Entity // Marking the class as a JPA entity
public class DadosOndas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cidade;
    private String estado;
    private String atualizado_em;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dados_ondas_id")
    private List<Onda> ondas;

    @Data
    @Entity
    public static class Onda {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String data;

        @ElementCollection
        private List<DadosHora> dados_ondas;

        @Data
        @Embeddable
        public static class DadosHora {
            private String hora;
            private double vento;
            private String direcao_vento;
            private String direcao_vento_desc;
            private double altura_onda;
            private String direcao_onda;
            private String direcao_onda_desc;
            private String agitation;
        }
    }
}
