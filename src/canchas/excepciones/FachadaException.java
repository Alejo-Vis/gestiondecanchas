package canchas.excepciones;

// esta excepcion la usa la fachada para envolver los errores del sistema
// de esta forma el main solo necesita capturar un tipo de excepcion
public class FachadaException extends Exception {

    // crea la excepcion con un mensaje de error
    public FachadaException(String mensaje) {
        super(mensaje);
    }
}