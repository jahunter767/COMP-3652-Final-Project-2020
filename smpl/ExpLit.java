public class ExpLit <Type> extends Exp {

    Type val;

    public ExpLit(Type v) {
	super(v.toString());
	val = v;
    }

    public ExpLit() {
	super("None");
	val = null;
    }


    public Type getVal() {
	return val;
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException{
	return v.visitExpLit(this, arg);
    }

    public String toString() {
	return val.toString();//Integer.toString(val);
    }
}

