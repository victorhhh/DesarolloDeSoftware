/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import BaseDeDatos.Grupo;
import BaseDeDatos.Maestro;
import JPAControllers.ClaseJpaController;
import JPAControllers.MaestroJpaController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    MenuItem MIRegistrar, MIConsultar;
    @FXML
    MenuButton BAlumnos, BMaestros, BClases, bPromociones, BReportes;
    @FXML
    Button BBaja, BModificar;
    @FXML
    Label LNombreProfesor, LNombreClase, LHora, LDia, LNumeroAlumnos, LEstado;
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

    public void AccionBaja(ActionEvent evento) {
        //DarDeBajaClaseController.initRootLayout(primaryStage, c1);

        Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoAlerta.setTitle("Ared Espacio");
        dialogoAlerta.setHeaderText(null);
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.setContentText("Realmente ¿quieres dar de baja la clase de " + " " + c1.getNombre() + "?");
        Optional<ButtonType> result = dialogoAlerta.showAndWait();
        if (result.get() == ButtonType.OK) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);
            if (c1.getEstado() == false) {
                Alert d = new Alert(Alert.AlertType.WARNING);
                d.setTitle("Ared Espacio");
                d.setHeaderText("¡Aviso!" + " " + "Clase inactiva");
                d.setContentText("Esta clase ya esta dada de baja");
                d.initStyle(StageStyle.UTILITY);
                d.showAndWait();

            } else {
                c1.setEstado(false);
                try {
                    controllerClases.edit(c1);
                    Alert da = new Alert(Alert.AlertType.INFORMATION);
                    da.setTitle("Ared Espacio");
                    da.setHeaderText("Clase dada de baja");
                    da.setContentText("La clase" + " " + c1.getNombre() + " " + " fue dada de baja ");
                    da.initStyle(StageStyle.UTILITY);
                    da.showAndWait();
                } catch (Exception e) {
                    Logger.getLogger(RegistrarClaseController.class.getName());
                }
                RegistrarClaseController.initRootLayout(primaryStage);
            }
        }
    }

    public void AccionModificar(ActionEvent evento) {

        ModificarClaseController.initRootLayout(primaryStage, c1);

    }

    public void AccionConsultarClase(ActionEvent evento) {
        ConsultarClaseController.initRootLayout(primaryStage);
    }

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
            LNombreProfesor.setText(" ");

            LNombreClase.setText(c1.getNombre());

            LHora.setText(c1.getHora());

            LDia.setText(c1.getDia());
            Grupo grupo = new Grupo();

            List<Grupo> listaDeGrupos = grupo.buscarGruposPorIDClase();
            int nA = 0;
            for (int i = 0; i < listaDeGrupos.size(); i++) {
                if (listaDeGrupos.get(i).getIDClaseG().getIDClase().equals(c1.getIDClase())) {
                    nA++;
                }
            }
            LNumeroAlumnos.setText(" " + nA);

            if (c1.getEstado() == true) {
                LEstado.setText("Activa");

            } else {
                LEstado.setText("Inactiva");

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
            LNombreProfesor.setText(maestro.getNombre() + " " + maestro.getPrimerApellido() + " " + maestro.getSegundoApellido());

            LNombreClase.setText(c1.getNombre());

            LHora.setText(c1.getHora());

            LDia.setText(c1.getDia());
            Grupo grupo = new Grupo();

            List<Grupo> listaDeGrupos = grupo.buscarGruposPorIDClase();
            int nA = 0;
            for (int i = 0; i < listaDeGrupos.size(); i++) {
                if (listaDeGrupos.get(i).getIDClaseG().getIDClase().equals(c1.getIDClase())) {
                    nA++;
                }
            }
            LNumeroAlumnos.setText(" " + nA);
            if (c1.getEstado() == true) {
                LEstado.setText("Activa");

            } else {
                LEstado.setText("Inactiva");

            }
        }
    }

}
