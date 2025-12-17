package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SaidaRepository {

    public static void gravarSaida(
            String nome,
            String carro,
            String placa,
            LocalDateTime dataEntrada

    ) {

        Path arquivo = Paths.get("Historico_saida.csv");

        LocalDate dataAtual = LocalDate.now();
        LocalTime horaAtual = LocalTime.now();
        String dataSaida = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String horaSaida = horaAtual.format(DateTimeFormatter.ofPattern("HH:mm"));

        String linha =
                nome + ";" +
                        carro + ";" +
                        placa + ";" +
                        dataSaida + ";" +
                        horaSaida + ";" +
                        "\n";

        try {
            Files.writeString(
                    arquivo,
                    linha,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static final DateTimeFormatter FORMATADOR_DATETIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatarLocalDateTimeParaCSV(LocalDateTime dateTime) {
        return dateTime.format(FORMATADOR_DATETIME);
    }
}