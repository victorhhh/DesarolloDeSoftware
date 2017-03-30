/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import JPAControllers.ClaseJpaController;
import java.util.List;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class RegistrarClaseController implements Initializable {

    @FXML
    MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    MenuItem MIRegistar;
    @FXML
            MenuItem MIConsultar;
    @FXML
    Button BGuardar, BAgregar;
    @FXML
    ListView LVClases;

    @FXML
    TextField TFNombre, TFHoraInicio, TFMinutosInicio, TFHoraFin, TFMinutosFin;

    @FXML
    ComboBox CBDia;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    @FXML
    private MenuItem MIInscribirAlumno;
    @FXML
    private MenuItem MIConsultarAlumno;
    @FXML
    private MenuItem MIRegistrarMaestro;
    @FXML
    private MenuItem MIConsultarMaestros;
    @FXML
    private MenuItem MIModificar;

    @FXML
    private void MIInscribirAlumnoAction(ActionEvent event) {
        InscribirAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarAlumnoAction(ActionEvent event) {
        ConsultarAlumno1Controller.initRootLayout(primaryStage);
    }

    @FXML
    private void MIRegistrarMaestroAction(ActionEvent event) {
        RegistrarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarMaestrosAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

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

    @FXML
    public void AccionAgregar(ActionEvent evento) {
          LVClases.getItems().clear();
        if (!TFNombre.getText().isEmpty() && CBDia.getValue()!=null&& (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty() && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
            if (disponibilidadHorario() == false) {
                Alert dialogoAlerta = new Alert(AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Espacio ocupado");
                dialogoAlerta.setContentText("No puedes registrar una clase este dia a esta hora");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                BGuardar.setDisable(true);
            } else {
                if (horarioMayor() == true) {
                    Alert dialogoAlerta = new Alert(AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Limite de horas de clase");
                    dialogoAlerta.setContentText("Una clase no puede ser dada mas de tres horas");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    dialogoAlerta.showAndWait();
                    BGuardar.setDisable(true);
                } else {
                    if (horarioMenor() == true) {
                        Alert dialogoAlerta = new Alert(AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Hora insuficiente");
                        dialogoAlerta.setContentText("Una clase no puede durar menos de una hora");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                       
                        dialogoAlerta.showAndWait();
                        BGuardar.setDisable(true);
                    } else {
                        Alert dialogoAlerta = new Alert(AlertType.INFORMATION);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText(null);
                        dialogoAlerta.setContentText("se agregara para guardar en la base de datos la clase ingresada");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        BGuardar.setDisable(false);
                    }
                }
            }

        } else {
            if (TFNombre.getText().isEmpty() && CBDia.getValue() == null&& (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty() && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                Alert dialogoAlerta = new Alert(AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                dialogoAlerta.setContentText("No puedes dejar vacios los campos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                BGuardar.setDisable(true);
            } else {
                if (dosCamposVacios() == true) {
                    if (!TFNombre.getText().isEmpty() && CBDia.getValue()==null
                            && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                            && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                        Alert dialogoAlerta = new Alert(AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el dia y la hora vacios");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        BGuardar.setDisable(true);
                    } else {
                        if (TFNombre.getText().isEmpty() && CBDia.getValue()!=null
                                && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                                && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                            Alert dialogoAlerta = new Alert(AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el nombre de la clase y la hora vacios");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            BGuardar.setDisable(true);
                        } else {
                            if (TFNombre.getText().isEmpty() && CBDia.getValue()==null
                                    && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty()
                                    && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
                                Alert dialogoAlerta = new Alert(AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar el nombre y el dia vacios");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                                BGuardar.setDisable(true);
                            }
                        }
                    }
                } else {
                    if (TFNombre.getText().isEmpty() && CBDia.getValue()!=null && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty() && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
                        Alert dialogoAlerta = new Alert(AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el nombre de la clase vacio");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        BGuardar.setDisable(true);
                    } else {
                        if (!TFNombre.getText().isEmpty() && CBDia.getValue()==null && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty() && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
                            Alert dialogoAlerta = new Alert(AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el dia vacio");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            dialogoAlerta.showAndWait();
                            BGuardar.setDisable(true);
                        } else {
                            if (!TFNombre.getText().isEmpty() && CBDia.getValue()!=null && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty() && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                                Alert dialogoAlerta = new Alert(AlertType.WARNING);
                                dialogoAlerta.setTitle("Ared Espacio");
                                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                                dialogoAlerta.setContentText("No puedes dejar la hora vacia");
                                dialogoAlerta.initStyle(StageStyle.UTILITY);
                                dialogoAlerta.showAndWait();
                                BGuardar.setDisable(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void AccionGuardar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        String h = TFHoraInicio.getText().concat(":").concat(TFMinutosInicio.getText()).concat("-").concat(TFHoraFin.getText()).concat(":").concat(TFMinutosFin.getText());
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

        } catch (Exception e) {
            Logger.getLogger(RegistrarClaseController.class.getName());
        }
    }

    @FXML
    public void AccionConsultarClases(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    @FXML
    public void AccionModificarClase(ActionEvent evento) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "sin seleccion");
                dialogoAlerta.setContentText("Debes consultar una clase antes de modificarla");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
    }

    public boolean dosCamposVacios() {
        if (!TFNombre.getText().isEmpty() && CBDia.getValue()==null
                && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())
                || TFNombre.getText().isEmpty() && CBDia.getValue()!=null
                && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())
                || TFNombre.getText().isEmpty() && CBDia.getValue()==null
                && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty()
                && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
            return true;
        }
        return false;
    }

    public boolean horarioMayor() {
        int hI = Integer.parseInt(TFHoraInicio.getText());
        int mI = Integer.parseInt(TFMinutosInicio.getText());
        int hF = Integer.parseInt(TFHoraFin.getText());
        int mF = Integer.parseInt(TFMinutosFin.getText());
        if ((hF > (hI + 3)) || (hF == (hI + 3) && mF > mI)) {
            return true;
        }
        return false;
    }

    public boolean horarioMenor() {
        int hI = Integer.parseInt(TFHoraInicio.getText());
        int mI = Integer.parseInt(TFMinutosInicio.getText());
        int hF = Integer.parseInt(TFHoraFin.getText());
        int mF = Integer.parseInt(TFMinutosFin.getText());
        if ((hF < hI) || (hF == hI) || (hF == (hI + 1) && mF < mI)) {
            return true;
        }
        return false;
    }

    public boolean disponibilidadHorario() {
        String h = TFHoraInicio.getText().concat(":").concat(TFMinutosInicio.getText()).concat("-").concat(TFHoraFin.getText()).concat(":").concat(TFMinutosFin.getText());
        Clase clase = new Clase();
        List<Clase> listaClases = clase.obtenerListaDeClases();
        for (int i = 0; i < listaClases.size(); i++) {
            LVClases.getItems().add(listaClases.get(i).getNombre() + "  " + listaClases.get(i).getDia() + "  " + listaClases.get(i).getHora());
        }
        for (int i = 0; i < listaClases.size(); i++) {
            if (listaClases.get(i).getDia().equals(CBDia.getValue().toString())) {
                if (listaClases.get(i).getHora().equals(h)) {
                    return false;
                }
            }
        }
        return true;
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
        TFHoraInicio.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                } else {
                    event.consume();
                }
                if (TFHoraInicio.getText().length() > 1) {
                    event.consume();
                }
            }
        });
        TFMinutosInicio.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                } else {
                    event.consume();
                }
                if (TFMinutosInicio.getText().length() > 1) {
                    event.consume();
                }
            }
        });
        TFHoraFin.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                } else {
                    event.consume();
                }
                if (TFHoraFin.getText().length() > 1) {
                    event.consume();
                }
            }
        });
        TFMinutosFin.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                } else {
                    event.consume();
                }
                if (TFMinutosFin.getText().length() > 1) {
                    event.consume();
                }
            }
        });

    }

}
