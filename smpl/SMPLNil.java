public class SMPLNil extends SMPLObject<String> {
    public SMPLNil() {
    super(null);
    }


    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    boolean result = object instanceof SMPLNil;
    return new SMPLBoolean(result);
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }


	public String toString(){
	return "[]";
    }

}
