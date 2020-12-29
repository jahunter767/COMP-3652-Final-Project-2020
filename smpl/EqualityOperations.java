import java.math.BigDecimal;

public class EqualityOperations extends SMPLExp{
	SMPLExp obj1;
	SMPLExp obj2;
	String op;
    public EqualityOperations(SMPLExp ob1, SMPLExp ob2, String op) {
	super("EqualityOperations");
	obj1 = ob1;
	obj2 = ob2;
	this.op = op;
    }
	
	
	public SMPLExp getObj1(){
		return obj1;
	}
	
	public SMPLExp getObj2(){
		return obj2;
	}
	public String getOp(){
		return op;
	}
	public String toString(){
		return "";
	}
	
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitEqualityOperations(this, arg);
    }
	
	
}
