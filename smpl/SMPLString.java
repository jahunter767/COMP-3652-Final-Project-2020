public class SMPLString extends SMPLObject{

    public SMPLString(String value) {
	super(value,"SMPLString");
    }

    public String getVal(){
		return (String) V;	
	}
	
	public SMPLExp subStr(SMPLExp e1, SMPLExp e2){
		return new SliceOperations(this,e1,e2);
	}
	
	
}
