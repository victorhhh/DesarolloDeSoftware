/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import JPAControllers.MaestroJpaController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
public class ConsultarClase2Controller implements Initializable {
    @FXML
    MenuItem MIRegistrar,MIConsultar;
    MenuButton BAlumnos;
    @FXML
    MenuButton BMaestros, BClases, bPromociones, BReportes;
    @FXML
    Button BBaja, BModificar;
    @FXML
    Label LNombreProfesor, LNombreClase, LHora, LDia,LNumeroAlumnos,LEstado;
    private static Clase c1;
    public int IDC;
    private int IDM;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    
    static void initRootLayout(Stage primaryStage, Clase clase1) {
        c1 = clase1;
        try {
            ConsultarClase2Controller.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("ConsultarClase2.fxml"));
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
    private MenuItem MIInscribirAlumno;
    @FXML
    private MenuItem BIConsultarAlumno;
    @FXML
    private MenuItem BIRegistrarMaestro;
    @FXML
    private MenuItem BIConsultarMaestro;
    @FXML
    private MenuButton BPromociones;
    
    @FXML
     public void AccionBaja(ActionEvent evento) {      
         DarDeBajaClaseController.initRootLayout(primaryStage, c1);
    }
    
    @FXML
     public void AccionModificar(ActionEvent evento) {
        
        ModificarClaseController.initRootLayout(primaryStage, c1);

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
        
        if (c1.getIDMaestroC()==null) {
            LNombreProfesor.setText(" ");
            
            LNombreClase.setText(c1.getNombre());
           
            LHora.setText(c1.getHora());
           
            LDia.setText(c1.getDia());
            
            LNumeroAlumnos.setText("0");
           
            if(c1.getEstado()==true){
                LEstado.setText("Activa");
                
            }else{
                LEstado.setText("Inactiva");
                
            }
            Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText(null);
            dialogoAlerta.setContentText("Esta clase aun no tiene un maestro asignado");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        }else{
            IDM = c1.getIDMaestroC().getIDMaestro();
            Maestro maestro = controllerMaestros.findMaestro(IDM);
            LNombreProfesor.setText(maestro.getNombre()+" "+maestro.getPrimerApellido()+" "+maestro.getSegundoApellido());
            
            LNombreClase.setText(c1.getNombre());
            
            LHora.setText(c1.getHora());
            
            LDia.setText(c1.getDia());
            LNumeroAlumnos.setText(" ");
            
            if(c1.getEstado()==true){
                LEstado.setText("Activa");
               
            }else{
                LEstado.setText("Inactiva");
                
            }
        }
    }    

    @FXML
    private void MIInscribirAlumnoAction(ActionEvent event) {
        InscribirAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void BIConsultarAlumnoAction(ActionEvent event) {
        ConsultarAlumno1Controller.initRootLayout(primaryStage);
    }

    @FXML
    private void BIRegistrarMaestroAction(ActionEvent event) {
        RegistrarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    private void BIConsultarMaestroAction(ActionEvent event) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }
    
}
