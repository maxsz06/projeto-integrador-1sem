package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;


import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.RegistrarEntrada;

import javafx.scene.control.TextField;
import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class ClienteRepository {




    public void gravarCliente(DadosDoCliente cliente) {

        LocalDate dataAtual = LocalDate.now();
        LocalTime horaAtual = LocalTime.now();
        String dataEntrada = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String horaEntrada = horaAtual.format(DateTimeFormatter.ofPattern("HH:mm"));

        Path arquivoEntrada= Paths.get("D:\\dadosDosClientes\\historicoDeEntrada.csv");
        Path arquivoSaida= Paths.get("D:\\dadosDosClientes\\historicoDeSaida.csv");
        try{
            Files.writeString(arquivoEntrada, cliente.nome+ ";" + cliente.carro + ";" + cliente.placa + ";" + dataEntrada + ";" + horaEntrada + "\n", StandardOpenOption.APPEND);
            Files.writeString(arquivoSaida, cliente.nome+ ";" + cliente.carro + ";" + cliente.placa + ";" + dataEntrada + ";" + horaEntrada +  "\n", StandardOpenOption.APPEND);
        }catch (IOException e){
            System.out.println("Erro ao criar o arquivo");
            System.out.println(e.getMessage());
        }
    }

    private final Path arquivo = Paths.get("D:\\dadosDosClientes\\dados.csv");
    private final String separador = ";"; // O separador do seu CSV
    public List<DadosDoCliente> listarTodos() {
        List<DadosDoCliente> clientes = new ArrayList<>();

        try {
            // Ler todas as linhas do arquivo
            List<String> todasAsLinhas = Files.readAllLines(arquivo);

            // Pular a primeira linha (cabeçalho)
            for (int i = 1; i < todasAsLinhas.size(); i++) {
                String linha = todasAsLinhas.get(i);

                // Ignorar linhas vazias
                if (linha.trim().isEmpty()) {
                    continue;
                }

                // Dividir a linha usando o separador
                String[] dados = linha.split(separador);

                // Deve haver 5 campos (Nome, Modelo, Placa, Data_In, Hora_In)
                if (dados.length >= 5) {
                    // Remover espaços em branco ou o ';' extra no final
                    String nome = dados[0].trim();
                    String carro = dados[1].trim();
                    String placa = dados[2].trim();
                    String dataEntrada = dados[3].trim();
                    String horaEntrada = dados[4].trim().replace(";", ""); // Remove o ';' do final

                    DadosDoCliente cliente = new DadosDoCliente(
                            nome,
                            carro,
                            placa,
                            dataEntrada,
                            horaEntrada
                    );
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV.");
            e.printStackTrace();
        }

        return clientes;
    }

    public void receberDados (TextField nomeUser, TextField placaCliente, TextField veiculoCliente) {

        RegistrarEntrada registrarEntrada =  new RegistrarEntrada();
        DadosDoCliente cliente = new DadosDoCliente();

        cliente.nome = nomeUser.getText();
        cliente.placa = placaCliente.getText();
        cliente.carro = veiculoCliente.getText();


        gravarCliente(cliente);
    }

    public boolean excluirRegistroPorPlaca(String placaVeiculo) {

        List<String> linhasParaManter = new ArrayList<>();
        boolean registroEncontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader("historicoDeEntrada.csv"))) {

            String linha;

            // 1. Ler o arquivo, linha por linha
            while ((linha = br.readLine()) != null) {

                // Supondo que a PLACA é o PRIMEIRO campo (índice 0)
                String[] colunas = linha.split(";"); // Use o delimitador correto do seu CSV!

                if (colunas.length > 0) {
                    String placaDoRegistro = colunas[0].trim();

                    // 2. Comparar a placa e decidir se a linha deve ser mantida
                    if (placaDoRegistro.equalsIgnoreCase(placaVeiculo.trim())) {
                        // Esta é a linha a ser excluída, então NÃO a adicionamos à lista
                        registroEncontrado = true;
                    } else {
                        // Linha a ser mantida
                        linhasParaManter.add(linha);
                    }
                } else {
                    // Manter linhas vazias ou mal formatadas, se necessário.
                    linhasParaManter.add(linha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            return false;
        }

        // Se o registro não foi encontrado, não há o que excluir
        if (!registroEncontrado) {
            System.out.println("Aviso: Registro com a placa " + placaVeiculo + " não encontrado.");
            // Retorna true se você considerar que a "exclusão" foi bem-sucedida porque o item não existe mais
            return true;
        }

        // 3. Sobrescrever o arquivo original
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("historicoDeEntrada.csv"))) {
            for (String linha : linhasParaManter) {
                bw.write(linha);
                bw.newLine(); // Adiciona uma nova linha após cada registro
            }
            return true; // Exclusão bem-sucedida

        } catch (IOException e) {
            System.err.println("Erro ao escrever (sobrescrever) o arquivo CSV: " + e.getMessage());
            return false;
        }
    }
}



