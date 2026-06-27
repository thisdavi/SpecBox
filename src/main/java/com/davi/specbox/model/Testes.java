package com.davi.specbox.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Testes {
    public enum Resultado {
        APROVADO, REPROVADO, PARCIAL
    }

    public enum Frequencia {
        SEMPRE, AS_VEZES, RARAMENTE, UMA_VEZ
    }

    public enum Prioridade {
        CRITICA, ALTA, MEDIA, BAIXA
    }

    public enum Ambiente {
        PRODUCAO, HOMOLOGACAO, DESENVOLVIMENTO
    }

    private final String id;
    private String titulo;
    private String procedimento;
    private String retorno;
    private Resultado resultado;
    private Frequencia frequencia;
    private Ambiente ambiente;
    private Prioridade prioridade;
    private String responsavel;
    private String obs;
    private final String dataCriacao;
    private String dataEdicao;

    public Testes(String titulo) {
        id = UUID.randomUUID().toString();
        this.titulo = titulo;
        dataCriacao = getDataFormatada();
    }

    public String getResultadoFormatado(){
        if (resultado == null) return "-";
        return switch (resultado){
            case APROVADO  -> "\uD83D\uDFE2 Aprovado";
            case REPROVADO -> "\uD83D\uDD34 Reprovado";
            case PARCIAL   -> "\uD83D\uDFE0 Parcial";
        };
    }

    public String getFrequenciaFormatada(){
        if (frequencia == null) return "-";
        return switch (frequencia){
            case SEMPRE    -> "Sempre";
            case AS_VEZES  -> "Às vezes";
            case RARAMENTE -> "Raramente";
            case UMA_VEZ   -> "Uma vez";
        };
    }

    private String getDataFormatada() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void atualizarDataEdicao() {
         dataEdicao = getDataFormatada();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(String dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    public String getId() {
        return id;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }
}
