package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;
import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;

import java.io.IOException;
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
        Path path = Paths.get("Historico_entrada.csv");

        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) return null;

            String ultimaLinha = linhas.get(linhas.size() - 1);
            String[] dados = ultimaLinha.split(";");

            DateTimeFormatter formatterData =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatterHora =
                    DateTimeFormatter.ofPattern("HH:mm");

            LocalDate data = LocalDate.parse(dados[3], formatterData);
            LocalTime hora = LocalTime.parse(dados[4], formatterHora);
            LocalDateTime dataEntrada = LocalDateTime.of(data, hora);

            DadosDoCliente cliente = new DadosDoCliente();
            cliente.nome = dados[0];
            cliente.carro = dados[1];
            cliente.placa = dados[2];
            cliente.dataEntrada = LocalDateTime.parse(String.valueOf(dataEntrada));

            return cliente;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}