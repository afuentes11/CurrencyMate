package main.java.view.home;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import main.java.controller.CurrencyConverter;
import main.java.controller.CurrencyConverterImpl;
import main.java.domain.Currency;

import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    CurrencyConverter converter;


    @FXML
    private Label lastUpdatedValue;


    @FXML
    private Label code1;
    @FXML
    private ComboBox comboBox1;
    @FXML
    private TextField input1;
    @FXML
    private Label code1SubText;


    @FXML
    private Label code2;
    @FXML
    private ComboBox comboBox2;
    @FXML
    private TextField input2;
    @FXML
    private Label code2SubText;


    @FXML
    private GridPane modalContainer;
    @FXML
    private Label modalTextIcon;
    @FXML
    private Label modalText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        converter = new CurrencyConverterImpl();


        comboBox1.setItems(FXCollections.observableArrayList(converter.listCurrencyName()));
        comboBox2.setItems(FXCollections.observableArrayList(converter.listCurrencyName()));
        code1.setText("");
        code2.setText("");
        lastUpdatedValue.setText(converter.getLastUpdateDate());
        modalContainer.setVisible(false);

    }


    public void handlerComboBox(ActionEvent actionEvent) {
        ComboBox comboBox = (ComboBox) actionEvent.getSource();
        String id = comboBox.getId();
        Object item = comboBox.getSelectionModel().getSelectedItem();

        Currency currency = converter.getCurrencyByName((String) item);

        if(id.equals("comboBox1")){
            code1.setText(currency.getSymbol_native() +" "+ currency.getCode());
            code1SubText.setText("1 USD = "+currency.getValue()+" "+currency.getCode());
        }else{
            code2.setText(currency.getSymbol_native() +" "+ currency.getCode());
            code2SubText.setText("1 USD = "+currency.getValue()+" "+currency.getCode());
        }
    }

    public void handlerUpdateExchange(ActionEvent actionEvent) {
        if (converter.updateExchangeRates()){
            lastUpdatedValue.setText(converter.getLastUpdateDate());
            showModal("Successfully", "Successfully" );
        }else{
            showModal("Error", "Error" );
        }
    }

    public void handlerCloseModal(ActionEvent actionEvent) {
        modalContainer.setVisible(false);
    }

    public void handlerTextField(ActionEvent actionEvent) {

        TextField textField = (TextField) actionEvent.getSource();

        String currency1 = (String) comboBox1.getSelectionModel().getSelectedItem();
        String currency2 = (String) comboBox2.getSelectionModel().getSelectedItem();

        if (isValidate()) {
            if(textField.getId().equals("input1")){
                double textInputDouble = Double.parseDouble(input1.getText());
                String value = String.valueOf(converter.converter(currency1, currency2, textInputDouble));
                input2.setText(value);
            }else{
                double textInputDouble = Double.parseDouble(input2.getText());
                String value = String.valueOf(converter.converter(currency2, currency1, textInputDouble));
                input1.setText(value);
            }

        }
    }

    public void handlerResult(ActionEvent actionEvent) {

        String currency1 = (String) comboBox1.getSelectionModel().getSelectedItem();
        String currency2 = (String) comboBox2.getSelectionModel().getSelectedItem();

        if(isValidate()){
            if(input1.getText().equals("")){
                double textInputDouble = Double.parseDouble(input2.getText());
                String value = String.valueOf(converter.converter(currency2, currency1, textInputDouble));
                input1.setText(value);
            }else{
                double textInputDouble = Double.parseDouble(input1.getText());
                String value = String.valueOf(converter.converter(currency1, currency2, textInputDouble));
                input2.setText(value);
            }
        }
    }

    private void showModal(String type, String message){
        if(type.equals("Successfully")){
            modalTextIcon.setStyle(modalTextIcon.getStyle() + "-fx-text-fill: #0cbc8b;");
            modalTextIcon.setText("✔");
        }else{
            modalTextIcon.setStyle(modalTextIcon.getStyle() + "-fx-text-fill: #ea4335;");
            modalTextIcon.setText("\u274c");
        }
        modalText.setText(message);
        modalContainer.setVisible(true);
    }

    private boolean isValidate(){

        String currency1 = (String) comboBox1.getSelectionModel().getSelectedItem();
        String currency2 = (String) comboBox2.getSelectionModel().getSelectedItem();

        String textInput1 = input1.getText();
        String textInput2 = input2.getText();

        boolean isDoubleTextInput1 = textInput1.matches("^\\d+(\\.\\d+)?$");
        boolean isDoubleTextInput2 = textInput2.matches("^\\d+(\\.\\d+)?$");

        if (currency1 == null || currency2 == null) {
            showModal("Error", "Select both currencies" );
            return false;
        } else if(textInput1.equalsIgnoreCase("") && textInput2.equalsIgnoreCase("")){
            showModal("Error", "Enter at least one of the currencies");
            return false;
        } else if ((!isDoubleTextInput1 && !textInput1.equalsIgnoreCase("")) || (!isDoubleTextInput2 && !textInput2.equalsIgnoreCase(""))){
            showModal("Error", "Numerical data only");
            return false;
        }
        return true;
    }

}
