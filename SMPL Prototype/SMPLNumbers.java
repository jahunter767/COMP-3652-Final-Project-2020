import java.util.ArrayList;
import java.lang.Math;


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


// --- FROM JASON'S CODE -----

    public boolean isInt(){
	return getVal().intValue() == getVal().doubleValue();
    }

	public int getIntVal(){
	return getVal().intValue();
    }

//--------------------------------


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


//---------- FROM JASON'S CODE --------------

    public SMPLObject bitwiseOr(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;

		if(isInt() && obj.isInt()){
			Integer val2 = obj.getVal().intValue();
			Integer result = getVal().intValue() | val2;
			return SMPL.makeInstance(result);
		}else {
			throw new TypeException();
		}
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject bitwiseNot() throws TypeException {
	if(isInt()){
		Integer result = ~getVal().intValue();
		return SMPL.makeInstance(result);
	}else {
		throw new TypeException();
	}
    }


	public SMPLObject subtract(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = getVal() - val2;
		return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject multiply(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = getVal() * val2;
		return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject divide(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = getVal() / val2;
		return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject mod(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		
		if(isInt() && obj.isInt()){
			Integer val2 = obj.getVal().intValue();
			Integer result = getVal().intValue() % val2;
			return SMPL.makeInstance(result);
		}else {
			throw new TypeException();
		}
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject pow(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = Math.pow(getVal(), val2);
		return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}
	}


    public SMPLObject lessThan(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		return SMPL.makeInstance(getVal() < val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject lessThanEq(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		return SMPL.makeInstance(getVal() <= val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		return SMPL.makeInstance(getVal() == val2);
    }else {
		return SMPL.makeInstance(false);
	}
    }

    public SMPLObject greaterThanEq(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		return SMPL.makeInstance(getVal() >= val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject greaterThan(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		return SMPL.makeInstance(getVal() > val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject notEqualTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		return SMPL.makeInstance(getVal() != val2);
    }else {
		throw new TypeException();
	}
	}








//----------------------------------------------



}
