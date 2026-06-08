package canchas.interfaces;

// este contrato obliga a las clases que lo implementen a poder gestionar su estado de mantenimiento
// lo usa Cancha para habilitar o deshabilitar una cancha del sistema
public interface Mantenible {
    // pone el objeto en modo mantenimiento para que no se pueda usar
    void ponerEnMantenimiento();
    // vuelve a habilitar el objeto para que se pueda usar de nuevo
    void habilitar();
}