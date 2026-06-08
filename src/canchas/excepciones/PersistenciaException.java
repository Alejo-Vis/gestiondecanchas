package canchas.excepciones;

// esta excepcion se lanza cuando hay un problema al guardar o buscar datos en el sistema
// por ejemplo, si un cliente ya existe o si una cancha no se encuentra
public class PersistenciaException extends Exception {

    // crea la excepcion con un mensaje que explica que salio mal
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }
}