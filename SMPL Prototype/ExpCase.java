import java.util.ArrayList;

public class ExpCase extends Exp {
    protected ExpCase(ArrayList<Exp> clauses) {
    super("case", clauses);
    }

    public ArrayList<Exp> getClauses(){
    return getSubTrees();
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitExpCase(this, arg);
    }

    public String toString(){
    return super.getName() + ": " + getName();
    }

}