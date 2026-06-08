package canchas.objetosNegocio;

// guarda los datos de cuanto vale una reserva especifica
// calcula el subtotal a partir de las horas reservadas y el precio por hora
public class DetalleReserva {
    private int id;
    private int cantidadHoras; // cuantas horas se reservo la cancha
    private double precioUnitario; // precio por hora del tipo de cancha
    private double subtotal; // resultado de multiplicar horas por precio

    // constructor que recibe los datos y calcula el subtotal automaticamente
    public DetalleReserva(int id, int cantidadHoras, double precioUnitario) {
        this.id = id;
        this.cantidadHoras = cantidadHoras;
        this.precioUnitario = precioUnitario;
        this.subtotal = calcularSubtotal();
    }

    // multiplica las horas por el precio para obtener el subtotal
    public double calcularSubtotal() {
        return cantidadHoras * precioUnitario;
    }

    // metodos para obtener y modificar los datos del detalle
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
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