/**

Shared context for Relational Operations 
param@ nextVal : This is the next value of iterated relational expression 
param@ iterated: This is a flag to determine if expression is iterated or not
param@ prev    : This stores prev boolean result of relational expression 

**/

public abstract class RelationOP extends ExpBinOp {
	
	public static SMPLExp nextVal =null; 
	public static Boolean iterated;
	public static Boolean prev = true;

    protected RelationOP(String name, SMPLExp exp1, SMPLExp exp2){
	super(name, exp1, exp2);
    }
}