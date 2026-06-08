package canchas.persistencia;

import canchas.objetosNegocio.Factura;
import java.util.ArrayList;

// clase que guarda todas las facturas generadas en el sistema
public class RegistroFacturas {

    // lista donde guardamos todas las facturas
    private ArrayList<Factura> facturas;

    public RegistroFacturas() {
        this.facturas = new ArrayList<>();
    }

    // agrega una nueva factura a la lista
    public void registrarFactura(Factura f) {
        facturas.add(f);
        System.out.println("factura registrada en el sistema: " + f.getNumeroFactura());
    }

    // devuelve todas las facturas registradas
    public ArrayList<Factura> obtenerTodas() {
        return facturas;
    }

    // devuelve solo las facturas que pertenecen a un cliente especifico
    public ArrayList<Factura> obtenerFacturasPorCliente(int idCliente) {
        ArrayList<Factura> resultado = new ArrayList<>();
        for (Factura f : facturas) {
            if (f.getReserva() != null && f.getReserva().getCliente() != null && f.getReserva().getCliente().getId() == idCliente) {
                resultado.add(f);
            }
        }
        return resultado;
    }
}