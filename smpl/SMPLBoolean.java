public class SMPLBoolean extends SMPLObject<Boolean> {    
    public SMPLBoolean(Boolean val) {
	super(val);
    }


    public SMPLObject and(SMPLObject object) throws TypeException {
	if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
        Boolean val2 = obj.getVal();
        Boolean result = getVal().intValue() && val2;
        return new SMPLBoolean(result);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject or(SMPLObject object) throws TypeException {
	if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
        Boolean val2 = obj.getVal();
        Boolean result = getVal().intValue() || val2;
        return new SMPLBoolean(result);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject not() throws TypeException {
    return new SMPLBoolean(!getVal());
    }


    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLBoolean){
		SMPLBoolean obj = (SMPLBoolean) object;
		Boolean val2 = obj.getVal();
		return new SMPLBoolean(getVal() == val2);
    }else {
		return new SMPLBoolean(false);
	}
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }


    public String toString(){
	return "Type: SMPLBoolean\nValue: " + String.valueOf(getVal());
    }

}
