/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import BaseDeDatos.Pagoegreso;
import JPAControllers.MaestroJpaController;
import JPAControllers.PagoegresoJpaController;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class ReportesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    ComboBox CBCriterio, CBMes;
    @FXML
    Button BBuscar, BDatos;
    @FXML
    TableView TVRegistros;
    @FXML
    TextField TFAnio;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    TableColumn<Pagoegreso, String> cFecha = new TableColumn<>("Fecha");
    TableColumn<Pagoegreso, String> cMonto = new TableColumn<>("Monto");
    TableColumn<Pagoegreso, String> cNombre = new TableColumn<>("maestro");

    static void initRootLayout(Stage primaryStage) {
        try {
            ReportesController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("Reportes.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BuscarAction(ActionEvent event) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        PagoegresoJpaController m = new PagoegresoJpaController(entityManagerFactory);
        MaestroJpaController ma = new MaestroJpaController(entityManagerFactory);
        Maestro mairo;
        List<Maestro> ListMa;
        List<Pagoegreso> ListPE;
        int p;
        ListMa = ma.findMaestroEntities();
        ListPE = m.findPagoegresoEntities();
        for (int i = 0; i < ListPE.size(); i++) {
           
            mairo = ListPE.get(i).getIDMaestroPE();
            
            TVRegistros.getItems().add(ListPE.get(i));

        }

    }

    @FXML
    public void DatosAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        cFecha.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));
        cMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        cNombre.setCellValueFactory(new PropertyValueFactory<>("maestro"));

        TVRegistros.getColumns().addAll(cNombre, cFecha, cMonto);

    }

}
