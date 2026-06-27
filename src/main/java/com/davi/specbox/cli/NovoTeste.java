package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistemas;
import com.davi.specbox.model.Testes;
import com.davi.specbox.model.Versoes;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;
import java.util.Scanner;

public class NovoTeste implements Comando {

    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public NovoTeste(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Registrar novo teste";
    }

    @Override
    public void executar(Scanner scanner) {
        List<Sistemas> sistemasList = service.carregarSistemas();

        if (sistemasList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda. Cadastre um sistema primeiro");
            return;
        }

        Sistemas sistemaEscolhido = leitor.escolherDaLista("Selecione o sistema", sistemasList, Sistemas::getNome);
        List<Versoes> versoesDisponiveis = sistemaEscolhido.getVersoes();

        if (versoesDisponiveis.isEmpty()) {
            System.out.println("Nenhuma versão cadastrada ainda. Cadastre uma primeiro");
            return;
        }
        Versoes versaoEscolhida = leitor.escolherDaLista("Selecione a versão", versoesDisponiveis, Versoes::getVersao);

        System.out.println("Registrando novo teste na versão" + versaoEscolhida.getVersao());

        String titulo = leitor.lerTexto("Título:", true);
        Testes teste = new Testes(titulo);

        teste.setProcedimento(leitor.lerTexto("Procedimento realizado:", false));
        teste.setRetorno(leitor.lerTexto("Retorno obtido:", false));
        teste.setPrioridade(leitor.lerEnum("Prioridade:", Testes.Prioridade.class ));
        teste.setFrequencia(leitor.lerEnum("Frequência:", Testes.Frequencia.class));
        teste.setAmbiente(leitor.lerEnum("Ambiente:", Testes.Ambiente.class ));
        teste.setResultado(leitor.lerEnum("Resultado", Testes.Resultado.class));
        teste.setResponsavel(leitor.lerTexto("Responsável:", false));
        teste.setObs(leitor.lerTexto("Observações:", false));

        versaoEscolhida.adicionarTeste(teste);
        service.salvarSistemas(sistemasList);

        System.out.println("Novo teste registrado na versão " + versaoEscolhida.getVersao());
    }
}

