package canchas.fachada;

import canchas.objetosServicio.*;
import canchas.persistencia.RegistroCanchas;
import canchas.persistencia.RegistroReservas;
import canchas.persistencia.RegistroUsuarios;
import canchas.persistencia.RegistroTiposCanchas;
import canchas.persistencia.RegistroFacturas;
import canchas.objetosNegocio.*;
import canchas.excepciones.CanchaOcupadaException;
import canchas.excepciones.FachadaException;
import canchas.excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// clase principal que centraliza el acceso a todas las funciones del sistema
public class FachadaSistemaCanchas implements IFachadaSistemaCanchas {

    private RegistroCanchas registroCanchas;
    private RegistroUsuarios registroUsuarios;
    private RegistroReservas registroReservas;
    private RegistroFacturas registroFacturas;
    private RegistroTiposCanchas registroTipos;

    // constructor que inicializa todos los registros del sistema
    public FachadaSistemaCanchas() {
        this.registroCanchas = new RegistroCanchas();
        this.registroUsuarios = new RegistroUsuarios();
        this.registroReservas = new RegistroReservas();
        this.registroFacturas = new RegistroFacturas();
        this.registroTipos = new RegistroTiposCanchas();
        System.out.println("--- Sistema Inicializado (Vacio) ---");
    }

    // verifica las credenciales del cliente y lo devuelve si son correctas
    @Override
    public Cliente login(String email, String contrasena) throws FachadaException {
        // el correo y la contrasena no pueden venir vacios
        if (email.trim().isEmpty() || contrasena.trim().isEmpty()) {
            throw new FachadaException("El correo y la contrasena no pueden estar vacios.");
        }
        for (Usuario u : registroUsuarios.obtenerTodos()) {
            if (u.login(email, contrasena) && u instanceof Cliente) {
                System.out.println("Bienvenido, " + u.getNombre() + "!");
                return (Cliente) u;
            }
        }
        throw new FachadaException("Correo o contrasena incorrectos.");
    }

    // registra un nuevo tipo de cancha en el sistema
    @Override
    public void registrarTipoCancha(TipoCancha tipo) throws FachadaException {
        try {
            registroTipos.agregarTipo(tipo);
        } catch (PersistenciaException e) {
            throw new FachadaException(e.getMessage());
        }
    }

    // muestra en pantalla todos los tipos de cancha registrados
    @Override
    public void listarTiposCanchas() {
        System.out.println("\n--- Tipos de cancha registrados ---");
        ArrayList<TipoCancha> tipos = registroTipos.obtenerTodos();
        if (tipos.isEmpty()) {
            System.out.println("No hay tipos de cancha registrados.");
            return;
        }
        for (TipoCancha t : tipos) {
            System.out.println(t);
        }
    }

    // busca el tipo de cancha por id, crea la cancha y devuelve su id
    @Override
    public int registrarCancha(String nombre, String descripcion, int idTipo) throws FachadaException {
        TipoCancha tipo = registroTipos.buscarTipo(idTipo);
        if (tipo == null) {
            throw new FachadaException("No se encontro el tipo de cancha con id: " + idTipo + ". Registre un tipo primero.");
        }
        try {
            Cancha nuevaCancha = new Cancha(nombre, descripcion, EstadoCancha.DISPONIBLE, tipo);
            registroCanchas.agregarCancha(nuevaCancha);
            System.out.println("Cancha registrada con ID: " + nuevaCancha.getId());
            // devolvemos el id para que quien llame pueda usarlo si lo necesita
            return nuevaCancha.getId();
        } catch (PersistenciaException e) {
            throw new FachadaException(e.getMessage());
        }
    }

    // elimina una cancha del sistema por su id
    @Override
    public void eliminarCancha(int idCancha) throws FachadaException {
        if (!registroCanchas.eliminarCancha(idCancha)) {
            throw new FachadaException("No se encontro la cancha con id: " + idCancha);
        }
    }

    // muestra en pantalla todas las canchas registradas
    @Override
    public void listarCanchas() {
        System.out.println("\n--- Lista de Canchas ---");
        ArrayList<Cancha> canchas = registroCanchas.obtenerTodas();
        if (canchas.isEmpty()) {
            System.out.println("No hay canchas registradas.");
            return;
        }
        for (Cancha c : canchas) {
            System.out.println(c);
        }
    }

