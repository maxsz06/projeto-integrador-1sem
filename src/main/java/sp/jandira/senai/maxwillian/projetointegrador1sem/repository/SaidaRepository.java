package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaidaRepository {

    public static void gravarSaida(
            String nome,
            String carro,
            String placa,
            LocalDateTime dataEntrada,
            LocalDateTime dataSaida

    ) {

        Path arquivo = Paths.get("Historico_saida.csv");

        DateTimeFormatter formatador =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String linha =
                nome + ";" +
                        carro + ";" +
                        placa + ";" +
                        dataEntrada.format(formatador) + ";" +
                        dataSaida.format(formatador) + ";" +
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