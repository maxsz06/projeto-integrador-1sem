package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import java.time.Duration;
import java.time.LocalDateTime;

public class CalculoTempo {

    // Valor cobrado pela primeira hora de estacionamento
    private static final double primeiraHora = 10.00;

    // Valor cobrado por cada hora adicional
    private static final double horaSubsequente = 5.00;

    // Minutos mínimos para arredondar e cobrar mais uma hora
    private static final int arredondamento = 5;

    public static double calcularCustoTotal(
            LocalDateTime entrada,
            LocalDateTime saida
    ) {

        // Calcula a diferença de tempo entre entrada e saída
        Duration duracao = Duration.between(entrada, saida);

        // Usa valor absoluto para evitar tempo negativo
        long totalMinutos = duracao.abs().toMinutes();

        // Caso o tempo seja de até 1 hora, cobra apenas a primeira hora
        if (totalMinutos <= 60) {
            return primeiraHora;
        }

        // Começa o cálculo já com o valor da primeira hora
        double custoTotal = primeiraHora;

        // Minutos que excedem a primeira hora
        long minutosExcedentes = totalMinutos - 60;

        // Quantidade de horas completas após a primeira hora
        long horasCompletas = minutosExcedentes / 60;

        // Minutos restantes que não completaram uma hora cheia
        long minutosResiduais = minutosExcedentes % 60;

        long horasPagas = horasCompletas;

        // Se passar do limite mínimo de minutos, cobra mais uma hora
        if (minutosResiduais >= arredondamento) {
            horasPagas++;
        }

        // Soma o valor das horas adicionais ao custo total
        custoTotal += horasPagas * horaSubsequente;
        return custoTotal;
    }

    public static String formatarDuracao(
            LocalDateTime entrada,
            LocalDateTime saida
    ) {

        // Calcula a duração total entre entrada e saída
        Duration duracao = Duration.between(entrada, saida);

        // Converte tudo para minutos
        long minutos = duracao.toMinutes();

        // Converte minutos em horas e minutos restantes
        long horas = minutos / 60;
        long resto = minutos % 60;

        // Retorna o tempo no formato legível para o usuário
        return horas + "h " + resto + "min";
    }
}
