package visitor;

import buque.Buque;

public class BuqueReportVisitor implements ReportVisitor {
	  private StringBuilder xml = new StringBuilder();

	    @Override
	    public void visit(Buque buque) {
	        xml.append("<report>\n");

	        xml.append("  <import>\n");
	        for (String id : buque.getIdentificadoresDescargados()) {
	            xml.append("    <item>").append(id).append("</item>\n");
	        }
	        xml.append("  </import>\n");

	        xml.append("  <export>\n");
	        for (String id : buque.getIdentificadoresCargados()) {
	            xml.append("    <item>").append(id).append("</item>\n");
	        }
	        xml.append("  </export>\n");

	        xml.append("</report>");
	    }
	    public String generarXML() {
	        return xml.toString();
	    }
}
