/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Alumno;
import JPAControllers.AlumnoJpaController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    private Button BGuardar;
    @FXML
    private ImageView PaneImagen;
    @FXML
    private TextField TNombreAlumno;
    @FXML
    private TextField TTelefono;
    @FXML
    private TextField TDireccion;
    private TextField TMonto;
    @FXML
    private Button BBuscarImagen;
    static Alumno alumno = new Alumno();

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
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
    @FXML
    private Button BCancelar;
    @FXML
    private MenuItem MIRegistrarClase;
    @FXML
    private MenuItem MIConsultarClase;
    @FXML
    private MenuItem MIRegistrarMaestro;
    @FXML
    private MenuItem MIConsultarMaestro;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MenuItem consultarAlumno = new MenuItem("Consultar Alumno");
        MenuItem inscribirAlumno = new MenuItem("Inscribir Alumno");

        BAlumnos.getItems().addAll(inscribirAlumno, consultarAlumno);

        consultarAlumno.setOnAction((ActionEvent) -> {
            ConsultarAlumno1Controller.initRootLayout(primaryStage);
        });
        inscribirAlumno.setOnAction((ActionEvent) -> {
            InscribirAlumnoController.initRootLayout(primaryStage);
        });

        Image img = new Image(new File(alumno.getRutaImagen()).toURI().toString());
        PaneImagen.setImage(img);
        TNombreAlumno.setText(alumno.getNombre());
        TPApellido.setText(alumno.getPrimerApellido());
        TSApellido.setText(alumno.getSegundoApellido());
        TTelefono.setText(alumno.getNumeroDeCelular());
        alumno.getFechaNacimiento();

        //TMonto.setText(Integer.toString(alumno.getIDInscripcionA().getMonto()));
        Instant instant = alumno.getFechaNacimiento().toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        DFechaNacimiento.setValue(localDate);

        TDireccion.setText(alumno.getDireccion());

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
    private void BGuardarAction(ActionEvent event) {
        if (validarGuardado()) {
            AlumnoJpaController jpaA = new AlumnoJpaController(emf);
            crearAlumno();
            alumno.getIDAlumno();
            try {
                jpaA.edit(alumno);
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText(null);
                dialogoAlerta.setContentText("Cambios aplicados con éxito");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                ConsultarAlumno2Controller.initRootLayout(primaryStage, alumno);
            } catch (Exception ex) {
                Logger.getLogger(EditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);

            }
        } else {
            System.out.println("campo vacio");
            Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText(null);
            dialogoAlerta.setContentText("Campo vacio, no se puede guardar");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }
    }
    
     @FXML
    public void BRegresarAction(ActionEvent event){
        PrincipalController.initRootLayout(primaryStage);
    }

    private void crearAlumno() {
        alumno.setNombre(TNombreAlumno.getText());
        alumno.setPrimerApellido(TPApellido.getText());
        alumno.setSegundoApellido(TSApellido.getText());
        alumno.setNumeroDeCelular(TTelefono.getText());
        Date date = new Date();
        try {
            date.setDate(DFechaNacimiento.getValue().getDayOfMonth());
            date.setMonth(DFechaNacimiento.getValue().getMonthValue());
            date.setYear(DFechaNacimiento.getValue().getYear() - 1900);
            alumno.setFechaNacimiento(date);
        } catch (Exception e) {
            System.out.println("No hay fecha");
        }

        alumno.setDireccion(TDireccion.getText());
        alumno.setEstado(true);
    }

    @FXML
    private void BBuscarImagenAction(ActionEvent event) {
        try {
            FileChooser fileChosser = new FileChooser();
            String src = fileChosser.showOpenDialog(primaryStage).toString();
            Image img = new Image(new File(src).toURI().toString());
            alumno.setRutaImagen(src);
            PaneImagen.setImage(img);
        } catch (NullPointerException e) {

        }
    }

    public boolean validarGuardado() {
        if (TNombreAlumno.getText().isEmpty() || TPApellido.getText().isEmpty() || TSApellido.getText().isEmpty()
                || TTelefono.getText().isEmpty() || TDireccion.getText().isEmpty()
                || DFechaNacimiento.getValue() == null
                || PaneImagen.getImage() == null) {
            return false;
        }
        return true;
    }

    @FXML
    private void BCancelarAction(ActionEvent event) {

        ConsultarAlumno2Controller.initRootLayout(primaryStage, alumno);
    }

    @FXML
    private void MIRegistrarClaseAction(ActionEvent event) {
        RegistrarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarClaseAction(ActionEvent event) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIRegistrarMaestroAction(ActionEvent event) {
        RegistrarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarMaestroAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

}
