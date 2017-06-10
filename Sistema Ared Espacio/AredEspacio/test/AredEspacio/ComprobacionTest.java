/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import com.jfoenix.controls.JFXTimePicker;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class ComprobacionTest {
    
    public ComprobacionTest() {
    }
    
    
      ComprobacionPrueba CP =new ComprobacionPrueba();
    /**
     * Test of dosCamposVaciosPromocion method, of class Comprobacion.
     */
    @Test
    public void testDosCamposVaciosPromocion() {
        System.out.println("dosCamposVaciosPromocion");
        String TFNombre = "2X1";
        String TADescripcion = "";
        String CBTipo = "Mensualidad";
        String CBPorcentaje = "30";
        boolean expResult = false;
        boolean result = CP.dosCamposVaciosPromocion(TFNombre, TADescripcion, CBTipo, CBPorcentaje);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of dosCamposVacios method, of class Comprobacion.
     */
    @Test
    public void testDosCamposVacios() {
        System.out.println("dosCamposVacios");
        String TFNombre = "Arabe";
        String CBDia = "Lunes";
        String TPHoraInicio = "12:00";
        String TPHoraFin = "14:00";
        
        boolean expResult = false;
        boolean result = CP.dosCamposVacios(TFNombre, CBDia, TPHoraInicio, TPHoraFin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of horarioMayor method, of class Comprobacion.
     */
    @Test
    public void testHorarioMayor() {
        System.out.println("horarioMayor");
       String hi="14:00";
        String hf="18:00";
        String horaIArray[] = hi.split(":");
    String horaFArray[] =hf.split(":");
        
        boolean expResult = true;
        boolean result = CP.horarioMayor(horaIArray, horaFArray);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of horarioMenor method, of class Comprobacion.
     */
    @Test
    public void testHorarioMenor() {
        System.out.println("horarioMenor");
        String CBDia = "Lunes";
        String hi="11:00";
        String hf="11:30";
        String horaIArray[] = hi.split(":");
    String horaFArray[] =hf.split(":");
       
        boolean expResult = true;
        boolean result = CP.horarioMenor(horaIArray, horaFArray);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of disponibilidadHorario method, of class Comprobacion.
     */
    @Test
    public void testDisponibilidadHorario() {
        System.out.println("disponibilidadHorario");
        String CBDia="Lunes";        
        String hi="16:30";
        String hf="18:00";
        
        Clase clase=new Clase();
        List<Clase> LVClases =clase.obtenerListaDeClases();
         for (int i = 0; i < LVClases.size(); i++) {
                    System.out.println(LVClases.get(i).getNombre()+" "+LVClases.get(i).getDia()+" "+LVClases.get(i).getHora());
                }
        boolean expResult =false ;
        boolean result = CP.disponibilidadHorario(hi, hf, LVClases, CBDia);
        assertEquals(expResult, result);
        
       
    }

    /**
     * Test of horarioPrudente method, of class Comprobacion.
     */
    @Test
    public void testHorarioPrudente() {
        System.out.println("horarioPrudente");
        String hi="6:00";
        String hf="9:00";
    String horaIArray[] = hi.split(":");
    String horaFArray[] =hf.split(":");
   
    boolean expResult = false;
    boolean result = CP.horarioPrudente(horaIArray, horaFArray);

    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.

}
}
