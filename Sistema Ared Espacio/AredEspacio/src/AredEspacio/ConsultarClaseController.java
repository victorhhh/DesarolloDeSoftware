/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Clase;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import JPAControllers.ClaseJpaController;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class ConsultarClaseController implements Initializable {

    @FXML
    ImageView IVLogo;
    @FXML
    TextField TFNombreClase, TFNombreProfesor;
    @FXML
    Button BConsultar, BModificar, BBaja, BBuscar;
    @FXML
    TableView<Clase> TVClases;
    Clase c1 = new Clase();
    public int IDC;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    public int tamañoLista() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        return controllerClases.getClaseCount();
    }

    static void initRootLayout(Stage primaryStage) {
        try {
            ConsultarClaseController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarClase.fxml"));
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
    
    public void AccionBuscar(ActionEvent evento) {
        Clase clase = new Clase();
        List<Clase> listaClases = clase.buscarClasesPorNombre(TFNombreClase.getText());
        TVClases.getItems().clear();
        if (listaClases.isEmpty()) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "!No se encontro registro¡");
            dialogoAlerta.setContentText("El nombre de la clase que busco no existe coincidencia ");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            java.awt.Toolkit.getDefaultToolkit().beep();
            dialogoAlerta.showAndWait();

        } else {
            for (int i = 0; i < listaClases.size(); i++) {
                TVClases.getItems().add(listaClases.get(i));
            }
            Alert da = new Alert(Alert.AlertType.INFORMATION);
            da.setTitle("Ared Espacio");
            da.setHeaderText("Consultar informacion");
            da.setContentText("Selecciona de la lista la clase que deseas consultar ");
            da.initStyle(StageStyle.UTILITY);
            java.awt.Toolkit.getDefaultToolkit().beep();
            da.showAndWait();
        }
    }

    public void AccionConsultar(ActionEvent evento) {
        if(TVClases.getSelectionModel().getSelectedItem()==null){
             Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "sin seleccion");
                dialogoAlerta.setContentText("No seleccionaste ninguna clase para consultar su informacion");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                java.awt.Toolkit.getDefaultToolkit().beep();
                dialogoAlerta.showAndWait();
        }else{
            ConsultarClase2Controller.initRootLayout(primaryStage, TVClases.getSelectionModel().getSelectedItem());
        }
        
    } 
    public void AccionRegistrarClase(ActionEvent evento) {
        RegistrarClaseController.initRootLayout(primaryStage);
    }

    public void AccionModificarClase(ActionEvent evento) {
        if(TVClases.getSelectionModel().getSelectedItem()!=null){
            ModificarClaseController.initRootLayout(primaryStage,TVClases.getSelectionModel().getSelectedItem());
        }else{
             Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "sin seleccion");
                dialogoAlerta.setContentText("No seleccionaste ninguna clase para modificar su informacion");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Clase, String> cLunes = new TableColumn<>("Lista de clases");
        cLunes.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cLunes.setMinWidth(100);
        TVClases.getColumns().addAll(cLunes);
        TVClases.getSelectionModel().setCellSelectionEnabled(true);
        TVClases.setOnMouseClicked((event) -> {
            c1 = TVClases.getSelectionModel().getSelectedItem();
        });
    }

}
