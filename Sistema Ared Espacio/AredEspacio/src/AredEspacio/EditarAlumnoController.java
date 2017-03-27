/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yoresroy
 */
public class EditarAlumnoController implements Initializable {

    @FXML
    private MenuButton BAlumnos;
    @FXML
    private MenuButton BClases;
    @FXML
    private MenuButton BPromociones;
    @FXML
    private MenuButton BReportes;
    @FXML
    private MenuButton BMaestros;
    @FXML
    private Label LConsultarAlumnos;
    @FXML
    private Button BBuscar;
    @FXML
    private TextField TBuscarNombreAlumno;
    @FXML
    private Label LBuscar;
    @FXML
    private Label LNombre;
    @FXML
    private Label LTelefono;
    @FXML
    private Label LFechaDeNac;
    @FXML
    private Label LDireccion;
    @FXML
    private Label LFechaDeProximoPago;
    @FXML
    private Label LMonto;
    @FXML
    private Label LClases;
    @FXML
    private Button BAgregar;
    @FXML
    private Button BGuardar;
    @FXML
    private Button BQuitar;
    @FXML
    private ImageView PaneImagen;
    @FXML
    private TextField TNombreAlumno;
    @FXML
    private TextField TTelefono;
    @FXML
    private TextField TFechaDeNacimiento;
    @FXML
    private TextField TDireccion;
    @FXML
    private TextArea TClases;
    @FXML
    private TextField TFechaProximoPago;
    @FXML
    private TextField TMonto;
    @FXML
    private Button BBuscarImagen;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            EditarAlumnoController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("EditarAlumno.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MenuItem consultarAlumno = new MenuItem("Consultar Alumno");
        MenuItem inscribirAlumno = new MenuItem("Inscribir Alumno");
        
        BAlumnos.getItems().addAll(inscribirAlumno, consultarAlumno);
        
        consultarAlumno.setOnAction((ActionEvent) ->{
            ConsultarAlumno1Controller.initRootLayout(primaryStage);
        });
        inscribirAlumno.setOnAction((ActionEvent) ->{
            InscribirAlumnoController.initRootLayout(primaryStage);
        });
        
    }

    @FXML
    private void BAlumnosAction(ActionEvent event) {
    }

    @FXML
    private void BClasesAction(ActionEvent event) {
    }

    @FXML
    private void BPromocionesAction(ActionEvent event) {
    }

    @FXML
    private void BReportesAction(ActionEvent event) {
    }

    @FXML
    private void BMaestrosAction(ActionEvent event) {
    }

    @FXML
    private void BBuscar(ActionEvent event) {
    }

    @FXML
    private void TNombreAlumnoAction(ActionEvent event) {
    }

    @FXML
    private void BAgregarAction(ActionEvent event) {
    }

    @FXML
    private void BGuardarAction(ActionEvent event) {
    }

    @FXML
    private void BQuitarAction(ActionEvent event) {
    }

    @FXML
    private void BBuscarImagenAction(ActionEvent event) {
    }

}
