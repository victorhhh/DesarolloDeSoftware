/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import JPAControllers.ClaseJpaController;
import JPAControllers.MaestroJpaController;
import com.jfoenix.controls.JFXTimePicker;
import java.time.LocalTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class ModificarClaseController implements Initializable {

    ListView LVClases;
    @FXML
    TextField TFNombreProfesor;
    @FXML
    ComboBox CBDia;
    MenuButton BAlumnos;
    @FXML
    MenuButton BMaestros, BClases, bPromociones, BReportes;
    @FXML
    Button BGuardar, BRegresar;
    @FXML
    TextField TFNombreClase, TFEstado;
    @FXML
    JFXTimePicker TPHoraInicio;
    @FXML
    JFXTimePicker TPHoraFin;
    Comprobacion comprobacion = new Comprobacion();
    @FXML
    private ImageView ivLogo;
    @FXML
    private MenuItem MIRegistrar;
    @FXML
    private MenuItem MIConsultar;
    @FXML
    private MenuButton BPromociones;
    @FXML
    private TextField TFCosto;

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
    private int IDM;
    private static Clase c1 = new Clase();
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage, Clase clase1) {
        c1 = clase1;
        try {
            ModificarClaseController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ModificarClase.fxml"));
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

    @FXML
    public void AccionGuardar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);

        if (!TFNombreClase.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
            if (comprobacion.horarioPrudente(TPHoraInicio, TPHoraFin) == false) {
                String horaIArray[] = TPHoraInicio.getValue().toString().split(":");
                String horaFArray[] = TPHoraFin.getValue().toString().split(":");
                int hI = Integer.parseInt(horaIArray[0]);
                int mI = Integer.parseInt(horaIArray[1]);
                int hF = Integer.parseInt(horaFArray[0]);
                int mF = Integer.parseInt(horaFArray[1]);
                if (hI < 8) {
                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Inicio de Clases antes de las 8 am ");
                    dialogoAlerta.setContentText("No puedes registrar una clase antes de que abra la escuela");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                } else {
                    if (hF > 21 || (hF == 21 && mF > 1)) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Fin de Clases hasta de las 9 pm ");
                        dialogoAlerta.setContentText("No puedes registrar una clase que acabe despues de que cierre la escuela");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                    }
                }
            } else {
                if (disponibilidadHorario() == false) {
                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Espacio ocupado");
                    dialogoAlerta.setContentText("No puedes registrar una clase este dia a esta hora");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    // BGuardar.setDisable(true);
                } else {
                    if (comprobacion.horarioMayor(TPHoraInicio, TPHoraFin) == true) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Limite de horas de clase");
                        dialogoAlerta.setContentText("Una clase no puede ser dada mas de tres horas");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);

                        dialogoAlerta.showAndWait();
                        // BGuardar.setDisable(true);
                    } else {
                        if (comprobacion.horarioMenor(TPHoraInicio, TPHoraFin) == true) {
                            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Hora insuficiente");
                            dialogoAlerta.setContentText("Una clase no puede durar menos de una hora");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);

                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            BGuardar.setDisable(false);
                            String h = TPHoraInicio.getValue().toString().concat("-").concat(TPHoraFin.getValue().toString());
                            c1.setDia(CBDia.getValue().toString());
                            c1.setEstado(true);
                            c1.setHora(h);
                            c1.setNombre(TFNombreClase.getText());
                            try {
                                controllerClases.edit(c1);
                                Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText(null);
                                dialogoAlerta.setContentText("Se ha modificado la clase en la base de datos");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                            } catch (Exception e) {
                                Logger.getLogger(RegistrarClaseController.class.getName());
                            }
                            ConsultarClaseController.initRootLayout(primaryStage);
                        }
                    }
                }
            }

        } else {
            if (TFNombreClase.getText().isEmpty() && CBDia.getValue() == null && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                dialogoAlerta.setContentText("No puedes dejar vacios los campos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                //BGuardar.setDisable(true);
            } else {
                if (comprobacion.dosCamposVacios(TFNombreClase, CBDia, TPHoraInicio, TPHoraFin) == true) {
                    if (!TFNombreClase.getText().isEmpty() && CBDia.getValue() == null
                            && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el dia y la hora vacios");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        //BGuardar.setDisable(true);
                    } else {
                        if (TFNombreClase.getText().isEmpty() && CBDia.getValue() != null
                                && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el nombre de la clase y la hora vacios");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            if (TFNombreClase.getText().isEmpty() && CBDia.getValue() == null
                                    && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
                                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
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
                    if (TFNombreClase.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el nombre de la clase vacio");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        //BGuardar.setDisable(true);
                    } else {
                        if (!TFNombreClase.getText().isEmpty() && CBDia.getValue() == null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
                            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el dia vacio");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            //BGuardar.setDisable(true);
                        } else {
                            if (!TFNombreClase.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)) {
                                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar la hora vacia");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                                //BGuardar.setDisable(true);
                            } else {
                                if (!TFNombreClase.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() != null)) {
                                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                    dialogoAlerta.setTitle("Ared Espacio");
                                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                    dialogoAlerta.setContentText("No puedes dejar la hora de inicio vacia");
                                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                                    dialogoAlerta.showAndWait();
                                    //BGuardar.setDisable(true);
                                } else {
                                    if (!TFNombreClase.getText().isEmpty() && CBDia.getValue() != null && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() == null)) {
                                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                                        dialogoAlerta.setTitle("Ared Espacio");
                                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                        dialogoAlerta.setContentText("No puedes dejar la hora de fin  vacia");
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
     public boolean disponibilidadHorario() {
        String h = TPHoraInicio.getValue().toString().concat("-").concat(TPHoraFin.getValue().toString());
        Clase clase = new Clase();
        List<Clase> listaClases = clase.obtenerListaDeClases();
        for (int i = 0; i < listaClases.size(); i++) {
            if (listaClases.get(i).getDia().equals(CBDia.getValue().toString())) {
                if (listaClases.get(i).getHora().equals(h)) {
                    return false;
                }
            }
        }
        return true;
    }

    @FXML
    public void AccionRegresar(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    public void AccionConsultarClase(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    public void AccionRegistrarClase(ActionEvent evento) {
        RegistrarClaseController.initRootLayout(primaryStage);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController controllerMaestros = new MaestroJpaController(entityManagerFactory);
        
        if (c1.getIDMaestroC() == null) {
            Maestro maestro = new Maestro();
            TFNombreClase.setText(c1.getNombre());

            String horaArray[] = c1.getHora().split("-");
            TPHoraInicio.setValue(LocalTime.parse(horaArray[0]));
            TPHoraFin.setValue(LocalTime.parse(horaArray[1]));
            CBDia.setItems(listaDias);
           TFNombreProfesor.setText(" ");
           TFNombreProfesor.setEditable(false);
           String cos=String.valueOf(c1.getCosto());
            TFCosto.setText(cos);
            if (c1.getEstado() == true) {
                TFEstado.setText("Activa");
                TFEstado.setEditable(false);
            } else {
                TFEstado.setText("Inactiva");
                TFEstado.setEditable(false);
            }
            Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText(null);
            dialogoAlerta.setContentText("Esta clase aun no tiene un maestro asignado");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        } else {
            IDM = c1.getIDMaestroC().getIDMaestro();
            Maestro maestro = controllerMaestros.findMaestro(IDM);
            TFNombreProfesor.setText(maestro.getNombre()+" "+maestro.getPrimerApellido()+" "+maestro.getSegundoApellido());
           TFNombreProfesor.setEditable(false);
            TFNombreClase.setText(c1.getNombre());
            String horaArray[] = c1.getHora().split("-");
            TPHoraInicio.setValue(LocalTime.parse(horaArray[0]));
            TPHoraFin.setValue(LocalTime.parse(horaArray[1]));
            CBDia.setItems(listaDias);
            String cos=String.valueOf(c1.getCosto());
            TFCosto.setText(cos);
            if (c1.getEstado() == true) {
                TFEstado.setText("Activa");
                TFEstado.setEditable(false);
            } else {
                TFEstado.setText("Inactiva");
                TFEstado.setEditable(false);
            }
            
        }
        TFNombreClase.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFNombreClase.getText().length() > 15) {
                    event.consume();
                }
            }
        });

        TFCosto.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int car = event.getCharacter().charAt(0);
                if (Character.isLetter(car)) {
                    event.consume();
                }
                if (TFCosto.getText().length() > 4) {
                    event.consume();
                }
            }
        });
    }

}
