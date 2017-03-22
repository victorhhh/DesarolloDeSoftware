/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aredespacio;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author VictorHugo
 */
public class AredEspacio extends Application {

    static Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        AredEspacio.primaryStage = primaryStage;
        AredEspacio.primaryStage.setTitle("Ared Espacio");
        RegistrarMaestroController.initRootLayout(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
