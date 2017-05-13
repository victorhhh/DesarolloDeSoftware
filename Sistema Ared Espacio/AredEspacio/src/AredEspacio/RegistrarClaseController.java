/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import JPAControllers.ClaseJpaController;
import java.io.IOException;
import java.util.logging.Logger;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.jfoenix.controls.JFXTimePicker;
import java.util.List;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class RegistrarClaseController implements Initializable {

    @FXML
    JFXTimePicker TPHoraInicio;
    @FXML
    JFXTimePicker TPHoraFin;
    @FXML
    MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    MenuItem MIRegistar, MIConsultar;
    @FXML
    Button BGuardar, BAgregar;
    @FXML
    ListView LVClases;

    @FXML
    TextField TFNombre;

    @FXML
    ComboBox CBDia;

    Comprobacion comprobacion = new Comprobacion();

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    public static class LDia {

        String dia;

        public LDia(String dia) {
            this.dia = dia;
        }

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            dia = dia;
        }

        @Override
        public String toString() {
            return dia;
        }
    }
    private final ObservableList<LDia> listaDias
            = FXCollections.observableArrayList(
                    new LDia("Lunes"),
                    new LDia("Martes"),
                    new LDia("Miercoles"),
                    new LDia("Jueves"),
                    new LDia("Viernes"),
                    new LDia("Sabado"));

    static void initRootLayout(Stage primaryStage) {
        try {
            RegistrarClaseController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarClase.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AccionAgregar(ActionEvent evento) {
        if (!TFNombre.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
            if (comprobacion.horarioPrudente(TPHoraInicio, TPHoraFin) == false) {
                String horaIArray[] = TPHoraInicio.getValue().toString().split(":");
                String horaFArray[] = TPHoraFin.getValue().toString().split(":");
                int hI = Integer.parseInt(horaIArray[0]);
                int mI = Integer.parseInt(horaIArray[1]);
                int hF = Integer.parseInt(horaFArray[0]);
                int mF = Integer.parseInt(horaFArray[1]);
                if (hI < 8) {
                    Alert dialogoAlerta = new Alert(AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Inicio de Clases antes de las 8 am ");
                    dialogoAlerta.setContentText("No puedes registrar una clase antes de que abra la escuela");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                } else {
                    if (hF > 21||(hF==21 &&mF>1)) {
                        Alert dialogoAlerta = new Alert(AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Fin de Clases hasta de las 9 pm ");
                        dialogoAlerta.setContentText("No puedes registrar una clase que acabe despues de que cierre la escuela");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                    }
                }
            } else {
                if (comprobacion.disponibilidadHorario(TPHoraInicio, TPHoraFin, LVClases, CBDia) == false) {
                    Alert dialogoAlerta = new Alert(AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Espacio ocupado");
                    dialogoAlerta.setContentText("No puedes registrar una clase este dia a esta hora");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    //BGuardar.setDisable(true);
                } else {
                    if (comprobacion.horarioMayor(TPHoraInicio, TPHoraFin) == true) {
                        Alert dialogoAlerta = new Alert(AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Limite de horas de clase");
                        dialogoAlerta.setContentText("Una clase no puede ser dada mas de tres horas");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        //BGuardar.setDisable(true);
                    } else {
                        if (comprobacion.horarioMenor(TPHoraInicio, TPHoraFin) == true) {
                            Alert dialogoAlerta = new Alert(AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Hora insuficiente");
                            dialogoAlerta.setContentText("Una clase no puede durar menos de una hora");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);

                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            LVClases.getItems().clear();
                            Alert dialogoAlerta = new Alert(AlertType.INFORMATION);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText(null);
                            dialogoAlerta.setContentText("se agregara para guardar en la base de datos la clase ingresada");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            //TFNombre.setEditable();
                            BGuardar.setDisable(false);
                        }
                    }
                }
            }

        } else {
            if (TFNombre.getText().isEmpty() && CBDia.getValue() == null && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                Alert dialogoAlerta = new Alert(AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                dialogoAlerta.setContentText("No puedes dejar vacios los campos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                //BGuardar.setDisable(true);
            } else {
                if (comprobacion.dosCamposVacios(TFNombre, CBDia, TPHoraInicio, TPHoraFin) == true) {
                    if (!TFNombre.getText().isEmpty() && CBDia.getValue() == null
                            && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                        Alert dialogoAlerta = new Alert(AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el dia y la hora vacios");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        //BGuardar.setDisable(true);
                    } else {
                        if (TFNombre.getText().isEmpty() && CBDia.getValue() != null
                                && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                            Alert dialogoAlerta = new Alert(AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el nombre de la clase y la hora vacios");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            if (TFNombre.getText().isEmpty() && CBDia.getValue() == null
                                    && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
                                Alert dialogoAlerta = new Alert(AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar el nombre y el dia vacios");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                                //BGuardar.setDisable(true);
                            }
                        }
                    }
                } else {
                    if (TFNombre.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
                        Alert dialogoAlerta = new Alert(AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el nombre de la clase vacio");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        //BGuardar.setDisable(true);
                    } else {
                        if (!TFNombre.getText().isEmpty() && CBDia.getValue() == null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
                            Alert dialogoAlerta = new Alert(AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el dia vacio");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            if (!TFNombre.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                                Alert dialogoAlerta = new Alert(AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar la hora vacia");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                                //BGuardar.setDisable(true);
                            } else {
                                if (!TFNombre.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() != null)) {
                                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                    dialogoAlerta.setTitle("Ared Espacio");
                                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                    dialogoAlerta.setContentText("No puedes dejar la hora de inicio vacia");
                                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                                    dialogoAlerta.showAndWait();
                                    //BGuardar.setDisable(true);
                                } else {
                                    if (!TFNombre.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() == null)) {
                                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                        dialogoAlerta.setTitle("Ared Espacio");
                                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                        dialogoAlerta.setContentText("No puedes dejar la hora de fin vacia");
                                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                                        dialogoAlerta.showAndWait();
                                        //BGuardar.setDisable(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void AccionGuardar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        String h = TPHoraInicio.getValue().toString().concat("-").concat(TPHoraFin.getValue().toString());
        Clase clase = new Clase();
        clase.setDia(CBDia.getValue().toString());
        clase.setEstado(true);
        clase.setHora(h);
        clase.setNombre(TFNombre.getText());
        try {
            controllerClases.create(clase);
            Alert dialogoAlerta = new Alert(AlertType.INFORMATION);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText(null);
            dialogoAlerta.setContentText("Se ha registrado la clase en la base de datos");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
            actualizarTabla();
            BGuardar.setDisable(true);
            
            
        } catch (Exception e) {
            Logger.getLogger(RegistrarClaseController.class.getName());
        }
    }

    public void AccionConsultarClases(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    public void AccionModificarClase(ActionEvent evento) {
        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
        dialogoAlerta.setTitle("Ared Espacio");
        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "sin seleccion");
        dialogoAlerta.setContentText("Debes consultar una clase antes de modificarla");
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.showAndWait();
    }

   
    public void actualizarTabla() {
        LVClases.getItems().clear();
        Clase clase = new Clase();
        List<Clase> listaClases = clase.obtenerListaDeClases();
        for (int i = 0; i < listaClases.size(); i++) {
            LVClases.getItems().add(listaClases.get(i).getNombre() + "  " + listaClases.get(i).getDia() + "  " + listaClases.get(i).getHora());
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TPHoraInicio.setEditable(false);
        TPHoraFin.setEditable(false);
        Clase clase = new Clase();
        List<Clase> listaClases = clase.obtenerListaDeClases();
        BGuardar.setDisable(true);
        for (int i = 0; i < listaClases.size(); i++) {
            LVClases.getItems().add(listaClases.get(i).getNombre() + "  " + listaClases.get(i).getDia() + "  " + listaClases.get(i).getHora());
        }
        CBDia.setItems(listaDias);
        TFNombre.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFNombre.getText().length() > 15) {
                    event.consume();
                }
            }
        });

    }

}
