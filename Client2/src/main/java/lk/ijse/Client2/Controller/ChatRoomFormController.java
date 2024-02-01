package lk.ijse.Client2.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import lk.ijse.Client2.Util.Navigation;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

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

    private File file;

    private String encodedImage;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    public void initialize(){
        setDate();
        setTime();
        lblUsername.setText(LoginFormController.userName);
         new Thread(()->{
             try {

                 remoteSocket=new Socket("localhost",5000);
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
                        String path = message;
                        byte[] imageDecode = Base64.getDecoder().decode(path);

                        int randomNumber = new Random().nextInt(10000);
                        String fileName = "image file"+randomNumber+".png";
                        File filePath =  new File("Client1/src/main/resources/ImageFiles");
                        File receivedImage = new File(filePath,fileName);

                        try (FileOutputStream fileOutputStream = new FileOutputStream(receivedImage)){
                            fileOutputStream.write(imageDecode);
                        }

                        Image image =new Image(receivedImage.toURI().toString());
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(300);
                        imageView.setFitWidth(300);

                        HBox imageHBox = new HBox(imageView);
                        imageHBox.setPadding(new Insets(5));

                        HBox hBox = new HBox(10);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);

                        HBox innerBox = new HBox();
                        innerBox.setPadding(new Insets(5,10,5,10));

                        String[] name = userName.split("img");
                        String realName= name[1];

                        if (lblUsername.getText().equalsIgnoreCase(realName)){
                            innerBox.setStyle(
                                    "-fx-background-color: #9c45f0;" +
                                            "-fx-background-radius: 15px"
                            );
                            innerBox.getChildren().add(imageHBox);
                            hBox.getChildren().add(innerBox);
                            hBox.setAlignment(Pos.TOP_RIGHT);
                            hBox.setPadding(new Insets(6,6,6,10));

                        }else {
                            innerBox.setStyle(
                                    "-fx-background-color: #c0c0c0;" +
                                            "-fx-background-radius: 15px"
                            );

                            Text text = new Text(" "+realName+": ");
                            text.setFont(Font.font(12.5));

                            innerBox.getChildren().addAll(text,imageHBox);
                            hBox.getChildren().add(innerBox);
                            hBox.setAlignment(Pos.TOP_LEFT);
                            hBox.setPadding(new Insets(6,6,6,10));


                        }
                        Platform.runLater(() -> {
                            vBox.getChildren().add(hBox);
                            scrollPane.layout();
                            scrollPane.setVvalue(1.0);
                        });

                     }else {
                         if (LoginFormController.userName.equalsIgnoreCase(userName)){
                             if (!message.isEmpty()){
                                 HBox hBox = new HBox();
                                 hBox.setAlignment(Pos.CENTER_RIGHT);
                                 hBox.setPadding(new Insets(5,10,5,10));

                                 HBox innerBox = new HBox();
                                 innerBox.setPadding(new Insets(2,10,2,10));
                                 innerBox.setStyle(
                                         "-fx-background-color: #9c45f0;" +
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
                                         "-fx-background-color: #c0c0c0;" +
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

        if (file!=null){
            printWriter.println("img"+lblUsername.getText()+":"+encodedImage);
            file=null;
        }else {
            printWriter.println(LoginFormController.userName + ": " + txtMessage.getText());
        }
        txtMessage.clear();
        emojiPane.setVisible(false);

        txtMessage.setEditable(true);
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
    void btnFileOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        //Dena type eke files witarai select karanna denne
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("image files","*.jpg","*.jpeg","*.png","*.gif");

        fileChooser.getExtensionFilters().add(extensionFilter);
        //window eka open karala denne showOpenDialog method eken
        file = fileChooser.showOpenDialog(txtMessage.getScene().getWindow());
        if (file!=null){
            txtMessage.setText("file selected");
            txtMessage.setEditable(false);

            byte[] imageToByte =Files.readAllBytes(file.toPath());
            //api yawana image eka encode karaganna puluwan class ekk
            encodedImage =Base64.getEncoder().encodeToString(imageToByte);

            btnSendOnAction(event);

        }
    }
    @FXML
    public void SmileMouseOnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0A");
        emojiFinalize();
    }
    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblDate.setText(date);
    }
    private void setTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        lblTime.setText(currentTime.format(formatter));
    }
    @FXML
    public void SorrowMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE22");
        emojiFinalize();
    }
    @FXML
    public void heartMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0D");
        emojiFinalize();
    }
    @FXML
    public void handsUpOnMouseClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE00");
        emojiFinalize();
    }
    @FXML
    public void handDownMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE2F");
        emojiFinalize();
    }
    @FXML
    public void sungalssMouseOnClicked(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0E");
        emojiFinalize();
    }
    @FXML
    private void emojiFinalize(){
        txtMessage.requestFocus();
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void btnMinimizeOnAction(ActionEvent event) {
        Navigation.minimize();
    }
}
