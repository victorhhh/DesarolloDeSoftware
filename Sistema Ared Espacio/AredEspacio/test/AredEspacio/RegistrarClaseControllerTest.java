/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import JPAControllers.ClaseJpaController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
public class RegistrarClaseControllerTest {
    
    public RegistrarClaseControllerTest() {
    }
    ComprobacionPrueba CP = new ComprobacionPrueba();
    Clase clase = new Clase();
    List<Clase> LVClases = clase.obtenerListaDeClases();
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
    ClaseJpaController controllerClases = new ClaseJpaController(entityManagerFactory);

    /**
     * Test of AccionGuardar method, of class RegistrarClaseController.
     */
    @Test
    public void testAccionGuardarClase() {
        System.out.println("AccionGuardar clase guardada");
        String hi = "13:00";
        String hf = "15:00";
        String horaIArray[] = hi.split(":");
        String horaFArray[] = hf.split(":");
        String CBDia = "Sabado";
        String nombre = "Merengue";

        boolean expResult = true;
        boolean result = false;
        boolean result1 = CP.horarioPrudente(horaIArray, horaFArray);
        boolean result2 = CP.disponibilidadHorario(hi, hf, LVClases, CBDia);
        boolean result3 = CP.horarioMayor(horaIArray, horaFArray);
        boolean result4 = CP.horarioMenor(horaIArray, horaFArray);
        if (!nombre.equals(null) && !CBDia.equals(null) && (!hi.equals(null) && !hf.equals(null))) {
            if (result1 == true) {
                if (result2 == true) {
                    if (result3 == true) {
                        System.out.println("la clase excedio de 3 horas");
                    } else {
                        if (result4 == true) {
                            System.out.println("la clase no dura ni una hora");
                        } else {
                            result = true;
                            String h = hi.concat("-").concat(hf);
                            clase.setDia(CBDia);
                            clase.setEstado(true);
                            clase.setHora(h);
                            clase.setNombre(nombre);
                            try {
                                controllerClases.create(clase);
                                System.out.println("Se ha registrado la clase en la base de datos");
                            } catch (Exception e) {
                              
                            }
                        }
                    }
                } else {
                    System.out.println("ya existe una clase en este dia a esta hora");
                }
            } else {
                System.out.println("la clase no comenzaran o terminaran antes o despues de que abra o cierre la escuela");
            }
        } else {
            System.out.println("no puedes dejar campos vacios al registrar una clase");
        }
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.

    }

    @Test
    public void testAccionGuardarClaseHorasExcedidas() {
        System.out.println("clase dura + de 3 horas");
        String hi = "8:00";
        String hf = "13:00";
        String horaIArray[] = hi.split(":");
        String horaFArray[] = hf.split(":");
        String CBDia = "Martes";
        String nombre = "Salsa";
        //RegistrarClaseController instance = new RegistrarClaseController();
        boolean expResult = false;
        boolean result = false;
        boolean result1 = CP.horarioPrudente(horaIArray, horaFArray);
        boolean result2 = CP.disponibilidadHorario(hi, hf, LVClases, CBDia);
        boolean result3 = CP.horarioMayor(horaIArray, horaFArray);
        boolean result4 = CP.horarioMenor(horaIArray, horaFArray);
        if (!nombre.equals(null) && !CBDia.equals(null) && (!hi.equals(null) && !hf.equals(null))) {
            if (result1 == true) {
                if (result2 == true) {
                    if (result3 == true) {
                        System.out.println("la clase excedio de 3 horas");
                    } else {
                        if (result4 == true) {
                            System.out.println("la clase no dura ni una hora");
                        } else {
                            result = true;
                        }
                    }
                } else {
                    System.out.println("ya existe una clase en este dia a esta hora");
                }
            } else {
                System.out.println("la clase no comenzaran o terminaran antes o despues de que abra o cierre la escuela");
            }
        } else {
            System.out.println("no puedes dejar campos vacios al registrar una clase");
        }
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.

    }

