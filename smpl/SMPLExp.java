import java.util.ArrayList;

/** A placeholder class to represent an arbitrary expression.  For
 * ArithExp, this class is really the top level ASTNode class, but by
 * including the ASTNode class as well, this code can be readily
 * generalised to larger languages that may have more sophisticated
 * forms than only algebraic expressions in it.
*/
public abstract class SMPLExp extends ASTNode<SMPLExp> {
	
	protected String SMPLType;
    
    protected SMPLExp(String name, SMPLExp... subExps) {
	super(name, subExps);
    }

    protected SMPLExp(String name, ArrayList<SMPLExp> subExps) {
	super(name, subExps);
    }
	
	public ExpAdd add(SMPLExp exp){
		return new ExpAdd(this,exp);
	}
	
	public ExpSub sub(SMPLExp exp){
		return new ExpSub(this,exp);
	}
	
	public ExpMul mul(SMPLExp exp){
		return new ExpMul(this,exp);
	}
	
	public ExpDiv div(SMPLExp exp){
		return new ExpDiv(this,exp);
	}
	
	public ExpMod mod(SMPLExp exp){
		return new ExpMod(this,exp);
	}
	
	public String getType(){
		return SMPLType;
	}
	public EqualityOperations equal(SMPLExp o, String op) {
            return new EqualityOperations(this,o,op);
    }
}
