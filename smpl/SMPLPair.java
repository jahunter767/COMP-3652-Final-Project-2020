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
		return pair.findByIndex(0);
	}
	
	public SMPLExp cdr(){
		return pair.findByIndex(1);
	}
	public void insert(SMPLExp exp){
		pair.insert(exp);
	}
	

	
}