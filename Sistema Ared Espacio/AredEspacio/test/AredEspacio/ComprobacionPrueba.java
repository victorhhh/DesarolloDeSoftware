/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio;

import BaseDeDatos.Clase;
import java.util.List;

/**
 *
 * @author ossiel
 */
public class ComprobacionPrueba {
    public boolean dosCamposVaciosPromocion(String TFNombre,String TADescripcion,String CBTipo,String CBPorcentaje) {
        if (TFNombre!=null && CBTipo!=null && TADescripcion==null&& CBPorcentaje==null
                || TFNombre!=null && CBTipo==null && TADescripcion!=null&& CBPorcentaje==null
                || TFNombre!=null && CBTipo==null && TADescripcion==null&& CBPorcentaje!=null
                ||TFNombre==null && CBTipo!=null && TADescripcion!=null&& CBPorcentaje==null
                ||TFNombre==null && CBTipo!=null && TADescripcion==null&& CBPorcentaje!=null
                ||TFNombre==null && CBTipo==null && TADescripcion!=null&& CBPorcentaje!=null) {
            return true;
        }
        return false;
    }
    
     public boolean dosCamposVacios(String TFNombre,String CBDia,String TPHoraInicio,String TPHoraFin) {
        if (TFNombre!=null && CBDia==null && (TPHoraInicio==null  && TPHoraFin==null)
                || TFNombre==null && CBDia!=null && (TPHoraInicio==null&& TPHoraFin==null)
                || TFNombre==null && CBDia==null&& (TPHoraInicio!=null && TPHoraFin!=null )) {
            return true;
        }
        return false;

    }

    public boolean horarioMayor(String horaIArray[],String horaFArray[]) {
       
        int hI = Integer.parseInt(horaIArray[0]);
        int mI = Integer.parseInt(horaIArray[1]);
        int hF = Integer.parseInt(horaFArray[0]);
        int mF = Integer.parseInt(horaFArray[1]);
        if ((hF > (hI + 3)) || (hF == (hI + 3) && mF > mI)) {
            return true;
        }
        return false;
    }

    public boolean horarioMenor(String horaIArray[],String horaFArray[]) {
        //String horaIArray[] = TPHoraInicio.equals(null).toString().split(":");
        //String horaFArray[] = TPHoraFin.equals(null).toString().split(":");
        int hI = Integer.parseInt(horaIArray[0]);
        int mI = Integer.parseInt(horaIArray[1]);
        int hF = Integer.parseInt(horaFArray[0]);
        int mF = Integer.parseInt(horaFArray[1]);
        if ((hF < hI) || (hF == hI) || (hF == (hI + 1) && mF < mI)) {
            return true;
        }
        return false;
    }

    public boolean disponibilidadHorario(String horaIArray,String horaFArray, List<Clase> LVClases,String CBDia) {
        String h = horaIArray.concat("-").concat(horaFArray);
        Clase clase = new Clase();
        List<Clase> listaClases = clase.obtenerListaDeClases();
        for (int i = 0; i < listaClases.size(); i++) {
            if (listaClases.get(i).getDia().equals(CBDia)) {
                if (listaClases.get(i).getHora().equals(h)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean horarioPrudente(String horaIArray[],String horaFArray[]) {
        //String horaIArray[] = TPHoraInicio.equals(null).toString().split(":");
        //String horaFArray[] = TPHoraFin.equals(null).toString().split(":");
        int hI = Integer.parseInt(horaIArray[0]);
        int mI = Integer.parseInt(horaIArray[1]);
        int hF = Integer.parseInt(horaFArray[0]);
        int mF = Integer.parseInt(horaFArray[1]);
        if ((hI < 8|| hI==21) || (hF >21||hF<8)) {
            return false;
        }
        return true;
    }
}
