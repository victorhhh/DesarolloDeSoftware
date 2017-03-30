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
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
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

    @FXML
    MenuButton BAlumnos;
    @FXML
    MenuButton BMaestros;
    MenuButton BClases;

    @FXML
    MenuButton bPromociones, BReportes;
    @FXML
    Button BGuardar, BRegresar;
    @FXML
    TextField TFNombreProfesor, TFNombreClase, TFHoraInicio, TFMinutosInicio, TFHoraFin, TFMinutosFin, TFEstado, TFDia;

    private String dia;

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
    private ImageView ivLogo;
    @FXML
    private MenuItem MIRegistrarAlumno;
    @FXML
    private MenuItem IMConsultarAlumno;
    @FXML
    private MenuItem MIRegistrarMaestro;
    @FXML
    private MenuItem MIRegistrar;
    @FXML
    private MenuItem MIConsultar;
    @FXML
    private MenuButton BPromociones;
    @FXML
    private MenuItem MIConsultarMaestro;

    @FXML
    public void AccionGuardar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);

        if (!TFNombreClase.getText().isEmpty() && !TFDia.getText().isEmpty() && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty() && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
            if (disponibilidadHorario() == false) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Espacio ocupado");
                dialogoAlerta.setContentText("No puedes registrar una clase este dia a esta hora");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                BGuardar.setDisable(true);
            } else {
                if (horarioMayor() == true) {
                    Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                    dialogoAlerta.setTitle("Ared Espacio");
                    dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Limite de horas de clase");
                    dialogoAlerta.setContentText("Una clase no puede ser dada mas de tres horas");
                    dialogoAlerta.initStyle(StageStyle.UTILITY);
                    
                    dialogoAlerta.showAndWait();
                    BGuardar.setDisable(true);
                } else {
                    if (horarioMenor() == true) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Hora insuficiente");
                        dialogoAlerta.setContentText("Una clase no puede durar menos de una hora");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        
                        dialogoAlerta.showAndWait();
                        BGuardar.setDisable(true);
                    } else {
                        String h = TFHoraInicio.getText().concat(":").concat(TFMinutosInicio.getText()).concat("-").concat(TFHoraFin.getText()).concat(":").concat(TFMinutosFin.getText());
                        c1.setDia(TFDia.getText());
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
                        RegistrarClaseController.initRootLayout(primaryStage);
                    }
                }
            }

        } else {
            if (TFNombreClase.getText().isEmpty() && TFDia.getText().isEmpty() && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty() && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                dialogoAlerta.setTitle("Ared Espacio");
                dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                dialogoAlerta.setContentText("No puedes dejar vacios los campos");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.showAndWait();
                BGuardar.setDisable(true);
            } else {
                if (dosCamposVacios() == true) {
                    if (!TFNombreClase.getText().isEmpty() && TFDia.getText().isEmpty()
                            && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                            && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el dia y la hora vacios");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        
                        dialogoAlerta.showAndWait();
                        BGuardar.setDisable(true);
                    } else {
                        if (TFNombreClase.getText().isEmpty() && !TFDia.getText().isEmpty()
                                && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                                && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el nombre de la clase y la hora vacios");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                           
                            dialogoAlerta.showAndWait();
                            BGuardar.setDisable(true);
                        } else {
                            if (TFNombreClase.getText().isEmpty() && TFDia.getText().isEmpty()
                                    && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty()
                                    && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
                                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
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
                    if (TFNombreClase.getText().isEmpty() && !TFDia.getText().isEmpty() && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty() && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                        dialogoAlerta.setContentText("No puedes dejar el nombre de la clase vacio");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        
                        dialogoAlerta.showAndWait();
                        BGuardar.setDisable(true);
                    } else {
                        if (!TFNombreClase.getText().isEmpty() && TFDia.getText().isEmpty() && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty() && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
                            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                            dialogoAlerta.setTitle("Ared Espacio");
                            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "Campos vacios");
                            dialogoAlerta.setContentText("No puedes dejar el dia vacio");
                            dialogoAlerta.initStyle(StageStyle.UTILITY);
                            
                            dialogoAlerta.showAndWait();
                            BGuardar.setDisable(true);
                        } else {
                            if (!TFNombreClase.getText().isEmpty() && !TFDia.getText().isEmpty() && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty() && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())) {
                                Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
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
            if (listaClases.get(i).getDia().equals(TFDia.getText())) {
                if (listaClases.get(i).getHora().equals(h)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    

    public boolean dosCamposVacios() {
        if (!TFNombreClase.getText().isEmpty() && TFDia.getText().isEmpty()
                && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())
                || TFNombreClase.getText().isEmpty() && !TFDia.getText().isEmpty()
                && (TFHoraInicio.getText().isEmpty() && TFMinutosInicio.getText().isEmpty()
                && TFHoraFin.getText().isEmpty() && TFMinutosFin.getText().isEmpty())
                || TFNombreClase.getText().isEmpty() && TFDia.getText().isEmpty()
                && (!TFHoraInicio.getText().isEmpty() && !TFMinutosInicio.getText().isEmpty()
                && !TFHoraFin.getText().isEmpty() && !TFMinutosFin.getText().isEmpty())) {
            return true;
        }
        return false;
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController controllerMaestros = new MaestroJpaController(entityManagerFactory);

        if (c1.getIDMaestroC() == null) {
            TFNombreProfesor.setText(" ");
            TFNombreProfesor.setEditable(false);
            TFNombreClase.setText(c1.getNombre());
            /*System.out.println(c1.getHora().substring(0, 2));
            System.out.println(c1.getHora().substring(3, 5));
            System.out.println(c1.getHora().substring(6, 8));
            System.out.println(c1.getHora().substring(9, 11));*/
            TFHoraInicio.setText(c1.getHora().substring(0, 2));
            TFMinutosInicio.setText(c1.getHora().substring(3, 5));
            TFHoraFin.setText(c1.getHora().substring(6, 8));
            TFMinutosFin.setText(c1.getHora().substring(9, 11));
            TFDia.setText(c1.getDia());
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
            TFNombreProfesor.setText(maestro.getNombre());
            TFNombreProfesor.setEditable(false);
            TFNombreClase.setText(c1.getNombre());
            TFHoraInicio.setText(c1.getHora().substring(0, 2));
            TFMinutosInicio.setText(c1.getHora().substring(3, 5));
            TFHoraFin.setText(c1.getHora().substring(6, 8));
            TFMinutosFin.setText(c1.getHora().substring(9, 11));
            TFDia.setText(c1.getDia());
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
        TFDia.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFDia.getText().length() >11) {
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

    @FXML
    private void MIRegistrarAlumnoAction(ActionEvent event) {
        InscribirAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void IMConsultarAlumno(ActionEvent event) {
        ConsultarAlumno1Controller.initRootLayout(primaryStage);
    }

    @FXML
    private void MIRegistrarMaestroAction(ActionEvent event) {
        RegistrarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    private void MIConsultarMaestroAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

}
