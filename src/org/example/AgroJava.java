package org.example;
import java.io.*;
import java.util.Scanner;

public class AgroJava {
    static Scanner sc = new Scanner(System.in);
    static String[] dias = {"Segunda", "Terca", "Quarta", "Quinta", "Sexta", "Sabado", "Domingo"};
    static double[][] umidade = new double[4][4];
    static boolean mapaOk = false;

    // historico de chuvas lido do CSV
    static String[] datas = new String[500];
    static double[] medias = new double[500];
    static double[] totais = new double[500];
    static int qtd = 0;

    public static void main(String[] args) throws IOException {
        int op = -1;
        while (op != 0) {
            System.out.println("\n===== AGROJAVA =====");
            System.out.println("1) Cadastrar Dados");
            System.out.println("2) Exibir Mapa do Campo");
            System.out.println("3) Relatorio de Alertas de Irrigacao");
            System.out.println("4) Consultar Historico de Chuvas");
            System.out.println("5) Semana Mais Chuvosa");
            System.out.println("6) Dashboard Climatico");
            System.out.println("7) Relatorio Gerencial da Fazenda");
            System.out.println("8) Exportar Relatorio");
            System.out.println("0) Sair");
            op = (int) lerNumero("Opcao: ", 8);

            switch (op) {
                case 1:
                    System.out.println("1) Chuvas da semana   2) Umidade dos talhoes");
                    int tipo = (int) lerNumero("Opcao: ", 2);
                    if (tipo == 1) {
                        cadastrarChuvas();
                    } else if (tipo == 2) {
                        cadastrarTalhoes();
                    }
                    break;
                case 2: exibirMapa(); break;
                case 3: gerarRelatorioIrrigacao(); break;
                case 4: consultarHistoricoChuvas(); break;
                case 5: semanaMaisChuvosa(); break;
                case 6: gerarDashboardClimatico(); break;
                case 7: gerarRelatorioGerencial(""); break;
                case 8: exportarRelatorio(); break;
                case 0: System.out.println("Ate logo!"); break;
            }
        }
    }

    // le um numero entre 0 e max (aceita virgula ou ponto)
    static double lerNumero(String msg, double max) {
        double v = -1;
        while (v < 0 || v > max) {
            System.out.print(msg);
            try {
                v = Double.parseDouble(sc.nextLine().replace(",", "."));
            } catch (Exception e) {
                v = -1;
            }
            if (v < 0 || v > max) {
                System.out.println("Valor invalido!");
            }
        }
        return v;
    }

    // ---------- RF01 e RF04 ----------
    static void cadastrarChuvas() throws IOException {
        double[] chuva = new double[7];
        System.out.print("Data da semana (dd/mm/aaaa): ");
        String data = sc.nextLine();
        double total = 0;
        int maior = 0;
        int i = 0;
        while (i < 7) {
            chuva[i] = lerNumero("Chuva de " + dias[i] + " (mm): ", 1000);
            total += chuva[i];
            if (chuva[i] > chuva[maior]) {
                maior = i;
            }
            i++;
        }
        double media = total / 7;
        System.out.println("Media semanal: " + Math.round(media * 10) / 10.0 + " mm");
        System.out.println("Total: " + total + " mm");
        System.out.println("Dia mais chuvoso: " + dias[maior]);
        salvarHistoricoChuvas(data, chuva, media, total);
    }

    static void salvarHistoricoChuvas(String data, double[] chuva, double media, double total) throws IOException {
        File arq = new File("historico_chuvas.csv");
        boolean novo = !arq.exists();
        BufferedWriter bw = new BufferedWriter(new FileWriter(arq, true)); // true = nao apaga o que ja existe
        if (novo) {
            bw.write("data,seg,ter,qua,qui,sex,sab,dom,media,total");
            bw.newLine();
        }
        String linha = data;
        int i = 0;
        while (i < 7) {
            linha += "," + chuva[i];
            i++;
        }
        bw.write(linha + "," + media + "," + total);
        bw.newLine();
        bw.close();
        System.out.println("Semana salva!");
    }

