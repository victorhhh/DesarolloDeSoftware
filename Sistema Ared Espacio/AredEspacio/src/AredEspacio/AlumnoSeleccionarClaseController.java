/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.InscribirAlumnoController.primaryStage;
import BaseDeDatos.Alumno;
import BaseDeDatos.Clase;
import BaseDeDatos.Inscripcion;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yoresroy
 */
public class AlumnoSeleccionarClaseController implements Initializable {

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
    private Button BOpcionesAlumno;
    @FXML
    private ImageView PaneImagen;
    @FXML
    private TableView<Clase> TClases;
    @FXML
    private Button BRegresar;
    @FXML
    private TableView<Clase> TClasesAgregadas;
    @FXML
    private Button BagregarClase;
    @FXML
    private Button BQuitarClase;
    Clase clase = new Clase();
    private int id;
    static List<Clase> clasesExistentes;
    static Alumno alumnoNuevo;
    static Inscripcion nuevaInscripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MenuItem editarAlumno = new MenuItem("Consultar/Editar");
        BAlumnos.getItems().addAll(editarAlumno);

        editarAlumno.setOnAction((ActionEvent event) -> {
            ConsultarAlumno1Controller.initRootLayout(primaryStage);
        });

        TableColumn<Clase, String> cNombre = new TableColumn<>("Clase");
        TableColumn<Clase, String> cDia = new TableColumn<>("Dia");
        TableColumn<Clase, String> cHora = new TableColumn<>("Horario");
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
        cHora.setCellValueFactory(new PropertyValueFactory<>("hora"));

        TableColumn<Clase, String> cNombre2 = new TableColumn<>("Clase");
        TableColumn<Clase, String> cDia2 = new TableColumn<>("Dia");
        TableColumn<Clase, String> cHora2 = new TableColumn<>("Horario");
        cNombre2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cDia2.setCellValueFactory(new PropertyValueFactory<>("dia"));
        cHora2.setCellValueFactory(new PropertyValueFactory<>("hora"));

        TClasesAgregadas.getColumns().addAll(cNombre2, cDia2, cHora2);
        cNombre.setMinWidth(97);

        TClases.getColumns().addAll(cNombre, cDia, cHora);
        cNombre.setMinWidth(97);

        Clase c = new Clase();
        List<Clase> clases = c.findAll();
        TClases.getItems().clear();
        if (!clasesExistentes.isEmpty()) {
            for (int i = 0; i < clasesExistentes.size(); i++) {
                TClasesAgregadas.getItems().add(clasesExistentes.get(i));
            }
        }

        if (!clases.isEmpty()) {
            for (int i = 0; i < clases.size(); i++) {
                if (!clasesExistentes.isEmpty()) {
                    int cont = clasesExistentes.size();
                    for (int o = 0; o < clasesExistentes.size(); o++) {
                        if (clases.get(i).getIDClase() == clasesExistentes.get(o).getIDClase()) {
                            cont--;
                        }
                    }
                    if (cont == clasesExistentes.size()) {
                        System.out.println("a a " + clases.get(i));
                        TClases.getItems().add(clases.get(i));
                    }
                } else {
                    System.out.println("");
                    TClases.getItems().add(clases.get(i));
                }
            }
        }

        TClases.setOnMouseClicked((event) -> {
            id = TClases.getSelectionModel().getSelectedIndex();
            clase = TClases.getSelectionModel().getSelectedItem();
        });
        TClasesAgregadas.setOnMouseClicked((event) -> {
            id = TClasesAgregadas.getSelectionModel().getSelectedIndex();
            clase = TClasesAgregadas.getSelectionModel().getSelectedItem();
        });
    }

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            AlumnoSeleccionarClaseController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("AlumnoSeleccionarClase.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void BOpcionesAlumnoAction(ActionEvent event) {
        List<Clase> clasesAgregar = TClasesAgregadas.getItems();
        System.out.println("se va "+ alumnoNuevo.getNombre() );
        InscribirAlumnoController.initRootLayout(primaryStage, clasesAgregar, alumnoNuevo);
    }

    @FXML
    private void BRegresar(ActionEvent event) {
        InscribirAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void BAgregarClaseAction(ActionEvent event) {
        if (TClases.getItems().size() > 0) {
            TClases.getSelectionModel().getSelectedItem();
            TClases.getItems().remove(id);
            TClasesAgregadas.getItems().add(clase);
        }
    }

    @FXML
    private void BQuitarAction(ActionEvent event) {
        if (TClasesAgregadas.getItems().size() > 0) {
            TClasesAgregadas.getSelectionModel().getSelectedItem();
            TClasesAgregadas.getItems().remove(id);
            TClases.getItems().add(clase);
        }
    }

    static void initRootLayout(Stage primaryStage, List<Clase> clase, Alumno alumno, Inscripcion inscri) {
        clasesExistentes = clase;
        alumnoNuevo=alumno;
        nuevaInscripcion=inscri;
        System.out.println("llegó " + alumnoNuevo.getNombre());
        try {
            AlumnoSeleccionarClaseController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("AlumnoSeleccionarClase.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
