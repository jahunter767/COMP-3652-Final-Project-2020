public class SMPLString extends SMPLObject<String> {
    public SMPLString(String val) {
	super(val);
    }


	public int getLen(){
		return getVal().length();
	}


	public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if (object instanceof SMPLString){
		SMPLString obj = (SMPLString) object;
		String val2 = obj.getVal();
		return new SMPLBoolean(getVal().equals(val2));
    }else {
		return new SMPLBoolean(false);
	}
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }

    public SMPLObject substr(SMPLObject arg1,SMPLObject arg2) throws TypeException, SubstringException {
	if ((arg1 instanceof SMPLNumbers) && (arg2 instanceof SMPLNumbers)){
		SMPLNumbers s = (SMPLNumbers)arg1;
		SMPLNumbers e = (SMPLNumbers)arg2;
		String result = "";
		int start = s.getIntVal();
		int end = e.getIntVal();
		int len = getLen();
		
		if(end <= start){
			return new SMPLString(result);
		}else if ((start >= 0) && (start < len) && (end <= len)){
			result = getVal().substring(start,end);
			return new SMPLString(result);
		}else{
			throw new SubstringException();
		}
    }else{
		throw new TypeException();
	}
    }


	public String toString(){
	return "Type: SMPLString\nValue: " + getVal();
    }

}
