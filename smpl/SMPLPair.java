public class SMPLPair extends SMPLObject{
	LinkedList pair;
	public SMPLPair(SMPLExp exp1, SMPLExp exp2){
		super(exp1,"SMPLPair");
		pair = new LinkedList();
		pair.insert(exp1);
		pair.insert(exp2);
		this.V = pair;
	}
	
	public String getVal(){
		return pair.toSMPLPair();
	}
	
	public SMPLExp car(){
		return pair.findByIndex(0);
	}
	
	public SMPLExp cdr(){
		return pair.findByIndex(1);
	}
	
	
	
}