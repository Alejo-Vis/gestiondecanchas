package canchas;

import canchas.fachada.FachadaSistemaCanchas;
import canchas.fachada.IFachadaSistemaCanchas;
import canchas.objetosNegocio.Cliente;
import canchas.objetosNegocio.TipoCancha;
import canchas.excepciones.FachadaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IFachadaSistemaCanchas fachada = new FachadaSistemaCanchas();
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("\n=== SISTEMA DE GESTION DE CANCHAS ===");
            System.out.println("1. Menu Administrador");
            System.out.println("2. Menu Cliente");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1: menuAdministrador(scanner, fachada); break;
                case 2: menuCliente(scanner, fachada); break;
                case 3: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opcion no valida.");
            }
        }
        scanner.close();
    }

    // menu del administrador con todas las opciones de gestion
    private static void menuAdministrador(Scanner scanner, IFachadaSistemaCanchas fachada) {
        int op = 0;
        while (op != 7) {
            System.out.println("\n=== MENU ADMINISTRADOR ===");
            System.out.println("1. Registrar tipo de cancha");
            System.out.println("2. Registrar cancha");
            System.out.println("3. Eliminar cancha");
            System.out.println("4. Listar canchas");
            System.out.println("5. Ver todas las reservas");
            System.out.println("6. Listar facturas");
            System.out.println("7. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: registrarTipoCanchaMenu(scanner, fachada); break;
                case 2: registrarCanchaMenu(scanner, fachada); break;
                case 3: eliminarCanchaMenu(scanner, fachada); break;
                case 4: fachada.listarCanchas(); break;
                case 5: fachada.verTodasReservas(); break;
                case 6: fachada.listarFacturas(); break;
                case 7: break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    // menu del cliente: primero se registra o inicia sesion
    private static void menuCliente(Scanner scanner, IFachadaSistemaCanchas fachada) {
        int op = 0;
        while (op != 3) {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Iniciar sesion");
            System.out.println("3. Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: registrarClienteMenu(scanner, fachada); break;
                case 2: iniciarSesionMenu(scanner, fachada); break;
                case 3: break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    // pide correo y contrasena para iniciar sesion
    private static void iniciarSesionMenu(Scanner scanner, IFachadaSistemaCanchas fachada) {
        try {
            System.out.print("Correo: ");
            String email = scanner.nextLine();
            System.out.print("Contrasena: ");
            String contrasena = scanner.nextLine();

            // si las credenciales son correctas, entramos al menu con sesion activa
            Cliente cliente = fachada.login(email, contrasena);
            menuClienteConSesion(scanner, fachada, cliente.getId(), cliente.getNombre());

        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // menu principal del cliente una vez que ya inicio sesion
    // el id del cliente ya esta guardado, no hace falta pedirlo en cada opcion
    private static void menuClienteConSesion(Scanner scanner, IFachadaSistemaCanchas fachada, int clienteId, String nombreCliente) {
        int op = 0;
        while (op != 6) {
            System.out.println("\n=== MENU CLIENTE (" + nombreCliente + ") ===");
            System.out.println("1. Ver canchas disponibles");
            System.out.println("2. Hacer una reserva");
            System.out.println("3. Reprogramar reserva");
            System.out.println("4. Cancelar reserva");
            System.out.println("5. Ver mis movimientos");
            System.out.println("6. Cerrar sesion");
            System.out.print("Seleccione una opcion: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: verDisponibilidadMenu(scanner, fachada); break;
                case 2: hacerReservaMenu(scanner, fachada, clienteId); break;
                case 3: reprogramarReservaMenu(scanner, fachada, clienteId); break;
                case 4: cancelarReservaMenu(scanner, fachada, clienteId); break;
                case 5: verMovimientosMenu(scanner, fachada, clienteId); break;
                case 6: System.out.println("Sesion cerrada. Hasta luego, " + nombreCliente + "!"); break;
                default: System.out.println("Opcion no valida.");
            }
        }
    }

    // pide los datos para registrar un tipo de cancha
    private static void registrarTipoCanchaMenu(Scanner scanner, IFachadaSistemaCanchas fachada) {
        try {
            System.out.print("Nombre del tipo (ej. Futbol 11, Baloncesto): ");
            String nombre = scanner.nextLine();
            System.out.print("Descripcion (ej. Cesped sintetico, Parquet): ");
            String descripcion = scanner.nextLine();
            System.out.print("Precio por hora: ");
            double precio = Double.parseDouble(scanner.nextLine());

            TipoCancha tipo = new TipoCancha(nombre, descripcion, precio);
            fachada.registrarTipoCancha(tipo);
            System.out.println("Tipo de cancha registrado con ID: " + tipo.getId());
        } catch (NumberFormatException e) {
            System.out.println("X ERROR: El precio debe ser un numero valido.");
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // pide los datos para registrar una cancha usando un tipo ya registrado
    private static void registrarCanchaMenu(Scanner scanner, IFachadaSistemaCanchas fachada) {
        try {
            fachada.listarTiposCanchas();
            System.out.print("Ingrese el ID del tipo de cancha: ");
            int idTipo = Integer.parseInt(scanner.nextLine());
            System.out.print("Nombre de la cancha: ");
            String nombre = scanner.nextLine();
            System.out.print("Descripcion o ubicacion: ");
            String descripcion = scanner.nextLine();

            fachada.registrarCancha(nombre, descripcion, idTipo);
        } catch (NumberFormatException e) {
            System.out.println("X ERROR: Ingrese un numero valido.");
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // pide el id de la cancha a eliminar
    private static void eliminarCanchaMenu(Scanner scanner, IFachadaSistemaCanchas fachada) {
        try {
            fachada.listarCanchas();
            System.out.print("Ingrese el ID de la cancha a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            fachada.eliminarCancha(id);
        } catch (NumberFormatException e) {
            System.out.println("X ERROR: Ingrese un numero valido.");
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // pide los datos para registrar un cliente nuevo
    private static void registrarClienteMenu(Scanner scanner, IFachadaSistemaCanchas fachada) {
        try {
            System.out.print("Cedula (solo numeros): ");
            long cedula = Long.parseLong(scanner.nextLine());
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Correo (debe contener @): ");
            String email = scanner.nextLine();
            System.out.print("Telefono (10 digitos): ");
            String telefono = scanner.nextLine();
            System.out.print("Contrasena: ");
            String contrasena = scanner.nextLine();

            Cliente cliente = new Cliente(cedula, nombre, apellido, email, telefono, contrasena);
            fachada.registrarCliente(cliente);
            System.out.println("Cliente registrado con ID: " + cliente.getId());
        } catch (NumberFormatException e) {
            System.out.println("X ERROR: La cedula debe ser un numero valido.");
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // pide la fecha y muestra las canchas disponibles ese dia
    private static void verDisponibilidadMenu(Scanner scanner, IFachadaSistemaCanchas fachada) {
        try {
            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            fachada.verDisponibilidad(fecha);
        } catch (DateTimeParseException e) {
            System.out.println("X ERROR: Formato de fecha incorrecto. Use YYYY-MM-DD.");
        }
    }

    // pide los datos para hacer una reserva (el id del cliente ya viene de la sesion)
    private static void hacerReservaMenu(Scanner scanner, IFachadaSistemaCanchas fachada, int clienteId) {
        try {
            fachada.listarCanchas();
            System.out.print("ID de la Cancha: ");
            int idCancha = Integer.parseInt(scanner.nextLine());
            System.out.print("Fecha de la reserva (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            System.out.print("Hora de inicio (HH:MM): ");
            LocalTime inicio = LocalTime.parse(scanner.nextLine());
            System.out.print("Hora de fin (HH:MM): ");
            LocalTime fin = LocalTime.parse(scanner.nextLine());

            fachada.hacerReserva(clienteId, idCancha, fecha, inicio, fin);
        } catch (NumberFormatException e) {
            System.out.println("X ERROR: El ID debe ser un numero.");
        } catch (DateTimeParseException e) {
            System.out.println("X ERROR: Formato de fecha u hora incorrecto.");
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // pide los datos para reprogramar una reserva (el id del cliente viene de la sesion)
    private static void reprogramarReservaMenu(Scanner scanner, IFachadaSistemaCanchas fachada, int clienteId) {
        try {
            fachada.verMovimientos(clienteId);
            System.out.print("ID de la Reserva a reprogramar: ");
            int idReserva = Integer.parseInt(scanner.nextLine());
            System.out.print("Nueva fecha (YYYY-MM-DD): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine());
            System.out.print("Nueva hora de inicio (HH:MM): ");
            LocalTime inicio = LocalTime.parse(scanner.nextLine());
            System.out.print("Nueva hora de fin (HH:MM): ");
            LocalTime fin = LocalTime.parse(scanner.nextLine());

            fachada.reprogramarReserva(clienteId, idReserva, fecha, inicio, fin);
        } catch (NumberFormatException e) {
            System.out.println("X ERROR: El ID debe ser un numero.");
        } catch (DateTimeParseException e) {
            System.out.println("X ERROR: Formato de fecha u hora incorrecto.");
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // pide el id de la reserva a cancelar (el id del cliente viene de la sesion)
    private static void cancelarReservaMenu(Scanner scanner, IFachadaSistemaCanchas fachada, int clienteId) {
        try {
            fachada.verMovimientos(clienteId);
            System.out.print("ID de la Reserva a cancelar: ");
            int idReserva = Integer.parseInt(scanner.nextLine());
            fachada.cancelarReservaCliente(clienteId, idReserva);
        } catch (NumberFormatException e) {
            System.out.println("X ERROR: El ID debe ser un numero.");
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }

    // muestra las reservas y facturas del cliente con sesion activa
    private static void verMovimientosMenu(Scanner scanner, IFachadaSistemaCanchas fachada, int clienteId) {
        try {
            fachada.verMovimientos(clienteId);
        } catch (FachadaException e) {
            System.out.println("X ERROR: " + e.getMessage());
        }
    }
}