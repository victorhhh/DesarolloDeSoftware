/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Promocion;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class ConsultarPromociones2Controller implements Initializable {

    @FXML
    Button  BModificar;

    @FXML
    MenuButton BAlumnos, BMaestros, BClases,BPromociones,BReportes;

    @FXML
    MenuItem MIRegistrar, MIConsultar;

    @FXML
    Label LNombrePromocion,LDescripcionPromocion, LPorcentaje,LTipos;
     private static Promocion promo;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    
    static void initRootLayout(Stage primaryStage,Promocion promocion) {
        promo=promocion;
        try {
            ConsultarPromociones2Controller.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarPromociones2.fxml"));
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
    void AccionConsultarClase(ActionEvent event) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    void AccionModificar(ActionEvent event) {
        ModificarPromocionController.initRootLayout(primaryStage, promo);
    }

    @FXML
    void AccionRegistrarClase(ActionEvent event) {
        RegistrarClaseController.initRootLayout(primaryStage);
    }
    /**
     * Initializes the controller class.
     */
     @FXML
    public void BRegresarAction(ActionEvent event){
        PrincipalController.initRootLayout(primaryStage);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Float porc=promo.getPorcentaje();
        String porcentaje=porc.toString();
        LNombrePromocion.setText(promo.getNombre());
        LDescripcionPromocion.setText(promo.getDescripcion());
        LPorcentaje.setText(porcentaje);
        LTipos.setText(promo.getTipo());
    }    
    
}
