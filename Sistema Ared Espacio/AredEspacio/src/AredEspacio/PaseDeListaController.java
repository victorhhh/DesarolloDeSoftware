/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Alumno;
import BaseDeDatos.Asistencia;
import BaseDeDatos.Clase;
import BaseDeDatos.Grupo;
import JPAControllers.AsistenciaJpaController;
import JPAControllers.GrupoJpaController;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
public class PaseDeListaController implements Initializable {

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
    private MenuItem MIRegistrarMaestro;
    @FXML
    private MenuItem MIConsultarMaestro;
    @FXML
    private Label LConsultarAlumnos;
    @FXML
    private Button BCancelar;
    @FXML
    private Button Guardar;
    @FXML
    private TableView<Alumno> TLFaltaron;
    @FXML
    private TableView<Alumno> TLAsistieron;
    @FXML
    private SplitMenuButton BMClase;
    @FXML
    private Button BAgregarAlumno;
    @FXML
    private Button BQuitarAlumnoAction;

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    Alumno alumno = new Alumno();
    Clase claseActual = new Clase();
    private int id;

    static void initRootLayout(Stage primaryStage) {
        try {
            PaseDeListaController.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("PaseDeLista.fxml"));
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
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn<Alumno, String> nombreAlumno = new TableColumn<>("Nombre");
        TableColumn<Alumno, String> pApellido = new TableColumn<>("P.Apellido");
        TableColumn<Alumno, String> sApellido = new TableColumn<>("S.Apellido");
        nombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        pApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        sApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        TLFaltaron.getColumns().addAll(nombreAlumno, pApellido, sApellido);

        TableColumn<Alumno, String> nombreAlumno1 = new TableColumn<>("Nombre");
        TableColumn<Alumno, String> pApellido1 = new TableColumn<>("P.Apellido");
        TableColumn<Alumno, String> sApellido1 = new TableColumn<>("S.Apellido");
        nombreAlumno1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        pApellido1.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        sApellido1.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        TLAsistieron.getColumns().addAll(nombreAlumno1, pApellido1, sApellido1);

        Clase clase = new Clase();
        List<Clase> listaClases;
        listaClases = clase.findAll();
        for (int i = 0; i < listaClases.size(); i++) {
            if (listaClases.get(i).getEstado()) {

                MenuItem menuItem = new MenuItem(listaClases.get(i).getNombre());
                menuItem.setText(listaClases.get(i).getNombre());
                menuItem.setId(listaClases.get(i).getIDClase().toString());

                BMClase.getItems().add(menuItem);
                menuItem.setOnAction((event) -> {
                    claseActual = clase.buscarClasePorID(Integer.parseInt(menuItem.getId())).get(0);
                    String clas = menuItem.getId();
                    BMClase.setText("");
                    BMClase.setText(menuItem.getText());
                    llenar(menuItem.getId());
                    
                });
            }

        }

    }

    public void llenar(String id) {
        TLFaltaron.getItems().clear();
        GrupoJpaController grp = new GrupoJpaController(emf);
        List<Grupo> listaGrupo = grp.findGrupoEntities();
        for (int i = 0; i < listaGrupo.size(); i++) {
            if (listaGrupo.get(i).getIDClaseG().getIDClase().equals(Integer.parseInt(id))) {
                TLFaltaron.getItems().add(listaGrupo.get(i).getIDAlumnoG());
            }
        }

    }

    @FXML
    private void BAlumnosAction(ActionEvent event) {
    }

    @FXML
    private void MIRegistrarClasesAction(ActionEvent event) {
    }

    @FXML
    private void MIConsultarClaseAction(ActionEvent event) {
    }

    @FXML
    private void BClasesAction(ActionEvent event) {
    }

    @FXML
    private void BPromocionesAction(ActionEvent event) {
    }

    
     @FXML
    public void BRegresarAction(ActionEvent event){
        PrincipalController.initRootLayout(primaryStage);
    }
    @FXML
    private void BReportesAction(ActionEvent event) {
    }

    @FXML
    private void MIRegistrarMaestroAction(ActionEvent event) {
    }

    @FXML
    private void MIConsultarMaestroAction(ActionEvent event) {
    }

    @FXML
    private void BMaestrosAction(ActionEvent event) {
    }

    @FXML
    private void BCancelarAction(ActionEvent event) {
        PrincipalController.initRootLayout(primaryStage);
    }

    @FXML
    private void BPagarAction(ActionEvent event) {
        List<Alumno> falta = TLFaltaron.getItems();
        List<Alumno> asiste = TLAsistieron.getItems();

        AsistenciaJpaController asi = new AsistenciaJpaController(emf);
        int idCont;
        idCont = asi.getAsistenciaCount();
        idCont++;

        if (falta.size() > 0) {

            for (int i = 0; i < falta.size(); i++) {
                Asistencia as = new Asistencia();

                as.setAsistencia(false);
                as.setIDAlumnoAsis(falta.get(i));
                as.setFecha(new Date());
                as.setIDClaseAsis(claseActual);
                as.setIDAsistencia(idCont);

                asi.create(as);

                idCont++;
            }

        }
        if (asiste.size() > 0) {

            for (int i = 0; i < asiste.size(); i++) {
                Asistencia as = new Asistencia();
                as.setAsistencia(true);
                as.setIDAlumnoAsis(asiste.get(i));
                as.setFecha(new Date());
                as.setIDClaseAsis(claseActual);
                as.setIDAsistencia(idCont);

                asi.create(as);

                idCont++;
            }

        }
        TLAsistieron.getColumns().clear();
        TLFaltaron.getColumns().clear();

        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
        dialogoAlerta.setTitle("Ared Espacio");
        dialogoAlerta.setHeaderText("¡Se pasó lista correctamente!");
        dialogoAlerta.setContentText("¡Ups!");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();

    }

    @FXML
    private void tFaltaOnMouseClicked(MouseEvent event) {
        id = TLFaltaron.getSelectionModel().getSelectedIndex();
        alumno = TLFaltaron.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void tAsistenciaOnMouseClicked(MouseEvent event) {
        id = TLAsistieron.getSelectionModel().getSelectedIndex();
        alumno = TLAsistieron.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void BAgregarAlumnoAction(ActionEvent event) {
        if (TLFaltaron.getItems().size() > 0) {
            TLFaltaron.getSelectionModel().getSelectedItem();
            TLFaltaron.getItems().remove(id);
            TLAsistieron.getItems().add(alumno);
        }

    }

    @FXML
    private void BQuitarAlumnoAction(ActionEvent event) {
        if (TLAsistieron.getItems().size() > 0) {
            TLAsistieron.getSelectionModel().getSelectedItem();
            TLAsistieron.getItems().remove(id);
            TLFaltaron.getItems().add(alumno);
        }

    }

}
