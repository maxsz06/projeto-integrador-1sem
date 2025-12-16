module sp.jandira.senai.maxwillian.projetointegrador1sem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens sp.jandira.senai.maxwillian.projetointegrador1sem to javafx.fxml;
    exports sp.jandira.senai.maxwillian.projetointegrador1sem;
    exports sp.jandira.senai.maxwillian.projetointegrador1sem.ui;
    opens sp.jandira.senai.maxwillian.projetointegrador1sem.ui to javafx.fxml;
}