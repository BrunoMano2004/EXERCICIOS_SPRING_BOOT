package br.com.alura;

import java.util.Scanner;

public class AdopetConsoleApplication {

    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        Scanner sc = new Scanner(System.in);

        System.out.println("##### BOAS VINDAS AO SISTEMA ADOPET CONSOLE #####");
        try {
            int opcaoEscolhida = 0;
            while (opcaoEscolhida != 5){
                System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
                System.out.println("1 -> Listar abrigos cadastrados");
                System.out.println("2 -> Cadastrar novo abrigo");
                System.out.println("3 -> Listar pets do abrigo");
                System.out.println("4 -> Importar pets do abrigo");
                System.out.println("5 -> Sair");

                opcaoEscolhida = sc.nextInt();
                switch (opcaoEscolhida) {
                    case 1 -> commandExecutor.executeCommand(new ListarAbrigoCommand());
                    case 2 -> commandExecutor.executeCommand(new CadastrarAbrigoCommand());
                    case 3 -> commandExecutor.executeCommand(new ListarPetsDoAbrigoCommand());
                    case 4 -> commandExecutor.executeCommand(new ImportarDadosAbrigoCommand());
                    case 5 -> {
                        System.out.println("Finalizando o programa...");
                        System.exit(0);
                    }
                    default -> System.out.println("NÚMERO INVÁLIDO");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