    // ---------- RF05, RF06, RF07 ----------
    static void carregarHistorico() throws IOException {
        qtd = 0;
        File arq = new File("historico_chuvas.csv");
        if (!arq.exists()) {
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(arq));
        br.readLine(); // pula o cabecalho
        String linha = br.readLine();
        while (linha != null) {
            String[] c = linha.split(",");
            if (c.length == 10) {
                datas[qtd] = c[0];
                medias[qtd] = Double.parseDouble(c[8]);
                totais[qtd] = Double.parseDouble(c[9]);
                qtd++;
            }
            linha = br.readLine();
        }
        br.close();
    }

    static void consultarHistoricoChuvas() throws IOException {
        carregarHistorico();
        if (qtd == 0) {
            System.out.println("Nenhuma semana registrada.");
        }
        int i = 0;
        while (i < qtd) {
            System.out.println("\nSemana: " + datas[i]);
            System.out.println("Media: " + Math.round(medias[i] * 10) / 10.0 + " mm");
            System.out.println("Total: " + totais[i] + " mm");
            i++;
        }
    }

    static int posMaior() {
        int pos = 0;
        int i = 1;
        while (i < qtd) {
            if (totais[i] > totais[pos]) {
                pos = i;
            }
            i++;
        }
        return pos;
    }

    static int posMenor() {
        int pos = 0;
        int i = 1;
        while (i < qtd) {
            if (totais[i] < totais[pos]) {
                pos = i;
            }
            i++;
        }
        return pos;
    }

    static void semanaMaisChuvosa() throws IOException {
        carregarHistorico();
        if (qtd == 0) {
            System.out.println("Nenhuma semana registrada.");
        } else {
            int p = posMaior();
            System.out.println("Semana mais chuvosa:\n" + datas[p] + "\nTotal:\n" + totais[p] + " mm");
        }
    }

    static void gerarDashboardClimatico() throws IOException {
        carregarHistorico();
        if (qtd == 0) {
            System.out.println("Nenhuma semana registrada.");
            return;
        }
        double volume = 0;
        int i = 0;
        while (i < qtd) {
            volume += totais[i];
            i++;
        }
        System.out.println("===== DASHBOARD =====");
        System.out.println("Semanas cadastradas: " + qtd);
        System.out.println("Volume acumulado: " + volume + " mm");
        System.out.println("Maior chuva: " + totais[posMaior()] + " mm (" + datas[posMaior()] + ")");
        System.out.println("Menor chuva: " + totais[posMenor()] + " mm (" + datas[posMenor()] + ")");
        System.out.println("Media geral: " + Math.round(volume / qtd * 10) / 10.0 + " mm");
    }

    // ---------- RF02 e RF08 ----------
    static String nome(int i, int j) {
        return (char) ('A' + i) + "" + (j + 1);
    }

    static void cadastrarTalhoes() throws IOException {
        int i = 0;
        while (i < 4) {
            int j = 0;
            while (j < 4) {
                umidade[i][j] = lerNumero("Umidade do talhao " + nome(i, j) + " (%): ", 100);
                j++;
            }
            i++;
        }
        mapaOk = true;
        exibirMapa();
        salvarMapaTalhoes();
    }

    static void exibirMapa() {
        if (!mapaOk) {
            System.out.println("Cadastre a umidade dos talhoes primeiro!");
            return;
        }
        System.out.println("\n===== MAPA DO CAMPO (* = abaixo de 30%) =====");
        String alertas = "";
        int i = 0;
        while (i < 4) {
            int j = 0;
            while (j < 4) {
                System.out.print(umidade[i][j] + "%");
                if (umidade[i][j] < 30) {
                    System.out.print("*");
                    alertas += nome(i, j) + " ";
                }
                System.out.print("\t");
                j++;
            }
            System.out.println();
            i++;
        }
        if (alertas.equals("")) {
            System.out.println("Nenhum talhao precisa de irrigacao.");
        } else {
            System.out.println("Precisam de irrigacao: " + alertas);
        }
    }

