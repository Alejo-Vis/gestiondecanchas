package canchas.persistencia;

import canchas.objetosNegocio.Usuario;
import canchas.objetosNegocio.Cliente;
import java.util.ArrayList;
import canchas.excepciones.PersistenciaException;

// clase responsable de guardar y buscar los usuarios del sistema
public class RegistroUsuarios {

    // lista donde guardamos todos los usuarios registrados
    private ArrayList<Usuario> usuarios;

    public RegistroUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    // registra un nuevo usuario verificando formato de datos y que no haya duplicados
    public void registrarUsuario(Usuario u) throws PersistenciaException {
        // el nombre no puede estar vacio
        if (u.getNombre().trim().isEmpty()) {
            throw new PersistenciaException("el nombre no puede estar vacio.");
        }

        // el apellido no puede estar vacio
        if (u.getApellido().trim().isEmpty()) {
            throw new PersistenciaException("el apellido no puede estar vacio.");
        }

        // el correo debe contener el simbolo @
        if (!u.getEmail().contains("@")) {
            throw new PersistenciaException("el correo no es valido, debe contener '@'.");
        }

        // el telefono debe tener exactamente 10 digitos y solo numeros
        String tel = u.getTelefono();
        if (tel.length() != 10) {
            throw new PersistenciaException("el telefono debe tener exactamente 10 digitos.");
        }
        for (int i = 0; i < tel.length(); i++) {
            if (!Character.isDigit(tel.charAt(i))) {
                throw new PersistenciaException("el telefono solo debe contener numeros, sin espacios ni guiones.");
            }
        }

        // la contrasena debe tener minimo 6 caracteres
        if (u.getContrasena().length() < 6) {
            throw new PersistenciaException("la contrasena debe tener al menos 6 caracteres.");
        }

        // convertimos la cedula a string con ceros a la izquierda si hace falta
        String cedulaStr = Long.toString(u.getCedula());
        while (cedulaStr.length() < 10) {
            cedulaStr = "0" + cedulaStr;
        }

        // validamos la cedula con el algoritmo ecuatoriano
        if (!validarCedula(cedulaStr)) {
            throw new PersistenciaException("la cedula ingresada no es valida.");
        }

        // verificamos que no exista otro usuario con la misma cedula o correo
        for (Usuario existente : usuarios) {
            if (existente.getCedula() == u.getCedula()) {
                throw new PersistenciaException("el usuario con esta cedula ya existe en el sistema.");
            }
            if (existente.getEmail().equals(u.getEmail())) {
                throw new PersistenciaException("el usuario con este correo ya existe en el sistema.");
            }
        }

        usuarios.add(u);
        System.out.println("usuario registrado: " + u.getNombre());
    }

    // algoritmo oficial para validar cedulas ecuatorianas
    // verifica la provincia, el tipo de persona y el digito verificador
    private boolean validarCedula(String cedula) {
        // debe tener exactamente 10 digitos
        if (cedula.length() != 10) return false;

        // todos deben ser digitos
        for (int i = 0; i < cedula.length(); i++) {
            if (!Character.isDigit(cedula.charAt(i))) return false;
        }

        // los dos primeros digitos son el codigo de provincia (01 a 24)
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) return false;

        // el tercer digito para personas naturales debe estar entre 0 y 5
        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito < 0 || tercerDigito > 5) return false;

        // aplicamos los coeficientes a los primeros 9 digitos
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            int resultado = digito * coeficientes[i];
            // si el resultado es mayor o igual a 10, le restamos 9
            if (resultado >= 10) resultado -= 9;
            suma += resultado;
        }

        // calculamos el digito esperado y lo comparamos con el decimo digito
        int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
        int residuo = suma % 10;
        int digitoEsperado = (residuo == 0) ? 0 : 10 - residuo;

        return digitoVerificador == digitoEsperado;
    }

    // busca un usuario en la lista por su id
    public Usuario buscarUsuario(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    // busca un usuario en la lista por su numero de cedula
    public Usuario buscarUsuarioPorCedula(long cedula) {
        for (Usuario u : usuarios) {
            if (u.getCedula() == cedula) {
                return u;
            }
        }
        return null;
    }

    // busca un usuario por id y verifica que sea un cliente antes de devolverlo
    public Cliente buscarCliente(int id) {
        Usuario u = buscarUsuario(id);
        if (u instanceof Cliente) {
            return (Cliente) u;
        }
        return null;
    }

    // busca un cliente usando su numero de cedula
    public Cliente buscarClientePorCedula(long cedula) {
        Usuario u = buscarUsuarioPorCedula(cedula);
        if (u instanceof Cliente) {
            return (Cliente) u;
        }
        return null;
    }

    // devuelve la lista completa de usuarios registrados
    public ArrayList<Usuario> obtenerTodos() {
        return usuarios;
    }
}