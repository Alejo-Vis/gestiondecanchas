package canchas.objetosServicio;

// define los posibles resultados de un pago dentro del sistema
public enum EstadoPago {
    PENDIENTE,   // el pago fue registrado pero todavia no se proceso
    COMPLETADO,  // el pago se realizo con exito
    REEMBOLSADO, // el pago fue devuelto al cliente
    FALLIDO      // el pago no se pudo completar por algun error
}