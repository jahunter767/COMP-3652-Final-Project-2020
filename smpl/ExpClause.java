public class ExpClause extends Exp {

    Exp  predicate;

    protected ExpClause(Exp predicate, Exp consequent) {
    super("clause", consequent);
    this.predicate = predicate;
    }

    public Exp getPredicate(){
    return this.predicate;
    }

    public Exp getConsequent(){
    return super.getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpClause(this, arg);
    }

    public String toString(){
    return super.getName() + ": " + getName();
    }

}
