package lk.ijse.Client2.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.Client.Util.Navigation;

import java.io.IOException;

public class LoginForm2Controller {

    @FXML
    private TextField txtUsername;
    public static String userName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        userName = txtUsername.getText();
        Navigation.switchNavigation("/View/ChatRoomForm2.fxml",event);
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}
