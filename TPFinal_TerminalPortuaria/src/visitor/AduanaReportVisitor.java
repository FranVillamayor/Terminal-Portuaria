package visitor;

import buque.Buque;
import container.Container;

public class AduanaReportVisitor implements ReportVisitor {
	private StringBuilder html = new StringBuilder();

    @Override
    public void visit(Buque buque) {
        html.append("<h1>Reporte Aduana</h1>")
            .append("<p>Buque: ").append(buque.getNombre()).append("</p>")
            .append("<p>Arribo: ").append(buque.getViaje().fechaLlegadaTerminalGestionada()).append("</p>")
            .append("<p>Partida: ").append(buque.getViaje().fechaSalidaTerminalGestionada()).append("</p>")
            .append("<ul>");
        for (Container c : buque.getContainers()) {
            html.append("<li>")
                .append(c.getTipo()).append(" - ").append(c.getId())
                .append("</li>");
        }

        html.append("</ul>");
    }
    

    public String generarHTML(){
        html.append("</ul>");
        return html.toString();
    }
}
 