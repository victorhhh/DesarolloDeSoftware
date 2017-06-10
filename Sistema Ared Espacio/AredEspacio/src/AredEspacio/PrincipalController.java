/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.EditarAlumnoController.primaryStage;
import BaseDeDatos.Alumno;
import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class PrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    @FXML
    private MenuItem IMInscribirAlumno;
    @FXML
    private MenuItem MIConsultarAlumno;
    @FXML
    private MenuItem MIRegistrar;
    @FXML
    private MenuItem MIConsultar;
    @FXML
    private MenuItem MIRegistrarClases;
    @FXML
    private MenuItem MIConsultarClases;
    @FXML
    private MenuItem MICrearReporte;
    @FXML
    private MenuItem MICrearPromocion;
    @FXML
    private MenuItem MIConsultarPromociones;
    @FXML
    private MenuItem BPagoMensualidad;
    @FXML
    private MenuItem MIPaseLista;
    @FXML
    private TableView<Alumno> tablaNotificaciones;

   @FXML
    public void MIConsultarAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void MIRegistrarAction(ActionEvent event) {
        List<Clase> cls = null;
        
        Maestro ma = null;
        
        RegistrarMaestroController.initRootLayout(primaryStage, cls, ma, true);
    }
    @FXML
    public void BAlumnosAction(ActionEvent event) {

    }

    @FXML
    public void BMaestrosAction(ActionEvent event) {

    }

    @FXML
    public void BClasesAction(ActionEvent event) {

    }

    @FXML
    public void BPromocionesAction(ActionEvent event) {

    }

    @FXML
    public void BReporteAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TableColumn<Alumno, String> cNombre = new TableColumn<>("Nombre");
        TableColumn<Alumno, String> cPrimerApellido = new TableColumn<>("P.Apellido");
        TableColumn<Alumno, String> cSegundoApellido = new TableColumn<>("S.Apellido");
        TableColumn<Alumno, String> cTelefono = new TableColumn<>("Telefono");
        TableColumn<Alumno, String> cDireccion = new TableColumn<>("Dirección");

        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cTelefono.setCellValueFactory(new PropertyValueFactory<>("numeroDeCelular"));
        cDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        cPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        cSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        
        cNombre.setMinWidth(100);
        cPrimerApellido.setMinWidth(100);
        cSegundoApellido.setMaxWidth(100);
        cTelefono.setMinWidth(100);
        cTelefono.setMinWidth(100);
        cDireccion.setMinWidth(100);
        
        tablaNotificaciones.getColumns().addAll(cNombre, cPrimerApellido, cSegundoApellido, cTelefono, cDireccion);
        
        
        Alumno al = new Alumno();
        List<Alumno> lisAl = al.buscarAlumnosPorNombre("");
        tablaNotificaciones.getItems().clear();
        if (!lisAl.isEmpty()) {
            for (int i = 0; i < lisAl.size(); i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lisAl.get(i).getIDMensualidadA().getFechaPago());
                calendar.add(Calendar.MONTH, 1);
                if(new Date().after(calendar.getTime()) && lisAl.get(i).getEstado()){
                    tablaNotificaciones.getItems().add(lisAl.get(i));
                } 
            }
        }
        
    }

    

    static void initRootLayout(Stage primaryStage) {
        try {
            PrincipalController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("Principal.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    

    @FXML
    private void IMInscribirAlumnoAction(ActionEvent event) {
        InscribirAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarAlumnoAction(ActionEvent event) {
        ConsultarAlumno1Controller.initRootLayout(primaryStage);
    }

    @FXML
    private void MIRegistrarClases(ActionEvent event) {
        RegistrarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarClasesAction(ActionEvent event) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    @FXML
   
        void MICrearReporteAction(ActionEvent event) {
        ReportesController.initRootLayout(primaryStage);
    }
    
    @FXML
   
        void MICrearPromocionAction(ActionEvent event) {
        CrearPromocionController.initRootLayout(primaryStage);
    }

        
    @FXML
   
        void MIConsultarPromocionesAction(ActionEvent event) {
        ConsultarPromocionController.initRootLayout(primaryStage);
    }

    @FXML
    private void BPagoMensualidadAction(ActionEvent event) {
        RegistrarPagoAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void paseListaAction(ActionEvent event) {
        PaseDeListaController.initRootLayout(primaryStage);
    }
}
