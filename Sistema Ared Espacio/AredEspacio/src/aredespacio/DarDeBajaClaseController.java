/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aredespacio;

import basededatos.Clase;
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
import jpacontrollers.ClaseJpaController;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class DarDeBajaClaseController implements Initializable {

    @FXML
    TextField LNombreClase;
    @FXML
    Button BAceptar, BCancelar;
    public final int IDClase = 3;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
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
        Clase clase = controllerClases.findClase(IDClase);
        clase.setEstado(false);
        try {
            controllerClases.edit(clase);
            System.out.println("Done");
        } catch (Exception e) {
            Logger.getLogger(RegistrarClaseController.class.getName());
        }
        RegistrarClaseController.initRootLayout(primaryStage);
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
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        Clase clase = controllerClases.findClase(IDClase);
        LNombreClase.setText(clase.getNombre());
        
    }

}
