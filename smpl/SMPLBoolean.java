import java.util.ArrayList;


public class SMPLBoolean extends SMPLObject<Boolean> {

    
    public SMPLBoolean(String type, Boolean val) {
	super(type, val);

    }


    public SMPLObject Equal(SMPLObject obj) throws TypeException{
	Boolean result = false;
	if(isEqualType(obj.getType())){
		SMPLBoolean arg2 = (SMPLBoolean)obj;

		if(getVal() == arg2.getVal()){
			result = true;
			return SMPL.makeInstance(result); //

		}else{
			return SMPL.makeInstance(result);
		}

    	} else {
		
		return SMPL.makeInstance(result);
	}

    }


    public String toString(){
	return "Type: " + getType() + "\nValue: " + getVal();
    }


    public String printVal(){
	return String.valueOf(getVal());
    }


//------- FROM JASON's CODE--------


    public SMPLObject bitwiseAnd(SMPLObject object) throws TypeException {
	if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
		Boolean val2 = obj.getVal();
		Boolean result = getVal().booleanValue() & val2;
		return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}
    }


    public SMPLObject bitwiseOr(SMPLObject object) throws TypeException {
	if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
		Boolean val2 = obj.getVal();
		Boolean result = getVal().booleanValue() | val2;
		return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}

    }

 


    public SMPLObject and(SMPLObject object) throws TypeException {
	if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
        Boolean val2 = obj.getVal();
        Boolean result = getVal().booleanValue() && val2;
        return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject or(SMPLObject object) throws TypeException {
	if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
        Boolean val2 = obj.getVal();
        Boolean result = getVal().booleanValue() || val2;
        return SMPL.makeInstance(result);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject not() throws TypeException {
    return SMPL.makeInstance(!getVal());
    }


    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
		Boolean val2 = obj.getVal();
		return SMPL.makeInstance(getVal() == val2);
    }else {
		return SMPL.makeInstance(false);
	}
    }




//----------------------------------



}
