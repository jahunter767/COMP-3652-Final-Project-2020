import java.util.ArrayList;

public class SMPLVector extends SMPLObject<Vector> {
    /*
    public SMPLVector(SMPLObject... vect) {
    super(vect);
    }
    */

    public SMPLVector(Vector vect) {
    super(vect);
    }


    public int length(){
        return getVal().length();
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
        boolean result = length() == obj.length();
        if (result){
            SMPLBoolean p;
            int i = 0;
            while ((i < length()) && (result)){
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


    public void set(SMPLObject index, SMPLObject val) throws TypeException, IndexOutOfBoundsException {
	getVal().set(index.getIntVal(), val);
    }

    public SMPLObject size() throws TypeException {
	return SMPL.makeInstance(length);
    }


	public String toString(){
	return "Type: SMPLVector\nValue: " + getVal();
    }

}
