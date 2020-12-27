/**
 * IR Class to represent a list of pairs
 */
import java.util.ArrayList;

public class ExpList extends Exp {

    ArrayList<Exp> arguments;


    protected ExpList(ArrayList<Exp> listArgs) {
	super("List",listArgs);
	this.arguments = listArgs;
    }


    public ArrayList<Exp> getArgs() {
	return this.arguments;
    }

    public String toString() {
	return " " + getArgs() + " ";
    }


    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpList(this, arg);
    }




}
