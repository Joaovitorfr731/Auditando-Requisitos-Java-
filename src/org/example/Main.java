package org.example;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcao = -1;

        // RF-01, RF-02 e RF-03: robô e programa
        boolean emergencia = false;
        String estadorobo = "Pronto";
        String cordorobo = "verde";
        boolean programaHabilitado = false;

        // RF-04: comunicação
        String comunicacao = "Online";

        // RF-05, RF-06, RF-07 e RF-14: produção
        int totalPecas = 0;
        int pecasCorretas = 0;
        int somaCiclos = 0;
        int[] pecasPorCor = new int[3];

        // RF-08, RF-09 e RF-10: câmera
        boolean cameraLigada = false;
        boolean rec = false;
        String estadoVisao = "Câmera desconectada";

        // RF-11: logs
        String[] logs = new String[100];
        int totalLogs = 0;

        // RF-12 e RF-13: indicadores
        double disponibilidade = 0;
        double oee = 0;

        // RF-16: identificação
        String instituicao = "SENAI";
        String nomeSistema = "Sistema de Monitoramento Integrado";

        // RF-18: notificações
        String[] notificacoes = new String[100];
        int totalNotificacoes = 0;

        // RF-19: usuário
        String usuario = "Operador";

        while (opcao != 0) {

            // RF-15: menu de navegação
            System.out.println();
            System.out.println("==========================================");
            System.out.println(" " + instituicao);
            System.out.println(" " + nomeSistema);
            System.out.println("==========================================");

            // RF-17: relógio
            System.out.println("Horário: " + LocalTime.now().withNano(0));
            System.out.println("Usuário: " + usuario);
            System.out.println("------------------------------------------");
            System.out.println("              MENU PRINCIPAL");
            System.out.println("------------------------------------------");
            System.out.println(" 1 - Parada de emergência");
            System.out.println(" 2 - Ver estado do robô");
            System.out.println(" 3 - Habilitar/Desabilitar programa");
            System.out.println(" 4 - Verificar comunicação");
            System.out.println(" 5 - Processar peça");
            System.out.println(" 6 - Ligar/Desligar câmera");
            System.out.println(" 7 - Ver logs");
            System.out.println(" 8 - Painel de produção");
            System.out.println(" 9 - Calcular OEE");
            System.out.println("10 - Liberar emergência");
            System.out.println("11 - Disponibilidade mecânica");
            System.out.println("12 - Central de notificações");
            System.out.println("13 - Perfil do usuário");
            System.out.println("14 - Configurações");
            System.out.println(" 0 - Sair");
            System.out.println("------------------------------------------");
            System.out.print("Escolha: ");
            opcao = teclado.nextInt();
            switch (opcao) {

                case 1: {
                    // RF-01: parada de emergência
                    if (emergencia == false) {
                        emergencia = true;
                        programaHabilitado = false;
                        System.out.println("PARADA DE EMERGÊNCIA ACIONADA!");
                        if (totalLogs < 100) {
                            logs[totalLogs] = LocalDateTime.now()
                                    + " - Parada de emergência acionada";
                            totalLogs++;
                        }
                        if (totalNotificacoes < 100) {
                            notificacoes[totalNotificacoes] =
                                    "ALERTA: Parada de emergência acionada";
                            totalNotificacoes++;
                        }
                    } else {
                        System.out.println("Emergência já acionada.");
                    }
                    break;
                }
                case 2: {
                    // RF-02, RF-03, RF-04, RF-08, RF-09 e RF-10
                    System.out.println("--- STATUS DO ROBÔ ---");
                    System.out.println("Estado: " + estadorobo);
                    System.out.println("Cor: " + cordorobo);
                    if (programaHabilitado == true) {
                        System.out.println("Programa: HABILITADO");
                    } else {
                        System.out.println("Programa: DESABILITADO");
                    }
                    System.out.println("Comunicação: " + comunicacao);
                    if (cameraLigada == true) {
                        System.out.println("Câmera: LIGADA");
                    } else {
                        System.out.println("Câmera: DESLIGADA");
                    }
                    if (rec == true) {
                        System.out.println("REC: ATIVO");
                    } else {
                        System.out.println("REC: INATIVO");
                    }
                    System.out.println("Visão computacional: " + estadoVisao);
                    break;
                }
                case 3: {
                    // RF-03: programa
                    if (emergencia == true) {
                        System.out.println("Não é possível habilitar durante a emergência.");
                    } else if (programaHabilitado == false) {
                        programaHabilitado = true;
                        System.out.println("Programa HABILITADO.");
                        if (totalLogs < 100) {
                            logs[totalLogs] = LocalDateTime.now()
                                    + " - Programa habilitado";
                            totalLogs++;
                        }
                    } else {
                        programaHabilitado = false;
                        System.out.println("Programa DESABILITADO.");
                        if (totalLogs < 100) {
                            logs[totalLogs] = LocalDateTime.now()
                                    + " - Programa desabilitado";
                            totalLogs++;
                        }
                    }
                    break;
                }
                case 4: {
                    // RF-04: comunicação
                    System.out.print("Segundos sem resposta do robô: ");
                    int segundos = teclado.nextInt();
                    if (segundos < 0) {
                        System.out.println("Valor inválido.");
                    } else if (segundos > 3) {
                        comunicacao = "Offline";
                        System.out.println("Comunicação OFFLINE.");
                        if (totalLogs < 100) {
                            logs[totalLogs] = LocalDateTime.now()
                                    + " - Comunicação perdida";
                            totalLogs++;
                        }
                        if (totalNotificacoes < 100) {
                            notificacoes[totalNotificacoes] =
                                    "ALERTA: Comunicação perdida";
                            totalNotificacoes++;
                        }
                    } else {
                        comunicacao = "Online";
                        System.out.println("Comunicação ONLINE.");
                    }
                    break;
                }
                case 5: {
                    // RF-05, RF-06, RF-07 e RF-14: processamento
                    if (emergencia == true) {
                        System.out.println("Emergência acionada. Processamento bloqueado.");
                    } else if (programaHabilitado == false) {
                        System.out.println("Programa desabilitado.");
                    } else if (comunicacao.equals("Offline")) {
                        System.out.println("Robô offline. Processamento bloqueado.");
                    } else {
                        System.out.println("Cor: 1- Vermelha  2- Verde  3- Azul");
                        int cor = teclado.nextInt();
                        if (cor < 1 || cor > 3) {
                            System.out.println("Cor inválida.");
                        } else {
                            System.out.print("Tempo de ciclo (segundos): ");
                            int ciclo = teclado.nextInt();
                            System.out.print("Peça correta? 1- Sim  0- Não: ");
                            int correta = teclado.nextInt();
                            if (ciclo <= 0 || (correta != 0 && correta != 1)) {
                                System.out.println("Dados inválidos.");
                            } else {
                                totalPecas++;
                                somaCiclos = somaCiclos + ciclo;
                                pecasPorCor[cor - 1]++;
                                if (correta == 1) {
                                    pecasCorretas++;
                                }
                                System.out.println("Peça registrada.");
                                if (totalLogs < 100) {
                                    logs[totalLogs] = LocalDateTime.now()
                                            + " - Peça " + totalPecas + " processada";
                                    totalLogs++;
                                }
                            }
                        }
                    }
                    break;
                }
                case 6: {
                    // RF-08 e RF-09: câmera e gravação
                    if (cameraLigada == false) {
                        cameraLigada = true;
                        rec = true;
                        System.out.println("Câmera LIGADA. REC ATIVO.");
                    } else {
                        cameraLigada = false;
                        rec = false;
                        System.out.println("Câmera DESLIGADA.");
                        if (totalNotificacoes < 100) {
                            notificacoes[totalNotificacoes] = "Câmera desligada";
                            totalNotificacoes++;
                        }
                    }
                    if (totalLogs < 100) {
                        logs[totalLogs] = LocalDateTime.now()
                                + " - Câmera " + (cameraLigada ? "ligada" : "desligada");
                        totalLogs++;
                    }
                    break;
                }
                case 7: {
                    // RF-11: logs
                    System.out.println("--- LOGS DO ROBÔ ---");
                    if (totalLogs == 0) {
                        System.out.println("Nenhum evento registrado.");
                    } else {
                        for (int i = 0; i < totalLogs; i++) {
                            System.out.println((i + 1) + " - " + logs[i]);
                        }
                    }
                    break;
                }
                case 8: {
                    // RF-05, RF-06, RF-07 e RF-14: painel de produção
                    System.out.println("--- PAINEL DE PRODUÇÃO ---");
                    System.out.println("Peças processadas: " + totalPecas);
                    if (totalPecas == 0) {
                        System.out.println("Tempo médio de ciclo: sem dados");
                        System.out.println("Taxa de acerto: sem dados");
                    } else {
                        double cicloMedio = somaCiclos * 1.0 / totalPecas;
                        double taxaAcerto = pecasCorretas * 100.0 / totalPecas;
                        System.out.println("Tempo médio de ciclo: " + cicloMedio + " s");
                        System.out.println("Taxa de acerto: " + taxaAcerto + "%");
                    }
                    System.out.println("--- PEÇAS POR COR ---");
                    System.out.println("Vermelhas: " + pecasPorCor[0]);
                    System.out.println("Verdes: " + pecasPorCor[1]);
                    System.out.println("Azuis: " + pecasPorCor[2]);
                    break;
                }
                case 9: {
                    // RF-12 e RF-13: OEE
                    if (totalPecas == 0) {
                        System.out.println("Sem peças para calcular o OEE.");
                    } else {
                        System.out.print("Tempo planejado (minutos): ");
                        int planejado = teclado.nextInt();
                        System.out.print("Tempo parado (minutos): ");
                        int parado = teclado.nextInt();
                        System.out.print("Tempo de ciclo ideal (segundos): ");
                        int cicloIdeal = teclado.nextInt();
                        if (planejado <= 0 || parado < 0 ||
                                parado > planejado || cicloIdeal <= 0) {
                            System.out.println("Valores inválidos.");
                        } else {
                            disponibilidade =
                                    (planejado - parado) * 100.0 / planejado;
                            double desempenho =
                                    cicloIdeal * totalPecas * 100.0 / somaCiclos;
                            if (desempenho > 100) {
                                desempenho = 100;
                            }
                            double qualidade =
                                    pecasCorretas * 100.0 / totalPecas;
                            oee = disponibilidade * desempenho * qualidade / 10000;
                            System.out.println("Disponibilidade: " + disponibilidade + "%");
                            System.out.println("Desempenho: " + desempenho + "%");
                            System.out.println("Qualidade: " + qualidade + "%");
                            System.out.println("OEE: " + oee + "%");
                        }
                    }
                    break;
                }
                case 10: {
                    // Liberação da emergência
                    if (emergencia == true) {
                        emergencia = false;
                        System.out.println("Emergência liberada.");
                        if (totalLogs < 100) {
                            logs[totalLogs] = LocalDateTime.now()
                                    + " - Emergência liberada";
                            totalLogs++;
                        }
                    } else {
                        System.out.println("Não há emergência acionada.");
                    }
                    break;
                }
                case 11: {
                    // RF-13: disponibilidade mecânica
                    System.out.print("Tempo planejado (minutos): ");
                    int planejado = teclado.nextInt();
                    System.out.print("Tempo de manutenção (minutos): ");
                    int parado = teclado.nextInt();
                    if (planejado <= 0 || parado < 0 || parado > planejado) {
                        System.out.println("Valores inválidos.");
                    } else {
                        double disponibilidadeMecanica =
                                (planejado - parado) * 100.0 / planejado;
                        System.out.println("Disponibilidade mecânica: "
                                + disponibilidadeMecanica + "%");
                    }
                    break;
                }
                case 12: {
                    // RF-18: central de notificações
                    System.out.println("--- CENTRAL DE NOTIFICAÇÕES ---");
                    if (totalNotificacoes == 0) {
                        System.out.println("Nenhuma notificação disponível.");
                    } else {
                        for (int i = 0; i < totalNotificacoes; i++) {
                            System.out.println((i + 1) + " - " + notificacoes[i]);
                        }
                    }
                    break;
                }
                case 13: {
                    // RF-19: perfil e logout
                    System.out.println("--- PERFIL DO USUÁRIO ---");
                    System.out.println("Usuário: " + usuario);
                    System.out.println("Instituição: " + instituicao);
                    System.out.println("Status: Logado");
                    System.out.println("1 - Sair do sistema");
                    System.out.println("2 - Voltar");
                    int escolha = teclado.nextInt();
                    if (escolha == 1) {
                        System.out.println("Sessão encerrada.");
                        if (totalLogs < 100) {
                            logs[totalLogs] = LocalDateTime.now()
                                    + " - Usuário encerrou a sessão";
                            totalLogs++;
                        }
                        opcao = 0;
                    } else {
                        System.out.println("Voltando ao menu.");
                    }
                    break;
                }
                case 14: {
                    // RF-15: configurações
                    System.out.println("--- CONFIGURAÇÕES ---");
                    System.out.println("Sistema: " + nomeSistema);
                    System.out.println("Instituição: " + instituicao);
                    System.out.println("Comunicação: " + comunicacao);
                    System.out.println("Câmera: " + (cameraLigada ? "Ligada" : "Desligada"));
                    System.out.println("Programa: "
                            + (programaHabilitado ? "Habilitado" : "Desabilitado"));
                    System.out.println("Emergência: "
                            + (emergencia ? "Acionada" : "Liberada"));
                    break;
                }
                case 0:
                    System.out.println("Encerrando o supervisório.");
                    break;
                default:
                    System.out.println("Esse comando não existe.");
            }
            // RF-02: atualização do estado e da cor do robô
            if (emergencia == true) {
                estadorobo = "Parado (emergência)";
                cordorobo = "vermelho";
            } else if (programaHabilitado == true) {
                estadorobo = "Em execução";
                cordorobo = "azul";
            } else {
                estadorobo = "Pronto";
                cordorobo = "verde";
            }
            // RF-10: atualização da visão computacional
            if (cameraLigada == false) {
                estadoVisao = "Câmera desconectada";
            } else if (programaHabilitado == true && emergencia == false) {
                estadoVisao = "Processando";
            } else {
                estadoVisao = "Ocioso";
            }
        }
        teclado.close();
    }
}
