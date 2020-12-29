import java.math.BigDecimal;

public class TupleOperations extends SMPLExp{
	SMPLExp tup;
	String op;
	SMPLExp val;
    public TupleOperations(SMPLExp tuple) {
	super("TupleOperations");
	this.tup = tuple;
	op = new String();
    }
	
	public void set(SMPLExp v){
		op = "set";
		val = v;
	}
	
	public void get(SMPLExp v){
		op = "get";
		val = v;
	}
	
	public void size(){
		op="size";
	}
	
	public String getOp(){
		return op;
	}
	
	public SMPLExp getVal(){
		return val;
	}
	
	public SMPLExp getTup(){
		return tup;
	}
	
	public String toString(){
		return op;
	}
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitTupleOperations(this, arg);
    }
	
	
}
