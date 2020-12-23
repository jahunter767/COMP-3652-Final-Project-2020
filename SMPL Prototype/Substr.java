public class Substr extends Exp {


    public Substr(Exp string, Exp start, Exp end) {
	super("substr",string,start,end);
    }


    public Exp getArg1() {
	return (Exp) getSubTree(0);
    }

    public Exp getArg2() {
	return (Exp) getSubTree(1);
    }

    public Exp getArg3() {
	return (Exp) getSubTree(2);
    }


    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSubstr(this, arg);
    }

    public String toString() {
	return getName() + "(" + getArg1().toString() + ","  + getArg2().toString() + "," + getArg3().toString() + ")";
    }
}
