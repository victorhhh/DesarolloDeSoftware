/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import java.security.Principal;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author VictorHugo
 */
public class AredEspacio extends Application {
    
    static Stage primaryStage;
    public static String nombreUsuario;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AredEspacio");
        PrincipalController.initRootLayout(primaryStage);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
