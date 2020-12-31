import java.util.ArrayList;

public class SMPLPair extends SMPLObject<Pair> {
    public SMPLPair(Pair val) {
    super(val);
    }


	public SMPLObject car() throws TypeException {
	return getVal().getFirstEl();
    }

    public SMPLObject cdr() throws TypeException {
	return getVal().getSecondEl();
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if (object instanceof SMPLPair){
        SMPLPair obj = (SMPLPair) object;
        SMPLBoolean result = (SMPLBoolean) car().equalTo(obj.car());
        result = (SMPLBoolean) result.and(cdr().equalTo(obj.cdr()));
		return result;
    }
	return SMPL.makeInstance(false);
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return SMPL.makeInstance(this == object);
    }


	public String toString(){
	return getVal().toString();
    }

}
