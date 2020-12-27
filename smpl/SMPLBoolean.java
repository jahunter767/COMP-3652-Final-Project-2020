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




}
