public class StringExp extends Exp {

    String str;

    public StringExp(String id) {
	super("string");
	str = id;
    }

    public String getString() {
	return str;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitStringExp(this, arg);
    }

    public String toString() {
	return str;
    }
}
