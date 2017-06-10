/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ossiel
 */
public class ConsultarClaseControllerTest {
    
    public ConsultarClaseControllerTest() {
    }
    
      /**
     * Test of AccionBuscar method, of class ConsultarClaseController.
     */
    @Test
    public void testAccionBuscarClaseEncontrada() {
        System.out.println("clase encontrada");
        Clase clase = new Clase();
        List<Clase> listaClases = clase.buscarClasesPorNombre("Salsa");
        boolean expResult = true;
        boolean result = true;
        if (listaClases.isEmpty()) {
            result = false;
            System.out.println("El nombre de la clase que busco no existe coincidencia ");
        } else {
            for (int i = 0; i < listaClases.size(); i++) {
                System.out.println(listaClases.get(i).getNombre() + " " + listaClases.get(i).getDia() + " " + listaClases.get(i).getHora());
            }
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testAccionBuscarClaseNoEncontrada() {
        System.out.println("clase no encontrada");
        Clase clase = new Clase();
        List<Clase> listaClases = clase.buscarClasesPorNombre("rap");
        boolean expResult = false;
        boolean result = true;
        if (listaClases.isEmpty()) {
            result = false;
            System.out.println("rap");
            System.out.println("El nombre de la clase que busco no existe coincidencia ");
        } else {
            for (int i = 0; i < listaClases.size(); i++) {
                System.out.println(listaClases.get(i).getNombre());
            }
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of AccionConsultar method, of class ConsultarClaseController.
     */
    @Test
    public void testAccionConsultarClaseEncontrada() {
        System.out.println("Consultar informacion Clase buscada");
        Clase clase = new Clase();
        List<Clase> listaClases = clase.buscarClasesPorNombre("Arabe");
        boolean expResult = true;
        boolean result = true;
        if (listaClases.isEmpty()) {
            result = false;
            System.out.println("El nombre de la clase que busco no existe coincidencia ");
        } else {
            for (int i = 0; i < listaClases.size(); i++) {
                System.out.println("Tipo de danza" + " " + listaClases.get(i).getNombre());
                System.out.println("La clase es el dia:" + " " + listaClases.get(i).getDia());
                System.out.println("la clase es de:" + "" + listaClases.get(i).getHora());
                System.out.println("La clase esta activa:" + " " + listaClases.get(i).getEstado());
                System.out.println("La clase es impartidar por" + " " + listaClases.get(i).getIDMaestroC().getNombre() + " "
                        + listaClases.get(i).getIDMaestroC().getPrimerApellido() + " "
                        + listaClases.get(i).getIDMaestroC().getSegundoApellido());
            }
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testAccionConsultarClaseEncontradaSinMaestro() {
        System.out.println("Consultar informacion Clase buscada sin maestro");
        Clase clase = new Clase();
        List<Clase> listaClases = clase.buscarClasesPorNombre("Bachata");
        boolean expResult = true;
        boolean result = false;
        if (listaClases.isEmpty()) {
            result = false;
            System.out.println("El nombre de la clase que busco no existe coincidencia ");
        } else {
            for (int j = 0; j < listaClases.size(); j++) {
                if (listaClases.get(j).getIDMaestroC() == null) {
                    for (int i = 0; i < listaClases.size(); i++) {
                        System.out.println("Tipo de danza" + " " + listaClases.get(i).getNombre());
                        System.out.println("La clase es el dia:" + " " + listaClases.get(i).getDia());
                        System.out.println("la clase es de:" + "" + listaClases.get(i).getHora());
                        System.out.println("La clase esta activa:" + " " + listaClases.get(i).getEstado());
                        System.out.println("La clase aun no tiene maestro");
                    }
                    result = true;
                } else {
                    for (int i = 0; i < listaClases.size(); i++) {
                        System.out.println("Tipo de danza" + " " + listaClases.get(i).getNombre());
                        System.out.println("La clase es el dia:" + " " + listaClases.get(i).getDia());
                        System.out.println("la clase es de:" + "" + listaClases.get(i).getHora());
                        System.out.println("La clase esta activa:" + " " + listaClases.get(i).getEstado());
                        System.out.println("La clase es impartidar por" + " " + listaClases.get(i).getIDMaestroC().getNombre() + " "
                                + listaClases.get(i).getIDMaestroC().getPrimerApellido() + " "
                                + listaClases.get(i).getIDMaestroC().getSegundoApellido());
                    }
                }

            }
        }
        assertEquals(expResult, result);
    }
}
