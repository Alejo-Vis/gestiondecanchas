package canchas.objetosNegocio;

import java.time.LocalDate;
import java.util.ArrayList;
import canchas.objetosServicio.HorarioDisponible;

// clase que representa a los clientes registrados en el sistema
// hereda de Usuario y agrega el historial de reservas
public class Cliente extends Usuario {

    // lista donde guardamos todas las reservas que ha hecho este cliente
    private ArrayList<Reserva> historialReservas;

    // constructor que inicializa el cliente con todos sus datos
    public Cliente(long cedula, String nombre, String apellido, String email, String telefono, String contrasena) {
        super(cedula, nombre, apellido, email, telefono, contrasena);
        this.historialReservas = new ArrayList<>(); // se inicializa el historial vacio
    }

    // hace una reserva y la agrega al historial del cliente
    public void hacerReserva(Reserva reserva) {
        historialReservas.add(reserva);
        System.out.println("reserva agregada al historial del cliente " + nombre);
    }

    // agrega una nueva reserva al historial del cliente
    public void agregarReserva(Reserva r) {
        historialReservas.add(r);
    }

    // busca una reserva en el historial y la cancela si la encuentra
    public boolean cancelarReserva(int idReserva) {
        for (Reserva r : historialReservas) {
            if (r.getId() == idReserva) {
                return r.cancelar();
            }
        }
        return false;
    }

    // busca una reserva en el historial y la reprograma con el nuevo horario
    public boolean reprogramarReserva(int idReserva, HorarioDisponible nuevoHorario) {
        for (Reserva r : historialReservas) {
            if (r.getId() == idReserva) {
                return r.reprogramar(nuevoHorario);
            }
        }
        return false;
    }

    // devuelve las reservas del cliente para una fecha especifica
    public ArrayList<Reserva> verDisponibilidad(LocalDate fecha) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva r : historialReservas) {
            if (r.getHorario() != null && r.getHorario().getFecha() != null
                    && r.getHorario().getFecha().equals(fecha)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    // devuelve la lista completa de reservas del cliente
    public ArrayList<Reserva> verHistorial() {
        return historialReservas;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre='" + nombre + ' ' + apellido + "'}";
    }
}