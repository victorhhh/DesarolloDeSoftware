/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aredespacio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
public class ConsultarMaestroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField TFBuscar;
    @FXML
    private Button BBuscar, BConsultar;
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    private ScrollPane SPListaMaestros;
    
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
            
    static void initRootLayout(Stage primaryStage) {
        try {
            ConsultarMaestroController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarMaestro.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//         private final ObservableList<ConsultarClaseController.LClase> listaClases
//            = FXCollections.observableArrayList();
        //SPListaMaestros.setContent();
//        ObservableList<String> lista = FXCollections.observableArrayList();
//        lista.add("hola culito");
//        lista.add("adios culito");
//        SPListaMaestros.setContent((Node) lista);
    }

}
