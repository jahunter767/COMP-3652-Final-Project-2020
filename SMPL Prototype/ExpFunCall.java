/**
 * IR Class to represent a function call new vers
 */
import java.util.ArrayList;
public class ExpFunCall extends Exp {
    //SMPLFunction procedure;
    Exp procedure;
    String name;
    ArrayList<Exp> arguments;
    // Implement this class

    protected ExpFunCall() {	// placeholder; can be removed eventually
	super("Funcall");
    }

    protected ExpFunCall(SMPLFunction proc, ArrayList<Exp> funcArgs) {	// placeholder; can be removed eventually
	super("Funcall",funcArgs);
	this.procedure = null;
	this.arguments = funcArgs;
    }

    protected ExpFunCall(Exp proc, ArrayList<Exp> funcArgs) {	// placeholder; can be removed eventually
	super("Funcall",funcArgs);
	this.procedure = proc;
	this.name = null;
	this.arguments = funcArgs;
    }


    protected ExpFunCall(String proc, ArrayList<Exp> funcArgs) {	// placeholder; can be removed eventually
	super("Funcall",funcArgs);
	this.procedure = null;
	this.name = proc ;
	this.arguments = funcArgs;
    }

    public Exp getProcedure() {
	return this.procedure;
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
