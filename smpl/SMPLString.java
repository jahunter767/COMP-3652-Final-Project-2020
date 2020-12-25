import java.util.ArrayList;

public class SMPLString extends SMPLObject<String> {
    public SMPLString(String val) {
	super(val);
    }


    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLString){
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

    public SMPLObject Substr(SMPLObject arg1,SMPLObject arg2) throws TypeException, SubstringException {
	if(isType("Numbers", arg1.getType()) && arg1.isEqualType(arg2.getType())){
		SMPLNumbers s = (SMPLNumbers)arg1;
		SMPLNumbers e = (SMPLNumbers)arg2;
		String result = "";
		int start = s.getVal().intValue();
		int end = e.getVal().intValue();
		
		if(end < start){

			return SMPL.makeInstance(result); //

		}else if(start >= 0 && start < getVal().length()){

			result = getVal().substring(start,end);
			return SMPL.makeInstance(result);

		}else{
			throw new SubstringException();
		}

    	} else {
		
		throw new TypeException();
	}

    }





    public String toString(){
	return "Type: " + getType() + "\nValue: " + getVal();
    }


    public String printVal(){
	return String.valueOf(getVal());
    }




}
