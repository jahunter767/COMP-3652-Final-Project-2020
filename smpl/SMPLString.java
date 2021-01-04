import java.util.ArrayList;


public class SMPLString extends SMPLObject<String> {

    
    public SMPLString(String type, String val) {
	super(type, val);

    }

    public int getLen(){
	return getVal().length();

    }


    public SMPLObject Substr(SMPLObject arg1,SMPLObject arg2) throws TypeException, SubstringException {
	if(isType("Numbers", arg1.getType()) && arg1.isEqualType(arg2.getType())){
		SMPLNumbers s = (SMPLNumbers)arg1;
		SMPLNumbers e = (SMPLNumbers)arg2;
		String result = "";
		int start = s.getVal().intValue();
		int end = e.getVal().intValue();
		int len = getLen();
		
		if(end < start){

			return SMPL.makeInstance(result); //

		}else if((start >= 0) && (start < len) && (end <= len) ){

			result = getVal().substring(start,end);
			return SMPL.makeInstance(result);

		}else{
			throw new SubstringException();
		}

    	} else {
		
		throw new TypeException();
	}

    }


    public SMPLObject Equal(SMPLObject obj) throws TypeException{
	Boolean result = false;
	if(isEqualType(obj.getType())){
		SMPLString arg2 = (SMPLString)obj;

		if(getVal().equals(arg2.getVal())){
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
	return String.valueOf(getVal());
    }

    public String printVal(){
	return String.valueOf(getVal());
    }




}
