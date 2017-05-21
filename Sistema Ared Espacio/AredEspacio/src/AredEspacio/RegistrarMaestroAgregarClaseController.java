/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import JPAControllers.ClaseJpaController;
import JPAControllers.MaestroJpaController;
import static AredEspacio.PrincipalController.primaryStage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class RegistrarMaestroAgregarClaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Clase> TVTotalClases;
    @FXML
    private TableView<Clase> TVAgregadasClases;
    @FXML
    private Button BAgregar;

    Clase clasesilla = new Clase();
    static Maestro maestro;
    static List<Clase> cls;
    static boolean crea;
    private int id;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    // private static TableColumn<Clase, String> listaAñadidos;

    // private TableColumn<Clase, String> todasLasClases = new TableColumn<>("Nombre");
    static void initRootLayout(Stage primaryStage, Maestro ma, boolean crear) {
        maestro = ma;
        crea = crear;
        try {
            RegistrarMaestroAgregarClaseController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarMaestroAgregarClase.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void initRootLayout(Stage primaryStage, Maestro ma, List<Clase> clases, boolean crear) {
        maestro = ma;
        System.out.println("llegue " + ma.getNombre());
        cls = clases;
        crea = crear;

        //  listaAñadidos = listaAñadidos;
        // TVAgregadasClases.getColumns().addAll(listaAñadidos);
        try {
            RegistrarMaestroAgregarClaseController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarMaestroAgregarClase.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void BAgregarAction(ActionEvent event) {
        if (TVTotalClases.getItems().size() > 0) {
            TVTotalClases.getSelectionModel().getSelectedItem();
            TVTotalClases.getItems().remove(id);
            TVAgregadasClases.getItems().add(clasesilla);
        }
    }

    @FXML
    private void BQuitarAction(ActionEvent event) {
        if (TVAgregadasClases.getItems().size() > 0) {
            TVAgregadasClases.getSelectionModel().getSelectedItem();
            TVAgregadasClases.getItems().remove(id);
            TVTotalClases.getItems().add(clasesilla);
        }
    }

    @FXML
    private void BGuardarAction(ActionEvent event) {
        List<Clase> clasesAg = TVAgregadasClases.getItems();

        RegistrarMaestroController.initRootLayout(primaryStage, clasesAg, maestro, crea);
    }

    @FXML
    private void BRegresaAction(ActionEvent event) {
        List<Clase> cls = null;

        // Maestro ma = null;
        RegistrarMaestroController.initRootLayout(primaryStage, cls, maestro, crea);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        TableColumn<Clase, String> cNombre = new TableColumn<>("Nombre");
        TableColumn<Clase, String> cClasesAgregadas = new TableColumn<>("Clases");
        cClasesAgregadas.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController m = new MaestroJpaController(entityManagerFactory);
        Clase clase = new Clase();
        List<Clase> listaClases = clase.findAll();
        if (listaClases.isEmpty()) {
            System.out.println("no hay clases registradas");
        } else {
            if(cls.size() != listaClases.size()){
            for (int i = 0; i < listaClases.size(); i++) {
                TVTotalClases.getItems().add(listaClases.get(i));
            }
            }
        }
        TVTotalClases.getColumns().addAll(cNombre);

        TVAgregadasClases.getColumns().addAll(cClasesAgregadas);
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cNombre.setMinWidth(150);

        TVTotalClases.setOnMouseClicked((event) -> {
            id = TVTotalClases.getSelectionModel().getSelectedIndex();

            clasesilla = TVTotalClases.getSelectionModel().getSelectedItem();
            System.out.println("hola compa ");
        });
        TVAgregadasClases.setOnMouseClicked((event) -> {
            id = TVAgregadasClases.getSelectionModel().getSelectedIndex();
            clasesilla = TVAgregadasClases.getSelectionModel().getSelectedItem();
        });
        //System.out.println(cls.get(0));
        if (!cls.isEmpty()) {
            for (int i = 0; i < cls.size(); i++) {
                TVAgregadasClases.getItems().add(cls.get(i));

            }
        }
        if (!cls.isEmpty() && cls.size() != listaClases.size()) {
            for (int i = 0; i < cls.size(); i++) {
                for (int j = 0; j < listaClases.size(); j++) {

                    if (cls.get(i).equals(listaClases.get(j))) {
                        
                        TVTotalClases.getItems().remove(j);
                    }
                }
            }
        }
        cClasesAgregadas.setMinWidth(150);

    }

}
