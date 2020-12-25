import java.util.ArrayList;

public class SMPLPair extends SMPLObject<ArrayList<SMPLObject>> {
    public SMPLPair(SMPLObject val) {
    ArrayList<SMPLObject> pair = new ArrayList<SMPLObject>();
    pair.add(val);
    pair.add(new SMPLNil());
	super(pair);
    }

    public SMPLPair(SMPLObject val1, SMPLObject val2) {
    ArrayList<SMPLObject> pair = new ArrayList<SMPLObject>();
    pair.add(val1);
    pair.add(val2);
	super(pair);
    }

    public SMPLPair(ArrayList<SMPLObject> list) {
    ArrayList<SMPLObject> lst = new ArrayList<SMPLObject>();
    lst.add(list.remove(0));
    if (list.size() == 0){
        lst.add(new SMPLNil());
        super(lst);
    }else{
        lst.add(new SMPLPair(list));
        super(lst);
    }
    }


	public SMPLObject car(SMPLObject object) throws TypeException {
    if (object instanceof SMPLPair){
		SMPLPair obj = (SMPLPair) object;
		return obj.getVal().get(0);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject cdr(SMPLObject object) throws TypeException {
    if (object instanceof SMPLPair){
		SMPLPair obj = (SMPLPair) object;
		return obj.getVal().get(1);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if (object instanceof SMPLPair){
        SMPLPair obj = (SMPLPair) object;
        boolean result = car().equalTo(obj.car()).getVal() &&
                        cdr().equalTo(obj.cdr()).getVal();
		return new SMPLBoolean(result);
    }else {
		return new SMPLBoolean(false);
	}
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }


	public String toString(){
	return "Type: SMPLPair\nValue: " + getVal();
    }

}
