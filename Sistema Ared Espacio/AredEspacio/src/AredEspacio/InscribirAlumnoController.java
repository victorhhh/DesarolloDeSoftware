/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Alumno;
import BaseDeDatos.Clase;
import BaseDeDatos.Grupo;
import BaseDeDatos.Inscripcion;
import JPAControllers.AlumnoJpaController;
import JPAControllers.GrupoJpaController;
import JPAControllers.InscripcionJpaController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
    private Label LMonto;
    @FXML
    private Label LClases;
    @FXML
    private Button BGuardar;
    @FXML
    private ImageView PaneImagen;
    @FXML
    private TextField TNombre;
    @FXML
    private TextField TTelefono;
    @FXML
    private DatePicker DFechaDeNacimiento;
    @FXML
    private TextField TDireccion;
    @FXML
    private TextField LInscripcion;
    @FXML
    private SplitMenuButton BMPromocion;
    @FXML
    private Button BImagenAlumno;
    @FXML
    private Label LPrimerApellido;
    @FXML
    private TextField TSApellido;
    @FXML
    private TextField TPApellido;
    @FXML
    private TableView<Clase> TClases;
    @FXML
    private Button BModificarClases;
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
    static Alumno alumnoNuevo = new Alumno();
    static Inscripcion nuevaInscripcion = new Inscripcion();
    static List<Clase> clases;
    static boolean primeraVez;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        primeraVez = true;
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

        TableColumn<Clase, String> cNombre = new TableColumn<>("Nombre");
        TableColumn<Clase, String> cDia = new TableColumn<>("Dia");
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        TClases.getColumns().addAll(cNombre, cDia);
        if (!primeraVez) {
            for (int i = 0; i < clases.size(); i++) {
                TClases.getItems().add(clases.get(i));
            }
        }
        if (alumnoNuevo != null) {
            System.out.println("Se entro");
            llenarCampos();
        }
        if (nuevaInscripcion != null) {
            llenarInscripcion();
        }

        MenuItem editarAlumno = new MenuItem("Consultar/Editar");
        BAlumnos.getItems().addAll(editarAlumno);

        editarAlumno.setOnAction((ActionEvent event) -> {
            ConsultarAlumno1Controller.initRootLayout(primaryStage);
        });
        /*consultarAlumno.setOnAction((ActionEvent event) -> {
            ConsultarAlumno1Controller.initRootLayout(primaryStage);
        });*/

        TNombre.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (car == ' ') {

            } else if (TNombre.getText().length() > 25 || !Character.isAlphabetic(car)) {
                event.consume();
            }
        });
        TPApellido.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (car == ' ') {

            } else if (TPApellido.getText().length() > 25 || !Character.isAlphabetic(car)) {
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
        LInscripcion.setOnKeyTyped((KeyEvent event) -> {
            char car = event.getCharacter().charAt(0);
            if (!Character.isDigit(car) || LInscripcion.getText().length() > 10) {
                event.consume();
            }
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
        if (TClases.getColumns().isEmpty()) {
            AlumnoSeleccionarClaseController.initRootLayout(primaryStage);
        } else {
            crearAlumno();
            crearInscripcion();
            System.out.println("alumno " + alumnoNuevo.getNombre());
            AlumnoSeleccionarClaseController.initRootLayout(primaryStage, TClases.getItems(), alumnoNuevo, nuevaInscripcion);
        }
    }

    private void crearAlumno() {
        alumnoNuevo.setNombre(TNombre.getText());
        alumnoNuevo.setPrimerApellido(TPApellido.getText());
        alumnoNuevo.setSegundoApellido(TSApellido.getText());
        alumnoNuevo.setNumeroDeCelular(TTelefono.getText());
        Date date = new Date();
        try {
            date.setDate(DFechaDeNacimiento.getValue().getDayOfMonth());
            date.setMonth(DFechaDeNacimiento.getValue().getMonthValue());
            date.setYear(DFechaDeNacimiento.getValue().getYear() - 1900);
            alumnoNuevo.setFechaNacimiento(date);
        } catch (Exception e) {
            System.out.println("No hay fecha");
        }

        alumnoNuevo.setDireccion(TDireccion.getText());
        alumnoNuevo.setEstado(true);
    }

    public void crearInscripcion() {
        if (LInscripcion.getText() != null) {
            nuevaInscripcion.setMonto(Integer.parseInt(LInscripcion.getText()));
            nuevaInscripcion.setFechaInscripcion(new Date());
            InscripcionJpaController jpaI = new InscripcionJpaController(emf);
            nuevaInscripcion.setIDInscripcion(jpaI.getInscripcionCount() + 1);
        }

    }

    public boolean validarGuardado() {
        if (TNombre.getText().isEmpty() || TPApellido.getText().isEmpty() || TSApellido.getText().isEmpty()
                || TTelefono.getText().isEmpty() || TDireccion.getText().isEmpty()
                || LInscripcion.getText().isEmpty() || DFechaDeNacimiento.getValue() == null
                || alumnoNuevo.getRutaImagen() == null) {
            return false;
        }
        return true;
    }

    @FXML
    private void BGuardarAction(ActionEvent event) {
        if (validarGuardado()) {
            validarGuardado();
            crearAlumno();
            crearInscripcion();

            AlumnoJpaController jpaA = new AlumnoJpaController(emf);
            GrupoJpaController jpaG = new GrupoJpaController(emf);
            InscripcionJpaController jpaI = new InscripcionJpaController(emf);

            jpaI.create(nuevaInscripcion);
            alumnoNuevo.setIDAlumno(jpaA.getAlumnoCount() + 1);
            alumnoNuevo.setIDInscripcionA(nuevaInscripcion);

            jpaA.create(alumnoNuevo);
            for (int i = 0; i < clases.size(); i++) {
                Grupo grupo = new Grupo();
                grupo.setIDAlumnoG(alumnoNuevo);
                grupo.setIDClaseG(clases.get(i));
                grupo.setIDGrupo(jpaG.getGrupoCount());
                jpaG.create(grupo);
            }
            Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText(null);
            dialogoAlerta.setContentText("Debes seleccionar un alumno primero");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        } else {
            Alert dialogoAlerta = new Alert(AlertType.WARNING);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText("¡No se pudo agrear!");
            dialogoAlerta.setContentText("Se dejaron campos vacios");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }

    }

    @FXML
    private void BImagenAlumnoAction(ActionEvent event) {
        FileChooser fileChosser = new FileChooser();
        String src = fileChosser.showOpenDialog(primaryStage).toString();
        Image img = new Image(new File(src).toURI().toString());
        alumnoNuevo.setRutaImagen(src);
        PaneImagen.setImage(img);
    }

    public void llenarCampos() {
        if (alumnoNuevo.getNombre() != null) {
            TNombre.setText(alumnoNuevo.getNombre());
        }
        if (alumnoNuevo.getPrimerApellido() != null) {
            TPApellido.setText(alumnoNuevo.getPrimerApellido());
        }
        if (alumnoNuevo.getSegundoApellido() != null) {
            TSApellido.setText(alumnoNuevo.getSegundoApellido());
        }
        if (alumnoNuevo.getNumeroDeCelular() != null) {
            TTelefono.setText(alumnoNuevo.getNumeroDeCelular());
        }
        if (alumnoNuevo.getFechaNacimiento() != null) {
            Instant instant = alumnoNuevo.getFechaNacimiento().toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            DFechaDeNacimiento.setValue(localDate);
        }
        if (alumnoNuevo.getDireccion() != null) {
            TDireccion.setText(alumnoNuevo.getDireccion());
        }
    }

    public void llenarInscripcion() {
        if (Integer.toString(nuevaInscripcion.getMonto()) != null) {
            LInscripcion.setText(Integer.toString(nuevaInscripcion.getMonto()));
            System.out.println("");
        }
    }

    static void initRootLayout(Stage primaryStage, List<Clase> listaClases, Alumno al) {
        clases = listaClases;
        alumnoNuevo = al;
        primeraVez = false;
        System.out.println("lo ultimo " + al.getNombre());
        System.out.println("lo ultimo 2 " + alumnoNuevo.getNombre());
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
}
