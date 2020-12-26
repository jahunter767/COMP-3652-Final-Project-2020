import java.util.ArrayList;

public class SMPLPair extends SMPLObject<ArrayList<SMPLObject>> {
    public SMPLPair(SMPLObject val) {
    super();
    ArrayList<SMPLObject> pair = new ArrayList<SMPLObject>();
    pair.add(val);
    pair.add(new SMPLNil());
	setVal(pair);
    }

    public SMPLPair(SMPLObject val1, SMPLObject val2) {
    super();
    ArrayList<SMPLObject> pair = new ArrayList<SMPLObject>();
    pair.add(val1);
    pair.add(val2);
	setVal(pair);
    }

    public SMPLPair(ArrayList<SMPLObject> list) {
    super();
    ArrayList<SMPLObject> lst = new ArrayList<SMPLObject>();
    lst.add(list.remove(0));
    if (list.size() == 0){
        lst.add(new SMPLNil());
        setVal(lst);
    }else{
        lst.add(new SMPLPair(list));
        setVal(lst);
    }
    }


	public SMPLObject car() throws TypeException {
	return getVal().get(0);
    }

    public SMPLObject cdr() throws TypeException {
	return getVal().get(1);
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if (object instanceof SMPLPair){
        SMPLPair obj = (SMPLPair) object;
        SMPLBoolean l = (SMPLBoolean) car().equalTo(obj.car());
        SMPLBoolean r = (SMPLBoolean) cdr().equalTo(obj.cdr());
        boolean result = l.getVal() && r.getVal();
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
