/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Clase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import JPAControllers.ClaseJpaController;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class DarDeBajaClaseController implements Initializable {

    @FXML
    Label LMensaje;
    @FXML
    Button BAceptar, BCancelar;
    private static Clase c1 ;
    public int IDC;
    private int IDM;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage,Clase clase1) {
        c1=clase1;
        try {
            DarDeBajaClaseController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("DarDeBajaClase.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     
    
    public void AccionAceptar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        if(c1.getEstado()==false){
             Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("¡Aviso!" + " " + "Clase inactiva");
                                dialogoAlerta.setContentText("Esta clase ya esta dada de baja");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                                ConsultarClase2Controller.initRootLayout(primaryStage, c1);
        }else{
            c1.setEstado(false);
        try {
            controllerClases.edit(c1);
            Alert da = new Alert(Alert.AlertType.INFORMATION);
            da.setTitle("Ared Espacio");
            da.setHeaderText("Clase dada de baja");
            da.setContentText("La clase"+" "+c1.getNombre()+" "+" fue dada de baja ");
            da.initStyle(StageStyle.UTILITY);
            
            da.showAndWait();
        } catch (Exception e) {
            Logger.getLogger(RegistrarClaseController.class.getName());
        }
        RegistrarClaseController.initRootLayout(primaryStage);
        }
        
    }

    public void AccionCancelar(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        LMensaje.setText("Seguro quieres dar de baja la clase de"+" "+c1.getNombre()+"?");
        
    }

}
