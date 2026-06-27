package com.davi.specbox.cli;

import java.io.IOException;
import java.util.Scanner;

public interface Comando {
    String getDescricao();

    void executar(Scanner scanner);
}
