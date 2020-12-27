public class Pair{
	SMPLExp p;
	SMPLExp c;
	public Pair(SMPLExp p, SMPLExp c){
		this.c = c;
		this.p = p;
		
	}
	
	public SMPLExp getPred(){
		return p;
	}
	
	public SMPLExp getConseq(){
		return c;
	}

}