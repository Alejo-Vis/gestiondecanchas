package canchas.persistencia;

import canchas.objetosNegocio.Cancha;
import canchas.objetosNegocio.Cliente;
import canchas.objetosServicio.HorarioDisponible;
import canchas.objetosNegocio.Reserva;
import canchas.excepciones.CanchaOcupadaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// clase que maneja el registro de reservas
public class RegistroReservas {

    // aqui guardamos todas las reservas del sistema
    private ArrayList<Reserva> reservas;

    // contador para asignar ids a los horarios
    private int contadorHorarios = 1;

    public RegistroReservas() {
        this.reservas = new ArrayList<>();
    }

    // crea una nueva reserva y la agrega a la lista
    public Reserva generarReserva(Cliente cliente, Cancha cancha, LocalDate fecha, LocalTime inicio, LocalTime fin) throws CanchaOcupadaException {
        // verificamos que los datos no vengan vacios
        if (inicio == null || fin == null || fecha == null) {
            throw new CanchaOcupadaException("fecha y horario invalidos para la reserva.");
        }

        // la hora de inicio tiene que ser antes que la de fin
        if (!inicio.isBefore(fin)) {
            throw new CanchaOcupadaException("la hora de inicio debe ser anterior a la hora de fin.");
        }

        // creamos el horario con los datos recibidos
        HorarioDisponible horario = new HorarioDisponible(contadorHorarios++, fecha, inicio, fin, true, cancha);

        // revisamos que no haya otra reserva en ese mismo horario
        for (Reserva existente : reservas) {
            if (existente.getHorario().getCancha().getId() == cancha.getId()
                    && existente.getHorario().getFecha().equals(fecha)
                    && horariosSeTraslapan(existente.getHorario(), horario)) {
                throw new CanchaOcupadaException("la cancha ya tiene una reserva en ese horario.");
            }
        }

        horario.marcarOcupado();
        Reserva nuevaReserva = new Reserva(LocalDateTime.now(), "reserva gestionada por sistema", horario, cliente);
        reservas.add(nuevaReserva);
        return nuevaReserva;
    }

    // genera un id nuevo para un horario sin agregar una reserva al sistema
    public int generarIdHorario() {
        return contadorHorarios++;
    }

    // devuelve las reservas de un cliente especifico
    public ArrayList<Reserva> obtenerReservasPorCliente(int idCliente) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getCliente() != null && r.getCliente().getId() == idCliente) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    // revisa si dos horarios se cruzan entre si
    private boolean horariosSeTraslapan(HorarioDisponible existente, HorarioDisponible nuevo) {
        if (!existente.getFecha().equals(nuevo.getFecha())) {
            return false;
        }
        return nuevo.getHoraInicio().isBefore(existente.getHoraFin()) && nuevo.getHoraFin().isAfter(existente.getHoraInicio());
    }

    // busca una reserva por su id
    public Reserva buscarReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    // devuelve todas las reservas
    public ArrayList<Reserva> obtenerTodas() {
        return reservas;
    }
}