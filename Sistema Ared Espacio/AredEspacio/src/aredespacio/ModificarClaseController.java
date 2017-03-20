/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aredespacio;

import basededatos.Clase;
import basededatos.Maestro;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontrollers.ClaseJpaController;
import jpacontrollers.MaestroJpaController;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class ModificarClaseController implements Initializable {

    @FXML
    MenuButton BAlumnos, BMaestros, BClases, bPromociones, BReportes;
    @FXML
    Button BGuardar, BRegresar;
    @FXML
    TextField TFNombreProfesor, TFNombreClase, TFHora,TFDia;
    
    private String dia;
    private final int IDClase=5;
    private int IDM;
    

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            ModificarClaseController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ModificarClase.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AccionGuardar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        //MaestroJpaController controllerMaestros =new MaestroJpaController(entityManagerFactory);
        Clase clase= controllerClases.findClase(IDClase);
        clase.setDia(TFDia.getText());
        clase.setEstado(true);
        clase.setHora(TFHora.getText());
        clase.setNombre(TFNombreClase.getText());
        try {
                controllerClases.edit(clase);
                System.out.println("Done");
            } catch (Exception e) {
                Logger.getLogger(RegistrarClaseController.class.getName());
            }
        RegistrarClaseController.initRootLayout(primaryStage);
    }

    public void AccionRegresar(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        MaestroJpaController controllerMaestros =new MaestroJpaController(entityManagerFactory);
        Clase clase= controllerClases.findClase(IDClase);
        IDM=clase.getIDMaestro().getIDMaestro();
        Maestro maestro=controllerMaestros.findMaestro(IDM);
        TFNombreProfesor.setText(maestro.getNombre());
        TFNombreClase.setText(clase.getNombre());
        TFHora.setText(clase.getHora());
        TFDia.setText(clase.getDia());
    }

}
