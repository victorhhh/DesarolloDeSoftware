/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aredespacio;

import basededatos.Alumno;
import basededatos.Clase;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontrollers.AlumnoJpaController;
import jpacontrollers.ClaseJpaController;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class RegistrarClaseController implements Initializable {

    @FXML
    MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    MenuItem MIRegistar, MIConsultar;
    @FXML
    Button BGuardar, BAgregar;
    @FXML
    TextArea TAListaClases;
    @FXML
    TextField TFNombre, TFHora;
    @FXML
    ComboBox CBDia;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    private int IDM,IDA;
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
                    new LDia(" "),
                    new LDia("Lunes"),
                    new LDia("Martes"),
                    new LDia("Miercoles"),
                    new LDia("Jueves"),
                    new LDia("Viernes"));

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

    public void AgregarClases(String nombreClase, String hora, String dia) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
       
        TAListaClases.setText(TFNombre.getText() + "   " + CBDia.getValue().toString() + "   " + TFHora.getText());

    }

    public void AccionAgregar(ActionEvent evento) {
        AgregarClases(TFNombre.getText(), TFHora.getText(), CBDia.getValue().toString());
    }

    public void AccionGuardar(ActionEvent evento) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        int i = controllerClases.getClaseCount();
        Clase clase = new Clase(i + 1);
        clase.setDia(CBDia.getValue().toString());
        clase.setEstado(true);
        clase.setHora(TFHora.getText());
        clase.setNombre(TFNombre.getText());
        try {
            controllerClases.create(clase);
            System.out.println("Done");
            TFNombre.setText(" ");
            TFHora.setText(" ");
            TAListaClases.setText(" ");
        } catch (Exception e) {
            Logger.getLogger(RegistrarClaseController.class.getName());
        }
    }

    public void AccionConsultarClases(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

    public void AccionModificarClase(ActionEvent evento) {
        ModificarClaseController.initRootLayout(primaryStage);
    }

    public void tamañoLista() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
        controllerClases.getClaseCount();
    }
    public void buscarIDMaestro(){
        
    }
    public void buscarIDAlumno(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        AlumnoJpaController controllerAlumnos= new AlumnoJpaController(entityManagerFactory);
        Alumno alumno=(Alumno) controllerAlumnos.findAlumnoEntities();
    }

    /**
     * Initializes the controller class.
     *
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TAListaClases.setText("Salsa" + "   " + "Viernes" + "   " + "15:00-17:00" + "\n"
                + "Bachata" + "   " + "Lunes" + "   " + "12:00-14:00" + "\n"
                + "Disco" + "   " + "Jueves" + "   " + "19:00-21:00");
        CBDia.setItems(listaDias);
    }

}
