package lk.ijse.deb.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailFormController {

    @FXML
    private JFXButton btnSend;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextArea txtMsgArea;

    @FXML
    void sendBtnOnAction(ActionEvent event) throws MessagingException {
        String recipientEmail = txtEmail.getText();
        sendEmail(recipientEmail);

    }

    private void sendEmail(String recipientEmail) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myEmail = "dilshaperera9999@gmail.com";
        String password = "vcfb rnqe kzgs cltc";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,password);
            }
        });
        Message message = prepareMessage(session,myEmail,recipientEmail,txtMsgArea.getText());
        if(message!=null){
            new Alert(Alert.AlertType.INFORMATION,"Send Email Successfully").show();
        }else {
           new Alert(Alert.AlertType.ERROR,"Please Try Again...").show();
        }
        Transport.send(message);
}

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg){
        try {
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress(myEmail));
           message.setRecipients(Message.RecipientType.TO,new InternetAddress[]{
                  new InternetAddress(recipientEmail)
        });
        message.setSubject("Messages");
        message.setText(msg);

            return message;
        }catch (Exception e){
            Logger.getLogger(EmailFormController.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }
}