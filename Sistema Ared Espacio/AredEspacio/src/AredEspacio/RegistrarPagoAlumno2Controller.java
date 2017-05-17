/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import static AredEspacio.InscribirAlumnoController.promocion;
import BaseDeDatos.Alumno;
import BaseDeDatos.Grupo;
import BaseDeDatos.Inscripcion;
import BaseDeDatos.Mensualidad;
import BaseDeDatos.Promocion;
import JPAControllers.AlumnoJpaController;
import JPAControllers.MensualidadJpaController;
import JPAControllers.exceptions.NonexistentEntityException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author yoresroy
 */
public class RegistrarPagoAlumno2Controller implements Initializable {

    @FXML
    private MenuButton BAlumnos;
    @FXML
    private MenuButton BClases;
    @FXML
    private MenuItem MIRegistrarClases;
    @FXML
    private MenuItem MIConsultarClase;
    @FXML
    private MenuButton BPromociones;
    @FXML
    private MenuButton BReportes;
    @FXML
    private MenuButton BMaestros;
    @FXML
    private MenuItem MIRegistrarMaestro;
    @FXML
    private MenuItem MIConsultarMaestro;
    @FXML
    private Label LConsultarAlumnos;
    @FXML
    private Label LNombre;
    @FXML
    private Label LTelefono;
    @FXML
    private Label LFechaDeNac;
    @FXML
    private Label LDireccion;
    @FXML
    private Label LFechaDeProximoPago;
    @FXML
    private Label LMonto;
    @FXML
    private Button BCancelar;
    @FXML
    private Button BPagar;
    @FXML
    private ImageView PaneImagen;
    @FXML
    private Label LCNombre;
    @FXML
    private Label LCTelefono;
    @FXML
    private Label LCFechaDeNacimiento;
    @FXML
    private Label LCDireccion;
    @FXML
    private Label LCFechaProximoPago;
    @FXML
    private Label LCMonto;
    @FXML
    private Label LabelFechaDeCorte;
    @FXML
    private Label montoPromo;
    @FXML
    private TextField LInscripcion;
    @FXML
    private SplitMenuButton BMPromocion;

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
    static private double total;
    private static int numeroPromocion;
    public static Stage primaryStage;
    private static AnchorPane rootLayout;
    private static Alumno alumno = new Alumno();
    private static boolean nuevo;
    Mensualidad mensualidad = new Mensualidad();

