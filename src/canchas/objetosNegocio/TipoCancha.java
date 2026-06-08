package canchas.objetosNegocio;

// clase que representa el tipo de cancha, por ejemplo futbol, voleibol o baloncesto
// cada tipo tiene su propio precio por hora y una descripcion
// usamos esto en lugar de crear una clase separada para cada deporte
public class TipoCancha {
    // contador para asignar un id unico a cada tipo que se registre
    private static int contadorTipos = 1;

    private int id;
    private String nombre;
    private String descripcion;
    private double precioHora;

    // constructor que crea un tipo de cancha y le asigna el id automaticamente
    public TipoCancha(String nombre, String descripcion, double precioHora) {
        this.id = contadorTipos++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioHora = precioHora;
    }

    public double getPrecioHora() { return precioHora; }
    public void setPrecioHora(double precioHora) { this.precioHora = precioHora; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return "TipoCancha{" + "id=" + id + ", nombre='" + nombre + "', precioHora=$" + precioHora + '}';
    }
}