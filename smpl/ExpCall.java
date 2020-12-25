public class ExpCall extends Statement {

    Exp args;

    protected ExpCall(Exp f, Exp lst) {
    super("call", f);
    this.args = lst;
    }


    public Exp getArgs(){
    return this.args;
    }

    public Exp getFunction(){
    return super.getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpCall(this, arg);
    }
    
    public String toString(){
    return super.getName() + ": " + getName();
    }
    
}
