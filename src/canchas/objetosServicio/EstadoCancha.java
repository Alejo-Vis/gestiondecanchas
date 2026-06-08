package canchas.objetosServicio;

// define los posibles estados en los que puede estar una cancha
// usamos esto para no escribir texto libre como "disponible" o "en mantenimiento"
public enum EstadoCancha {
    DISPONIBLE,        // la cancha se puede reservar
    EN_MANTENIMIENTO,  // la cancha no esta disponible por arreglos o reparaciones
    OCUPADA            // la cancha ya tiene una reserva activa en ese momento
}