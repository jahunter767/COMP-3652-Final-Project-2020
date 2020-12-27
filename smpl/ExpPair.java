public class ExpPair extends ExpBinOp {


    public ExpPair(Exp Obj1, Exp Obj2) {
	super("Pair",Obj1, Obj2);
    }


    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpPair(this, arg);
    }

    public String toString() {
	return getName() + "(" + getExpL().toString() + ","  + getExpR().toString() +  ")";
    }
}
