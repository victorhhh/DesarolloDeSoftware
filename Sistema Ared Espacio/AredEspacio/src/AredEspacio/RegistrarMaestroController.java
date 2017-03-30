/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Maestro;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import JPAControllers.MaestroJpaController;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class RegistrarMaestroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BEliminar, BGuardar, BAgregar;
    @FXML
    private TextField TFNombre, TFCelular, TFDireccion, TFPrimerApellido, TFSegundoApellido;

    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    private DatePicker DPFechaNac;
    @FXML
    private ImageView IVMaestro;

    private String src;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            RegistrarMaestroController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarMaestro.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private MenuItem MIInscribirAlumno;
    @FXML
    private MenuItem MIConsultarAlumno;
    @FXML
    private MenuItem MIConsultarMaestro;
    @FXML
    private MenuItem MIRegistrarMaestro;
    @FXML
    private MenuItem MIRegistrarClase;
    @FXML
    private MenuItem IMConsultarClase;
    @FXML
    private TableView<?> TVNombreClase;
    @FXML
    private Button BExminar;

    

    @FXML
    public void MIConsultarMaestroAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void MIRegistrarMaestroAction(ActionEvent event) {
        RegistrarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void BGuardarAction(ActionEvent event) {
        if (TFNombre.getText().isEmpty() || TFPrimerApellido.getText().isEmpty() || TFSegundoApellido.getText().isEmpty() || TFCelular.getText().isEmpty() || TFDireccion.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ared Espacio");
            alert.setHeaderText("Campos vacios");
            alert.setContentText("Los campos estan vacios, rellenos para continuar");
            alert.showAndWait();
        } else {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            MaestroJpaController m = new MaestroJpaController(entityManagerFactory);
            Maestro maestro = new Maestro();

            String nombre = TFNombre.getText();

            // String fecha = TFFechaNac.getText();
            LocalDate fechini = DPFechaNac.getValue();
            java.sql.Date fecha = java.sql.Date.valueOf(fechini);
            //System.out.println("hola soy fechini"+fecha);
            maestro.setFechaNacimiento(fecha);
            maestro.setDireccion(TFDireccion.getText());
            maestro.setEstado(true);
            maestro.setNombre(TFNombre.getText());
            maestro.setNumeroDeTelefono(TFCelular.getText());
            maestro.setPrimerApellido(TFPrimerApellido.getText());
            maestro.setSegundoApellido(TFSegundoApellido.getText());
            maestro.setRutaImagen(src);

            try {
                m.create(maestro);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Ared Espacio");
                alert.setHeaderText(null);
                alert.setContentText("Se ha guardado correctamente el maestro¡¡¡");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ared Esapcio");
                alert.setHeaderText("No se pudo guardar");
                alert.setContentText("No se pudo guardar en la base de datos ");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("Stacktrace:");

                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);
                alert.getDialogPane().setExpandableContent(expContent);

                alert.showAndWait();

                Logger.getLogger(RegistrarMaestroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void BExaminarAction(ActionEvent event) {
        FileChooser fileChosser = new FileChooser();
        src = fileChosser.showOpenDialog(primaryStage).toString();
        System.out.println(src);
        Image img = new Image(new File(src).toURI().toString());
        IVMaestro.setImage(img);

    }

    @FXML
    public void BAgregarAction(ActionEvent event) {

    }
    
    @FXML
    public void BEliminarAction(ActionEvent event) {

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

    @FXML
    public void DPFechaNacAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //private TextField TFNombre, TFCelular, TFDireccion, TFPrimerApellido, TFSegundoApellido;
        TFNombre.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFNombre.getText().length() > 15) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFPrimerApellido.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFPrimerApellido.getText().length() > 15) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFSegundoApellido.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);

                if (TFSegundoApellido.getText().length() > 15) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFDireccion.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);

                if (TFDireccion.getText().length() > 40) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFCelular.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                } else {
                    event.consume();
                }
                if (TFCelular.getText().length() > 10) {
                    event.consume();
                }
            }
        });

    }

    @FXML
    private void MIInscribirAlumnoAction(ActionEvent event) {
        InscribirAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarAlumnoAction(ActionEvent event) {
        ConsultarAlumno1Controller.initRootLayout(primaryStage);
    }

    @FXML
    private void MIRegistrarClaseAction(ActionEvent event) {
        RegistrarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    private void IMConsultarClaseAction(ActionEvent event) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

}
