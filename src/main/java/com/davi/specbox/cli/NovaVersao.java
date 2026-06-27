package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistemas;
import com.davi.specbox.model.Versoes;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;
import java.util.Scanner;

public class NovaVersao implements Comando {

    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public NovaVersao(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Cadastrar nova versão";
    }

    @Override
    public void executar(Scanner scanner) {
        List<Sistemas> sistemasList = service.carregarSistemas();
        if (sistemasList.isEmpty()) {
            System.out.println("Nenhum sistema cadastrado ainda. Cadastre um sistema primeiro");
            return;
        }

        Sistemas sistemaEscolhido = leitor.escolherDaLista("Selecione o sistema:", sistemasList, Sistemas::getNome);
        System.out.print("Cadastrando nova versão para o sistema: " + sistemaEscolhido.getNome());
        String nomeVersao = leitor.lerTexto("Versão:", true);

        List<Versoes> versoes = sistemaEscolhido.getVersoes();

        boolean jaExiste = versoes.stream()
                .anyMatch(v -> v.getVersao().equalsIgnoreCase(nomeVersao));

        if (jaExiste) {
            System.out.print("O Sistema \"" + sistemaEscolhido.getNome() + "\" já possui a versão" + "\"" + nomeVersao + "\" cadastrada");
            return;
        }

        sistemaEscolhido.adicionarVersao(new Versoes(nomeVersao));
        service.salvarSistemas(sistemasList);
        System.out.println("Versão \"" + nomeVersao + "\" cadastrada com sucesso!");
    }
}
