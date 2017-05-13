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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ossiel
 */
public class MostrarHorarioController implements Initializable {

    @FXML
    Button BConsultar, BLimpiar;
    ;

    @FXML
    MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;

    @FXML
    MenuItem MIRegistrar, MIConsultar;

    @FXML
    ComboBox CBMaestros;

    @FXML
    TableView TVHorario;
    @FXML
    TableColumn<Clase, String> cHora;

    @FXML
    TableColumn<Clase, String> cTipoDanza;

    @FXML
    TableColumn<Clase, String> cDia;

    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    Clase clase = new Clase();
    List<Clase> listaDeClases = clase.obtenerListaDeClases();
    Maestro maestro = new Maestro();
    List<Maestro> listaMaestros = maestro.obtenerListaDeMaestros();

    @FXML
    void AccionConsultarClase(ActionEvent event) {

    }

    @FXML
    void AccionLimpiar(ActionEvent event) {
        TVHorario.getItems().clear();
    }

    @FXML
    void AccionConsultar(ActionEvent event) {
        if (CBMaestros.getValue() == null) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText("!Aviso¡" + " " + "sin seleccion");
            dialogoAlerta.setContentText("No seleccionaste a ningun maestro para consultar su horario");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
        } else {

            int ID = 0;
            for (int i = 0; i < listaMaestros.size(); i++) {
                if (CBMaestros.getValue().toString().equals(listaMaestros.get(i).getNombre().concat(" ").concat(listaMaestros.get(i).getPrimerApellido().concat(" ").concat(listaMaestros.get(i).getSegundoApellido())))) {
                    ID = listaMaestros.get(i).getIDMaestro();

                }
            }
            cHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
            for (int i = 0; i < listaDeClases.size(); i++) {
                int IDM = listaDeClases.get(i).getIDMaestroC().getIDMaestro();
                //System.out.println("el id del,aestro es:" + " " + ID);
                //System.out.println("antes de comparar que el id de la clase conecta con elid del maestro asignado ");
                //System.out.println(i + "vez" + "------" + "id del maestro:" + IDM);
                if (ID == IDM) {
                   //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX");
                    //System.out.println("id de maestro seleccionado " + ID + "------" + "id del m,aestro segun la lista de clase " + IDM);
                    //System.out.println(listaDeClases.get(i).getNombre()+" "+listaDeClases.get(i).getDia()+" "+listaDeClases.get(i).getHora());
                    if (listaDeClases.get(i).getDia().equals("Lunes")) {
                        cTipoDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                        cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
                        TVHorario.getItems().add(listaDeClases.get(i));
                        //System.out.println("hay clases el lunes");
                      
                        //break;
                    } else {
                        if (listaDeClases.get(i).getDia().equals("Martes")) {
                            cTipoDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                            cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
                            TVHorario.getItems().add(listaDeClases.get(i));
                            //System.out.println("hay clases el MArtes");
                            
                            //break;
                        } else {
                            if (listaDeClases.get(i).getDia().equals("Miercoles")) {
                                cTipoDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                                cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
                                TVHorario.getItems().add(listaDeClases.get(i));
                                //System.out.println("hay clases el miercoles");
                                
                                //break;
                            } else {
                                if (listaDeClases.get(i).getDia().equals("Jueves")) {
                                    cTipoDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                                    cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
                                    TVHorario.getItems().add(listaDeClases.get(i));
                                    //System.out.println("hay clases el jueves");
                                    
                                    //break;
                                } else {
                                    if (listaDeClases.get(i).getDia().equals("Viernes")) {
                                        cTipoDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                                        cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
                                        TVHorario.getItems().add(listaDeClases.get(i));
                                        //System.out.println("hay clases el viermes");
                                        
                                        //break;
                                    } else {
                                        cTipoDanza.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                                        cDia.setCellValueFactory(new PropertyValueFactory<>("dia"));
                                        TVHorario.getItems().add(listaDeClases.get(i));
                                        //System.out.println("hay clases el sabado");
                                        
                                        //  break;
                                    }
                                }
                            }
                        }
                    }

                }

               continue;
            }
        }

    }

    @FXML
    void AccionRegistrarClase(ActionEvent event) {

    }

    static void initRootLayout(Stage primaryStage) {

        try {
            MostrarHorarioController.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("MostrarHorario.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (int i = 0; i < listaMaestros.size(); i++) {
            CBMaestros.getItems().add(listaMaestros.get(i).getNombre().toString() + " " + listaMaestros.get(i).getPrimerApellido() + " " + listaMaestros.get(i).getSegundoApellido());
        }

    }

}

