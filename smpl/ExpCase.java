import java.util.ArrayList;

/**
 * IR Class to represent an if statement
 */
public class ExpCase extends Exp {

    Exp  predicate;

    protected ExpCase(ArrayList<ExpClause> clauses) {
    super("case", clauses);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpCase(this, arg);
    }

    public String toString(){
    return super.getName() + ": " + getName();
    }

}
