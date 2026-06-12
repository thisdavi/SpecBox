package com.davi.delphos.service;

import com.davi.delphos.model.SistemaQA;
import com.davi.delphos.model.TesteQA;
import com.davi.delphos.model.VersaoQA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class QAService {
    private static final Path PASTA = Path.of(System.getProperty("user.home"), ".delphosqa");
    private static final Path ARQUIVO = PASTA.resolve("qa_testes.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void salvarSistemas(List<SistemaQA> sistemas) throws IOException {
        Files.createDirectories(PASTA);

        try (BufferedWriter escritor = Files.newBufferedWriter(ARQUIVO, UTF_8)) {
            escritor.write(gson.toJson(sistemas));
        }
    }

    public List<SistemaQA> carregarSistemas() {
        if (Files.exists(ARQUIVO)) {
            try (BufferedReader ler = Files.newBufferedReader(ARQUIVO, UTF_8)) {
                List<SistemaQA> sistemas = gson.fromJson(ler, new TypeToken<List<SistemaQA>>() {}.getType()
                );

                return sistemas != null ? sistemas : new ArrayList<>();

            } catch (IOException e) {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    public List<SistemaQA> salvarTeste(List<SistemaQA> sistemas, String nomeSistema, String versao, TesteQA
            teste) throws IOException {

        if (nomeSistema == null || nomeSistema.isBlank()) {
            throw new IllegalArgumentException("Sistema inválido");
        }
        if (versao == null || versao.isBlank()){
            throw new IllegalArgumentException("Versão inválida");
        }

        SistemaQA sistemaOpt = sistemas.stream()
                .filter(s -> s.getNome().equalsIgnoreCase(nomeSistema))
                .findFirst()
                .orElseGet(() -> {
                    SistemaQA sistemaNovo = new SistemaQA(nomeSistema);
                    sistemas.add(sistemaNovo);
                    return sistemaNovo;
                });

        VersaoQA versaoOpt = sistemaOpt.buscarVersao(versao)
                .orElseGet(() -> {
                    VersaoQA versaoNova = new VersaoQA(versao);
                    sistemaOpt.adicionarVersao(versaoNova);
                    return versaoNova;
                });

        Optional<TesteQA> existe = versaoOpt.buscarTeste(teste.getId());
        if (existe.isPresent()){
            versaoOpt.removerTeste(teste.getId());
            teste.atualizarDataEdicao();
        }
        versaoOpt.adicionarTeste(teste);
        salvarSistemas(sistemas);
        return sistemas;
    }
}
