package com.davi.specbox.service;

import com.davi.specbox.model.Sistemas;
import com.davi.specbox.model.Testes;
import com.davi.specbox.model.Versoes;
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

public class SpecBoxService {
    private static final Path PASTA = Path.of(System.getProperty("user.home"), ".specbox");
    private static final Path ARQUIVO = PASTA.resolve("specbox_testes.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void salvarSistemas(List<Sistemas> sistemas) {
        try {
            Files.createDirectories(PASTA);
            try (BufferedWriter escritor = Files.newBufferedWriter(ARQUIVO, UTF_8)) {
                escritor.write(gson.toJson(sistemas));
            }
        } catch (IOException ioException) {
            System.out.println("Erro ao salvar sistema: " + ioException.getMessage());
        }
    }

    public List<Sistemas> carregarSistemas() {
        if (Files.exists(ARQUIVO)) {
            try (BufferedReader ler = Files.newBufferedReader(ARQUIVO, UTF_8)) {
                List<Sistemas> sistemas = gson.fromJson(ler, new TypeToken<List<Sistemas>>() {
                        }.getType()
                );

                return sistemas != null ? sistemas : new ArrayList<>();

            } catch (IOException e) {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    public List<Sistemas> salvarTeste(List<Sistemas> sistemas, String nomeSistema, String versao, Testes
            teste) throws IOException {

        if (nomeSistema == null || nomeSistema.isBlank()) {
            throw new IllegalArgumentException("Sistema inválido");
        }
        if (versao == null || versao.isBlank()) {
            throw new IllegalArgumentException("Versão inválida");
        }

        Sistemas sistemaOpt = sistemas.stream()
                .filter(s -> s.getNome().equalsIgnoreCase(nomeSistema))
                .findFirst()
                .orElseGet(() -> {
                    Sistemas sistemaNovo = new Sistemas(nomeSistema);
                    sistemas.add(sistemaNovo);
                    return sistemaNovo;
                });

        Versoes versaoOpt = sistemaOpt.buscarVersao(versao)
                .orElseGet(() -> {
                    Versoes versaoNova = new Versoes(versao);
                    sistemaOpt.adicionarVersao(versaoNova);
                    return versaoNova;
                });

        Optional<Testes> existe = versaoOpt.buscarTeste(teste.getId());
        if (existe.isPresent()) {
            versaoOpt.removerTeste(teste.getId());
            teste.atualizarDataEdicao();
        }
        versaoOpt.adicionarTeste(teste);
        salvarSistemas(sistemas);
        return sistemas;
    }

    public List<Sistemas> excluirTeste(List<Sistemas> sistemas, String nomeSistema, String versao, String idTeste) throws IOException {

        Optional<Sistemas> sistemaOpt = sistemas.stream()
                .filter(s -> s.getNome().equalsIgnoreCase(nomeSistema))
                .findFirst();

        if (sistemaOpt.isEmpty()) {
            return sistemas;
        }

        Sistemas sistemaEncontrado = sistemaOpt.get();

        Optional<Versoes> versaoOpt = sistemaEncontrado.buscarVersao(versao);
        if (versaoOpt.isEmpty()) {
            return sistemas;
        }
        Versoes versaoEncontrada = versaoOpt.get();
        versaoEncontrada.removerTeste(idTeste);

        if (versaoEncontrada.getTestes().isEmpty()) {
            sistemaEncontrado.removerVersao(versaoEncontrada.getVersao());
        }

        if (sistemaEncontrado.getVersoes().isEmpty()) {
            sistemas.removeIf(s -> s.getNome().equalsIgnoreCase(nomeSistema));
        }

        salvarSistemas(sistemas);
        return sistemas;
    }
}
