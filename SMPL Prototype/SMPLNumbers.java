import java.util.ArrayList;


public class SMPLNumbers extends SMPLObject<Double> {

    private boolean wasInt;
    private Double result;
    
    public SMPLNumbers(String type, Double val) {
	super(type, val);
	wasInt = false;
    }

    public SMPLNumbers(String type, Integer val) {
	super(type, new Double(val));
	wasInt = true;
    }


    public String toString(){
	return "Type: " + getType() + "\nValue: " + String.valueOf(getVal());
    }


    public String printVal(){
	return String.valueOf(getVal());
    }


    public boolean wasInt(){
	return wasInt;
    }


    public SMPLObject add(SMPLObject object) throws TypeException {
	if(isEqualType(object.getType())){
		SMPLNumbers obj = (SMPLNumbers)object;
		Double val2 = obj.getVal();
		result = getVal() + val2;
		
		if(this.wasInt && obj.wasInt()){
			
			Integer res = new Integer(result.intValue());
			return SMPL.makeInstance(res); //
		}else{
			return SMPL.makeInstance(result);

		}

    	} else {
		
		throw new TypeException();
	}

    }


    public SMPLObject Equal(SMPLObject obj) throws TypeException{
	Boolean result = false;
	if(isEqualType(obj.getType())){
		SMPLNumbers arg2 = (SMPLNumbers)obj;

		if(getVal().compareTo(arg2.getVal()) == 0){
			result = true;
			return SMPL.makeInstance(result); //

		}else{
			return SMPL.makeInstance(result);
		}

    	} else {
		
		return SMPL.makeInstance(result);
	}

    }



}
