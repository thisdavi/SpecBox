package com.davi.delphos.service;

import com.davi.delphos.model.SistemaQA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class QAService {
    private static final Path PASTA = Path.of(System.getProperty("user.home"), ".delphosqa");
    private static final Path ARQUIVO = PASTA.resolve("qa_testes.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<SistemaQA> carregarSistemas() {
        if (!Files.exists(ARQUIVO)) {
            return new ArrayList<>();
        }
        try (BufferedReader ler = Files.newBufferedReader(ARQUIVO)) {
            List<SistemaQA> sistemas = gson.fromJson(ler, new TypeToken<List<SistemaQA>>() {
            }.getType());

            return sistemas != null ? sistemas : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
