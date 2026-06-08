package canchas.excepciones;

// esta excepcion se lanza cuando se intenta reservar una cancha en un horario que ya esta ocupado
// o cuando los datos del horario no son validos para crear la reserva
public class CanchaOcupadaException extends Exception {

    // crea la excepcion con un mensaje que explica el problema
    public CanchaOcupadaException(String mensaje) {
        super(mensaje);
    }
}