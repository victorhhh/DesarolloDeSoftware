/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Maestro;
import JPAControllers.MaestroJpaController;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class ConsultarMaestroConfirmarController implements Initializable {

    @FXML
    private Button BBaja, BEditar;
    @FXML
    private Label LProfesor, LCelular, LFechaNac, LDireccion, LEstado;
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    private ImageView IVMaestro;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    private static Maestro maestroSeleccionado;

    static void initRootLayout(Stage primaryStage, Maestro maestroSelected) {
        maestroSeleccionado = maestroSelected;
        try {
            ConsultarMaestroConfirmarController.primaryStage = primaryStage;
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarMaestroConfirmar.fxml"));
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
    private MenuItem MIConsultarClase;
    @FXML
    private ListView<?> LVClases;

    @FXML
    public void MIConsultarMaestroAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void MIRegistrarMaestroAction(ActionEvent event) {
        RegistrarMaestroController.initRootLayout(primaryStage);
    }

    public void BBuscarAction(ActionEvent event) {

    }

    @FXML
    public void BBajaAction(ActionEvent event) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController m = new MaestroJpaController(entityManagerFactory);

        Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoAlerta.setTitle("Ared Espacio");
        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "!Esta apunto de dar de modificar a " + maestroSeleccionado.getNombre());
        dialogoAlerta.setContentText("¿Esta seguro que desea modificar su estado? ");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
    
        Optional<ButtonType> result = dialogoAlerta.showAndWait();
        if (result.get() == ButtonType.OK) {
            

            if (maestroSeleccionado.getEstado() == true) {
                maestroSeleccionado.setEstado(false);
            } else {
                maestroSeleccionado.setEstado(true);
            }
            try {
                m.edit(maestroSeleccionado);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Ared Espacio");
                alert.setHeaderText(null);
                alert.setContentText("Se ha modificado a " + maestroSeleccionado.getNombre());

                alert.showAndWait();
                if (maestroSeleccionado.getEstado() == true) {
                    BBaja.setText("Baja");
                    LEstado.setText("Activo");
                } else {
                    BBaja.setText("Alta");
                    LEstado.setText("Inactivo");
                }
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ared Esapcio");
                alert.setHeaderText("No se puedo editar");
                alert.setContentText("Ocurrio un error al editar a " + maestroSeleccionado.getNombre());
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

        } else {
            
            System.out.println("bannnnn");
        }
    }

    @FXML
    public void BEditarAction(ActionEvent event) {
        MaestrosEditarController.initRootLayout(primaryStage, maestroSeleccionado);

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LProfesor.setText(maestroSeleccionado.getNombre());
        LCelular.setText(maestroSeleccionado.getNumeroDeTelefono());
        LocalDate fe = maestroSeleccionado.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LFechaNac.setText(fe.toString());
        LDireccion.setText(maestroSeleccionado.getDireccion());

        Image img = new Image(new File(maestroSeleccionado.getRutaImagen()).toURI().toString());
        IVMaestro.setImage(img);
        if (maestroSeleccionado.getEstado() == true) {
            BBaja.setText("Baja");
            LEstado.setText("Activo");
        } else {
            BBaja.setText("Alta");
            LEstado.setText("Inactivo");
        }
    }

    @FXML
    private void MIInscribirAlumnoAction(ActionEvent event) {
        InscribirAlumnoController.initRootLayout(primaryStage);
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
    private void MIConsultarAlumnoAction(ActionEvent event) {
        ConsultarAlumno1Controller.initRootLayout(primaryStage);
    }

}
