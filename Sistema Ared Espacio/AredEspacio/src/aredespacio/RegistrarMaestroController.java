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
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class RegistrarMaestroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BEliminar, BGuardar, BAgregar;
    @FXML
    private TextField TFNombre, TFCelular, TFFechaNac, TFDireccion;

    @FXML
    private ScrollPane SPClases;
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            RegistrarMaestroController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarMaestro.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void BEliminarAction(ActionEvent event) {

    }

    @FXML
    public void BGuardarAction(ActionEvent event) {

    }

    @FXML
    public void BAgregarAction(ActionEvent event) {

    }

    @FXML
    public void BAlumnosAction(ActionEvent event) {

    }

    @FXML
    public void BMaestrosAction(ActionEvent event) {

    }

    @FXML
    public void BClasesAction(ActionEvent event) {

    }

    @FXML
    public void BPromocionesAction(ActionEvent event) {

    }

    @FXML
    public void BReporteAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
