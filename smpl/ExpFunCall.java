import java.util.ArrayList;

/**
 * IR Class to represent a function call
 */
public class ExpFunCall extends Exp {
    
    // Implement this class

    Exp func;

    protected ExpFunCall(Exp func, ArrayList<Exp> args) {	// placeholder; can be removed eventually
    super("call", args);
    this.func = func;
    }

    public Exp getFunction(){
    return this.func;
    }

    public ArrayList<Exp> getArgs(){
    return super.getSubTrees();
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpFunCall(this, arg);
    }
    
    public String toString(){
    return super.getName() + ": " + getName();
    }
}
