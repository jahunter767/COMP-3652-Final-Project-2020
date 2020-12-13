/**
 * IR Class to represent a function call new vers
 */
import java.util.ArrayList;
public class ExpFunCall extends Exp {
    String name;
    ArrayList<Exp> arguments;
    // Implement this class

    protected ExpFunCall() {	// placeholder; can be removed eventually
	super("Funcall");
    }

    protected ExpFunCall(String funcName, ArrayList<Exp> funcArgs) {	// placeholder; can be removed eventually
	super("Funcall",funcArgs);
	this.name = funcName;
	this.arguments = funcArgs;
    }

    public String getName() {
	return this.name;
    }

    public ArrayList<Exp> getArgs() {
	return this.arguments;
    }

    public String toString() {
	return "";
    }


    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpFunCall(this, arg);
    }




}
