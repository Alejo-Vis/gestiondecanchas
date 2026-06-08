package canchas.objetosNegocio;

// clase que representa a los administradores del sistema
// hereda de Usuario y agrega el nivel de acceso y los metodos de gestion
public class Administrador extends Usuario {
    // ej: 1=basico, 2=total
    private int nivelAcceso;

    // constructor que recibe todos los datos del administrador
    public Administrador(long cedula, String nombre, String apellido, String email, String telefono, String contrasena, int nivelAcceso) {
        super(cedula, nombre, apellido, email, telefono, contrasena);
        this.nivelAcceso = nivelAcceso;
    }

    // solicita al sistema agregar una nueva cancha
    // la fachada es quien valida y ejecuta la operacion en el registro
    public void agregarCancha(Cancha cancha) {
        System.out.println("admin " + nombre + " solicita agregar cancha: " + cancha.getNombre());
    }

    // solicita al sistema eliminar una cancha por su id
    public boolean eliminarCancha(int idCancha) {
        System.out.println("admin " + nombre + " solicita eliminar cancha con id: " + idCancha);
        return true;
    }

    // solicita al sistema actualizar los datos de una cancha
    public void editarCancha(Cancha cancha) {
        System.out.println("admin " + nombre + " solicita editar cancha: " + cancha.getNombre());
    }

    // solicita al sistema mostrar todas las reservas registradas
    public void verTodasReservas() {
        System.out.println("admin " + nombre + " consulta todas las reservas del sistema.");
    }

    // genera un reporte general del sistema
    public void generarReporte() {
        System.out.println("admin " + nombre + " genera reporte del sistema.");
    }

    // solicita al sistema gestionar los horarios de una cancha especifica
    public void gestionarHorarios(Cancha cancha) {
        System.out.println("admin " + nombre + " gestiona horarios de: " + cancha.getNombre());
    }

    // solicita al sistema cancelar una reserva especifica
    public boolean cancelarReserva(int idReserva) {
        System.out.println("admin " + nombre + " solicita cancelar reserva con id: " + idReserva);
        return true;
    }

    public int getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(int nivelAcceso) { this.nivelAcceso = nivelAcceso; }

    @Override
    public String toString() {
        return "Administrador{" + "id=" + id + ", nombre='" + nombre + ' ' + apellido + "', nivelAcceso=" + nivelAcceso + '}';
    }
}