package lk.ijse.Client.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatRoomFormController {

    @FXML
    private Label lblUsername;

    @FXML
    private TextField txtMessage;

    @FXML
    private VBox vBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button btnEmoji;

    @FXML
    private Button btnFile;

    @FXML
    private Pane emojiPane;



    private Socket remoteSocket;
    private PrintWriter printWriter;

    private BufferedReader bufferedReader;



    public void initialize(){
        lblUsername.setText(LoginFormController.userName);
         new Thread(()->{
             try {
                 System.out.println("client started");
                 remoteSocket=new Socket("localhost",5000);
                 System.out.println("client connected");
                 bufferedReader = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
                 printWriter = new PrintWriter(remoteSocket.getOutputStream(),true);
                 while(true){
                     String fullMessage = bufferedReader.readLine();
                     String[] splitMessage = fullMessage.split(":");
                     String userName = splitMessage[0];
                     String message = splitMessage[1];

                     System.out.println("username print"+userName);
                     System.out.println("message print"+message);

                     String firstCharacter = "";
                     if (userName.length()>3){
                       firstCharacter = userName.substring(0,3);
                         System.out.println("firstCharacter " + firstCharacter);
                     }
                     if (firstCharacter.equalsIgnoreCase("img")){

                     }else {
                         if (LoginFormController.userName.equalsIgnoreCase(userName)){
                             if (!message.isEmpty()){
                                 HBox hBox = new HBox();
                                 hBox.setAlignment(Pos.CENTER_RIGHT);
                                 hBox.setPadding(new Insets(5,10,5,10));

                                 HBox innerBox = new HBox();
                                 innerBox.setPadding(new Insets(2,10,2,10));
                                 innerBox.setStyle(
                                         "-fx-background-color: blue;" +
                                         "-fx-background-radius: 15px"
                                 );



                                 Text text = new Text(message);
                                 TextFlow textFlow = new TextFlow(text);
                                 textFlow.setPadding(new Insets(5,10,5,10));


                                 innerBox.getChildren().add(textFlow);
                                 hBox.getChildren().add(innerBox);

                                 //vbox ekata hbox eka set karanna yanne
                                 Platform.runLater(new Runnable() {
                                     @Override
                                     public void run() {

                                         vBox.getChildren().add(hBox);
                                         scrollPane.layout();
                                         scrollPane.setVvalue(1.0);
                                     }
                                 });
                             }
                         }else {
                             if (!LoginFormController.userName.equalsIgnoreCase(userName)){
                                 HBox hBox = new HBox();
                                 hBox.setAlignment(Pos.CENTER_LEFT);
                                 hBox.setPadding(new Insets(5,10,5,10));

                                 HBox innerBox = new HBox();
                                 innerBox.setPadding(new Insets(2,10,2,10));
                                 innerBox.setStyle(
                                         "-fx-background-color: green;" +
                                                 "-fx-background-radius: 15px"
                                 );

                                 Text txtUsr = new Text(userName + ": ");
                                 txtUsr.setFont(Font.font(12.5));

                                 Text text = new Text(message);
                                 TextFlow textFlow = new TextFlow(txtUsr,text);
                                 textFlow.setPadding(new Insets(5,10,5,10));


                                 innerBox.getChildren().add(textFlow);
                                 hBox.getChildren().add(innerBox);

                                 //vbox ekata hbox eka set karanna yanne
                                 Platform.runLater(new Runnable() {
                                     @Override
                                     public void run() {
                                         vBox.getChildren().add(hBox);
                                         scrollPane.layout();
                                         scrollPane.setVvalue(1.0);
                                     }
                                 });
                             }
                         }
                     }

                 }
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }).start();
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        printWriter.println(LoginFormController.userName + ": " + txtMessage.getText());
        txtMessage.clear();
        emojiPane.setVisible(false);
    }

    @FXML
    void txtMessageOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    @FXML
    void btnEmojiOnAction(ActionEvent event) {
        emojiPane.setVisible(!emojiPane.isVisible());
    }

    @FXML
    void btnFileOnAction(ActionEvent event) {

    }
    
    public void SmileMouseOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0A");
    }

    public void SorrowMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE22");
    }

    public void heartMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0D");
    }

    public void handsUpOnMouseClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE00");
    }

    public void handDownMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE2F");
    }

    public void sungalssMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0E");
    }
}
