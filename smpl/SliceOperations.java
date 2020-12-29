import java.math.BigDecimal;

public class SliceOperations extends SMPLExp{
	SMPLExp exp1;
	SMPLExp exp2;
	SMPLExp exp3;
    public SliceOperations(SMPLExp exp1, SMPLExp exp2, SMPLExp exp3) {
	super("SliceOperations");
	this.exp1 = exp1;
	this.exp2 = exp2;
	this.exp3 = exp3;
    }
	
	
	public SMPLExp getObj(){
		return exp1;
	}
	
	public SMPLExp getInit(){
		return exp2;
	}
	public SMPLExp getEnd(){
		return exp3;
	}
	public String toString(){
		return "";
	}
	
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitSliceOperations(this, arg);
    }
	
	
}