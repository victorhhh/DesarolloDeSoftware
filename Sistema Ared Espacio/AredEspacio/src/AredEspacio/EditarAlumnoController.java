/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.InscribirAlumnoController.primaryStage;
import BaseDeDatos.Alumno;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
    private Label LNombre;
    @FXML
    private Label LTelefono;
    @FXML
    private Label LFechaDeNac;
    @FXML
    private Label LDireccion;
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
    private TextField TDireccion;
    @FXML
    private TextArea TClases;
    @FXML
    private TextField TMonto;
    @FXML
    private Button BBuscarImagen;
    static Alumno alumno = new Alumno();

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage, Alumno alumn) {
        alumno = alumn;
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
    @FXML
    private TextField TPApellido;
    @FXML
    private TextField TSApellido;
    @FXML
    private DatePicker DFechaNacimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MenuItem consultarAlumno = new MenuItem("Consultar Alumno");
        MenuItem inscribirAlumno = new MenuItem("Inscribir Alumno");

        BAlumnos.getItems().addAll(inscribirAlumno, consultarAlumno);

        Image img = new Image(new File(alumno.getRutaImagen()).toURI().toString());
        PaneImagen.setImage(img);
        TNombreAlumno.setText(alumno.getNombre());
        TPApellido.setText(alumno.getPrimerApellido());
        TSApellido.setText(alumno.getSegundoApellido());
        TTelefono.setText(alumno.getNumeroDeCelular());
        alumno.getFechaNacimiento();

        LocalDate ld = DFechaNacimiento.getValue();
        Instant instant = alumno.getFechaNacimiento().toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        DFechaNacimiento.setValue(localDate);
        TDireccion.setText(alumno.getDireccion());
        consultarAlumno.setOnAction((ActionEvent) -> {
            ConsultarAlumno1Controller.initRootLayout(primaryStage);
        });
        inscribirAlumno.setOnAction((ActionEvent) -> {
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
        FileChooser fileChosser = new FileChooser();
        String src = fileChosser.showOpenDialog(primaryStage).toString();
        Image img = new Image(new File(src).toURI().toString());
        alumno.setRutaImagen(src);
        PaneImagen.setImage(img);
    }

}
