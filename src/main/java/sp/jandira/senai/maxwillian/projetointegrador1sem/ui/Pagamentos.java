package sp.jandira.senai.maxwillian.projetointegrador1sem.ui;

import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;
import sp.jandira.senai.maxwillian.projetointegrador1sem.repository.CalculoTempo;
import sp.jandira.senai.maxwillian.projetointegrador1sem.repository.SaidaRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import sp.jandira.senai.maxwillian.projetointegrador1sem.repository.LeituraDaEntradaCsv;

public class Pagamentos extends VBox {

    // Label que mostra o valor final calculado
    private Label labelValorTotal = new Label("R$ 0,00");

    // Lê a última entrada registrada no arquivo CSV
    DadosDoCliente cliente = LeituraDaEntradaCsv.lerUltimaEntrada();

    // Data e hora de entrada do cliente
    LocalDateTime dataEntrada = cliente.dataEntrada;

    // Momento atual (saída)
    LocalDateTime dataSaida = LocalDateTime.now();

    // Cálculo inicial do valor do estacionamento
    double valor = CalculoTempo.calcularCustoTotal(
            dataEntrada,
            dataSaida
    );

    public Pagamentos(){
        TeladePagamento();
    }

    public void TeladePagamento() {

        // Define a cor de fundo da tela
        this.setStyle("-fx-background-color: #F0D49B;");

        // Área do título da tela
        VBox headerTitulo = new VBox();
        Label titulo = new Label("Valor à ser pago");
        titulo.setStyle("-fx-text-fill: black;-fx-font-size: 32; -fx-font-weight: Bold;");
        headerTitulo.getChildren().add(titulo);
        VBox.setMargin(headerTitulo, new Insets(50, 0, 0, 50));

        // Texto fixo indicando data e hora da saída
        Label dataHorarioTitulo = new Label("Data e horário de saida: ");
        dataHorarioTitulo.setStyle("-fx-text-fill: black;-fx-font-size: 25; ");

        // Captura o horário atual e formata para exibição
        LocalDateTime dataSaida = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHorarioValor = dataSaida.format(formatador);

        // Atualiza o valor exibido no label
        labelValorTotal.setText(formatarMoeda(valor));

        // Calcula o tempo total que o carro ficou estacionado
        String tempo = CalculoTempo.formatarDuracao(dataEntrada, dataSaida);
        System.out.println("Tempo total: " + tempo);

        // Label que mostra a data e hora formatadas
        Label dataHorarioValorLabel = new Label(dataHorarioValor);
        dataHorarioValorLabel.setStyle("-fx-text-fill: black;-fx-font-size: 25;");

        // Texto fixo do valor do estacionamento
        Label valorDoEstacionamentoTitulo = new Label("Valor do estacionamento: ");
        valorDoEstacionamentoTitulo.setStyle("-fx-text-fill: black;-fx-font-size: 25;");

        // Verifica se existe um cliente válido antes de calcular
        if (cliente == null || cliente.dataEntrada == null) {
            labelValorTotal.setText("Nenhuma entrada registrada");
        } else {
            LocalDateTime dataEntrada = cliente.dataEntrada;
            dataSaida = LocalDateTime.now();

            double valor = CalculoTempo.calcularCustoTotal(
                    dataEntrada,
                    dataSaida
            );

            labelValorTotal.setText(formatarMoeda(valor));
        }

        // Grid responsável por organizar data/hora e valor em linhas
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        grid.add(dataHorarioTitulo, 0, 0);
        grid.add(dataHorarioValorLabel, 1, 0);

        grid.add(valorDoEstacionamentoTitulo, 0, 1);
        grid.add(labelValorTotal, 1, 1);

        VBox.setVgrow(grid, javafx.scene.layout.Priority.ALWAYS);

        // Botão de confirmação do pagamento
        Button btnDeEntrada = new Button("Confirmar");
        btnDeEntrada.setPrefWidth(200);
        btnDeEntrada.setPrefHeight(100);
        btnDeEntrada.setStyle("-fx-background-color: #93E681; -fx-font-size: 16px; -fx-background-radius: 12; -fx-font-weight: Bold;");

        // Sombra usada para efeito visual ao passar o mouse
        DropShadow shadow = new DropShadow();
        shadow.setRadius(8);

        btnDeEntrada.setOnMouseEntered(passarMouse -> btnDeEntrada.setEffect(shadow));
        btnDeEntrada.setOnMouseExited(mouseJaPassou -> btnDeEntrada.setEffect(null));

        // Animação de clique (reduz tamanho)
        btnDeEntrada.setOnMousePressed(clickIn -> {
            btnDeEntrada.setScaleX(0.92);
            btnDeEntrada.setScaleY(0.92);
        });

        // Volta o botão ao tamanho normal
        btnDeEntrada.setOnMouseReleased(clickOut -> {
            btnDeEntrada.setScaleX(1);
            btnDeEntrada.setScaleY(1);
        });

        // Ao confirmar, grava a saída no CSV
        btnDeEntrada.setOnAction(e -> {
            SaidaRepository.gravarSaida(
                    cliente.nome,
                    cliente.carro,
                    cliente.placa,
                    cliente.dataEntrada
            );
        });

        // Botão para voltar à tela principal
        Button btnDeSaida = new Button("Voltar");
        btnDeSaida.setPrefWidth(200);
        btnDeSaida.setPrefHeight(100);
        btnDeSaida.setStyle("-fx-background-color: #FF8989; -fx-font-size: 16px; -fx-background-radius: 12; -fx-font-weight: Bold;");
        btnDeSaida.setFont(Font.font("arial", 16));

        DropShadow shadow2 = new DropShadow();
        shadow2.setRadius(8);

        btnDeSaida.setOnMouseEntered(passarMouse -> btnDeSaida.setEffect(shadow2));
        btnDeSaida.setOnMouseExited(mouseJaPassou -> btnDeSaida.setEffect(null));

        btnDeSaida.setOnMousePressed(clickIn -> {
            btnDeSaida.setScaleX(0.92);
            btnDeSaida.setScaleY(0.92);
        });

        btnDeSaida.setOnMouseReleased(clickOut -> {
            btnDeSaida.setScaleX(1);
            btnDeSaida.setScaleY(1);
        });

        // Retorna para a tela principal reutilizando o mesmo Stage
        btnDeSaida.setOnAction(e -> {
            try {
                TelaPrincipal telaPrincipal = new TelaPrincipal();
                Stage stageAtual = (Stage) btnDeSaida.getScene().getWindow();
                telaPrincipal.start(stageAtual);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Caixa que organiza os botões lado a lado
        HBox boxBotoes = new HBox(100);
        boxBotoes.setAlignment(Pos.BOTTOM_CENTER);
        boxBotoes.getChildren().addAll(btnDeEntrada, btnDeSaida);
        VBox.setMargin(boxBotoes, new Insets(30, 0, 50, 0));

        // Adiciona todos os componentes na tela
        this.getChildren().addAll(headerTitulo, grid, boxBotoes);
    }

    private String formatarMoeda(double valor) {
        // Formata o valor para o padrão brasileiro (R$)
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }
}
