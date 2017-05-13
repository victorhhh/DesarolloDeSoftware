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

/**
 *
 * @author ossiel
 */
public class Comprobacion {
    
    public boolean dosCamposVaciosPromocion(TextField TFNombre,TextArea TADescripcion,ComboBox CBTipo,ComboBox CBPorcentaje) {
        if (!TFNombre.getText().isEmpty() && CBTipo.getValue() != null && TADescripcion.getText().isEmpty()&& CBPorcentaje.getValue()==null
                || !TFNombre.getText().isEmpty() && CBTipo.getValue() == null && !TADescripcion.getText().isEmpty()&& CBPorcentaje.getValue()==null
                || !TFNombre.getText().isEmpty() && CBTipo.getValue() == null && TADescripcion.getText().isEmpty()&& CBPorcentaje.getValue()!=null
                || TFNombre.getText().isEmpty() && CBTipo.getValue() != null && !TADescripcion.getText().isEmpty()&& CBPorcentaje.getValue()==null
                || TFNombre.getText().isEmpty() && CBTipo.getValue() != null && TADescripcion.getText().isEmpty()&& CBPorcentaje.getValue()!=null
                || TFNombre.getText().isEmpty() && CBTipo.getValue() == null && !TADescripcion.getText().isEmpty()&& CBPorcentaje.getValue()!=null) {
            return true;
        }
        return false;
    }
    
     public boolean dosCamposVacios(TextField TFNombre,ComboBox CBDia,JFXTimePicker TPHoraInicio,JFXTimePicker TPHoraFin) {
        if (!TFNombre.getText().isEmpty() && CBDia.getValue() == null
                && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)
                || TFNombre.getText().isEmpty() && CBDia.getValue() != null
                && (TPHoraInicio.getValue() == null && TPHoraFin.getValue() == null)
                || TFNombre.getText().isEmpty() && CBDia.getValue() == null
                && (TPHoraInicio.getValue() != null && TPHoraFin.getValue() != null)) {
            return true;
        }
        return false;

    }

    public boolean horarioMayor(JFXTimePicker TPHoraInicio,JFXTimePicker TPHoraFin) {
        String horaIArray[] = TPHoraInicio.getValue().toString().split(":");
        String horaFArray[] = TPHoraFin.getValue().toString().split(":");
        int hI = Integer.parseInt(horaIArray[0]);
        int mI = Integer.parseInt(horaIArray[1]);
        int hF = Integer.parseInt(horaFArray[0]);
        int mF = Integer.parseInt(horaFArray[1]);
        if ((hF > (hI + 3)) || (hF == (hI + 3) && mF > mI)) {
            return true;
        }
        return false;
    }

    public boolean horarioMenor(JFXTimePicker TPHoraInicio,JFXTimePicker TPHoraFin) {
        String horaIArray[] = TPHoraInicio.getValue().toString().split(":");
        String horaFArray[] = TPHoraFin.getValue().toString().split(":");
        int hI = Integer.parseInt(horaIArray[0]);
        int mI = Integer.parseInt(horaIArray[1]);
        int hF = Integer.parseInt(horaFArray[0]);
        int mF = Integer.parseInt(horaFArray[1]);
        if ((hF < hI) || (hF == hI) || (hF == (hI + 1) && mF < mI)) {
            return true;
        }
        return false;
    }

    public boolean disponibilidadHorario(JFXTimePicker TPHoraInicio,JFXTimePicker TPHoraFin, ListView LVClases,ComboBox CBDia) {
        String h = TPHoraInicio.getValue().toString().concat("-").concat(TPHoraFin.getValue().toString());
        Clase clase = new Clase();
        List<Clase> listaClases = clase.obtenerListaDeClases();
        for (int i = 0; i < listaClases.size(); i++) {
            LVClases.getItems().add(listaClases.get(i).getNombre() + "  " + listaClases.get(i).getDia() + "  " + listaClases.get(i).getHora());
        }
        for (int i = 0; i < listaClases.size(); i++) {
            if (listaClases.get(i).getDia().equals(CBDia.getValue().toString())) {
                if (listaClases.get(i).getHora().equals(h)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean horarioPrudente(JFXTimePicker TPHoraInicio,JFXTimePicker TPHoraFin) {
        String horaIArray[] = TPHoraInicio.getValue().toString().split(":");
        String horaFArray[] = TPHoraFin.getValue().toString().split(":");
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