    @Test
    public void testAccionGuardarClaseDisponibilidadHoraYDia() {
        System.out.println("Dia y hora ocupado");
        String hi = "16:30";
        String hf = "18:00";
        String horaIArray[] = hi.split(":");
        String horaFArray[] = hf.split(":");
        String CBDia = "Lunes";
        String nombre = "Belly Dance";
        
        boolean expResult = false;
        boolean result = false;
        boolean result1 = CP.horarioPrudente(horaIArray, horaFArray);
        boolean result2 = CP.disponibilidadHorario(hi, hf, LVClases, CBDia);
        boolean result3 = CP.horarioMayor(horaIArray, horaFArray);
        boolean result4 = CP.horarioMenor(horaIArray, horaFArray);
        if (!nombre.equals(null) && !CBDia.equals(null) && (!hi.equals(null) && !hf.equals(null))) {
            if (result1 == true) {
                if (result2 == true) {
                    if (result3 == true) {
                        System.out.println("la clase excedio de 3 horas");
                    } else {
                        if (result4 == true) {
                            System.out.println("la clase no dura ni una hora");
                        } else {
                            result = true;
                        }
                    }
                } else {
                    System.out.println("ya existe una clase en este dia a esta hora");
                }
            } else {
                System.out.println("la clase no comenzaran o terminaran antes o despues de que abra o cierre la escuela");
            }
        } else {
            System.out.println("no puedes dejar campos vacios al registrar una clase");
        }
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.

    }

    @Test
    public void testAccionGuardarClaseMenosDeLaHora() {
        System.out.println("clase dura - de 1 hora");
        String hi = "13:00";
        String hf = "13:59";
        String horaIArray[] = hi.split(":");
        String horaFArray[] = hf.split(":");
        String CBDia = "Martes";
        String nombre = "Chachacha";
        boolean expResult = false;
        boolean result = false;
        boolean result1 = CP.horarioPrudente(horaIArray, horaFArray);
        boolean result2 = CP.disponibilidadHorario(hi, hf, LVClases, CBDia);
        boolean result3 = CP.horarioMayor(horaIArray, horaFArray);
        boolean result4 = CP.horarioMenor(horaIArray, horaFArray);
        if (!nombre.equals(null) && !CBDia.equals(null) && (!hi.equals(null) && !hf.equals(null))) {
            if (result1 == true) {
                if (result2 == true) {
                    if (result3 == true) {
                        System.out.println("la clase excedio de 3 horas");
                    } else {
                        if (result4 == true) {
                            System.out.println("la clase no dura ni una hora");
                        } else {
                            result = true;
                        }
                    }
                } else {
                    System.out.println("ya existe una clase en este dia a esta hora");
                }
            } else {
                System.out.println("la clase no comenzaran o terminaran antes o despues de que abra o cierre la escuela");
            }
        } else {
            System.out.println("no puedes dejar campos vacios al registrar una clase");
        }
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
    }

    @Test
    public void testAccionGuardarClaseCamposVacios() {
        System.out.println("Datos incompletos");
        String hi = "10:00";
        String hf = "12:00";
        String horaIArray[] = hi.split(":");
        String horaFArray[] = hf.split(":");
        String CBDia = null;
        String nombre = null;
        //RegistrarClaseController instance = new RegistrarClaseController();
        boolean expResult = false;
        boolean result = false;
        boolean result1 = CP.horarioPrudente(horaIArray, horaFArray);
        boolean result2 = CP.disponibilidadHorario(hi, hf, LVClases, CBDia);
        boolean result3 = CP.horarioMayor(horaIArray, horaFArray);
        boolean result4 = CP.horarioMenor(horaIArray, horaFArray);
        if (nombre != null && CBDia != null && (hi != null && hf != null)) {
            if (result1 == true) {
                if (result2 == true) {
                    if (result3 == true) {
                        System.out.println("la clase excedio de 3 horas");
                    } else {
                        if (result4 == true) {
                            System.out.println("la clase no dura ni una hora");
                        } else {
                            result = true;
                        }
                    }
                } else {
                    System.out.println("ya existe una clase en este dia a esta hora");
                }
            } else {
                System.out.println("la clase no comenzaran o terminaran antes o despues de que abra o cierre la escuela");
            }
        } else {
            System.out.println("no puedes dejar campos vacios al registrar una clase");
        }
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.

    }
}
