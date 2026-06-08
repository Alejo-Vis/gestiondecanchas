package canchas.objetosNegocio;

import java.time.LocalDate;

// clase base de la que heredan Cliente y Administrador
// tiene los datos comunes que cualquier usuario necesita para acceder al sistema
public abstract class Usuario {
    // este contador asigna un id unico a cada usuario que se registra
    protected static int contadorUsuarios = 1;

    protected int id;
    protected long cedula;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String telefono;
    protected String contrasena;
    protected LocalDate fechaRegistro;

    // constructor que inicializa los datos y asigna el id automaticamente
    public Usuario(long cedula, String nombre, String apellido, String email, String telefono, String contrasena) {
        this.id = contadorUsuarios++;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.fechaRegistro = LocalDate.now();
    }

    // verifica si el email y la contrasena coinciden con los del usuario
    public boolean login(String email, String pass) {
        return this.email.equals(email) && this.contrasena.equals(pass);
    }

    // muestra un mensaje cuando el usuario cierra sesion
    public void logout() {
        System.out.println("El usuario " + this.nombre + " ha cerrado sesion.");
    }

    // actualiza el telefono y la contrasena del usuario
    public void actualizarPerfil(String telefono, String contrasena) {
        this.telefono = telefono;
        this.contrasena = contrasena;
        System.out.println("Perfil actualizado con exito.");
    }

    // metodos para obtener y modificar los datos del usuario
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public long getCedula() { return cedula; }
    public void setCedula(long cedula) { this.cedula = cedula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}