    static void salvarMapaTalhoes() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("mapa_umidade.csv"));
        bw.write("talhao,umidade,status");
        bw.newLine();
        int i = 0;
        while (i < 4) {
            int j = 0;
            while (j < 4) {
                bw.write(nome(i, j) + "," + umidade[i][j] + "," + classificarTalhao(umidade[i][j]));
                bw.newLine();
                j++;
            }
            i++;
        }
        bw.close();
        System.out.println("Mapa salvo!");
    }

    // ---------- RF09, RF10, RF11, RF12 ----------
    static String classificarTalhao(double u) {
        if (u < 20) {
            return "Critico";
        } else if (u <= 30) {
            return "Atencao";
        } else {
            return "Adequado";
        }
    }

    static void gerarRelatorioIrrigacao() {
        if (!mapaOk) {
            System.out.println("Cadastre a umidade dos talhoes primeiro!");
            return;
        }
        System.out.println("===== ALERTAS =====");
        int total = 0;
        int i = 0;
        while (i < 4) {
            int j = 0;
            while (j < 4) {
                String status = classificarTalhao(umidade[i][j]);
                if (!status.equals("Adequado")) {
                    System.out.println(nome(i, j) + " - " + status);
                    total++;
                }
                j++;
            }
            i++;
        }
        System.out.println("Total de alertas: " + total);
    }

    static void gerarRelatorioGerencial(String data) {
        if (!mapaOk) {
            System.out.println("Cadastre a umidade dos talhoes primeiro!");
            return;
        }
        System.out.print(montarRelatorio(data));
    }

    static String montarRelatorio(String data) {
        double soma = 0;
        int crit = 0, aten = 0, adeq = 0;
        int si = 0, sj = 0, ui = 0, uj = 0; // posicao do mais seco e do mais umido
        int i = 0;
        while (i < 4) {
            int j = 0;
            while (j < 4) {
                soma += umidade[i][j];
                String status = classificarTalhao(umidade[i][j]);
                if (status.equals("Critico")) {
                    crit++;
                } else if (status.equals("Atencao")) {
                    aten++;
                } else {
                    adeq++;
                }
                if (umidade[i][j] < umidade[si][sj]) {
                    si = i;
                    sj = j;
                }
                if (umidade[i][j] > umidade[ui][uj]) {
                    ui = i;
                    uj = j;
                }
                j++;
            }
            i++;
        }
        return "===== RELATORIO DA FAZENDA =====\n" + data
                + "Media Geral: " + Math.round(soma / 16 * 10) / 10.0 + "%\n"
                + "Talhoes Criticos: " + crit + "\n"
                + "Talhoes em Atencao: " + aten + "\n"
                + "Talhoes Adequados: " + adeq + "\n"
                + "Necessidade de Irrigacao: " + Math.round((crit + aten) * 100.0 / 16) + "%\n"
                + "Talhao mais seco: " + nome(si, sj) + " - " + umidade[si][sj] + "%\n"
                + "Talhao mais umido: " + nome(ui, uj) + " - " + umidade[ui][uj] + "%\n";
    }

    static void exportarRelatorio() throws IOException {
        if (!mapaOk) {
            System.out.println("Cadastre a umidade dos talhoes primeiro!");
            return;
        }
        System.out.print("Data de hoje (dd/mm/aaaa): ");
        String data = sc.nextLine();
        BufferedWriter bw = new BufferedWriter(new FileWriter("relatorio_fazenda.txt"));
        bw.write(montarRelatorio("Data: " + data + "\n"));
        bw.close();
        System.out.println("Relatorio salvo em relatorio_fazenda.txt");
    }
}
