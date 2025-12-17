package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaidaRepository {

    private static final Path arquivo_saida = Paths.get("Historico_saida.csv");

    private static final DateTimeFormatter formatador =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void gravarSaida(
            String nome,
            String carro,
            String placa,
            LocalDateTime dataEntrada
    ) {

        // 1️⃣ Momento real da saída
        LocalDateTime dataSaida = LocalDateTime.now();

        // 2️⃣ Cálculos
        String tempoTotal = CalculoTempo.formatarDuracao(dataEntrada, dataSaida);
        double valorTotal = CalculoTempo.calcularCustoTotal(dataEntrada, dataSaida);

        // 3️⃣ Montar linha do CSV
        String linha =
                nome + ";" +
                        carro + ";" +
                        placa + ";" +
                        dataEntrada.format(formatador) + ";" +
                        dataSaida.format(formatador) + ";" +
                        tempoTotal + ";" +
                        String.format("R$ %.2f", valorTotal).replace(".", ",") +
                        "\n";

        try {
            // 4️⃣ Gravar saída
            Files.writeString(
                    arquivo_saida,
                    linha,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            // 5️⃣ Remover da entrada
            ClienteRepository repo = new ClienteRepository();
            repo.excluirRegistroPorPlaca(placa);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
