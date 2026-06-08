package canchas.objetosNegocio;

import java.time.LocalDateTime;
import canchas.interfaces.Procesable;
import canchas.objetosServicio.EstadoPago;

// clase que representa el pago de una reserva
// implementa Procesable para poder confirmar o cancelar el cobro
public class Pago implements Procesable {
    private int id;
    private double monto; // valor total que se cobra
    private LocalDateTime fecha;
    private EstadoPago estado;
    private String tipoPago; // ej: tarjeta, efectivo, transferencia
    private Reserva reserva; // reserva a la que corresponde este pago
    private Factura factura; // factura que se genero con este pago

    // constructor principal
    public Pago(int id, double monto, LocalDateTime fecha, EstadoPago estado, String tipoPago, Reserva reserva) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.estado = estado;
        this.tipoPago = tipoPago;
        this.reserva = reserva;
    }

    // confirma el pago si estaba pendiente y el monto es mayor a cero
    @Override
    public boolean procesar() {
        if (this.estado == EstadoPago.PENDIENTE && this.monto > 0) {
            this.estado = EstadoPago.COMPLETADO;
            return true;
        }
        return false;
    }

    // genera un texto con el resumen del pago para mostrarlo como comprobante
    public String generarComprobante() {
        return "Comprobante de Pago ID: " + id + " - Monto: $" + monto + " - Estado: " + estado;
    }

    // cancela el pago y lo marca como reembolsado
    @Override
    public boolean cancelar() {
        if (this.estado == EstadoPago.PENDIENTE || this.estado == EstadoPago.COMPLETADO) {
            this.estado = EstadoPago.REEMBOLSADO;
            return true;
        }
        return false;
    }

    // metodos para obtener y modificar los datos del pago
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }
    public String getTipoPago() { return tipoPago; }
    public void setTipoPago(String tipoPago) { this.tipoPago = tipoPago; }
    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
}