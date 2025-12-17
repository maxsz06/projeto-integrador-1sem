package sp.jandira.senai.maxwillian.projetointegrador1sem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DadosDoCliente {

    public String placa;
    public String carro;
    public String nome;
    public LocalDateTime dataEntrada;
    public String horaEntrada;

    // Construtor vazio (OBRIGATÓRIO)
    public DadosDoCliente() {
    }

    /*
     * Construtor usado pelo Repository
     * O Repository já deve mandar o LocalDateTime pronto
     */
    public DadosDoCliente(
            String nome,
            String carro,
            String placa,
            LocalDateTime dataEntrada
    ) {
        this.nome = nome;
        this.carro = carro;
        this.placa = placa;
        this.dataEntrada = dataEntrada;
        this.horaEntrada = dataEntrada.toLocalTime().format(
                DateTimeFormatter.ofPattern("HH:mm")
        );
    }

    @Override
    public String toString() {
        return nome + " | " +
                carro + " | Placa: " +
                placa + " | Entrada: " +
                dataEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
