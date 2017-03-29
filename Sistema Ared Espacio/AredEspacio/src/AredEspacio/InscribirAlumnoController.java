/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Alumno;
import BaseDeDatos.Clase;
import BaseDeDatos.Inscripcion;
import JPAControllers.AlumnoJpaController;
import JPAControllers.InscripcionJpaController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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

    Alumno alumnoNuevo = new Alumno();
    Inscripcion nuevaInscripcion = new Inscripcion();
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
        if(!primeraVez){
            for(int i = 0 ; i < clases.size() ; i++){
            TClases.getItems().add(clases.get(i));
            }
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
        if(TClases.getColumns().isEmpty()){
            AlumnoSeleccionarClaseController.initRootLayout(primaryStage);
        }else{
            AlumnoSeleccionarClaseController.initRootLayout(primaryStage, TClases.getItems());   
        }
    }

    @FXML
    private void BGuardarAction(ActionEvent event) {
        TableColumn<Clase, String> cClase = new TableColumn<>("Clase");
        TableColumn<Clase, String> cDia = new TableColumn<>("Dia");

        cClase.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        TClases.getColumns().addAll(cClase, cDia);

        alumnoNuevo.setNombre(TNombre.getText());
        alumnoNuevo.setPrimerApellido(TPApellido.getText());
        alumnoNuevo.setSegundoApellido(TSApellido.getText());
        alumnoNuevo.setNumeroDeCelular(TTelefono.getText());
        Date date = new Date();

        date.setDate(DFechaDeNacimiento.getValue().getDayOfMonth());
        date.setMonth(DFechaDeNacimiento.getValue().getMonthValue());
        date.setYear(DFechaDeNacimiento.getValue().getYear() - 1900);
        alumnoNuevo.setFechaNacimiento(date);
        alumnoNuevo.setDireccion(TDireccion.getText());
        alumnoNuevo.setEstado(true);

        nuevaInscripcion.setMonto(Integer.parseInt(LInscripcion.getText()));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        EntityManager em = emf.createEntityManager();
        InscripcionJpaController jpaClase = new InscripcionJpaController(emf);
        AlumnoJpaController jpaA = new AlumnoJpaController(emf);
        nuevaInscripcion.setIDInscripcion(jpaClase.getInscripcionCount() + 1);
        Date d = new Date();

        nuevaInscripcion.setFechaInscripcion(d);
        alumnoNuevo.setIDAlumno(jpaA.getAlumnoCount() + 1);
        alumnoNuevo.setIDInscripcionA(nuevaInscripcion);
        jpaClase.create(nuevaInscripcion);
        jpaA.create(alumnoNuevo);

        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        //EntityManager em = emf.createEntityManager();
        //em.persist(nuevaInscripcion);
    }

    @FXML
    private void BImagenAlumnoAction(ActionEvent event) {
        FileChooser fileChosser = new FileChooser();
        String src = fileChosser.showOpenDialog(primaryStage).toString();
        Image img = new Image(new File(src).toURI().toString());
        alumnoNuevo.setRutaImagen(src);
        PaneImagen.setImage(img);
    }

    static void initRootLayout(Stage primaryStage, List<Clase> listaClases) {
        clases = listaClases;
        primeraVez=false;
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
