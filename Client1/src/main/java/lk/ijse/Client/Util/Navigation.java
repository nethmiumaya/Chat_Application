package lk.ijse.Client.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import java.io.IOException;

public class Navigation {
    private static Stage stage;

    public static void switchNavigation(String path, ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(Navigation.class.getResource(path));
        stage= (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public static void minimize(){
        if (stage!=null){
            stage.setIconified(true);
        }
    }
}
