package com.davi.delphos.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class VersaoQA {
    private String versao;
    private List<TesteQA> listaTestes;

    public VersaoQA(String versao) {
        this.versao = versao;
        listaTestes = new ArrayList<>();
    }

    public void adicionarTeste(TesteQA teste) {
        if (teste != null) {
            listaTestes.add(teste);
        }
    }

    public void removerTeste(String id) {
        listaTestes.removeIf(testes -> testes.getId().equals(id));
    }

    public Optional<TesteQA> buscarTeste(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido");
        }
        return listaTestes.
                stream().
                filter(teste -> Objects.equals(teste.getId(), id)).
                findFirst();
    }

    @Override
    public String toString() {
        return versao;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public List<TesteQA> getTestes() {
        return listaTestes;
    }

    public void setTestes(List<TesteQA> testes) {
        this.listaTestes = testes;
    }
}
