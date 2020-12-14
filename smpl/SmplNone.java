import java.util.ArrayList;

public class SmplNone extends ExpLit {

    public SmplNone() {
	super("None");
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplNone(this, arg);
    }

    public String toString() {
	return super().getName();
    }
}
