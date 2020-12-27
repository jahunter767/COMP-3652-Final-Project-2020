public class SMPLNil extends SMPLObject<Nil> {

    public SMPLNil(String type, Nil val) {
	super(type,val);
    }

    public String toString(){
	return "Type: " + getType() + "\nValue: " + getVal();
    }


    public SMPLObject Equal(SMPLObject obj) throws TypeException{
	Boolean result = false;
	if(isEqualType(obj.getType())){
		result = true;
	}
	
	return SMPL.makeInstance(result);

    }

}