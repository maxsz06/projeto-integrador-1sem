package sp.jandira.senai.maxwillian.projetointegrador1sem.ui;


import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.TelaPrincipal;

import sp.jandira.senai.maxwillian.projetointegrador1sem.repository.ClienteRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sp.jandira.senai.maxwillian.projetointegrador1sem.model.DadosDoCliente;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class RegistrarEntrada extends VBox {

    ClienteRepository clienteRepository;
    DadosDoCliente dadosDoCliente;


    TextField nomeUser;   //Variavel para guardar o nome do usuario de forma global do arquivo (como os subsequentes)
    TextField veiculoCliente;
    TextField placaCliente;
    public String nome;
    public String placa;
    public String carro;

    public RegistrarEntrada() {
        telaDeRegistro();
    }

    public void telaDeRegistro() {

        clienteRepository = new ClienteRepository();
        dadosDoCliente = new DadosDoCliente();

        this.setStyle("-fx-background-color: #F0D49B;");


        //Configurar o header da tela
        VBox header = new VBox();
        header.setStyle("-fx-padding: 40;-fx-background-color: #f0d49b");
        Label titulo = new Label("Cadastro de Veículo");
        titulo.setStyle("-fx-text-fill: black;-fx-font-size: 40;-fx-font-weight: bold;");
        titulo.setAlignment(Pos.TOP_LEFT);         //Alinhar o header no centro
        titulo.setMaxWidth(Double.MAX_VALUE);    //Necessário determinar a largura do header para o alinhamento
        header.getChildren().add(titulo);


        //Configurar o main
        VBox main = new VBox();
        //main.setStyle("-fx-background-color: #ffffff");
        main.setPrefHeight(700);
        main.setPrefWidth(200);

        //Adicionando os intens

        main.setSpacing(10);
        //Criando a Hbox do nome do usuário
        HBox nameUser = new HBox();
        Label labelnome = new Label("Digite seu Nome: ");

        nomeUser = new TextField();

        HBox modeloVeiculo = new HBox();
        Label veiculo = new Label("Modelo do veículo: ");


        veiculoCliente = new TextField();
        modeloVeiculo.getChildren().addAll(veiculo,veiculoCliente);

        HBox placaDoVeiculo = new HBox();
        Label placa = new Label("Placa do Veículo: ");


        placaCliente = new TextField();
        placaDoVeiculo.getChildren().addAll(placa, placaCliente);

        HBox classeTempo = new HBox();

        // Pegando o timestamp atual formatado
        RegistrarEntrada.dataEntrada = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        // Criando o Label com o timestamp
        String dataEntradaFormatada =
                RegistrarEntrada.dataEntrada.format(formatter);
        Label horario = new Label(dataEntradaFormatada);
        System.out.println("Entrada: " + RegistrarEntrada.dataEntrada);

        // Adicionando o Label ao HBox
        classeTempo.getChildren().add(horario);

        //Alterando o estilo do texto

        classeTempo.setStyle("-fx-background-color: red;");
        classeTempo.setAlignment(Pos.CENTER);
        classeTempo.setStyle("-fx-font-size: 15");


        //Configurando o layout da Hbox do nome do usuário

        nameUser.setStyle("-fx-spacing: 40; -fx-font-size: 20");
        modeloVeiculo.setStyle("-fx-spacing: 25; -fx-font-size: 20");
        placaDoVeiculo.setStyle("-fx-spacing: 45; -fx-font-size: 20");


        nameUser.getChildren().addAll(labelnome, nomeUser);

        main.getChildren().addAll(nameUser, modeloVeiculo, placaDoVeiculo,  classeTempo);
        main.setPadding(new Insets(150 ,400, 200, 400 ));
        main.setSpacing(30);


        // FOOTER

        HBox footer= new HBox();
        // footer.setStyle("-fx-background-color: #ffffff");
        footer.setPrefHeight(300);
        footer.setPrefWidth(200);

        //Botao para fechar o sistema
        Button confirmar = new Button("Confirmar");

        confirmar.setOnAction(actionEvent ->
        {
                registrarEntrada();
                try {
                    // Criar nova cena da TelaPrincipal
                    TelaPrincipal telaPrincipal = new TelaPrincipal();

                    Stage stageAtual = (Stage) confirmar.getScene().getWindow();

                    // Chamar o start() da tela principal novamente no mesmo Stage
                    telaPrincipal.start(stageAtual);

                }
                //Informar se irá ocorrer algum durante a execução do código dentro do try
                catch (Exception ex) {       //Guardar o erro dentro da variável ex
                    ex.printStackTrace();    //Imprimir o erro no console
                }
        });

        //Configurando o botao confirmar
        confirmar.setPrefWidth(200);
        confirmar.setPrefHeight(100);
        confirmar.setFont(Font.font("arial", 16));

        //Configurando o layout do botao confirmar
        DropShadow shadow3 = new DropShadow();
        shadow3.setRadius(8);
        confirmar.setStyle("-fx-background-color: #93E681; -fx-text-fill: black; -fx-background-radius: 12;");
        confirmar.setOnMouseEntered(passarMouse -> confirmar.setEffect(shadow3));
        confirmar.setOnMouseExited(mouseJaPassou -> confirmar.setEffect(null));
        confirmar.setOnMousePressed(clickIn -> {
            confirmar.setScaleX(0.92);
            confirmar.setScaleY(0.92);
        });
        confirmar.setOnMouseReleased(clickOut -> {
            confirmar.setScaleX(1);
            confirmar.setScaleY(1);
        });

        footer.setAlignment(Pos.CENTER);
        footer.setSpacing(20
        );


        //Botao para fechar o sistema
        Button voltar = new Button("Voltar");

        //Configurando o botao voltar
        voltar.setPrefWidth(200);
        voltar.setPrefHeight(100);
        voltar.setFont(Font.font("arial", 16));

        //Configurando o layout do botao voltar
        DropShadow shadow5 = new DropShadow();
        shadow5.setRadius(8);
        voltar.setStyle("-fx-background-color: FF8989; -fx-text-fill: black; -fx-background-radius: 12;");
        voltar.setOnMouseEntered(passarMouse -> voltar.setEffect(shadow3));
        voltar.setOnMouseExited(mouseJaPassou -> voltar.setEffect(null));
        voltar.setOnMousePressed(clickIn -> {
            voltar.setScaleX(0.92);
            voltar.setScaleY(0.92);
        });
        voltar.setOnMouseReleased(clickOut -> {
            voltar.setScaleX(1);
            voltar.setScaleY(1);
        });

        voltar.setOnAction(e -> {

            try {
                // Criar nova cena da TelaPrincipal
                TelaPrincipal telaPrincipal = new TelaPrincipal();

                Stage stageAtual = (Stage) voltar.getScene().getWindow();

                // Chamar o start() da tela principal novamente no mesmo Stage
                telaPrincipal.start(stageAtual);

            }
            //Informar se irá ocorrer algum durante a execução do código dentro do try
            catch (Exception ex) {       //Guardar o erro dentro da variável ex
                ex.printStackTrace();    //Imprimir o erro no console
            }
        });


        String nomeUsuario = labelnome.getText();

        footer.getChildren().addAll(confirmar,voltar);
        this.getChildren().addAll(header, main, footer);
    }

    //Configurando funções dos botões
    public void fechar() {
        if (placa.isEmpty() || carro.isEmpty() || nome.isEmpty()) {
            placaCliente.setPromptText("Insira a Placa");
            veiculoCliente.setPromptText("Insira o Veículo");
            nomeUser.setPromptText("Insira o nome ");
            return;
        }
    }
    public void registrarEntrada() {

        dadosDoCliente.carro = veiculoCliente.getText();
        dadosDoCliente.nome = nomeUser.getText();
        dadosDoCliente.placa = placaCliente.getText();
        clienteRepository.gravarCliente(dadosDoCliente);

    }

    public static LocalDateTime dataEntrada;


}