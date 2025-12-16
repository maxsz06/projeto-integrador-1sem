package br.senai.sp.jandira.projetointegradorbackendestacionamento.repository;



import br.senai.sp.jandira.projetointegradorbackendestacionamento.model.DadosDoCliente;
import br.senai.sp.jandira.projetointegradorbackendestacionamento.ui.RegistrarEntrada;
import javafx.scene.control.TextField;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ClienteRepository {

    public void gravarCliente(DadosDoCliente cliente) {

        LocalDateTime horaAtual = LocalDateTime.now();
        DateTimeFormatter formator = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String horaEntrada = horaAtual.format(formator);

        LocalDateTime horaSaidaAtual = LocalDateTime.now();
        String horaSaida = horaSaidaAtual.format(formator);

        Path arquivoEntrada= Paths.get("Historico_entrada.csv");
        Path arquivoSaida= Paths.get("Historico_saida.csv");
        try{
            Files.writeString(arquivoEntrada, cliente.nome+ ";" + cliente.carro + ";" + cliente.placa + ";" + horaEntrada + "\n", StandardOpenOption.APPEND);
            Files.writeString(arquivoSaida, cliente.nome+ ";" + cliente.carro + ";" + cliente.placa + ";" + horaEntrada + "\n", StandardOpenOption.APPEND);
        }catch (IOException e){
            System.out.println("Erro ao criar o arquivo");
            System.out.println(e.getMessage());
        }
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

        try (BufferedReader br = new BufferedReader(new FileReader("Historico_entrada.csv"))) {

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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Historico_entrada.csv"))) {
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




