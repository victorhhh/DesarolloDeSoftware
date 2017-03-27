/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Alumno;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yoresroy
 */
public class InscribirAlumnoController implements Initializable {

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
    private TextField TNombre;
    @FXML
    private TextField TTelefono;
    @FXML
    private DatePicker DFechaInscripcion;
    @FXML
    private DatePicker DFechaDeNacimiento;
    @FXML
    private TextField TDireccion;
    @FXML
    private Label LClasesAgregadas;
    @FXML
    private TextField LInscripcion;
    @FXML
    private TextField LMensualidad;
    @FXML
    private SplitMenuButton BMPromocion;
    @FXML
    private Button BImagenAlumno;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            InscribirAlumnoController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("InscribirAlumno.fxml"));
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
        MenuItem editarAlumno = new MenuItem("Editar Alumno");
        MenuItem consultarAlumno = new MenuItem("Consultar Alumno");

        BAlumnos.getItems().addAll(editarAlumno, consultarAlumno);

        TNombre.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (car == ' ') {

            } else if (TNombre.getText().length() > 25 || !Character.isAlphabetic(car)) {
                event.consume();
            }
        });
        TTelefono.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (!Character.isDigit(car) || TTelefono.getText().length() > 10) {
                event.consume();
            }
        });
        TDireccion.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (TDireccion.getText().length() > 30) {
                event.consume();
            }
        });

        editarAlumno.setOnAction((ActionEvent event) -> {
            EditarAlumnoController.initRootLayout(primaryStage);
        });

        consultarAlumno.setOnAction((ActionEvent event) -> {
            ConsultarAlumno1Controller.initRootLayout(primaryStage);
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
    private void BAgregarAction(ActionEvent event) {
    }

    @FXML
    private void BGuardarAction(ActionEvent event) {
        Alumno alumnoNuevo = new Alumno();
        LocalDate localDate = DFechaDeNacimiento.getValue();
        System.out.println("FEcha " + localDate);
        LocalDate localDate2 = DFechaInscripcion.getValue();
        System.out.println("FEcha " + localDate2);
    }

    @FXML
    private void BQuitarAction(ActionEvent event) {
    }

    @FXML
    private void BImagenAlumnoAction(ActionEvent event) {
    }

}
