package sp.jandira.senai.maxwillian.projetointegrador1sem.ui;

import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.RegistrarEntrada;
import sp.jandira.senai.maxwillian.projetointegrador1sem.repository.ClienteRepository;
import sp.jandira.senai.maxwillian.projetointegrador1sem.ui.Pagamentos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class TelaPrincipal extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //Definir o tamanho da tela
        stage.setWidth(1440);
        stage.setHeight(1024);

        //Controlando o fechamento ao clicar no fechar da janela
        stage.setOnCloseRequest(click -> {
            fechar();
            click.consume();
        });

        //bloquear o redimensionamento
        stage.setResizable(false);

        VBox root = new VBox();              //Criando o Vbox (Alinhamento dos itens na vertical)
        Scene scene = new Scene(root);       //Criando a cena que recebe como parametro o root
        root.setStyle("-fx-background-color: #F0D49B;");  //Cor de fundo do root (Próximo de um laranja)

        //Configurar o header da tela
        VBox header = new VBox();
        header.setStyle("-fx-padding: 10;-fx-background-color: #F7F7F2");
        Label titulo = new Label("Gerenciamento de estacionamento");
        titulo.setStyle("-fx-text-fill: black;-fx-font-size: 30;-fx-font-weight: bold;");
        titulo.setAlignment(Pos.CENTER);         //Alinhar o header no centro
        titulo.setMaxWidth(Double.MAX_VALUE);    //Necessário determinar a largura do header para o alinhamento
        header.getChildren().add(titulo);

        //Configurar o main
        VBox listaDeVeiculos = new VBox();
        listaDeVeiculos.setStyle("-fx-padding: 10; -fx-background-color: #BDE7DF");

        //Determinando o tamanho da Vbox listaDeVeículos
        listaDeVeiculos.setPrefWidth(1440);
        listaDeVeiculos.setPrefHeight(1024);

        //Criar o header da Vbox lista de veiculos
        VBox headerListaDeVeiculos = new VBox();
        headerListaDeVeiculos.setStyle("-fx-padding: 10;-fx-background-color: #E6F2F5");
        Label tituloListaDeVeiculos = new Label("Lista de veículos");
        tituloListaDeVeiculos.setStyle("-fx-text-fill: black;-fx-font-size: 30;-fx-font-weight: bold");
        tituloListaDeVeiculos.setAlignment(Pos.CENTER);
        tituloListaDeVeiculos.setMaxWidth(Double.MAX_VALUE);
        headerListaDeVeiculos.getChildren().add(tituloListaDeVeiculos);


        // Criar o tableview para visualizar o nome, modelo, placa, hora de entrada



        VBox vboxDosBotoes = new VBox();

        //Determinando o tamanho da Vbox vboxDosBotoes
        vboxDosBotoes.setPrefWidth(1440);
        vboxDosBotoes.setPrefHeight(512);
        vboxDosBotoes.setAlignment(Pos.CENTER_RIGHT);    //Alinhar itens da vboxDosBotoes ao centro e a direita
        vboxDosBotoes.setPadding(new Insets(20));

        //Criar Hbox para alinhar a Vbox listaDeVeiculos e vboxDosBotoes na horizontal
        HBox main = new HBox();
        main.getChildren().addAll(listaDeVeiculos, vboxDosBotoes);

        //Criar os botoes
        VBox boxbotaoSair = new VBox();
        boxbotaoSair.setPrefWidth(1440);
        boxbotaoSair.setPrefHeight(512);
        boxbotaoSair.setAlignment(Pos.BOTTOM_RIGHT);    //Alinhar os filhos da Vbox boxbotaoSair ao canto inferior direito
        boxbotaoSair.setPadding(new Insets(10));


        //Botao de cadastro
        Button cadastrar = new Button("Cadastro de veículos");

        //Configurando o botao cadastrar
        cadastrar.setPrefWidth(200);
        cadastrar.setPrefHeight(100);
        cadastrar.setFont(Font.font("arial", 16));

        //Configurando o layout do botao cadastrar
        DropShadow shadow = new DropShadow();    //Adiciona uma sombra por traz do objeto ao passar o mouse
        shadow.setRadius(8);                     //Tamanho da sombra
        cadastrar.setStyle("-fx-background-color: #93E681; -fx-text-fill: black; -fx-background-radius: 12;");

        cadastrar.setOnMouseEntered(passarMouse -> cadastrar.setEffect(shadow));       //Aplica o efeito Shadow quando passa o mouse
        cadastrar.setOnMouseExited(mouseJaPassou -> cadastrar.setEffect(null));        //Retira o efeito Shadow quando o mouse ja passou

        cadastrar.setOnMousePressed(clickIn -> {
            cadastrar.setScaleX(0.92);      //Reduz o tamanho do botao ao clicar no mesmo, ocasionando um efeito visual
            cadastrar.setScaleY(0.92);
        });

        cadastrar.setOnMouseReleased(clickOut -> {
            cadastrar.setScaleX(1);        //O tamanho volta ao normal após o clickOut do mouse
            cadastrar.setScaleY(1);
        });

        cadastrar.setOnAction(click -> {
            //Chamar a classe para cadastro
            RegistrarEntrada registrarEntrada = new RegistrarEntrada();
            Scene novoEntrada = new Scene(registrarEntrada,1440,1024);
            Stage stageExistente = (Stage) cadastrar.getScene().getWindow();
            stageExistente.setScene(novoEntrada);
        });


        //Botao de registro de saida/pagamento
        Button registrarSaida = new Button("Saída/Pagamento");

        //Configurando o botao registrarSaida
        registrarSaida.setPrefWidth(200);
        registrarSaida.setPrefHeight(100);
        registrarSaida.setFont(Font.font("arial", 16));

        //Configurando o layout do botao  Saida/Pagamento
        DropShadow shadow2 = new DropShadow();
        shadow2.setRadius(8);
        registrarSaida.setStyle("-fx-background-color: #83FFEE;  -fx-text-fill: black; -fx-background-radius: 12;");
        registrarSaida.setOnMouseEntered(passarMouse -> registrarSaida.setEffect(shadow2));
        registrarSaida.setOnMouseExited(mouseJaPassou -> registrarSaida.setEffect(null));
        registrarSaida.setOnMousePressed(clickIn -> {
            registrarSaida.setScaleX(0.92);
            registrarSaida.setScaleY(0.92);
        });
        registrarSaida.setOnMouseReleased(clickOut -> {
            registrarSaida.setScaleX(1);
            registrarSaida.setScaleY(1);
        });
        registrarSaida.setOnAction(click -> {
            System.out.println("Nova tela para confirmar o pagamento e a saída");

            //Chamar a classe com o algoritmo da lógica e configuração da tela
            Pagamentos telaDePagamento = new Pagamentos();    //Criando o objeto para chamar a classe Pagamentos
            Scene novaCenaEntrada = new Scene(telaDePagamento, 1440, 1024);    //Configurando a nova cena com os parâmetros da classe pagamentos
            Stage stageExistente = (Stage) registrarSaida.getScene().getWindow();    //Utilizando o palco existente dando como parâmetros ao clicar no registrar
            //saída para pegar a cena e a janela
            stageExistente.setScene(novaCenaEntrada);    // Trocar a cena exibida na janela
        });

        //Botao para fechar o sistema
        Button sair = new Button("Sair");

        //Configurando o botao sair
        sair.setPrefWidth(200);
        sair.setPrefHeight(100);
        sair.setFont(Font.font("arial", 16));

        //Configurando o layout do botao sair
        DropShadow shadow3 = new DropShadow();
        shadow3.setRadius(8);
        sair.setStyle("-fx-background-color: FF8989; -fx-text-fill: black; -fx-background-radius: 12;");
        sair.setOnMouseEntered(passarMouse -> sair.setEffect(shadow3));
        sair.setOnMouseExited(mouseJaPassou -> sair.setEffect(null));
        sair.setOnMousePressed(clickIn -> {
            sair.setScaleX(0.92);
            sair.setScaleY(0.92);
        });
        sair.setOnMouseReleased(clickOut -> {
            sair.setScaleX(1);
            sair.setScaleY(1);
        });
        sair.setOnAction(click -> {
            System.out.println("Nova tela para sair");
            //Chamar função com o algoritmo da lógica
            fechar();
        });

        //Adicionando os filhos nas box
        boxbotaoSair.getChildren().add(sair);
        vboxDosBotoes.getChildren().addAll(cadastrar, registrarSaida, boxbotaoSair);    //Adicionando os botoes na vBoxDosBotoes
        vboxDosBotoes.setSpacing(50);
        root.getChildren().addAll(header, main);
        listaDeVeiculos.getChildren().addAll(headerListaDeVeiculos);


        stage.setTitle("Estacionamento");
        stage.setScene(scene);
        stage.show();
    }

    //Configurando funções dos botões
    public void fechar(){
        Alert alertaFechar = new Alert(
                Alert.AlertType.CONFIRMATION, "Tem certeza que deseja sair?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> resposta = alertaFechar.showAndWait();
        if (resposta.isPresent() && resposta.get() == ButtonType.YES){
            Platform.exit();
        }
    }
}

