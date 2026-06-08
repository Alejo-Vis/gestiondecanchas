package canchas.objetosNegocio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import canchas.interfaces.Mantenible;
import canchas.objetosServicio.EstadoCancha;
import canchas.objetosServicio.HorarioDisponible;

// clase que representa una cancha fisica del sistema
// implementa Mantenible para poder cambiar su estado de disponibilidad facilmente
public class Cancha implements Mantenible {
    // este contador asigna un id unico a cada cancha que se registra
    private static int contadorCanchas = 1;

    private int id;
    private String nombre;
    private String descripcion;
    private EstadoCancha estado;
    private TipoCancha tipoCancha; // tipo al que pertenece esta cancha, por ejemplo futbol

    // lista de horarios que ya fueron reservados en esta cancha
    private ArrayList<HorarioDisponible> horariosOcupados;

    // constructor que crea una cancha y le asigna un id automaticamente
    public Cancha(String nombre, String descripcion, EstadoCancha estado, TipoCancha tipoCancha) {
        this.id = contadorCanchas++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tipoCancha = tipoCancha;
        this.horariosOcupados = new ArrayList<>(); // empieza sin horarios ocupados
    }

    // revisa si la cancha esta libre para la fecha y hora pedidas
    // primero verifica el estado general y luego revisa si hay una reserva que se cruce
    public boolean verificarDisponibilidad(LocalDate fecha, LocalTime hora) {
        // si la cancha esta en mantenimiento u ocupada en general, no esta disponible
        if (this.estado != EstadoCancha.DISPONIBLE) return false;

        // revisamos si la hora pedida cae dentro de alguna reserva activa
        for (HorarioDisponible h : horariosOcupados) {
            if (h.getFecha().equals(fecha) && !h.isDisponible()) {
                // la hora solicitada esta dentro de este horario reservado
                if (!hora.isBefore(h.getHoraInicio()) && hora.isBefore(h.getHoraFin())) {
                    return false;
                }
            }
        }
        return true;
    }

    // agrega un horario a la lista de horarios ocupados de esta cancha
    public void agregarHorarioOcupado(HorarioDisponible h) {
        horariosOcupados.add(h);
    }

    // cambia el estado de la cancha al valor que le pasamos
    public void actualizarEstado(EstadoCancha estado) {
        this.estado = estado;
    }

    // pone la cancha en mantenimiento para que no se pueda reservar
    @Override
    public void ponerEnMantenimiento() {
        this.estado = EstadoCancha.EN_MANTENIMIENTO;
        System.out.println("Cancha " + this.nombre + " puesta en mantenimiento.");
    }

    // vuelve a poner la cancha como disponible despues del mantenimiento
    @Override
    public void habilitar() {
        this.estado = EstadoCancha.DISPONIBLE;
        System.out.println("Cancha " + this.nombre + " habilitada para reservas.");
    }

    // calcula cuanto cuesta reservar la cancha por una cantidad de horas
    public double getPrecioTotal(int horas) {
        if (tipoCancha != null) {
            return tipoCancha.getPrecioHora() * horas;
        }
        return 0.0;
    }

    // metodos para obtener y modificar los datos de la cancha
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public EstadoCancha getEstado() { return estado; }
    public void setEstado(EstadoCancha estado) { this.estado = estado; }
    public TipoCancha getTipoCancha() { return tipoCancha; }
    public void setTipoCancha(TipoCancha tipoCancha) { this.tipoCancha = tipoCancha; }

    @Override
    public String toString() {
        return "Cancha{" + "id=" + id + ", nombre='" + nombre + "', estado=" + estado
                + ", tipoCancha=" + (tipoCancha != null ? tipoCancha.getNombre() : "Ninguno") + '}';
    }
}