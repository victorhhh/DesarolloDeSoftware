/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Promocion;
import JPAControllers.PromocionJpaController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class ModificarPromocionController implements Initializable {

    @FXML
    Button BGuardar;

    @FXML
    MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;

    @FXML
    MenuItem MIRegistrar, MIConsultar, MIConsultarPromocion;

    @FXML
    TextField TFNombrePromocion;

    @FXML
    ComboBox CBTipo,CBPorcentaje;

    @FXML
    TextArea TADescripcion;
    Comprobacion comprobacion =new Comprobacion();
    private static Promocion promo;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    public static class LTipo {

        String tipo;

        public LTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            tipo = tipo;
        }

        @Override
        public String toString() {
            return tipo;
        }
    }
    private final ObservableList<LTipo> listaTipos
            = FXCollections.observableArrayList(
                    new LTipo("Inscripcion"),
                    new LTipo("Mensualidad"));

     public static class LPorcentaje {

        String porcentaje;

        public LPorcentaje(String porcentaje) {
            this.porcentaje = porcentaje;
        }

        public String getPorcentaje() {
            return porcentaje;
        }

        public void setPorcentaje(Float porcentaje) {
            porcentaje = porcentaje;
        }
        @Override
        public String toString() {
            return porcentaje;
        }
    }
    private final ObservableList<LPorcentaje> listaPorcentajes
            = FXCollections.observableArrayList(
                    new LPorcentaje("5"),
                    new LPorcentaje("10"),
                    new LPorcentaje("15"),
                    new LPorcentaje("20"),
                    new LPorcentaje("25"),
                    new LPorcentaje("30"),
                    new LPorcentaje("35"),
                    new LPorcentaje("40"),
                    new LPorcentaje("45"),
                    new LPorcentaje("50"),
                    new LPorcentaje("55"),
                    new LPorcentaje("60"),
                    new LPorcentaje("65"),
                    new LPorcentaje("70"),
                    new LPorcentaje("75"),
                    new LPorcentaje("80"),
                    new LPorcentaje("85"),
                    new LPorcentaje("90"),
                    new LPorcentaje("95"),
                    new LPorcentaje("100"));

    static void initRootLayout(Stage primaryStage, Promocion promocion) {
        promo = promocion;
        try {
            ModificarPromocionController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ModificarPromocion.fxml"));
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
    void AccionConsultarClase(ActionEvent event) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    public void AccionConsultarPromocion(ActionEvent event) {
        ConsultarPromocionController.initRootLayout(primaryStage);
    }

    @FXML
    void AccionGuardar(ActionEvent event) {
        if (!TFNombrePromocion.getText().isEmpty() && !TADescripcion.getText().isEmpty() && CBTipo.getValue() != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            PromocionJpaController controllerPromocion = new PromocionJpaController(entityManagerFactory);
            promo.setTipo(CBTipo.getValue().toString());
            
            promo.setDescripcion(TADescripcion.getText());
            promo.setNombre(TFNombrePromocion.getText());
            try {
                controllerPromocion.edit(promo);
                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText(null);
                dialogoAlerta.setContentText("Se ha modificado la promocion en la base de datos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                ConsultarPromocionController.initRootLayout(primaryStage);
            } catch (Exception e) {
                Logger.getLogger(CrearPromocionController.class.getName());
            }
        } else {
            if (comprobacion.dosCamposVaciosPromocion(TFNombrePromocion, TADescripcion, CBTipo, CBPorcentaje) == true) {
                if (!TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() != null && TADescripcion.getText().isEmpty() && CBPorcentaje.getValue() == null) {
                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                    dialogoAlerta.setContentText("No puedes dejar la descripcion y porcentaje  vacios");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    //BGuardar.setDisable(true);
                } else {
                    if (!TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() == null && !TADescripcion.getText().isEmpty() && CBPorcentaje.getValue() == null) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el tipo y el porcentaje vacios");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        //BGuardar.setDisable(true);
                    } else {
                        if (!TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() == null && TADescripcion.getText().isEmpty() && CBPorcentaje != null) {
                            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el tipo y la descripcion vacios");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            if (TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() != null && !TADescripcion.getText().isEmpty() && CBPorcentaje == null) {
                                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar el nombre de la promocion  y el porcentaje vacios");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                                //BGuardar.setDisable(true);
                            } else {
                                if (TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() != null && TADescripcion.getText().isEmpty() && CBPorcentaje != null) {
                                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                    dialogoAlerta.setTitle("Ared Espacio");
                                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                    dialogoAlerta.setContentText("No puedes dejar el nombre de la promocion  y la descripcion vacios");
                                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                                    dialogoAlerta.showAndWait();
                                    //BGuardar.setDisable(true);
                                } else {
                                    if (TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() == null && !TADescripcion.getText().isEmpty() && CBPorcentaje != null) {
                                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                        dialogoAlerta.setTitle("Ared Espacio");
                                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                        dialogoAlerta.setContentText("No puedes dejar el nombre de la promocion  y el tipo vacios");
                                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                                        dialogoAlerta.showAndWait();
                                        //BGuardar.setDisable(true);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() != null && !TADescripcion.getText().isEmpty()) {
                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                    dialogoAlerta.setContentText("No puedes dejar el nombre de la promocion vacio");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    //BGuardar.setDisable(true);
                } else {
                    if (!TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() == null && !TADescripcion.getText().isEmpty()) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el tipo vacio");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        //BGuardar.setDisable(true);
                    } else {
                        if (!TFNombrePromocion.getText().isEmpty() && CBTipo.getValue() != null && TADescripcion.getText().isEmpty()) {
                            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar la descripcion vacia");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            if (!TFNombrePromocion.getText().isEmpty() && !TADescripcion.getText().isEmpty() && CBTipo.getValue() != null&&CBPorcentaje.getValue()==null) {
                                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar el porcentaje vacio");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                            }else{
                                 if (TFNombrePromocion.getText().isEmpty() && TADescripcion.getText().isEmpty() && CBTipo.getValue() == null) {
                                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar los campos vacios");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                            }
                            }
                        }
                    }
                }
            }
        }

    }

    @FXML
    void AccionRegistrarClase(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TFNombrePromocion.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (TFNombrePromocion.getText().length() > 15) {
                    event.consume();
                }
            }
        });

        TADescripcion.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //char car = event.getCharacter().charAt(0);
                if (TADescripcion.getText().length() > 300) {
                    event.consume();
                }
            }
        });
        TFNombrePromocion.setText(promo.getNombre());
        TADescripcion.setText(promo.getDescripcion());
        CBTipo.setItems(listaTipos);
        CBPorcentaje.setItems(listaPorcentajes);
    }

}
