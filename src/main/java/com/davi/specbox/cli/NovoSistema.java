package com.davi.specbox.cli;

import com.davi.specbox.io.LeitorConsole;
import com.davi.specbox.model.Sistemas;
import com.davi.specbox.service.SpecBoxService;

import java.util.List;
import java.util.Scanner;

public class NovoSistema implements Comando {
    private final SpecBoxService service;
    private final LeitorConsole leitor;

    public NovoSistema(SpecBoxService service, LeitorConsole leitor) {
        this.service = service;
        this.leitor = leitor;
    }

    @Override
    public String getDescricao() {
        return "Cadastrar novo sistema";
    }

    @Override
    public void executar(Scanner entrada) {

        System.out.println("Cadastrando novo sistema");
        String nomeSistema = leitor.lerTexto("Nome do Sistema: ", true);

        List<Sistemas> sistemas = service.carregarSistemas();
        boolean jaExiste = sistemas.stream()
                .anyMatch(s -> s.getNome().equalsIgnoreCase(nomeSistema));
        if (jaExiste) {
            System.out.println("O Sistema \"" + nomeSistema + "\" já está cadastrado");
            return;
        }

        sistemas.add(new Sistemas(nomeSistema));
        service.salvarSistemas(sistemas);
    }
}
