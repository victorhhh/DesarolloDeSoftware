/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.AlumnoSeleccionarClaseController.primaryStage;
import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Alumno;
import BaseDeDatos.Clase;
import BaseDeDatos.Grupo;
import BaseDeDatos.Inscripcion;
import BaseDeDatos.Promocion;
import JPAControllers.AlumnoJpaController;
import JPAControllers.GrupoJpaController;
import JPAControllers.InscripcionJpaController;
import JPAControllers.PromocionJpaController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private MenuItem c;
    @FXML
    private MenuItem MIConsultarClase;
    @FXML
    private MenuItem MIRegistrarMaestro;
    @FXML
    private MenuItem MIConsultarMaestro;
    @FXML
    private Button ButtonCancelar;
    @FXML
    private Label LabelMontoPromocion;
    @FXML
    private Label montoPromo;
    @FXML
    private Button BModificarClases;
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
    static Alumno alumnoNuevo = new Alumno();
    static Inscripcion nuevaInscripcion = new Inscripcion();
    static Promocion promocion = new Promocion();
    static List<Clase> clases;
    static boolean primeraVez;
    static private double total;
    private static int numeroPromocion;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        total = 0;
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
        
        

        List<Promocion> listaPromo;
        listaPromo = promocion.buscarPromocionPorNombre("");
        for (int i = 0; i < listaPromo.size(); i++) {
            if (listaPromo.get(i).getTipo().equals("Inscripcion")) {
                MenuItem menuItem = new MenuItem(listaPromo.get(i).getNombre());
                menuItem.setId(listaPromo.get(i).getNombre());
                BMPromocion.getItems().add(menuItem);
                menuItem.setOnAction((ActionEvent event) -> {
                    String promo = menuItem.getId();
                    promocion = promocion.buscarPromocionPorNombre(promo).get(0);
                    float descuentoPorciento = promocion.buscarPromocionPorNombre(promo).get(0).getPorcentaje();
                    numeroPromocion = promocion.buscarPromocionPorNombre(promo).get(0).getIDPromocion();
                    double montoNeto  = Double.parseDouble(LInscripcion.getText());
                    total = (montoNeto/100) * (100 - descuentoPorciento);
                    total  = Math.round(total * 100.0) / 100.0;
                    montoPromo.setText("");
                    montoPromo.setText("Monto con promocion: "+  Math.round(total * 100.0) / 100.0);
                    BMPromocion.setText(promo);
                });
            }
        }

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
    public void BRegresarAction(ActionEvent event){
        PrincipalController.initRootLayout(primaryStage);
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
            
        }

        alumnoNuevo.setDireccion(TDireccion.getText());
        alumnoNuevo.setEstado(true);
    }

    public void crearInscripcion() {
        if (LInscripcion.getText() != null) {
            if(total == 0){
                nuevaInscripcion.setMonto(Integer.parseInt(LInscripcion.getText()));
            }else{
                nuevaInscripcion.setMonto((int) total);
                nuevaInscripcion.setIDPromocionI(promocion);
            }
            
            nuevaInscripcion.setFechaInscripcion(new Date());
            InscripcionJpaController jpaI = new InscripcionJpaController(emf);
            nuevaInscripcion.setIDInscripcion(jpaI.getInscripcionCount() + 1);
        }

    }

    public boolean validarGuardado() {
        if (TNombre.getText().isEmpty() || TPApellido.getText().isEmpty() || LInscripcion.getText().equals("0")
                || TSApellido.getText().isEmpty()
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
            dialogoAlerta.setContentText("Alumno guardado con exito");
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
        total = 0;

    }

    @FXML
    private void BImagenAlumnoAction(ActionEvent event) {
        try {
            FileChooser fileChosser = new FileChooser();
            String src = fileChosser.showOpenDialog(primaryStage).toString();
            Image img = new Image(new File(src).toURI().toString());
            alumnoNuevo.setRutaImagen(src);
            PaneImagen.setImage(img);
        } catch (NullPointerException e) {

        }
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
        }
    }

    static void initRootLayout(Stage primaryStage, List<Clase> listaClases, Alumno al) {
        clases = listaClases;
        alumnoNuevo = al;
        primeraVez = false;
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
        RegistrarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarMaestroAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    private void ButtonCancelarAction(ActionEvent event) {
        PrincipalController.initRootLayout(primaryStage);
    }
}
