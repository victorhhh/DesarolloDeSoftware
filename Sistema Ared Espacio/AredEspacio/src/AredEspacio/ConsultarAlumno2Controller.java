/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Alumno;
import BaseDeDatos.Grupo;
import BaseDeDatos.Inscripcion;
import JPAControllers.AlumnoJpaController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    private static Alumno alumno = new Alumno();
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    private void agregarAlumno() {

    }

    static void initRootLayout(Stage primaryStage, Alumno alumno1) {
        alumno = alumno1;
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

    public void llenarCampos() {
        LCNombre.setText(alumno.getNombre() + " " + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());
        Image img = new Image(new File(alumno.getRutaImagen()).toURI().toString());
        PaneImagen.setImage(img);
        Inscripcion ins = new Inscripcion();
        
        
        LCDireccion.setText(alumno.getDireccion());
        LCTelefono.setText(alumno.getNumeroDeCelular());
        LCFechaDeNacimiento.setText(alumno.getFechaNacimiento().getDate() + " / " + (alumno.getFechaNacimiento().getMonth() + 1) + " / " + alumno.getFechaNacimiento().getYear());
        Grupo grupo = new Grupo();
        List<Grupo> listGrupo = grupo.buscarGruposAlumno(alumno.getIDAlumno());
        LCFechaProximoPago.setText(alumno.getIDInscripcionA().getFechaInscripcion().getDate() + " / " + (alumno.getIDInscripcionA().getFechaInscripcion().getMonth() + 1) + " / "
                + (alumno.getIDInscripcionA().getFechaInscripcion().getYear() - 100));
        LCMonto.setText(String.valueOf(alumno.getIDInscripcionA().getMonto()));
        if (listGrupo.isEmpty()) {
            LCClases.setText("Sin clases inscritas");
        } else {
            for (int o = 0; o < listGrupo.size(); o++) {
                if (o + 1 == listGrupo.size()) {
                    LCClases.setText(LCClases.getText() + listGrupo.get(o).getIDClaseG().getNombre() + ". ");
                } else {
                    LCClases.setText(LCClases.getText() + listGrupo.get(o).getIDClaseG().getNombre() + ", ");
                    System.out.println(LCClases.getText() + listGrupo.get(o).getIDClaseG().getNombre());
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCampos();
        MenuItem inscribirAlumno = new MenuItem("Inscribir");
        MenuItem editarAlumno = new MenuItem("Consultar/Editar");
        BAlumnos.getItems().addAll(inscribirAlumno, editarAlumno);

        editarAlumno.setOnAction((ActionEvent event) -> {
            EditarAlumnoController.initRootLayout(primaryStage, alumno);
        });
        inscribirAlumno.setOnAction((ActionEvent event) -> {
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
    private void BReinscribirAction(ActionEvent event) {
        Alert dialogoAlerta = new Alert(AlertType.CONFIRMATION);
        dialogoAlerta.setTitle("Ared Espacio");
        dialogoAlerta.setHeaderText("¿Seguro quieres reinscribir?");
        dialogoAlerta.setContentText("El alumno será reinscrito de la escuela");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = dialogoAlerta.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                alumno.setEstado(true);
                AlumnoJpaController  a= new AlumnoJpaController(emf);
                a.edit(alumno);
            } catch (Exception ex) {
                Logger.getLogger(ConsultarAlumno2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void BBajaAction(ActionEvent event) {
        Alert dialogoAlerta = new Alert(AlertType.CONFIRMATION);
        dialogoAlerta.setTitle("Ared Espacio");
        dialogoAlerta.setHeaderText("¿Seguro quieres dar de baja?");
        dialogoAlerta.setContentText("El alumno será dado de baja en la escuela");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = dialogoAlerta.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                alumno.setEstado(false);
                AlumnoJpaController  a= new AlumnoJpaController(emf);
                a.edit(alumno);
            } catch (Exception ex) {
                Logger.getLogger(ConsultarAlumno2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void BEditarAction(ActionEvent event) {
        EditarAlumnoController.initRootLayout(primaryStage, alumno);
    }

}
