public class SMPLCharacter extends SMPLObject<Character> {    
    public SMPLCharacter(Character val) {
	super(val);
    }


    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLCharacter){
		SMPLCharacter obj = (SMPLCharacter) object;
		Character val2 = obj.getVal();
		return new SMPLBoolean(getVal() == val2);
    }else {
		return new SMPLBoolean(false);
	}
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }


    public String toString(){
	return String.valueOf(getVal());
    }

}
