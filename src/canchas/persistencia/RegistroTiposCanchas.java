package canchas.persistencia;

import canchas.objetosNegocio.TipoCancha;
import java.util.ArrayList;
import canchas.excepciones.PersistenciaException;

// clase que guarda y gestiona los tipos de cancha disponibles en el sistema
public class RegistroTiposCanchas {

    // lista donde guardamos todos los tipos registrados
    private ArrayList<TipoCancha> tipos;

    public RegistroTiposCanchas() {
        this.tipos = new ArrayList<>();
    }

    // registra un nuevo tipo verificando que los datos sean correctos y no haya duplicados
    public void agregarTipo(TipoCancha t) throws PersistenciaException {
        // el nombre del tipo no puede estar vacio
        if (t.getNombre().trim().isEmpty()) {
            throw new PersistenciaException("el nombre del tipo de cancha no puede estar vacio.");
        }

        // el precio por hora debe ser mayor a cero
        if (t.getPrecioHora() <= 0) {
            throw new PersistenciaException("el precio por hora debe ser mayor a cero.");
        }

        // verificamos que no exista otro tipo con el mismo nombre
        for (TipoCancha existente : tipos) {
            if (existente.getNombre().equalsIgnoreCase(t.getNombre())) {
                throw new PersistenciaException("ya existe un tipo de cancha con el nombre: " + t.getNombre());
            }
        }

        tipos.add(t);
        System.out.println("tipo de cancha registrado: " + t.getNombre());
    }

    // busca un tipo por su id
    public TipoCancha buscarTipo(int id) {
        for (TipoCancha t : tipos) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    // devuelve todos los tipos registrados
    public ArrayList<TipoCancha> obtenerTodos() {
        return tipos;
    }

    // elimina un tipo si lo encuentra por su id
    public boolean eliminarTipo(int id) {
        TipoCancha t = buscarTipo(id);
        if (t != null) {
            tipos.remove(t);
            System.out.println("tipo de cancha eliminado.");
            return true;
        }
        return false;
    }
}