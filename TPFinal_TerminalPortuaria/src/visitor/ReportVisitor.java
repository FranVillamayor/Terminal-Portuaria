package visitor;

import buque.Buque;
import orden.Orden;

public interface ReportVisitor {
	public void visit(Buque buque);
	
}
