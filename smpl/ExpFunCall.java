import java.util.ArrayList;

/**
 * IR Class to represent a function call
 */
public class ExpFunCall extends Exp {
    
    // Implement this class

    String name;

    protected ExpFunCall(String funName, ArrayList<Exp> args) {	// placeholder; can be removed eventually
    super("call", args);
    this.name = funName;
    }

    public String getName(){
    return this.name;
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
