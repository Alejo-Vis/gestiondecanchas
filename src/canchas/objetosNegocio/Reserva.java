package canchas.objetosNegocio;

import java.time.LocalDateTime;
import canchas.interfaces.Procesable;
import canchas.objetosServicio.EstadoReserva;
import canchas.objetosServicio.HorarioDisponible;

// clase principal del sistema, representa una reserva hecha por un cliente
// implementa Procesable para poder confirmar o cancelar la reserva
public class Reserva implements Procesable {
    private static int contadorReservas = 1; // autoincrementable

    private int id;
    private LocalDateTime fechaReserva;
    private EstadoReserva estado;
    private String observaciones;
    private DetalleReserva detalle; // datos del costo de esta reserva

    private HorarioDisponible horario; // fecha y hora que se reservo la cancha
    private Cliente cliente; // cliente que hizo esta reserva
    private Factura factura; // factura generada al confirmar el pago

    // constructor que crea la reserva en estado pendiente
    public Reserva(LocalDateTime fechaReserva, String observaciones, HorarioDisponible horario, Cliente cliente) {
        this.id = contadorReservas++;
        this.fechaReserva = fechaReserva;
        this.estado = EstadoReserva.PENDIENTE;
        this.observaciones = observaciones;
        this.horario = horario;
        this.cliente = cliente;
    }

    // cambia el estado de la reserva a confirmada
    public void confirmar() {
        this.estado = EstadoReserva.CONFIRMADA;
    }

    // confirma la reserva si todavia estaba pendiente
    @Override
    public boolean procesar() {
        if (this.estado == EstadoReserva.PENDIENTE) {
            this.confirmar();
            return true;
        }
        return false;
    }

    // cancela la reserva y libera el horario para que otro pueda reservar
    @Override
    public boolean cancelar() {
        if (this.estado == EstadoReserva.PENDIENTE || this.estado == EstadoReserva.CONFIRMADA) {
            this.estado = EstadoReserva.CANCELADA;
            // liberamos el horario para que la cancha quede disponible en ese bloque
            if (this.horario != null) {
                this.horario.setDisponible(true);
            }
            return true;
        }
        return false;
    }

    // cambia el horario de la reserva, libera el anterior y vuelve a estado pendiente
    public boolean reprogramar(HorarioDisponible nuevoHorario) {
        if (this.estado == EstadoReserva.PENDIENTE || this.estado == EstadoReserva.CONFIRMADA) {
            // liberamos el horario anterior antes de asignar el nuevo
            if (this.horario != null) {
                this.horario.setDisponible(true);
            }
            this.horario = nuevoHorario;
            this.estado = EstadoReserva.PENDIENTE;
            return true;
        }
        return false;
    }

    // devuelve el subtotal de la reserva usando el detalle
    public double calcularCostoTotal() {
        if (detalle != null) {
            return detalle.calcularSubtotal();
        }
        return 0.0;
    }

    // devuelve el estado actual de la reserva
    public EstadoReserva getEstado() { return estado; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public DetalleReserva getDetalle() { return detalle; }
    public void setDetalle(DetalleReserva detalle) { this.detalle = detalle; }
    public HorarioDisponible getHorario() { return horario; }
    public void setHorario(HorarioDisponible horario) { this.horario = horario; }
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
    public Cancha getCancha() { return horario != null ? horario.getCancha() : null; }
    public Cliente getCliente() { return cliente; }

    @Override
    public String toString() {
        return "Reserva{" + "id=" + id + ", fechaReserva=" + fechaReserva + ", estado=" + estado + '}';
    }
}