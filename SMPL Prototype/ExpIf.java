/**
 * IR Class to represent an if statement
 */
public class ExpIf extends Exp {

    Exp  predicate;

    protected ExpIf(Exp predicate, Exp consequent, Exp alternative) {
    super("if", consequent, alternative);
    this.predicate = predicate;
    }

    public Exp getPredicate(){
    return this.predicate;
    }

    public Exp getConsequent(){
    return super.getSubTree(0);
    }

    public Exp getAlternative(){
    return super.getSubTree(1);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpIf(this, arg);
    }

    public String toString(){
    return super.getName() + ": " + getName();
    }

}