    // muestra todas las reservas del sistema, exclusivo para el administrador
    @Override
    public void verTodasReservas() {
        System.out.println("\n--- Todas las reservas del sistema ---");
        ArrayList<Reserva> todas = registroReservas.obtenerTodas();
        if (todas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        for (Reserva r : todas) {
            System.out.println("- Reserva " + r.getId()
                    + " | Cliente: " + r.getCliente().getNombre()
                    + " | Cancha: " + r.getCancha().getNombre()
                    + " | Estado: " + r.getEstado());
        }
    }

    // muestra todas las facturas generadas en el sistema
    @Override
    public void listarFacturas() {
        System.out.println("\n--- Facturas del sistema ---");
        ArrayList<Factura> facturas = registroFacturas.obtenerTodas();
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
            return;
        }
        for (Factura f : facturas) {
            if (f.getReserva() != null) {
                System.out.println(f.getNumeroFactura()
                        + " | Cliente: " + f.getReserva().getCliente().getNombre()
                        + " | Cancha: " + f.getReserva().getCancha().getNombre()
                        + " | Total: $" + f.getTotal());
            } else {
                System.out.println(f.getNumeroFactura() + " | Total: $" + f.getTotal());
            }
        }
    }

    // registra un nuevo cliente verificando que no haya duplicados y que los datos sean validos
    @Override
    public void registrarCliente(Cliente cliente) throws FachadaException {
        try {
            registroUsuarios.registrarUsuario(cliente);
        } catch (PersistenciaException e) {
            throw new FachadaException(e.getMessage());
        }
    }

