/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import AredEspacio.AredEspacio;
import BaseDeDatos.Clase;
import BaseDeDatos.Inscripcion;
import BaseDeDatos.Maestro;
import BaseDeDatos.Mensualidad;
import BaseDeDatos.Pagoegreso;
import JPAControllers.InscripcionJpaController;
import JPAControllers.MaestroJpaController;
import JPAControllers.MensualidadJpaController;
import JPAControllers.PagoegresoJpaController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    List<Pagoegreso> lalista = new ArrayList<>();
    List<Mensualidad> lalista2 = new ArrayList<>();

    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    static int opcion;

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

    public void CBCriterioAction(ActionEvent event) {

    }

    public String calcularMes(int mes) {
        String mesFinal;
        switch (mes) {
            case 1:
                mesFinal = "Enero";
                break;
            case 2:
                mesFinal = "Febrero";
                break;
            case 3:
                mesFinal = "Marzo";
                break;
            case 4:
                mesFinal = "Abril";
                break;
            case 5:
                mesFinal = "Mayo";
                break;
            case 6:
                mesFinal = "Junio";
                break;
            case 7:
                mesFinal = "Julio";
                break;
            case 8:
                mesFinal = "Agosto";
                break;
            case 9:
                mesFinal = "Septiembre";
                break;
            case 10:
                mesFinal = "Octubre";
                break;
            case 11:
                mesFinal = "Noviembre";
                break;
            case 12:
                mesFinal = "Diciembre";
                break;
            default:
                mesFinal = "";
                break;
        }
        return mesFinal;
    }

    public void BuscarAction(ActionEvent event) {
        TVRegistros.getItems().clear();
        TVRegistros.getColumns().clear();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        if (CBCriterio.getValue().equals("Egresos")) {

            TableColumn<Pagoegreso, String> cFecha = new TableColumn<>("Fecha");
            TableColumn<Pagoegreso, String> cMonto = new TableColumn<>("Monto");
            TableColumn<Pagoegreso, String> cNombre = new TableColumn<>("Maestro");

            cNombre.setCellValueFactory(new PropertyValueFactory<>("maestro"));
            cFecha.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));
            cMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

            TVRegistros.getColumns().addAll(cNombre, cFecha, cMonto);

            PagoegresoJpaController m = new PagoegresoJpaController(entityManagerFactory);
            MaestroJpaController ma = new MaestroJpaController(entityManagerFactory);
            Maestro mairo;
            List<Maestro> ListMa;
            List<Pagoegreso> ListPE;
            int p;
            ListMa = ma.findMaestroEntities();
            ListPE = m.findPagoegresoEntities();
            for (int i = 0; i < ListPE.size(); i++) {
                if (CBMes.getValue() != null ) {
                    //|| TFAnio == ListPE.get(i).getFechaPago().getYear()
                    if (CBMes.getValue().equals(calcularMes(ListPE.get(i).getFechaPago().getMonth() + 1)) || Integer.parseInt(TFAnio.getText()) == (ListPE.get(i).getFechaPago().getYear()+1900)) {
                        TVRegistros.getItems().add(ListPE.get(i));
                        lalista.add(ListPE.get(i));
                        System.out.println(ListPE.get(i).getFechaPago().getYear()+1900);
                    }
                } else {

                    mairo = ListPE.get(i).getIDMaestroPE();

                    TVRegistros.getItems().add(ListPE.get(i));
                    lalista.add(ListPE.get(i));
                }

            }
        } else {

            if (CBCriterio.getValue().equals("Ingresos")) {

                TableColumn<Pagoegreso, String> cFecha = new TableColumn<>("Fecha");
                TableColumn<Pagoegreso, String> cMonto = new TableColumn<>("Monto");
                TableColumn<Pagoegreso, String> cNombre = new TableColumn<>("Promocion");

                cFecha.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));
                cMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
                cNombre.setCellValueFactory(new PropertyValueFactory<>("promocion"));

                TVRegistros.getColumns().addAll(cNombre, cFecha, cMonto);

                InscripcionJpaController ins = new InscripcionJpaController(entityManagerFactory);
                MensualidadJpaController mens = new MensualidadJpaController(entityManagerFactory);
                List<Mensualidad> lmens;
                List<Inscripcion> lins;
                lmens = mens.findMensualidadEntities();
                lins = ins.findInscripcionEntities();
                for (int i = 0; i < lmens.size(); i++) {
                    if (CBMes.getValue() != null) {
                        if (CBMes.getValue().equals(calcularMes(lmens.get(i).getFechaPago().getMonth() + 1)) || Integer.parseInt(TFAnio.getText()) == (lmens.get(i).getFechaPago().getYear()+1900)) {
                            TVRegistros.getItems().add(lmens.get(i));
                            lalista2.add(lmens.get(i));
                        }
                    } else {
                        TVRegistros.getItems().add(lmens.get(i));
                        lalista2.add(lmens.get(i));
                    }
                }
                /*for (int i = 0; i < lins.size(); i++) {
                    TVRegistros.getItems().add(lins.get(i));
                }*/

            }
        }
        System.out.println(CBCriterio.getValue());
        System.out.println(CBMes.getValue());
        

    }

    @FXML
    public void DatosAction(ActionEvent event) {
        int total = 0;
        if (lalista2.isEmpty()) {
            for (int i = 0; i < lalista.size(); i++) {
                total += lalista.get(i).getMonto();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ared Espacio");
            alert.setHeaderText("Datos");
            alert.setContentText("El numero total de datos es: "+lalista.size()+" "+
                    "El total fue: "+total+"\n");
            alert.showAndWait();
        } else {
            for (int i = 0; i < lalista2.size(); i++) {
                total += lalista2.get(i).getMonto();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ared Espacio");
            alert.setHeaderText("Datos");
            alert.setContentText("El numero total de datos es: "+lalista2.size()+" "+
                    "El total fue: " +total+"\n");
            alert.showAndWait();
        }

    }

    @FXML
    public void BRegresarAction(ActionEvent e) {
        PrincipalController.initRootLayout(primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //ObservableList<String> options = FXCollections.observableArrayList("Ingresos", "Egresos");

        CBCriterio.getItems().addAll("Egresos", "Ingresos");
        CBMes.getItems().addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
    }

}
