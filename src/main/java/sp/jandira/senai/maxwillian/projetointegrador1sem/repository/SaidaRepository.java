package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaidaRepository {

    // Caminho do arquivo onde será salvo o histórico de saída
    private static final Path arquivo_saida = Paths.get("Historico_saida.csv");

    // Formato padrão usado para salvar data e hora no CSV
    private static final DateTimeFormatter formatador =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void gravarSaida(
            String nome,
            String carro,
            String placa,
            LocalDateTime dataEntrada
    ) {

        // Captura o momento exato em que o veículo está saindo
        LocalDateTime dataSaida = LocalDateTime.now();

        // Calcula o tempo total que o veículo ficou estacionado
        String tempoTotal = CalculoTempo.formatarDuracao(dataEntrada, dataSaida);

        // Calcula o valor total a ser pago com base no tempo
        double valorTotal = CalculoTempo.calcularCustoTotal(dataEntrada, dataSaida);

        // Monta a linha que será salva no CSV de saída
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

            // Grava a linha no arquivo Historico_saida.csv
            Files.writeString(
                    arquivo_saida,
                    linha,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            // Após salvar a saída, remove o veículo do histórico de entrada
            ClienteRepository repo = new ClienteRepository();
            repo.excluirRegistroPorPlaca(placa);

        } catch (Exception e) {
            // Exibe qualquer erro que ocorra durante a gravação
            e.printStackTrace();
        }
    }
}
