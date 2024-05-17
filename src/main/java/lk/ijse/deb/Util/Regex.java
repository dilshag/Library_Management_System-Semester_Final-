package lk.ijse.deb.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField) {
           //signup
            case TYPE:
                filed=".*";
                break;
            case NAME:
                filed = "^[a-zA-Z ]+$";
                break;
            case NIC:
                filed="^[0-9]{12}|[0-9]{9}[VvXx]$";
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case CONTACT:
                filed="^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case PASSWORD:
                filed=".*";
                break;
                //AUTHOR
            case AID:
                filed="^A\\d{3}$";
                break;
            case QUANTITY:
                filed="^\\d+$\n";
                break;
                //BOOK
            case BID:
                filed="^A\\d{3}$";
                break;
            //^\d{9}[\dX]$ ISBN 10 PATTERN
            //^\d{13}$ ISBN 13 PATTERN
            //^(?:\d{9}[\dX]|\d{13})$ ISBN COMBINED PATTERN

            //BOOK RACK
            case BRID:
                filed="^A\\d{3}$";
                break;
                //MEMBER
            case MID:
                filed="^A\\d{3}$";
                break;
                //MEMBERFEE
            case MFEEID:
                filed="^A\\d{3}$";
                break;
                //RESERVATION
            case RID:
                filed="^A\\d{3}$";
                break;
        }
        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }
    public static boolean setTextColor(TextField textField, javafx.scene.control.TextField fxTextField){
        boolean isValid = isTextFieldValid(textField, fxTextField.getText());
        if (isValid) {
            fxTextField.setStyle("-fx-border-color: green; -fx-unfocus-color: green;");
        } else {
            fxTextField.setStyle("-fx-border-color: red; -fx-unfocus-color: red;");
        }
        return isValid;
    }
}