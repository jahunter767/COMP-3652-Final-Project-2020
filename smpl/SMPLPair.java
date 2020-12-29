public class SMPLPair extends SMPLObject{
	LinkedList pair;
	SMPLExp p1;
	SMPLExp p2;
	public SMPLPair(SMPLExp exp1, SMPLExp exp2){
		super(exp1,"SMPLPair");
		pair = new LinkedList();
		pair.insert(exp1);
		pair.insert(exp2);
		this.V = pair;
	}
	
	
	
	public SMPLExp getExp1(){
		return p1;
	}
	public SMPLExp getExp2(){
		return p2;
	}
	public LinkedList getTuple(){
		return pair;
	}
	public String getVal(){
		return pair.toSMPLPair();
	}
	public String getSring(){
		return pair.toSMPLPair();
	}
	public void setVal(LinkedList pair){
		this.V = pair;
	}
	public SMPLExp car(){
		TupleOperations t = new TupleOperations(this);
		t.get(new SMPLInteger(new Integer(0)));
		return t;
	}
	
	public SMPLExp cdr(){
		TupleOperations t = new TupleOperations(this);
		t.get(new SMPLInteger(new Integer(1)));
		return t;
	}
	public void insert(SMPLExp exp){
		pair.insert(exp);
	}
	

	
}