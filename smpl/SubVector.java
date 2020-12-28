/*

Primitive for Vector Object.

*/


public class SubVector extends Statement {

    private Pair vectorExp;

    public SubVector(SMPLExp num, SMPLExp proc) {
	super("SubVector");
	vectorExp = new Pair(num, proc);
    }

    public Pair getSubVectorExp(){
		return vectorExp;
	}
	public String toString(){
		return "";
	}
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitSubVector(this, arg);
    }
	
}

