import java.util.ArrayList;


public class SMPLCharacter extends SMPLObject<Character> {

    
    public SMPLCharacter(String type, Character val) {
	super(type, val);

    }




    public SMPLObject Equal(SMPLObject obj) throws TypeException{
	Boolean result = false;
	if(isEqualType(obj.getType())){
		SMPLCharacter arg2 = (SMPLCharacter)obj;

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
	return "Type: " + getType() + "\nValue: " + getVal();
    }


    public String printVal(){
	return String.valueOf(getVal());
    }




}
