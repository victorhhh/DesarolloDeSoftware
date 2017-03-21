/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aredespacio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontrollers.ClaseJpaController;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class ConsultarClaseController implements Initializable {
    @FXML
    ImageView IVLogo; 
    @FXML
    TextField TFNombreClase, TFNombreProfesor;
    
    @FXML
    Button BConsultar, BModificar, BBaja;
    @FXML
    TableView TVClases;
    @FXML
    TableColumn TCLunes, TCMartes, TCMiercoles, TCJueves, TCViernes,TCSabado;
   
    private int IDC;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            ConsultarClaseController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarClase.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //http://acodigo.blogspot.mx/2015/08/tableview-control-javafx.html
    public void AccionConsultar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        
        //tfNombreClase.getText();
        
    }

    public void AccionModificar(ActionEvent evento) {
        ModificarClaseController.initRootLayout(primaryStage);
    }

    public void AccionBaja(ActionEvent evento) {
        DarDeBajaClaseController.initRootLayout(primaryStage);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
   }

}
