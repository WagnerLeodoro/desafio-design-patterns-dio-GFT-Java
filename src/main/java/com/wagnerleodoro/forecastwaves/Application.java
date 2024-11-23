package com.wagnerleodoro.forecastwaves;

import com.wagnerleodoro.forecastwaves.model.Cidade;
import com.wagnerleodoro.forecastwaves.services.impl.LocalidadeImpl;
import com.wagnerleodoro.forecastwaves.services.impl.PrevisaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Optional;
import java.util.Scanner;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.wagnerleodoro.forecastwaves")
public class Application implements CommandLineRunner {

    @Autowired
    private PrevisaoServiceImpl previsaoService;
    @Autowired
    private LocalidadeImpl localidadeService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            System.out.println("=== Bem-vindo ao Sistema de Previsão de Ondas ===");
            while (running) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Consultar e salvar previsão para uma cidade");
                System.out.println("2. Buscar dados de uma cidade no banco");
                System.out.println("0. Sair");

                System.out.print("Digite sua escolha: ");
                int escolha = Integer.parseInt(scanner.nextLine());

                switch (escolha) {
                    case 1:
                        System.out.print("Digite o nome da cidade: ");
                        String cityName = scanner.nextLine();
                        Optional<Cidade> cidadeOpt = localidadeService.buscarPorNome(cityName);

                        if (cidadeOpt.isPresent()) {
                        Cidade cidade = cidadeOpt.get();
                        } else {
                            Cidade cidade = new Cidade();
                            cidade.setNome(cityName);
                            localidadeService.inserir(cidade);
                            System.out.println("Cidade adicionada ao banco com sucesso.");
                            var result = previsaoService.fetchAndSavePrevisao(cidade.getCodigo());

                            result.ifPresentOrElse(
                                    informacao -> System.out.println("Previsão salva com sucesso: " + previsaoService.informacoes(cidade.getCodigo())),
                                    () -> System.out.println("Não foi possível salvar a previsão.")
                            );
                        }
                        break;

                    case 2:
                        System.out.print("Digite o ID dos dados de ondas: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        var dados = previsaoService.getDadosFromDB(id);

                        if (dados != null) {
                            System.out.println("Dados encontrados: " + dados);
                        } else {
                            System.out.println("Nenhum dado encontrado com o ID informado.");
                        }
                        break;

                    case 0:
                        running = false;
                        System.out.println("Encerrando o sistema. Até mais!");
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
    }
}
