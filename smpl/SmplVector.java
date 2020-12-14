import java.util.ArrayList;

public class SmplVector extends ExpLit {

    ArrayList<ExpLit> vector;

    public SmplVector(ExpLit... v) {
	super("Vector");
    vector = new ArrayList<ExpLit>();
    for (ExpLit e: v){
        vector.add(e);
    }
    }

    public SmplVector(ArrayList<ExpLit> v) {
	super("Vector");
    vector = v;
    }

    public ArrayList<ExpLit> getVal() {
	return vector;
    }

    public ExpLit getElement(int i) {
	return vector.get(i);
    }

    public int getLength() {
	return vector.getSize();
    }

    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitSmplVector(this, arg);
    }

    public String toString() {
    String result = "[: ";
    for (int i; i < getLength()-1; i++){
        result = result + getElement(i).toString() + ", ";
    }
    result = result + getElement(i).toString() + " :]";
	return result;
    }
}
