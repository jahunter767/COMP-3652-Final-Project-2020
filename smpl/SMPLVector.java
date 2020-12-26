import java.util.ArrayList;

public class SMPLVector extends SMPLObject<ArrayList<SMPLObject>> {
    /*
    public SMPLVector(SMPLObject... vect) {
    super(vect);
    }
    */

    public SMPLVector(ArrayList<SMPLObject> vect) {
    super(vect);
    }


    public int size(){
        return getVal().size();
    }

    public SMPLObject get(SMPLObject object) throws TypeException {
    if (object instanceof SMPLNumbers){
        SMPLNumbers obj = (SMPLNumbers) object;
        if (obj.isInt()){
            return getVal().get(obj.getIntVal());
        }else{
            throw new TypeException();
        }
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject get(int i) throws TypeException {
    return getVal().get(i);
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if (object instanceof SMPLVector){
		SMPLVector obj = (SMPLVector) object;
        boolean result = size() == obj.size();
        if (result){
            SMPLBoolean p;
            int i = 0;
            while ((i < size()) && (result)){
                p = (SMPLBoolean) get(i).equalTo(obj.get(i));
                result = result && p.getVal().booleanValue();
            }
        }
		return new SMPLBoolean(result);
    }else {
		return new SMPLBoolean(false);
	}
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }


	public String toString(){
	return "Type: SMPLVector\nValue: " + getVal();
    }

}
