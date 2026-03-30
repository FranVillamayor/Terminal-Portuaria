package visitor;

public interface Visitable {
	void accept(ReportVisitor visitor);
}
