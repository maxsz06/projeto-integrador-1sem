package sp.jandira.senai.maxwillian.projetointegrador1sem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DadosDoCliente {

    // Dados básicos do cliente e do veículo
    public String placa;
    public String carro;
    public String nome;

    // Data e hora completas da entrada
    public LocalDateTime dataEntrada;

    // Hora da entrada separada, usada mais para exibição
    public String horaEntrada;

    // Construtor vazio
    // Necessário para criar o objeto e preencher os dados depois
    public DadosDoCliente() {
    }

    // Construtor usado quando já se tem todas as informações prontas
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

        // Extrai apenas a hora da data de entrada e formata para HH:mm
        this.horaEntrada = dataEntrada.toLocalTime().format(
                DateTimeFormatter.ofPattern("HH:mm")
        );
    }

    // Método usado para exibir o cliente em ListView
    @Override
    public String toString() {
        return nome + " | " +
                carro + " | Placa: " +
                placa + " | Entrada: " +
                dataEntrada.format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                );
    }
}