package canchas;

import canchas.fachada.FachadaSistemaCanchas;
import canchas.fachada.IFachadaSistemaCanchas;
import canchas.objetosNegocio.Cliente;
import canchas.objetosNegocio.TipoCancha;
import canchas.excepciones.FachadaException;
import java.time.LocalDate;
import java.time.LocalTime;

// clase de prueba del sistema de canchas
// carga datos reales y verifica que todo funcione correctamente
public class PruebaSistemaCanchas {

    public static void main(String[] args) {
        System.out.println("=== iniciando prueba del sistema de gestion de canchas ===");

        try {
            IFachadaSistemaCanchas sistema = new FachadaSistemaCanchas();

            // --- 1. registramos los tipos de cancha disponibles ---
            System.out.println("\n[1] registrando tipos de cancha...");
            TipoCancha tipoFutbol = new TipoCancha("Futbol 11", "Cesped sintetico con arcos reglamentarios", 20.0);
            TipoCancha tipoBaloncesto = new TipoCancha("Baloncesto", "Parquet de madera con aros reglamentarios", 15.0);
            TipoCancha tipoEcuavoley = new TipoCancha("Ecuavoley", "Cemento pulido con red reglamentaria", 10.0);

            sistema.registrarTipoCancha(tipoFutbol);
            sistema.registrarTipoCancha(tipoBaloncesto);
            sistema.registrarTipoCancha(tipoEcuavoley);

            // --- 2. registramos las canchas usando el id del tipo ---
            System.out.println("\n[2] registrando canchas...");
            int idCanchaA = sistema.registrarCancha("Cancha A", "Zona norte del complejo deportivo", tipoFutbol.getId());
            int idCanchaB = sistema.registrarCancha("Cancha B", "Zona sur del complejo deportivo", tipoFutbol.getId());
            int idCanchaC = sistema.registrarCancha("Cancha C", "Interior techado, climatizado", tipoBaloncesto.getId());
            int idCanchaD = sistema.registrarCancha("Cancha D", "Exterior, junto al parqueadero", tipoEcuavoley.getId());

            // --- 3. registramos los clientes de prueba ---
            // cedulas verificadas con el algoritmo ecuatoriano
            System.out.println("\n[3] registrando clientes...");
            Cliente c1 = new Cliente(1710034065L, "Juan", "Perez", "juan.perez@gmail.com", "0991234567", "pass123");
            Cliente c2 = new Cliente(1713175071L, "Maria", "Lopez", "maria.lopez@gmail.com", "0987654321", "pass456");

            sistema.registrarCliente(c1);
            sistema.registrarCliente(c2);

            // --- 4. hacemos reservas en fechas futuras ---
            // usamos plusDays para que siempre sean fechas futuras sin importar cuando se corra la prueba
            System.out.println("\n[4] realizando reservas...");
            LocalDate fechaProxima = LocalDate.now().plusDays(3);
            LocalDate fechaSiguiente = LocalDate.now().plusDays(5);

            // juan reserva la cancha A de 9 a 11
            sistema.hacerReserva(c1.getId(), idCanchaA, fechaProxima, LocalTime.of(9, 0), LocalTime.of(11, 0));

            // maria reserva la cancha B el mismo dia de 10 a 12
            sistema.hacerReserva(c2.getId(), idCanchaB, fechaProxima, LocalTime.of(10, 0), LocalTime.of(12, 0));

            // juan reserva la cancha C para otro dia de 14 a 16
            sistema.hacerReserva(c1.getId(), idCanchaC, fechaSiguiente, LocalTime.of(14, 0), LocalTime.of(16, 0));

            // --- 5. probamos que no se pueda reservar un horario ocupado ---
            System.out.println("\n[5] probando validacion de horario duplicado...");
            try {
                // este intento debe fallar porque la cancha A ya tiene reserva de 9 a 11
                sistema.hacerReserva(c2.getId(), idCanchaA, fechaProxima, LocalTime.of(9, 30), LocalTime.of(11, 30));
                System.out.println("ERROR: debia haber lanzado excepcion por horario ocupado");
            } catch (FachadaException e) {
                System.out.println("validacion correcta -> " + e.getMessage());
            }

            // --- 6. probamos otras validaciones del sistema ---
            System.out.println("\n[6] probando otras validaciones...");

            // no se puede reservar en una fecha pasada
            try {
                sistema.hacerReserva(c1.getId(), idCanchaD, LocalDate.now().minusDays(1), LocalTime.of(10, 0), LocalTime.of(12, 0));
                System.out.println("ERROR: debia rechazar fecha pasada");
            } catch (FachadaException e) {
                System.out.println("validacion correcta -> " + e.getMessage());
            }

            // no se puede reservar despues de las 22:00
            try {
                sistema.hacerReserva(c1.getId(), idCanchaD, fechaProxima, LocalTime.of(20, 0), LocalTime.of(23, 0));
                System.out.println("ERROR: debia rechazar hora fuera del horario");
            } catch (FachadaException e) {
                System.out.println("validacion correcta -> " + e.getMessage());
            }

            // --- 7. probamos el login ---
            System.out.println("\n[7] probando inicio de sesion...");
            Cliente loginOk = sistema.login("juan.perez@gmail.com", "pass123");
            System.out.println("login exitoso: " + loginOk.getNombre() + " (ID: " + loginOk.getId() + ")");

            try {
                sistema.login("juan.perez@gmail.com", "claveincorrecta");
                System.out.println("ERROR: debia rechazar credenciales incorrectas");
            } catch (FachadaException e) {
                System.out.println("validacion correcta -> " + e.getMessage());
            }

            // --- 8. mostramos el estado final del sistema ---
            System.out.println("\n[8] estado final del sistema:");
            sistema.listarTiposCanchas();
            sistema.listarCanchas();
            sistema.verTodasReservas();
            sistema.listarFacturas();

            System.out.println("\n[9] movimientos de juan perez:");
            sistema.verMovimientos(c1.getId());

            System.out.println("\n[10] movimientos de maria lopez:");
            sistema.verMovimientos(c2.getId());

            System.out.println("\n=== prueba completada exitosamente ===");

        } catch (FachadaException e) {
            System.out.println("error inesperado en la prueba: " + e.getMessage());
        }
    }
}