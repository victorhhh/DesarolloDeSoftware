/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Alumno;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yoresroy
 */
public class RegistrarPagoAlumnoController implements Initializable {

    @FXML
    private MenuButton BAlumnos;
    @FXML
    private MenuButton BClases;
    @FXML
    private MenuItem MIRegistrarClases;
    @FXML
    private MenuItem MIConsultarClase;
    @FXML
    private MenuButton BPromociones;
    @FXML
    private MenuButton BReportes;
    @FXML
    private MenuButton BMaestros;
    @FXML
    private MenuItem MIRegistratMaestro;
    @FXML
    private MenuItem BIConsultarMaestro;
    @FXML
    private Label LConsultarAlumnos;
    @FXML
    private Button BBuscar;
    @FXML
    private TextField TNombreAlumno;
    @FXML
    private Label LBuscar;
    @FXML
    private Button registrarPago;
    @FXML
    private ImageView PaneImagen;
    @FXML
    private TableView<Alumno> TResultadoAlumno;
    
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    private Alumno al = new Alumno();

    static void initRootLayout(Stage primaryStage) {
        try {
            RegistrarPagoAlumnoController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarPagoAlumno.fxml"));
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
        //BOpcionesAlumno.setDisable(true);
        MenuItem inscribirAlumno = new MenuItem("Inscribir Alumno");
        BAlumnos.getItems().addAll(inscribirAlumno);

        inscribirAlumno.setOnAction((ActionEvent event) -> {
            InscribirAlumnoController.initRootLayout(primaryStage);
        });

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

        cNombre.setMinWidth(50);
        cTelefono.setMinWidth(100);
        cTelefono.setMinWidth(100);

        TResultadoAlumno.getColumns().addAll(cNombre, cPrimerApellido, cSegundoApellido, cTelefono, cDireccion);
        TResultadoAlumno.setOnMouseClicked((event) -> {
            al = TResultadoAlumno.getSelectionModel().getSelectedItem();
            
        });

        TNombreAlumno.setOnKeyTyped((KeyEvent event) -> {

            char car = event.getCharacter().charAt(0);
            if (TNombreAlumno.getText().length() > 78) {
                event.consume();
            }
        });

        TNombreAlumno.setOnAction((ActionEvent event) -> {

        });
    }   
    
    

    @FXML
    private void BAlumnosAction(ActionEvent event) {
    }

    @FXML
    private void MIRegistrarClasesAction(ActionEvent event) {
    }

    @FXML
    private void MIConsultarAclasection(ActionEvent event) {
    }

    @FXML
    private void BClasesAction(ActionEvent event) {
    }
     @FXML
    public void BRegresarAction(ActionEvent event){
        PrincipalController.initRootLayout(primaryStage);
    }

    @FXML
    private void BPromocionesAction(ActionEvent event) {
    }

    @FXML
    private void BReportesAction(ActionEvent event) {
    }

    @FXML
    private void MIRegistratMaestroAction(ActionEvent event) {
    }

    @FXML
    private void BIConsultarMaestroAction(ActionEvent event) {
    }

    @FXML
    private void BMaestrosAction(ActionEvent event) {
    }

    @FXML
    private void BBuscarAction(ActionEvent event) {
        //Alumno al = new Alumno();
        List<Alumno> lisAl = al.buscarAlumnosPorNombre(TNombreAlumno.getText());

        TResultadoAlumno.getItems().clear();
        if (!lisAl.isEmpty()) {
            for (int i = 0; i < lisAl.size(); i++) {
                TResultadoAlumno.getItems().add(lisAl.get(i));
            }
        }

    }

    @FXML
    private void TNombreAlumnoAction(ActionEvent event) {
    }

    @FXML
    private void BRegistrarPago(ActionEvent event) {
        if (1 < TResultadoAlumno.getItems().size()) {
            System.out.println("el ñiño tiene " + al.getNombre() + " " + al.getPrimerApellido() + " " 
                    + al.getSegundoApellido() + " " + al.getRutaImagen() +" "+ al.getNumeroDeCelular() + al.getDireccion() );
        RegistrarPagoAlumno2Controller.initRootLayout(primaryStage, al);
        } else {
            Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText(null);
            dialogoAlerta.setContentText("Debes seleccionar un alumno primero");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }
    }
    
}
