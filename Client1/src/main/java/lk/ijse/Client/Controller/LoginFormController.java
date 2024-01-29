package lk.ijse.Client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lk.ijse.Client.Util.Navigation;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private TextField txtUsername;
    public static String userName;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        userName = txtUsername.getText();
        Navigation.switchNavigation("/View/ChatRoomForm.fxml",event);
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}
