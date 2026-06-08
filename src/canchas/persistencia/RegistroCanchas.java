package canchas.persistencia;

import canchas.objetosNegocio.Cancha;
import java.util.ArrayList;
import canchas.excepciones.PersistenciaException;

// clase encargada de guardar y buscar las canchas del sistema
public class RegistroCanchas {

    // lista donde guardamos todas las canchas registradas
    private ArrayList<Cancha> canchas;

    public RegistroCanchas() {
        this.canchas = new ArrayList<>();
    }

    // registra una nueva cancha verificando que el nombre no este vacio y que no haya duplicados
    public void agregarCancha(Cancha c) throws PersistenciaException {
        // el nombre de la cancha no puede estar vacio
        if (c.getNombre().trim().isEmpty()) {
            throw new PersistenciaException("el nombre de la cancha no puede estar vacio.");
        }

        // verificamos que no haya otra cancha con el mismo nombre
        for (Cancha existente : canchas) {
            if (existente.getNombre().equals(c.getNombre())) {
                throw new PersistenciaException("ya existe una cancha con el nombre: " + c.getNombre());
            }
        }

        canchas.add(c);
        System.out.println("cancha agregada: " + c.getNombre());
    }

    // busca una cancha en la lista usando su id
    public Cancha buscarCancha(int id) {
        for (Cancha c : canchas) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // devuelve todas las canchas registradas
    public ArrayList<Cancha> obtenerTodas() {
        return canchas;
    }

    // elimina una cancha del registro si la encuentra por su id
    public boolean eliminarCancha(int id) {
        Cancha c = buscarCancha(id);
        if (c != null) {
            canchas.remove(c);
            System.out.println("cancha eliminada.");
            return true;
        }
        return false;
    }
}