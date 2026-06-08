package canchas.fachada;

import canchas.excepciones.FachadaException;
import canchas.objetosNegocio.Cliente;
import canchas.objetosNegocio.TipoCancha;
import java.time.LocalDate;
import java.time.LocalTime;

// interfaz que define los metodos que debe tener la fachada del sistema
public interface IFachadaSistemaCanchas {

    // verifica las credenciales y devuelve el cliente si son correctas
    Cliente login(String email, String contrasena) throws FachadaException;

    // operaciones de administrador
    void registrarTipoCancha(TipoCancha tipo) throws FachadaException;
    void listarTiposCanchas();
    // devuelve el id de la cancha creada para poder usarlo despues
    int registrarCancha(String nombre, String descripcion, int idTipo) throws FachadaException;
    void eliminarCancha(int idCancha) throws FachadaException;
    void listarCanchas();
    void verTodasReservas();
    void listarFacturas();

    // operaciones de cliente
    void registrarCliente(Cliente cliente) throws FachadaException;
    void verDisponibilidad(LocalDate fecha);
    void hacerReserva(int idCliente, int idCancha, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) throws FachadaException;
    void reprogramarReserva(int idCliente, int idReserva, LocalDate nuevaFecha, LocalTime nuevaInicio, LocalTime nuevaFin) throws FachadaException;
    void cancelarReservaCliente(int idCliente, int idReserva) throws FachadaException;
    void verMovimientos(int idCliente) throws FachadaException;
}