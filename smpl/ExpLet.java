import java.util.ArrayList;

public class ExpLet extends Exp {

    ArrayList<ExpBind> bindings;

    protected ExpLet(ArrayList<ExpBind> bindLst, Exp body) {	// placeholder; can be removed eventually
    super("let", body);
    this.bindings = bindLst;
    }

    public ArrayList<ExpBind> getBindings(){
    return this.bindings;
    }

    public Exp getBody(){
    return getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpLet(this, arg);
    }
    
    public String toString(){
    return super.getName() + ": " + getName();
    }
}