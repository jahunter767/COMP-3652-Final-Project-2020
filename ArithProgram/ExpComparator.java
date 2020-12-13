import java.util.ArrayList;
public class ExpComparator extends ExpBinOp {

    public ExpComparator(ArrayList<Exp> cList) {
	super("Comparator List", cList);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitExpComparator(this, arg);
    } 

}