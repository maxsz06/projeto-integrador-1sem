package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;
import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.RegistrarEntrada;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private static final Path arquivoEntrada = Paths.get("Historico_entrada.csv");
    private static final String separador = ";";

    private static final DateTimeFormatter formatarData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter formatarHora =
            DateTimeFormatter.ofPattern("HH:mm");

    // ================== GRAVAR CLIENTE ==================
    public void gravarCliente(DadosDoCliente cliente) {

        LocalDate dataAtual = LocalDate.now();
        LocalTime horaAtual = LocalTime.now();

        String dataEntrada = dataAtual.format(formatarData);
        String horaEntrada = horaAtual.format(formatarHora);

        try {
            Files.writeString(
                    arquivoEntrada,
                    cliente.nome + separador +
                            cliente.carro + separador +
                            cliente.placa + separador +
                            dataEntrada + separador +
                            horaEntrada + "\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.out.println("Erro ao gravar cliente.");
            e.printStackTrace();
        }
    }

    // ================== LISTAR TODOS ==================
    public static List<DadosDoCliente> listarTodos() {
        List<DadosDoCliente> clientes = new ArrayList<>();

        try {
            List<String> linhas = Files.readAllLines(arquivoEntrada);

            for (int i = 1; i < linhas.size(); i++) { // pula cabeçalho
                String linha = linhas.get(i).trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(separador);
                if (dados.length < 5) continue;

                String nome = dados[0].trim();
                String carro = dados[1].trim();
                String placa = dados[2].trim();

                LocalDate data = LocalDate.parse(dados[3].trim(), formatarData);
                LocalTime hora = LocalTime.parse(dados[4].trim(), formatarHora);
                LocalDateTime dataEntrada = LocalDateTime.of(data, hora);

                DadosDoCliente cliente = new DadosDoCliente(
                        nome,
                        carro,
                        placa,
                        dataEntrada
                );

                clientes.add(cliente);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler Historico_entrada.csv");
            e.printStackTrace();
        }

        return clientes;
    }

    // ================== RECEBER DADOS ==================
    public void receberDados(TextField nomeUser, TextField placaCliente, TextField veiculoCliente) {

        DadosDoCliente cliente = new DadosDoCliente();
        cliente.nome = nomeUser.getText();
        cliente.placa = placaCliente.getText();
        cliente.carro = veiculoCliente.getText();

        gravarCliente(cliente);
    }

    // ================== EXCLUIR POR PLACA ==================
    public boolean excluirRegistroPorPlaca(String placaVeiculo) {

        List<String> linhasParaManter = new ArrayList<>();
        boolean registroEncontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader("Historico_entrada.csv"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] colunas = linha.split(separador);

                if (colunas.length > 2 &&
                        colunas[2].trim().equalsIgnoreCase(placaVeiculo.trim())) {
                    registroEncontrado = true;
                } else {
                    linhasParaManter.add(linha);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (!registroEncontrado) return true;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Historico_entrada.csv"))) {
            for (String linha : linhasParaManter) {
                bw.write(linha);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
