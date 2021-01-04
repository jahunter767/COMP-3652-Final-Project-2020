
import java.util.ArrayList;
public class ExpCall extends Exp {

    Exp procedure;
    String name;
    String lst;
    Exp arguments;



    protected ExpCall(Exp proc, Exp funcArgs) { // when there's a raw proc nd arguments
	super("Funcall",funcArgs);
	this.procedure = proc;
	this.name = null;
	this.lst = null;
	this.arguments = funcArgs;
    }


    protected ExpCall(String funcName, Exp lst) { // when there's a var nd arguments
	super("call",lst);
	this.procedure = null;
	this.name = funcName ;
	this.lst = null;
	this.arguments = lst;
    }


    protected ExpCall(String funcName, String lst) { // when there's a var nd arguments
	super("call");
	this.procedure = null;
	this.name = funcName ;
	this.lst = lst;
	this.arguments = null;
    }

    public Exp getProcedure() {
	return this.procedure;
    }

    public String getName() {
	return this.name;
    }

    public Exp getList() {
	return this.arguments;
    }

    public String toString() {
	return "";
    }


    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpCall(this, arg);
    }


}
