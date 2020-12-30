public class ExpBind extends Exp {

    String id;

    protected ExpBind(String id, Exp e) {	// placeholder; can be removed eventually
    super("bind", e);
    this.id = id;
    }

    public String getName(){
    return this.id;
    }

    public Exp getExpr(){
    return super.getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpBind(this, arg);
    }
    
    public String toString(){
    return super.getName() + ": " + getName();
    }
}