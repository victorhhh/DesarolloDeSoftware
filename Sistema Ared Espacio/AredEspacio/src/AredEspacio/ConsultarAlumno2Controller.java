/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Alumno;
import BaseDeDatos.Grupo;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yoresroy
 */
public class ConsultarAlumno2Controller implements Initializable {

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
    private TextField TNombreAlumno;
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
    private Button BReinscribir;
    @FXML
    private Button BBaja;
    @FXML
    private Button BEditar;
    @FXML
    private ImageView PaneImagen;
    @FXML
    private Label LCNombre;
    @FXML
    private Label LCTelefono;
    @FXML
    private Label LCFechaDeNacimiento;
    @FXML
    private Label LCDireccion;
    @FXML
    private Label LCClases;
    @FXML
    private Label LCFechaProximoPago;
    @FXML
    private Label LCMonto;
    private static Alumno al = new Alumno();

    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    //private Alumno al = new Alumno();

    private void agregarAlumno() {

    }

    static void initRootLayout(Stage primaryStage, Alumno alumno) {
        al = alumno;
        System.out.println("pollo robot 3000 " + al.getNombre() + "Drieccion " + al.getDireccion() + "Id " + al.getIDAlumno());

        try {
            ConsultarAlumno2Controller.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarAlumno2.fxml"));
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
        LCNombre.setText(al.getNombre() + " " + al.getPrimerApellido() + " " + al.getSegundoApellido());
        LCDireccion.setText(al.getDireccion());
        LCFechaDeNacimiento.setText("");
        LCTelefono.setText(al.getNumeroDeCelular());
        LCFechaDeNacimiento.setText(al.getFechaNacimiento().getDate() + " / " + (al.getFechaNacimiento().getMonth() + 1) + " / " + al.getFechaNacimiento().getYear());
        Grupo grupo = new Grupo();
        System.out.println("SSS "+ grupo.buscarGruposAlumno(al.getIDAlumno()));
        
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
    private void BReinscribirAction(ActionEvent event) {
    }

    @FXML
    private void BBajaAction(ActionEvent event) {
    }

    @FXML
    private void BEditarAction(ActionEvent event) {
    }

}
