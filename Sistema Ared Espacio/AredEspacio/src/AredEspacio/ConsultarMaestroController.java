/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import JPAControllers.MaestroJpaController;
import AredEspacio.AredEspacio;
import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import static AredEspacio.PrincipalController.primaryStage;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class ConsultarMaestroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField TFBuscar;
    @FXML
    private Button BBuscar, BConsultar;
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    private ScrollPane SPListaMaestros;
    @FXML
    private TableView<Maestro> TVResultado;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    private TableColumn<Maestro, String> cNombre = new TableColumn<>("Nombre");

    static void initRootLayout(Stage primaryStage) {
        try {
            ConsultarMaestroController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarMaestro.fxml"));
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
    public void BRegresarAction(ActionEvent event){
        PrincipalController.initRootLayout(primaryStage);
    }
    
    // private ListView<String> resultadosMaestro = new ListView<>();
    @FXML
    public void BBuscarAction(ActionEvent event) {

        TVResultado.getItems().clear();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController m = new MaestroJpaController(entityManagerFactory);

        Maestro maestro = new Maestro();
        List<Maestro> listaMaestrosResultado = maestro.buscarMaestroPorNombre(TFBuscar.getText());

        if (listaMaestrosResultado.isEmpty()) {
            System.out.println("no entre");
        } else {
            System.out.println("si entre");
            for (int i = 0; i < listaMaestrosResultado.size(); i++) {
                TVResultado.getItems().add(listaMaestrosResultado.get(i));
            }

        }

    }

    @FXML
    public void MIConsultarMaestroAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void MIRegistrarMaestroAction(ActionEvent event) {
       List<Clase> cls = null;
        
        Maestro ma = null;
        
        RegistrarMaestroController.initRootLayout(primaryStage, cls, ma, true);
    }

    @FXML
    public void BConsultarAction(ActionEvent event) {
        // System.out.println(LVResultados.getSelectionModel().getSelectedItems().toString());
        ConsultarMaestroConfirmarController.initRootLayout(primaryStage, (Maestro) TVResultado.getSelectionModel().getSelectedItem());
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
    //

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController m = new MaestroJpaController(entityManagerFactory);
        TVResultado.getColumns().addAll(cNombre);
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cNombre.setMinWidth(326);
         TFBuscar.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFBuscar.getText().length() > 15) {
                    event.consume();
                }
                if(car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-'){
                    event.consume();
                }
            }
        });

        TFBuscar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                TVResultado.getItems().clear();
                Maestro maestro = new Maestro();
                List<Maestro> listaMaestrosResultado = maestro.buscarMaestroPorNombre(TFBuscar.getText());

                if (listaMaestrosResultado.isEmpty()) {
                   
                } else {
                   
                    for (int i = 0; i < listaMaestrosResultado.size(); i++) {
                        TVResultado.getItems().add(listaMaestrosResultado.get(i));
                    }

                }

            }
        });

    }

}
