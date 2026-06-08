package canchas.objetosNegocio;

import java.time.LocalDateTime;

// clase que representa la factura generada al confirmar una reserva
// calcula el iva y el total de forma automatica al crearse
public class Factura {
    private static int contadorFacturas = 1000; // autoincrementable
    public static final double IVA_PORCENTAJE = 0.15; // constante del iva aplicado

    private int id;
    private LocalDateTime fechaEmision;
    private double subtotal;
    private double iva; // monto del iva en dolares, no el porcentaje
    private double total;
    private DetalleFactura detalle; // linea de detalle con lo que se reservo
    private Pago pago; // pago asociado a esta factura
    private Reserva reserva; // reserva que dio origen a esta factura

    // constructor principal que recibe la reserva y calcula todo automaticamente
    public Factura(LocalDateTime fechaEmision, Reserva reserva) {
        this.id = ++contadorFacturas;
        this.fechaEmision = fechaEmision;
        this.reserva = reserva;
        int horas = reserva.getHorario().calcularDuracion();
        double precioUnitario = reserva.getCancha().getTipoCancha().getPrecioHora();
        this.detalle = new DetalleFactura(reserva.getId(), "Reserva de cancha " + reserva.getCancha().getNombre(), horas, precioUnitario);
        this.subtotal = this.detalle.calcularSubtotal();
        // iva guarda el monto en dolares, no el porcentaje
        this.iva = this.subtotal * IVA_PORCENTAJE;
        this.total = calcularTotal();
        reserva.setFactura(this);
    }

    // constructor alternativo que recibe directamente el subtotal
    public Factura(LocalDateTime fechaEmision, double subtotal) {
        this.id = ++contadorFacturas;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        // iva guarda el monto en dolares, no el porcentaje
        this.iva = subtotal * IVA_PORCENTAJE;
        this.total = calcularTotal();
    }

    // muestra en pantalla el numero de esta factura
    public void generar() {
        System.out.println("Generando Factura: " + getNumeroFactura());
    }

    // el total es la suma del subtotal mas el monto del iva
    public double calcularTotal() {
        return subtotal + iva;
    }

    // devuelve el numero de factura en un formato legible
    public String getNumeroFactura() {
        return "FAC-" + id;
    }

    // metodos para obtener y modificar los datos de la factura
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
        this.iva = subtotal * IVA_PORCENTAJE;
        this.total = calcularTotal();
    }
    public double getIva() { return iva; }
    public void setIva(double iva) {
        this.iva = iva;
        this.total = calcularTotal();
    }
    public double getTotal() { return total; }
    public DetalleFactura getDetalle() { return detalle; }
    public void setDetalle(DetalleFactura detalle) { this.detalle = detalle; }
    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }
    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }
}