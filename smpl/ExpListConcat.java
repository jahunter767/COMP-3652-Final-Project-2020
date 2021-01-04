public class ExpListConcat extends ExpBinOp {


    public ExpListConcat(Exp Obj1, Exp Obj2) {
	super("@",Obj1, Obj2);
    }


    public <S, T> T visit(Visitor<S, T> v, S arg) throws VisitException {
	return v.visitExpListConcat(this, arg);
    }

    public String toString() {
	return getName() + "(" + getExpL().toString() + ","  + getExpR().toString() +  ")";
    }
}
