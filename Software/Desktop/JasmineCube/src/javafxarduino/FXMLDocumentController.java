/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxarduino;

import com.fazecast.jSerialComm.SerialPort;
import com.jfoenix.controls.JFXButton;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Samuelson
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox ListPortes;
    @FXML
    private Button btnConectar;

    private SerialPort porte;
    private int led = 13;
    @FXML
    private JFXButton btnLigarLed;
    @FXML
    private JFXButton ReloadListPortes;
    @FXML
    private JFXButton btUpdateData;
    @FXML
    private Text testTextid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadports();

    }

    public void loadports() {

        SerialPort[] portNames = SerialPort.getCommPorts();

        for (SerialPort portName : portNames) {
            ListPortes.getItems().add(portName.getSystemPortName());
        }

    }
    
    
    

    @FXML
    private void conectar(ActionEvent event) {

        if (btnConectar.getText().equals("الإتصال بالمكعب")) {

            porte = SerialPort.getCommPort(ListPortes.getSelectionModel().getSelectedItem().toString());
            porte.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

            if (porte.openPort()) {
                btnConectar.setText("قطع الإتصال");
                ListPortes.setDisable(true);
            }

        } else {

            porte.closePort();
            ListPortes.setDisable(false);
            btnConectar.setText("الإتصال بالمكعب");

        }
    }
    
    @FXML
    private void ligarLed(ActionEvent event) {

       PrintWriter output = new PrintWriter(porte.getOutputStream());
       
       if(led == 0){
           output.print("1");
           output.flush();
           led = 1;
       }else{
           output.print("0");
           output.flush();
           led = 0;
       }
       

    }
        @FXML
        private void ReloadListPortesAction(ActionEvent event) {
            
       
        SerialPort[] portNames = SerialPort.getCommPorts();
                 
        for (SerialPort portName : portNames) {
            ListPortes.getItems().add(portName.getSystemPortName());
        }
       
    }

    

    @FXML
    private void UpdateData(ActionEvent event) {
        testTextid.setText(""); 
        

        //read from arduino  
    
        porte.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 10000, 0);
        InputStream in = porte.getInputStream();
        try
        {
            for (int j = 0; j < 100; ++j)
                testTextid.setText(testTextid.getText()+(char)in.read());
                in.close();
        } catch (Exception e) { e.printStackTrace(); }

        //end read from arduino
    }

    @FXML
    private void testText(MouseEvent event) {

    }

}
