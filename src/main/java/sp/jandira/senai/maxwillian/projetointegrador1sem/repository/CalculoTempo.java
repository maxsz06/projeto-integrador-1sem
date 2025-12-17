package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import java.time.Duration;
import java.time.LocalDateTime;

public class CalculoTempo {

    private static final double primeiraHora = 10.00;
    private static final double horaSubsequente = 5.00;
    private static final int arredondamento = 5;

    public static double calcularCustoTotal(
            LocalDateTime entrada,
            LocalDateTime saida
    ) {

        Duration duracao = Duration.between(entrada, saida);
        long totalMinutos = duracao.abs().toMinutes();

        // Até 1 hora
        if (totalMinutos <= 60) {
            return primeiraHora;
        }

        double custoTotal = primeiraHora;
        long minutosExcedentes = totalMinutos - 60;

        long horasCompletas = minutosExcedentes / 60;
        long minutosResiduais = minutosExcedentes % 60;

        long horasPagas = horasCompletas;

        if (minutosResiduais >= arredondamento) {
            horasPagas++;
        }

        custoTotal += horasPagas * horaSubsequente;
        return custoTotal;
    }

    public static String formatarDuracao(
            LocalDateTime entrada,
            LocalDateTime saida
    ) {
        Duration duracao = Duration.between(entrada, saida);
        long minutos = duracao.toMinutes();
        long horas = minutos / 60;
        long resto = minutos % 60;
        return horas + "h " + resto + "min";
    }
}
