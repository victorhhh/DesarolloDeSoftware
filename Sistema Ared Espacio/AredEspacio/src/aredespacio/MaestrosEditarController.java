/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aredespacio;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author victor
 */
public class MaestrosEditarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BQuitar, BGuardar, BAgregar;
    @FXML
    private TextField TFNombre, TFCelular, TFFechaNac, TFDireccion, TFSueldo;
    @FXML
    private ScrollPane SPClases;
    @FXML
    private MenuButton BAlumnos, BMaestros, BClases, BPromociones, BReportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
