package sp.jandira.senai.maxwillian.projetointegrador1sem.ui;

import sp.jandira.senai.maxwillian.projetointegrador1sem.repository.CalculoTempo;
import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.TelaPrincipal;
import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;
import sp.jandira.senai.maxwillian.projetointegrador1sem.repository.SaidaRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

import static sp.jandira.senai.maxwillian.projetointegrador1sem.ui.RegistrarEntrada.dataEntrada;

public class Pagamentos extends VBox {
    private Label labelValorTotal = new Label("R$ 0,00");
    DadosDoCliente cliente = LeituraDaEntradaCsv.lerUltimaEntrada();
    public Pagamentos(){
        TeladePagamento();
    }

    public void TeladePagamento() {


        // Definir o tamanho da tela, cor e titulo
        this.setStyle("-fx-background-color: #F0D49B;");

        // Titulo do stage
        VBox headerTitulo = new VBox();
        Label titulo = new Label("Valor à ser pago");
        titulo.setStyle("-fx-text-fill: black;-fx-font-size: 32; -fx-font-weight: Bold;");
        headerTitulo.getChildren().add(titulo);
        VBox.setMargin(headerTitulo, new Insets(50, 0, 0, 50));

        // Label Data e o horário
        Label dataHorarioTitulo = new Label("Data e horário de saida: ");
        dataHorarioTitulo.setStyle("-fx-text-fill: black;-fx-font-size: 25; ");

        // Horário agora formatado
        LocalDateTime dataSaida = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHorarioValor = dataSaida.format(formatador);

        // String transformada em label da data e horário
        Label dataHorarioValorLabel = new Label(dataHorarioValor);
        dataHorarioValorLabel.setStyle("-fx-text-fill: black;-fx-font-size: 25;");

        Label valorDoEstacionamentoTitulo = new Label("Valor do estacionamento: ");
        valorDoEstacionamentoTitulo.setStyle("-fx-text-fill: black;-fx-font-size: 25;");

//        valorDoEstacionamentoTextField.setPromptText("R$ 0,00");

        System.out.println("Entrada: " + dataEntrada);
        System.out.println("Saída: " + dataSaida);

        if (dataEntrada == null) {
            labelValorTotal.setText("Nenhuma entrada registrada");
        } else {
            // aqui depois você pode calcular normalmente
        }


        // GridPane data e horário, valor do estacionamento
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        grid.add(dataHorarioTitulo, 0, 0);
        grid.add(dataHorarioValorLabel, 1, 0);

        grid.add(valorDoEstacionamentoTitulo, 0, 1);
        grid.add(labelValorTotal, 1, 1);


        VBox.setVgrow(grid, javafx.scene.layout.Priority.ALWAYS);

        // Botões de sair e entrada
        Button btnDeEntrada = new Button("Confirmar");
        btnDeEntrada.setPrefWidth(200);
        btnDeEntrada.setPrefHeight(100);
        btnDeEntrada.setStyle("-fx-background-color: #93E681; -fx-font-size: 16px; -fx-background-radius: 12; -fx-font-weight: Bold;");

        DropShadow shadow = new DropShadow();    //Adicionar uma sombra por traz do objeto ao passar o mouse
        shadow.setRadius(8);                     //Tamanho da sombra adicionada

        btnDeEntrada.setOnMouseEntered(passarMouse -> btnDeEntrada.setEffect(shadow));       //Aplicar o efeito escuro quando passa o mouse
        btnDeEntrada.setOnMouseExited(mouseJaPassou -> btnDeEntrada.setEffect(null));        //Retirar o efeito escuro quando o mouse já passou

        btnDeEntrada.setOnMousePressed(clickIn -> {
            btnDeEntrada.setScaleX(0.92);      //Reduzir o tamanho do botão ao clicar no mesmo
            btnDeEntrada.setScaleY(0.92);
        });

        btnDeEntrada.setOnMouseReleased(clickOut -> {
            btnDeEntrada.setScaleX(1);        //O tamanho volta ao normal após o clickOut do mouse
            btnDeEntrada.setScaleY(1);
        });

        btnDeEntrada.setOnAction(e -> {

            SaidaRepository.gravarSaida(
                    cliente.nome,
                    cliente.carro,
                    cliente.placa,
                    dataEntrada,
                    dataSaida
            );
        });


        // Criar botões
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

        btnDeSaida.setOnAction(e -> {

            try {
                // Criar nova cena da TelaPrincipal
                TelaPrincipal telaPrincipal = new TelaPrincipal();

                Stage stageAtual = (Stage) btnDeSaida.getScene().getWindow();

                // Chamar o start() da tela principal novamente no mesmo Stage
                telaPrincipal.start(stageAtual);

            }
            //Informar se irá ocorrer algum durante a execução do código dentro do try
            catch (Exception ex) {       //Guardar o erro dentro da variável ex
                ex.printStackTrace();    //Imprimir o erro no console
            }
        });


        HBox boxBotoes = new HBox(100);
        boxBotoes.setAlignment(Pos.BOTTOM_CENTER);
        boxBotoes.getChildren().addAll(btnDeEntrada, btnDeSaida);
        VBox.setMargin(boxBotoes, new Insets(30, 0, 50, 0));

        // Filhos do stage
        this.getChildren().addAll(headerTitulo, grid, boxBotoes);

    }

    private String formatarMoeda(double valor) {
        // Formato brasileiro para exibir R$
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    public void exibirValorAPagar() {

        // 1. Chamar a função de cálculo do custo
        double valorBruto = Double.parseDouble(String.valueOf(CalculoTempo.calcularCustoTotal()));

        // 2. Formatar o valor para R$
        String valorFormatado = formatarMoeda(valorBruto);

        // 3. Atualizar o texto do Label
        labelValorTotal.setText(valorFormatado);

        System.out.println("Valor total a pagar: " + valorFormatado);
    }

    public void chamarVolta() {

        exibirValorAPagar();
    }

    public static LocalDateTime dataSaida;

}


