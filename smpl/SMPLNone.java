public class SMPLNone extends SMPLObject<String> {
    public SMPLNone() {
    super(null);
    }


    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    boolean result = object instanceof SMPLNone;
    return new SMPLBoolean(result);
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }


	public String toString(){
	return "Type: SMPLNone\nValue: " + getVal();
    }

}