    static void initRootLayout(Stage primaryStage, Alumno al) {
        alumno = al;

        try {
            RegistrarPagoAlumno2Controller.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AredEspacio.class.getResource("RegistrarPagoAlumno2.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void llenarCampos() {
        LCNombre.setText(alumno.getNombre() + " " + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());
        Image img = new Image(new File(alumno.getRutaImagen()).toURI().toString());
        PaneImagen.setImage(img);
        Inscripcion ins = new Inscripcion();
        LCDireccion.setText(alumno.getDireccion());
        LCTelefono.setText(alumno.getNumeroDeCelular());
        LCFechaDeNacimiento.setText(alumno.getFechaNacimiento().getDate() + " / " + (alumno.getFechaNacimiento().getMonth() + 1) + " / " + (alumno.getFechaNacimiento().getYear() - 100));
        Grupo grupo = new Grupo();
        List<Grupo> listGrupo = grupo.buscarGruposAlumno(alumno.getIDAlumno());
        if (alumno.getIDMensualidadA() == null) {
            LCFechaProximoPago.setText(alumno.getIDInscripcionA().getFechaInscripcion().getDate() + " / " + (alumno.getIDInscripcionA().getFechaInscripcion().getMonth() + 1) + " / "
                    + (alumno.getIDInscripcionA().getFechaInscripcion().getYear() - 100));
            nuevo = true;
        } else {
            LCFechaProximoPago.setText(alumno.getIDInscripcionA().getFechaInscripcion().getDate() + " / " + (alumno.getIDInscripcionA().getFechaInscripcion().getMonth() + 1) + " / "
                    + (alumno.getIDInscripcionA().getFechaInscripcion().getYear() - 100));
            nuevo = false;
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarCampos();
        total = 0;
        List<Promocion> listaPromo;
        listaPromo = promocion.buscarPromocionPorNombre("");
        for (int i = 0; i < listaPromo.size(); i++) {

            if (listaPromo.get(i).getTipo().equals("Mensualidad")) {
                MenuItem menuItem = new MenuItem(listaPromo.get(i).getNombre());
                menuItem.setId(listaPromo.get(i).getNombre());
                BMPromocion.getItems().add(menuItem);
                menuItem.setOnAction((ActionEvent event) -> {
                    if (LInscripcion.getText().isEmpty()) {
                        Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
                        dialogoAlerta.setTitle("Ared Espacio");
                        dialogoAlerta.setHeaderText("¡Introduce primero el monto!");
                        dialogoAlerta.setContentText("Se dejaron campos vacios");
                        dialogoAlerta.initStyle(StageStyle.UTILITY);
                        dialogoAlerta.showAndWait();
                        montoPromo.setText("Monto con promocion: ");
                    } else {

                        String promo = menuItem.getId();
                        System.out.println("asdasdsa" + promo);
                        promocion = promocion.buscarPromocionPorNombre(promo).get(0);
                        float descuentoPorciento = promocion.buscarPromocionPorNombre(promo).get(0).getPorcentaje();
                        numeroPromocion = promocion.buscarPromocionPorNombre(promo).get(0).getIDPromocion();
                        double montoNeto = Double.parseDouble(LInscripcion.getText());
                        total = (montoNeto / 100) * (100 - descuentoPorciento);
                        total = Math.round(total * 100.0) / 100.0;
                        montoPromo.setText("");
                        montoPromo.setText("Monto con promocion: " + Math.round(total * 100.0) / 100.0);
                        BMPromocion.setText(promo);
                        mensualidad.setIDPromocionM(promocion);
                    }

                });
            }
        }

    }

    @FXML
    private void BAlumnosAction(ActionEvent event) {
    }

    @FXML
    private void MIRegistrarClasesAction(ActionEvent event) {
    }

    @FXML
    private void MIConsultarClaseAction(ActionEvent event) {
    }

    @FXML
    private void BClasesAction(ActionEvent event) {
    }

    @FXML
    private void BPromocionesAction(ActionEvent event) {
    }

    @FXML
    private void BReportesAction(ActionEvent event) {
    }

    @FXML
    private void MIRegistrarMaestroAction(ActionEvent event) {
    }

    @FXML
    private void MIConsultarMaestroAction(ActionEvent event) {
    }

    @FXML
    private void BMaestrosAction(ActionEvent event) {
    }

    @FXML
    private void BCancelarAction(ActionEvent event) {
        RegistrarPagoAlumnoController.initRootLayout(primaryStage);
    }

    @FXML
    private void BPagarAction(ActionEvent event) {
        if (LInscripcion.getText().isEmpty()) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.WARNING);
            dialogoAlerta.setTitle("Ared Espacio");
            dialogoAlerta.setHeaderText("¡No se pudo realizar pago!");
            dialogoAlerta.setContentText("Se dejaron campos vacios");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.showAndWait();
            montoPromo.setText("Monto con promocion: ");
            total = 0;
        } else {
            mensualidad.setMonto((int) total);
            AlumnoJpaController alumnoJPA = new AlumnoJpaController(emf);
            MensualidadJpaController mensuJPA = new MensualidadJpaController(emf);
            Calendar calendar = Calendar.getInstance();

            if (nuevo) {

                calendar.setTime(alumno.getIDInscripcionA().getFechaInscripcion());
                calendar.add(Calendar.MONTH, 1);

            } else {
                calendar.setTime(alumno.getIDMensualidadA().getFechaPago());
                calendar.add(Calendar.MONTH, 1);

            }
            try {
                mensualidad.setFechaPago(new Date());
                mensualidad.setIDMensualidad(mensuJPA.getMensualidadCount() + 1);
                mensualidad.setMonto((int) total);
                alumno.setIDMensualidadA(mensualidad);
                mensuJPA.create(mensualidad);
                alumnoJPA.edit(alumno);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RegistrarPagoAlumno2Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RegistrarPagoAlumno2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
