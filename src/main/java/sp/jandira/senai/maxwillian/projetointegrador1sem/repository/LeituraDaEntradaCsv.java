package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LeituraDaEntradaCsv {

    public static DadosDoCliente lerUltimaEntrada() {

        // Caminho do arquivo que contém as entradas do estacionamento
        Path path = Paths.get("Historico_entrada.csv");

        try {
            // Lê todas as linhas do arquivo CSV
            List<String> linhas = Files.readAllLines(path);

            // Se o arquivo estiver vazio, não há entrada registrada
            if (linhas.isEmpty()) return null;

            // Pega a última linha do arquivo (última entrada registrada)
            String ultimaLinha = linhas.get(linhas.size() - 1);

            // Divide a linha pelos separadores do CSV
            String[] dados = ultimaLinha.split(";");

            // Formatadores usados para converter data e hora do texto
            DateTimeFormatter formatterData =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatterHora =
                    DateTimeFormatter.ofPattern("HH:mm");

            // Converte a data e a hora separadamente
            LocalDate data = LocalDate.parse(dados[3], formatterData);
            LocalTime hora = LocalTime.parse(dados[4], formatterHora);

            // Junta data e hora em um único LocalDateTime
            LocalDateTime dataEntrada = LocalDateTime.of(data, hora);

            // Cria o objeto cliente com os dados lidos do arquivo
            DadosDoCliente cliente = new DadosDoCliente();
            cliente.nome = dados[0];
            cliente.carro = dados[1];
            cliente.placa = dados[2];

            // Define a data de entrada já convertida
            cliente.dataEntrada = LocalDateTime.parse(String.valueOf(dataEntrada));

            return cliente;

        } catch (Exception e) {
            // Caso ocorra qualquer erro na leitura ou conversão
            e.printStackTrace();
            return null;
        }
    }
}
