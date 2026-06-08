package canchas.objetosNegocio;

// guarda el detalle de lo que se cobra en la factura
// es como una linea de la factura que dice que se reservo y cuanto costo
public class DetalleFactura {
    private int id;
    private String descripcion; // nombre de la cancha que se reservo
    private int cantidadHoras; // cuantas horas se reservo
    private double precioUnitario; // precio por hora
    private double subtotal; // total sin iva

    // constructor que calcula el subtotal al momento de crearse
    public DetalleFactura(int id, String descripcion, int cantidadHoras, double precioUnitario) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidadHoras = cantidadHoras;
        this.precioUnitario = precioUnitario;
        this.subtotal = calcularSubtotal();
    }

    // multiplica las horas por el precio para sacar el subtotal
    public double calcularSubtotal() {
        return cantidadHoras * precioUnitario;
    }

    // metodos para obtener y modificar los datos del detalle
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getCantidadHoras() { return cantidadHoras; }
    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
        this.subtotal = calcularSubtotal();
    }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subtotal = calcularSubtotal();
    }
    public double getSubtotal() { return subtotal; }
}