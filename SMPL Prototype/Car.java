public class Car extends Exp {


    public Car(Exp exp1) {
	super("car",exp1);
    }


    public Exp getArg1() {
	return (Exp) getSubTree(0);
    }


    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitCar(this, arg);
    }

    public String toString() {
	return getName() + "(" + getArg1().toString() + ")";
    }
}
