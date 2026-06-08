package canchas.objetosServicio;

import canchas.objetosNegocio.Cancha;

import java.time.LocalDate;
import java.time.LocalTime;

// clase que representa un bloque de tiempo disponible para una cancha
// guarda la fecha, hora de inicio, hora de fin y si el bloque esta libre o no
public class HorarioDisponible {
    private int id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean disponible; // true si el horario esta libre, false si ya fue reservado
    private Cancha cancha; // cancha a la que pertenece este horario

    // constructor principal
    public HorarioDisponible(int id, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, boolean disponible, Cancha cancha) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
        this.cancha = cancha;
    }

    // calcula la duracion restando la hora de fin menos la de inicio
    public int calcularDuracion() {
        int horaInicial = this.horaInicio.getHour();
        int horaFinal = this.horaFin.getHour();
        return horaFinal - horaInicial;
    }

    // marca el horario como ocupado cuando se confirma una reserva
    public void marcarOcupado() {
        this.disponible = false;
    }

    // dice si el horario todavia esta libre para reservar
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // metodos para obtener y modificar los datos del horario
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Cancha getCancha() { return cancha; }
    public void setCancha(Cancha cancha) { this.cancha = cancha; }

    @Override
    public String toString() {
        return "HorarioDisponible{" +
                "fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", disponible=" + disponible +
                '}';
    }
}