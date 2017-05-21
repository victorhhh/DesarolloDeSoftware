/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.ConsultarAlumno1Controller.primaryStage;
import BaseDeDatos.Clase;
import BaseDeDatos.Maestro;
import JPAControllers.ClaseJpaController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import JPAControllers.MaestroJpaController;
import JPAControllers.exceptions.NonexistentEntityException;
import static AredEspacio.PrincipalController.primaryStage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class RegistrarMaestroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BEliminar, BGuardar, BAgregar;
    @FXML
    private TextField TFNombre, TFCelular, TFDireccion, TFPrimerApellido, TFSegundoApellido, SSueldo;

    @FXML
    private TableView<Clase> TVNombreClase;
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;
    @FXML
    private DatePicker DPFechaNac;
    @FXML
    private ImageView IVMaestro;

    private String src;
    private TableColumn<Clase, String> cNombre = new TableColumn<>("Clases");
    static Maestro ma = new Maestro();
    static List<Clase> cls;
    static boolean crea;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;

    static void initRootLayout(Stage primaryStage) {
        try {
            RegistrarMaestroController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarMaestro.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void initRootLayout(Stage primaryStage, Maestro maestroNovo) {
        try {
            RegistrarMaestroController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarMaestro.fxml"));
            rootLayout = (AnchorPane) loader.load();

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

    static void initRootLayout(Stage primaryStage, List<Clase> listaAgregados, Maestro maestroNovo, boolean crear) {
        ma = maestroNovo;
        cls = listaAgregados;
        crea = crear;
        try {
            RegistrarMaestroController.primaryStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarMaestro.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void MIConsultarMaestroAction(ActionEvent event) {
        ConsultarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void MIRegistrarMaestroAction(ActionEvent event) {
        List<Clase> cls = null;

        Maestro ma = null;

        RegistrarMaestroController.initRootLayout(primaryStage, cls, ma, true);
        //RegistrarMaestroController.initRootLayout(primaryStage);
    }

    @FXML
    public void BGuardarAction(ActionEvent event) throws NonexistentEntityException, Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClaseJpaController clase1 = new ClaseJpaController(entityManagerFactory);
        MaestroJpaController m = new MaestroJpaController(entityManagerFactory);
        Maestro maestro = new Maestro();

        if (TFNombre.getText().isEmpty() || TFPrimerApellido.getText().isEmpty() || TFSegundoApellido.getText().isEmpty() || TFCelular.getText().isEmpty() || TFDireccion.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ared Espacio");
            alert.setHeaderText("Campos vacios");
            alert.setContentText("Los campos estan vacios, rellenos para continuar");
            alert.showAndWait();
        } else {

            // String fecha = TFFechaNac.getText();
            LocalDate fechini = DPFechaNac.getValue();
            java.sql.Date fecha = java.sql.Date.valueOf(fechini);
            //System.out.println("hola soy fechini"+fecha);

            try {
                //Maestro temp = m.findMaestro(ma.getIDMaestro());

                if (crea) {
                    String nombre = TFNombre.getText();
                    maestro.setFechaNacimiento(fecha);
                    maestro.setDireccion(TFDireccion.getText());
                    maestro.setEstado(true);
                    maestro.setNombre(TFNombre.getText());
                    maestro.setNumeroDeTelefono(TFCelular.getText());
                    maestro.setPrimerApellido(TFPrimerApellido.getText());
                    maestro.setSegundoApellido(TFSegundoApellido.getText());
                    maestro.setRutaImagen(src);
                    maestro.setSueldo(Double.parseDouble(SSueldo.getText()));
                    m.create(maestro);
                    System.out.println("aqui esta la fecha guardada " + maestro.getFechaNacimiento());
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Ared Espacio");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha guardado correctamente el maestro¡¡¡");
                    alert.showAndWait();
                    if (!TVNombreClase.getItems().isEmpty()) {
                        Clase clasesita = clase1.findClase(cls.get(0).getIDClase());
                        if (clasesita.getIDMaestroC() == null) {
                           // maestro = ma.;
                            System.out.println("id de la clase: "+clasesita.getIDClase()+"id del maestro"+maestro.getIDMaestro());
                            clasesita.setIDMaestroC(maestro);

                            clase1.edit(clasesita);
                        }
                    }
                } else {
                    maestro = ma;
                    maestro.setDireccion(TFDireccion.getText());
                    maestro.setFechaNacimiento(fecha);
                    maestro.setNombre(TFNombre.getText());
                    maestro.setNumeroDeTelefono(TFCelular.getText());
                    maestro.setPrimerApellido(TFPrimerApellido.getText());
                    maestro.setSegundoApellido(TFSegundoApellido.getText());
                    maestro.setRutaImagen(src);
                    maestro.setSueldo(Double.parseDouble(SSueldo.getText()));
                    //maestro.setIDMaestro(ma.getIDMaestro());
                    System.out.println(maestro.getNombre() + " " + maestro.getIDMaestro());

                    m.edit(maestro);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ared Espacio");
                    alert.setHeaderText(null);
                    alert.setContentText("Se ha modificado de baja a " + ma.getNombre() + " " + ma.getPrimerApellido() + " " + ma.getSegundoApellido());

                    alert.showAndWait();
                    if (!TVNombreClase.getItems().isEmpty()) {
                        Clase clasesita = clase1.findClase(cls.get(0).getIDClase());
                        if (clasesita.getIDMaestroC() == null) {
                            maestro = ma;
                            System.out.println("id de la clase: "+clasesita.getIDClase()+"id del maestro"+maestro.getIDMaestro());
                            clasesita.setIDMaestroC(maestro);

                            clase1.edit(clasesita);
                        }
                    }
                }

            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ared Esapcio");
                alert.setHeaderText("No se pudo guardar");
                alert.setContentText("No se pudo guardar en la base de datos ");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("Stacktrace:");

                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);
                alert.getDialogPane().setExpandableContent(expContent);

                alert.showAndWait();

                Logger.getLogger(RegistrarMaestroController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                entityManagerFactory.getCache().evictAll();
                //entityManagerFactory.close();

            }
        }
    }

    @FXML
    public void BExaminarAction(ActionEvent event) {
        FileChooser fileChosser = new FileChooser();
        src = fileChosser.showOpenDialog(primaryStage).toString();
        System.out.println(src);
        Image img = new Image(new File(src).toURI().toString());
        IVMaestro.setImage(img);

    }

    public Maestro prepararAlumno() {
        Maestro mairo = new Maestro();

        mairo.setNombre(TFNombre.getText());

        mairo.setPrimerApellido(TFPrimerApellido.getText());
        mairo.setSegundoApellido(TFSegundoApellido.getText());
        mairo.setSueldo(Double.parseDouble(SSueldo.getText()));
        mairo.setNumeroDeTelefono(TFCelular.getText());
        mairo.setDireccion(TFDireccion.getText());
        mairo.setRutaImagen(src);
        Date date = new Date();
        try {
            date.setDate(DPFechaNac.getValue().getDayOfMonth());
            date.setMonth(DPFechaNac.getValue().getMonthValue());
            date.setYear(DPFechaNac.getValue().getYear() - 1900);
            mairo.setFechaNacimiento(date);
        } catch (Exception e) {
            System.out.println("no hay fecha");
        }
        mairo.setEstado(true);

        System.out.println(mairo.getNombre());
        // mairo.setFechaNacimiento(localDate);
        return mairo;
    }

    @FXML
    public void BAgregarAction(ActionEvent event) {
        if (TFNombre.getText().isEmpty() || TFPrimerApellido.getText().isEmpty() || TFSegundoApellido.getText().isEmpty() || TFCelular.getText().isEmpty() || TFDireccion.getText().isEmpty() || SSueldo.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ared Espacio");
            alert.setHeaderText(null);
            alert.setContentText("Llene todos los capos primero");
            alert.showAndWait();

        } else {

            if (TVNombreClase.getColumns().isEmpty()) {
                Maestro miss = prepararAlumno();
                System.out.println("salgo " + miss.getNombre());

                RegistrarMaestroAgregarClaseController.initRootLayout(primaryStage, prepararAlumno(), crea);
            } else {
                List<Clase> listaAñadidos = TVNombreClase.getItems();
                Maestro miss = prepararAlumno();
                System.out.println("salgo " + miss.getNombre());
                RegistrarMaestroAgregarClaseController.initRootLayout(primaryStage, prepararAlumno(), listaAñadidos, crea);
            }
        }

    }

    @FXML
    public void BEliminarAction(ActionEvent event) {

    }

    @FXML
    public void BAlumnosAction(ActionEvent event) {

    }

    @FXML
    public void BMaestrosAction(ActionEvent event) {

    }

    @FXML
    public void BClasesAction(ActionEvent event) {

    }

    @FXML
    public void BPromocionesAction(ActionEvent event) {

    }

    @FXML
    public void BReporteAction(ActionEvent event) {

    }

    @FXML
    public void DPFechaNacAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TVNombreClase.getColumns().addAll(cNombre);
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cNombre.setMinWidth(180);
        TFNombre.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFNombre.getText().length() > 15) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFPrimerApellido.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                    event.consume();
                }
                if (TFPrimerApellido.getText().length() > 15) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFSegundoApellido.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);

                if (TFSegundoApellido.getText().length() > 15) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFDireccion.setOnKeyTyped(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                char car = event.getCharacter().charAt(0);

                if (TFDireccion.getText().length() > 40) {
                    event.consume();
                }
                if (car == ';' || car == ':' || car == ',' || car == '.' || car == '_' || car == '-') {
                    event.consume();
                }
            }
        });
        TFCelular.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int car = event.getCharacter().charAt(0);
                if (Character.isDigit(car)) {
                } else {
                    event.consume();
                }
                if (TFCelular.getText().length() > 10) {
                    event.consume();
                }
            }
        });
        if (cls != null && !cls.isEmpty()) {
            for (int i = 0; i < cls.size(); i++) {
                TVNombreClase.getItems().add(cls.get(i));
            }
        }
        if (ma != null) {
            System.out.println("llegue2 " + ma.getNombre());
            TFNombre.setText(ma.getNombre());
            TFPrimerApellido.setText(ma.getPrimerApellido());
            TFSegundoApellido.setText(ma.getSegundoApellido());
            TFDireccion.setText(ma.getDireccion());
            TFCelular.setText(ma.getNumeroDeTelefono());
            Instant instant = ma.getFechaNacimiento().toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            DPFechaNac.setValue(localDate);

            SSueldo.setText(String.valueOf(ma.getSueldo()));
            src = ma.getRutaImagen();
            Image img = new Image(new File(ma.getRutaImagen()).toURI().toString());
            IVMaestro.setImage(img);
        }

    }

}
