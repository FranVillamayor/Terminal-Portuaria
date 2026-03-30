package factura;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import orden.Orden;
import servicio.Servicio;
import viaje.Viaje;

public class Factura {

    private Orden orden;
    private Set<ConceptoServicio> conceptos;

    public Factura(ArrayList <Servicio> servicios,Orden orden) {
        this.orden = orden;
        this.conceptos = new LinkedHashSet<>();

        // Agregar cada servicio como un concepto individual
        for (Servicio servicio : orden.getServicios()) {
            ConceptoServicio concepto = new ConceptoServicio(
                servicio.getNombreServicio(),
                servicio.getFechaServicio(),
                servicio.costoServicio(orden)
            );
            this.conceptos.add(concepto);
        }

        // Subtotal de servicios
        this.conceptos.add(new ConceptoServicio(
            "Subtotal servicios", 
            LocalDate.now(),
            orden.montoTotalServicios()
        ));

        // Si la orden tiene un viaje asociado, se agrega su costo
        Viaje viaje = orden.getViaje();
        if (viaje != null) {
            this.conceptos.add(new ConceptoServicio(
                "Costo total del viaje",
                LocalDate.now(),
                viaje.precioFinal()
            ));
        }

        // Total general
        double total = orden.montoTotalServicios()
                      + (viaje != null ? viaje.precioFinal() : 0);

        this.conceptos.add(new ConceptoServicio(
            "TOTAL A PAGAR",
            LocalDate.now(),
            total
        ));
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String desgloseDeServicios() {
        StringBuilder sb = new StringBuilder();
        sb.append("FACTURA\n");
        sb.append("--------------------------------\n");
      //  sb.append("Orden: ").append(orden.getId()).append("\n");
        sb.append("Fecha de emisión: ").append(LocalDateTime.now()).append("\n");
        sb.append("--------------------------------\n");

        for (ConceptoServicio c : conceptos) {
            sb.append(String.format("%-25s | %10s | $%.2f\n",
                    c.getNombreServicio(),
                    c.getFechaEmision(),
                    c.getMonto()));
        }

        sb.append("--------------------------------\n");
        return sb.toString();
    }

    public Orden getOrden() {
        return orden;
    }

    public Set<ConceptoServicio> getConceptos() {
        return conceptos;
    }
}