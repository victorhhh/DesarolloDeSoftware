/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import JPAControllers.MaestroJpaController;
import static AredEspacio.ConsultarMaestroConfirmarController.primaryStage;
import static AredEspacio.PrincipalController.primaryStage;
import static AredEspacio.RegistrarMaestroController.primaryStage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class MaestrosEditarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BQuitar, BGuardar, BAgregar;
    @FXML
    private TextField TFNombre, TFCelular, TFFechaNac, TFDireccion, TFSueldo, TFPrimerApellido, TFSegundoApellido;
    @FXML
    private ScrollPane SPClases;
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    private DatePicker DPFechaNac;
    @FXML
    private ImageView IVMaestro;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    private static Maestro maestroSeleccionado;
    private String src;

    static void initRootLayout(Stage primaryStage, Maestro maestroSelct) {
        maestroSeleccionado = maestroSelct;
        try {
            MaestrosEditarController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("MaestrosEditar.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void MIConsultarMaestrosAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void MIRegistrarMaestroAction(ActionEvent event) {
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
    public void BReportesAction(ActionEvent event) {

    }

    @FXML
    public void BExaminarAction(ActionEvent event) {
        FileChooser fileChosser = new FileChooser();
        src = fileChosser.showOpenDialog(primaryStage).toString();
        Image img = new Image(new File(src).toURI().toString());
     IVMaestro.setImage(img);
    }
    
    
     @FXML
    public void BRegresarAction(ActionEvent event){
        PrincipalController.initRootLayout(primaryStage);
    }

    @FXML
    public void BQuitarAction(ActionEvent event) {

    }

    @FXML
    public void BAgregarAction(ActionEvent event) {

    }

    @FXML
    public void BGuardarAction(ActionEvent event) {
        if(TFNombre.getText().isEmpty() || TFPrimerApellido.getText().isEmpty() || TFSegundoApellido.getText().isEmpty() || TFDireccion.getText().isEmpty() || TFPrimerApellido.getText().isEmpty() || src.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ared Esapcio");
            alert.setHeaderText("Campos vacios");
            alert.setContentText("Los campos estan vacion, intentele de nuevo");
            alert.showAndWait();
        }else{
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController m = new MaestroJpaController(entityManagerFactory);
        Maestro maestro = new Maestro();
        LocalDate fechini = DPFechaNac.getValue();
        java.sql.Date fecha = java.sql.Date.valueOf(fechini);
        Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoAlerta.setTitle("Ared Espacio");
        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "!Esta apunto de modificar a" + maestroSeleccionado.getNombre());
        dialogoAlerta.setContentText("¿Esta seguro que desea modificarlo? ");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        
        //maestro.
        Optional<ButtonType> result = dialogoAlerta.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
             
//       System.out.println(src);
  //     Image img = new Image(new File(src).toURI().toString());
//        IVMaestro.setImage(img);

            maestroSeleccionado.setDireccion(TFDireccion.getText());
            maestroSeleccionado.setFechaNacimiento(fecha);
            maestroSeleccionado.setNombre(TFNombre.getText());
            maestroSeleccionado.setNumeroDeTelefono(TFCelular.getText());
            maestroSeleccionado.setPrimerApellido(TFPrimerApellido.getText());
            maestroSeleccionado.setSegundoApellido(TFSegundoApellido.getText());
            maestroSeleccionado.setRutaImagen(src);
           

            try {
                m.edit(maestroSeleccionado);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ared Espacio");
                alert.setHeaderText(null);
                alert.setContentText("Se ha dado de baja a " + maestroSeleccionado.getNombre()+" "+maestroSeleccionado.getPrimerApellido()+" "+maestroSeleccionado.getSegundoApellido());

                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ared Esapcio");
                alert.setHeaderText("No se puedo editar");
                alert.setContentText("Ocurrio un error al editar a " + maestroSeleccionado.getNombre()+" "+maestroSeleccionado.getPrimerApellido()+" "+maestroSeleccionado.getSegundoApellido());
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
                Logger.getLogger(ConsultarMaestroConfirmarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        TFNombre.setText(maestroSeleccionado.getNombre());
        TFCelular.setText(maestroSeleccionado.getNumeroDeTelefono());

        LocalDate f = maestroSeleccionado.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       // System.out.println(f);

        DPFechaNac.setValue(f);
        TFDireccion.setText(maestroSeleccionado.getDireccion());
        TFPrimerApellido.setText(maestroSeleccionado.getPrimerApellido());
        TFSegundoApellido.setText(maestroSeleccionado.getSegundoApellido());

        Image img = new Image(new File(maestroSeleccionado.getRutaImagen()).toURI().toString());
        IVMaestro.setImage(img);
        src = maestroSeleccionado.getRutaImagen();
        //private TextField TFNombre, TFCelular, TFFechaNac, TFDireccion, TFSueldo, TFPrimerApellido, TFSegundoApellido;
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
                if(car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-'){
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
                if(car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-'){
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
                if(car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-'){
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
                if(car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-'){
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
                if (TFCelular.getText().length() > 1) {
                    event.consume();
                }
            }
        });
    }

}
