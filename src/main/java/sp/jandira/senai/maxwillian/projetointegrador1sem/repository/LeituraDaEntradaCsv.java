package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;
import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LeituraDaEntradaCsv {

    public static DadosDoCliente lerUltimaEntrada() {
        Path path = Paths.get("historicoDeEntrada.csv");
        try {
            List<String> linhas = Files.readAllLines(path);

            if (linhas.isEmpty()) return null;

            // Pega a última linha do CSV
            String ultimaLinha = linhas.get(linhas.size() - 1);

            // Seu CSV usa ;
            String[] dados = ultimaLinha.split(";");

            DadosDoCliente cliente = new DadosDoCliente();
            cliente.nome = dados[0];
            cliente.carro = dados[1];
            cliente.placa = dados[2];

            DateTimeFormatter formatador =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            cliente.dataEntrada = String.valueOf(LocalDateTime.parse(dados[3], formatador));

            return cliente;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
