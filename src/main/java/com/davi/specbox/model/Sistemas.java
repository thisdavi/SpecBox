package com.davi.specbox.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sistemas {
    private String nome;
    private List<Versoes> versoes;

    public Sistemas(String nome) {
        this.nome = nome;
        versoes = new ArrayList<>();
    }

    public void adicionarVersao(Versoes versao) {
        if (versao != null) {
            versoes.add(versao);
        }
    }

    public void removerVersao(String nomeVersao) {
        versoes.removeIf(versao -> versao.getVersao().equalsIgnoreCase(nomeVersao));
    }

    public Optional<Versoes> buscarVersao(String versao) {
        if (versao == null || versao.isBlank()) {
            throw new IllegalArgumentException("Versão inválida");
        }
        return versoes.
                stream().
                filter(v -> v.getVersao().equalsIgnoreCase(versao)).
                findFirst();
    }

    @Override
    public String toString() {
        return nome;
    }

    public List<Versoes> getVersoes() {
        return versoes;
    }

    public void setVersoes(List<Versoes> versoes) {
        this.versoes = versoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
