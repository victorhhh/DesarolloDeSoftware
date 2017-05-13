/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Promocion;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
 * @author ossiel
 */
public class ConsultarPromocionController implements Initializable {
  @FXML
     ImageView IVLogo;

    @FXML
    TextField TFNombrePromocion;

    @FXML
    Button BConsultar,BBuscar;

    @FXML
    MenuButton MBAlumnos, MBMaestros,MBClases,MBPromociones, MBReportes;

    @FXML
    MenuItem MIRegistrar, MIModificar,MICrearPromocion;

    @FXML
    TableView<Promocion> TVPromociones;
    Promocion promo= new Promocion();
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    
    static void initRootLayout(Stage primaryStage) {
        try {
            ConsultarPromocionController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarPromocion.fxml"));
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
    void AccionBuscar(ActionEvent event) {
       Promocion pr = new Promocion();
       List<Promocion> listaPromociones = pr.buscarPromocionPorNombre(TFNombrePromocion.getText());
        TVPromociones.getItems().clear();
        if (listaPromociones.isEmpty()) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "!No se encontro registro¡");
            dialogoAlerta.setContentText("El nombre de la promocion que busco no existe coincidencia ");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();

        } else {
            for (int i = 0; i < listaPromociones.size(); i++) {
                TVPromociones.getItems().add(listaPromociones.get(i));
            }
            Alert da = new Alert(Alert.AlertType.INFORMATION);
            da.setTitle("Ared Espacio");
            da.setHeaderText("Consultar informacion");
            da.setContentText("Selecciona de la lista la promocion que deseas consultar ");
            da.initStyle(StageStyle.UTILITY);
            da.showAndWait();
        }
    }

    @FXML
    void AccionConsultar(ActionEvent event) {
        if(TVPromociones.getSelectionModel().getSelectedItem()==null){
             Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "sin seleccion");
                dialogoAlerta.setContentText("No seleccionaste ninguna promocion para consultar su informacion");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                java.awt.Toolkit.getDefaultToolkit().beep();
                dialogoAlerta.showAndWait();
        }else{
           ConsultarPromociones2Controller.initRootLayout(primaryStage, TVPromociones.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void AccionCrearPromocion(ActionEvent event) {
        CrearPromocionController.initRootLayout(primaryStage);
    }

    @FXML
    void AccionModificarClase(ActionEvent event) {

    }

    @FXML
    void AccionRegistrarClase(ActionEvent event) {

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Promocion, String> cpromo = new TableColumn<>("Lista de promociones");
        
        cpromo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TVPromociones.getColumns().addAll(cpromo);
        TVPromociones.getSelectionModel().setCellSelectionEnabled(true);
        TVPromociones.setOnMouseClicked((event) -> {
            promo = TVPromociones.getSelectionModel().getSelectedItem();
        });
         TFNombrePromocion.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (TFNombrePromocion.getText().length() > 22) {
                    event.consume();
                }
            }
        });
    }    
    
}
