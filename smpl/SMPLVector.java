import java.util.ArrayList;

public class SMPLVector extends SMPLObject<Vector> {
    /*
    public SMPLVector(String Type, SMPLObject... vect) {
    super(vect);
    }
    */

    public SMPLVector(String Type,Vector vect) {
    super(Type, vect);
    }


    public int length(){
        return getVal().length();
    }

    public SMPLObject pop(SMPLObject object) throws TypeException, VectorException {
    if (object instanceof SMPLNumbers){
        SMPLNumbers obj = (SMPLNumbers) object;
        if (obj.getIntVal() < length() && obj.getIntVal() >= 0  ){
            return getVal().pop(obj.getIntVal());
        }else{
            throw new VectorException();
        }
    }else {
		throw new TypeException();
	}
    }


    public SMPLObject get(SMPLObject object) throws TypeException, VectorException {
    if (object instanceof SMPLNumbers){
        SMPLNumbers obj = (SMPLNumbers) object;
        if (obj.getIntVal() < length() && obj.getIntVal() >= 0  ){
            return getVal().get(obj.getIntVal());
        }else{
            throw new VectorException();
        }
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject get(int i) throws TypeException, VectorException  {
    
	if(i > length() || i < 0 ){ throw new VectorException();}
	return getVal().get(i);

    }


    public SMPLObject set(SMPLObject indx, SMPLObject val) throws TypeException, VectorException  {
	// do some validation here
	if (indx instanceof SMPLNumbers){
		SMPLNumbers index = (SMPLNumbers)indx;
		if(index.getIntVal() >= length() || index.getIntVal() < 0 ) {throw new VectorException();}
		getVal().set(index.getIntVal(), val);
		return SMPL.makeInstance();
	} else throw new TypeException();
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException{
    if (object instanceof SMPLVector){
	try{
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
		return SMPL.makeInstance(result);
	}catch(VectorException ex) { 
		System.out.println(ex.toString());
		return SMPL.makeInstance(false);

	}
    }else {
		return SMPL.makeInstance(false);
	}
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return SMPL.makeInstance(this == object);
    }


    public SMPLObject size() throws TypeException {
	return SMPL.makeInstance(length());
    }


	public String toString(){
	return "Type: SMPLVector\nValue: " + getVal();
    }

}