package sp.jandira.senai.maxwillian.projetointegrador1sem.repository;

import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.Pagamentos;
import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.RegistrarEntrada;

import java.time.Duration;
import java.time.LocalDateTime;
import java.text.NumberFormat;
import java.util.Locale;

// Certifique-se de que esta classe está importando RegistrarEntrada e Pagamentos corretamente
public class CalculoTempo {

    // --- Constantes de Tarifa (Definidas em um local central) ---
    private static final double TARIFA_PRIMEIRA_HORA = 10.00;
    private static final double TARIFA_HORA_SUBSEQUENTE = 5.00;
    private static final int REGRA_ARREDONDAMENTO_MINUTOS = 5;

    public static double calcularCustoTotal() {

        // 1. Obter a Duração
        Duration duracao = Duration.between(
                RegistrarEntrada.dataEntrada,
                Pagamentos.dataSaida
        );
        long totalMinutos = duracao.toMinutes();

        if (totalMinutos <= 60) {
            return 0.0;
        }

        // 2. Cobrança da Primeira Hora
        double custoTotal = TARIFA_PRIMEIRA_HORA;
        long minutosExcedentes = totalMinutos - 60;

        if (minutosExcedentes <= 0) {
            return custoTotal;
        }

        // 3. Cálculo das Horas Subsequentes com Arredondamento
        long horasCompletas = minutosExcedentes / 60;
        long minutosResiduais = minutosExcedentes % 60;

        long horasSubsequentesPagas = horasCompletas;

        // Regra de Arredondamento: >= 5 minutos extras paga 1 hora inteira
        if (minutosResiduais >= REGRA_ARREDONDAMENTO_MINUTOS) {
            horasSubsequentesPagas += 1;
        }

        // 4. Custo Final
        custoTotal += horasSubsequentesPagas * TARIFA_HORA_SUBSEQUENTE;

        return custoTotal;
    }

    // Mantendo sua função original para exibir a duração (se for útil)
    public static String formatarDuracao() {
        Duration duracao = Duration.between(RegistrarEntrada.dataEntrada, Pagamentos.dataSaida);
        long minutos = duracao.toMinutes();
        long horas = minutos / 60;
        long restoMinutos = minutos % 60;
        return horas + "h " + restoMinutos + "min";
    }
}