    // muestra las canchas que estan disponibles para la fecha indicada
    @Override
    public void verDisponibilidad(LocalDate fecha) {
        System.out.println("\n--- Canchas disponibles para " + fecha + " ---");
        boolean hayDisponibles = false;
        for (Cancha c : registroCanchas.obtenerTodas()) {
            // usamos verificarDisponibilidad con hora 08:00 para saber si la cancha esta libre al inicio del dia
            if (c.verificarDisponibilidad(fecha, LocalTime.of(8, 0))) {
                System.out.println("ID: " + c.getId()
                        + " | " + c.getNombre()
                        + " | Tipo: " + c.getTipoCancha().getNombre()
                        + " | Precio/hora: $" + c.getTipoCancha().getPrecioHora());
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay canchas disponibles.");
        }
    }

    // maneja todo el proceso de hacer una reserva desde la validacion hasta la factura
    @Override
    public void hacerReserva(int idCliente, int idCancha, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
            throws FachadaException {
        Cliente cliente = registroUsuarios.buscarCliente(idCliente);
        Cancha cancha = registroCanchas.buscarCancha(idCancha);

        // verificamos que el cliente y la cancha existan antes de continuar
        if (cliente == null || cancha == null) {
            throw new FachadaException("Cliente o Cancha no existen en el sistema.");
        }

        // no se puede reservar en una fecha que ya paso
        if (fecha.isBefore(LocalDate.now())) {
            throw new FachadaException("No se puede hacer una reserva en una fecha pasada.");
        }

        // el centro deportivo solo abre a partir de las 8 de la manana
        if (horaInicio.isBefore(LocalTime.of(8, 0))) {
            throw new FachadaException("El centro deportivo abre a las 08:00.");
        }

        // el centro deportivo cierra a las 22:00
        if (horaFin.isAfter(LocalTime.of(22, 0))) {
            throw new FachadaException("El centro deportivo cierra a las 22:00. La reserva debe terminar antes.");
        }

        // la hora de inicio tiene que ser antes que la hora de fin
        if (!horaInicio.isBefore(horaFin)) {
            throw new FachadaException("La hora de inicio debe ser anterior a la hora de fin.");
        }

        try {
            // creamos la reserva y la agregamos al historial del cliente
            Reserva nuevaReserva = registroReservas.generarReserva(cliente, cancha, fecha, horaInicio, horaFin);
            cliente.agregarReserva(nuevaReserva);

            // calculamos el costo y confirmamos la reserva
            DetalleReserva detalle = new DetalleReserva(nuevaReserva.getId(),
                    nuevaReserva.getHorario().calcularDuracion(), cancha.getTipoCancha().getPrecioHora());
            nuevaReserva.setDetalle(detalle);
            nuevaReserva.procesar();

            // registramos el horario ocupado en la cancha para consultas de disponibilidad
            cancha.agregarHorarioOcupado(nuevaReserva.getHorario());

            // generamos la factura y el pago, enlazandolos entre si
            Factura factura = new Factura(LocalDateTime.now(), nuevaReserva);
            Pago pago = new Pago(nuevaReserva.getId(), factura.getTotal(), LocalDateTime.now(),
                    EstadoPago.COMPLETADO, "Efectivo", nuevaReserva);
            factura.setPago(pago);
            pago.setFactura(factura);
            registroFacturas.registrarFactura(factura);

            System.out.println("Reserva confirmada. " + factura.getNumeroFactura() + " generada por $" + factura.getTotal());

        } catch (CanchaOcupadaException e) {
            throw new FachadaException("No se pudo completar la reserva: " + e.getMessage());
        }
    }

    // cambia el horario de una reserva existente del cliente
    @Override
    public void reprogramarReserva(int idCliente, int idReserva, LocalDate nuevaFecha, LocalTime nuevaInicio, LocalTime nuevaFin)
            throws FachadaException {
        Cliente cliente = registroUsuarios.buscarCliente(idCliente);
        if (cliente == null) throw new FachadaException("Cliente no encontrado.");

        Reserva reserva = registroReservas.buscarReserva(idReserva);
        if (reserva == null || reserva.getCliente().getId() != idCliente) {
            throw new FachadaException("Reserva no encontrada para este cliente.");
        }

        // no se puede reprogramar a una fecha que ya paso
        if (nuevaFecha.isBefore(LocalDate.now())) {
            throw new FachadaException("No se puede reprogramar a una fecha pasada.");
        }

        // el centro deportivo cierra a las 22:00
        if (nuevaFin.isAfter(LocalTime.of(22, 0))) {
            throw new FachadaException("El centro deportivo cierra a las 22:00. La reserva debe terminar antes.");
        }

        // la hora de inicio tiene que ser antes que la de fin
        if (!nuevaInicio.isBefore(nuevaFin)) {
            throw new FachadaException("La hora de inicio debe ser anterior a la hora de fin.");
        }

        Cancha cancha = reserva.getCancha();
        HorarioDisponible nuevoHorario = new HorarioDisponible(
                registroReservas.generarIdHorario(), nuevaFecha, nuevaInicio, nuevaFin, true, cancha);

        if (cliente.reprogramarReserva(idReserva, nuevoHorario)) {
            // registramos el nuevo horario ocupado en la cancha
            // el horario anterior ya fue liberado dentro de reserva.reprogramar()
            cancha.agregarHorarioOcupado(nuevoHorario);
            System.out.println("Reserva " + idReserva + " reprogramada para " + nuevaFecha + " de " + nuevaInicio + " a " + nuevaFin);
        } else {
            throw new FachadaException("No se pudo reprogramar. Verifique el estado de la reserva.");
        }
    }

    // cancela una reserva del historial del cliente
    @Override
    public void cancelarReservaCliente(int idCliente, int idReserva) throws FachadaException {
        Cliente cliente = registroUsuarios.buscarCliente(idCliente);
        if (cliente == null) throw new FachadaException("Cliente no encontrado.");

        if (!cliente.cancelarReserva(idReserva)) {
            throw new FachadaException("No se pudo cancelar. Verifique el id o el estado de la reserva.");
        }
        // el horario quedo liberado automaticamente dentro de reserva.cancelar()
        System.out.println("Reserva " + idReserva + " cancelada exitosamente.");
    }

    // muestra las reservas y facturas de un cliente especifico
    @Override
    public void verMovimientos(int idCliente) throws FachadaException {
        Cliente cliente = registroUsuarios.buscarCliente(idCliente);
        if (cliente == null) throw new FachadaException("Cliente no encontrado.");

        System.out.println("\n--- Movimientos del Cliente: " + cliente.getNombre() + " ---");

        ArrayList<Reserva> reservas = registroReservas.obtenerReservasPorCliente(idCliente);
        if (reservas.isEmpty()) {
            System.out.println("El cliente no tiene reservaciones.");
        } else {
            for (Reserva r : reservas) {
                System.out.println("- Reserva " + r.getId()
                        + " | Fecha: " + r.getHorario().getFecha()
                        + " | Cancha: " + r.getCancha().getNombre()
                        + " | Estado: " + r.getEstado()
                        + " | Factura: " + (r.getFactura() != null ? r.getFactura().getNumeroFactura() : "Sin factura"));
            }
        }

        ArrayList<Factura> facturas = registroFacturas.obtenerFacturasPorCliente(idCliente);
        if (!facturas.isEmpty()) {
            System.out.println("\n--- Facturas del cliente ---");
            for (Factura f : facturas) {
                System.out.println("- " + f.getNumeroFactura() + " | Total: $" + f.getTotal());
            }
        }
    }
}