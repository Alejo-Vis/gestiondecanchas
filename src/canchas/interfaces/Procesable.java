package canchas.interfaces;

// este contrato obliga a las clases que lo implementen a tener los metodos procesar y cancelar
// lo usan Reserva y Pago para confirmar o cancelar operaciones del sistema
public interface Procesable {
    // confirma o ejecuta la operacion
    boolean procesar();
    // deshace o cancela la operacion
    boolean cancelar();
}