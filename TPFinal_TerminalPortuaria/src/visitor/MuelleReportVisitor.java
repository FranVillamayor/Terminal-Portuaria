package visitor;

import buque.Buque;
import orden.Orden;

public class MuelleReportVisitor implements ReportVisitor {
	private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(Buque buque) {
        sb.append("Buque: ").append(buque.getNombre()).append("\n")
          .append("Arribo: ").append(buque.getViaje().fechaLlegadaTerminalGestionada()).append("\n")
          .append("Partida: ").append(buque.getViaje().fechaSalidaTerminalGestionada()).append("\n")
          .append("Containers operados: ").append(buque.getContenedoresOperados()).append("\n\n");
    }

    public String generarReporte() {
        return sb.toString();
    }

}

 