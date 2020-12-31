import java.util.ArrayList;

public class SMPLList extends SMPLObject<List> {

    public SMPLList(List val) {
	super(val);
    }

    public SMPLObject car() throws TypeException {
	return getVal().getFirstEl(); // [1,[2,[3,[4,nil]]]]
    }

    public SMPLObject cdr() throws TypeException {
	return (SMPLPair) getVal().getSecondEl();// [1,[2,[3,[4,nil]]]] => [2,[3,[4,nill]]]
	}

    public SMPLObject concat(SMPLObject arg1) throws TypeException{
	if(arg1 instanceof SMPLList){
		SMPLList otherlst = (SMPLList) arg1;
		SMPLObject curr, next;
		ArrayList<SMPLObject> mergedEl = new ArrayList<SMPLObject>();

		curr = car();
		next = cdr();
		while (!(next instanceof SMPLNil)){
			mergedEl.add(curr);
			curr = next.car();
			next = next.cdr();
		}
		mergedEl.add(curr);

		curr = otherlst.car();
		next = otherlst.cdr();
		while (!(next instanceof SMPLNil)){
			mergedEl.add(curr);
			curr = next.car();
			next = next.cdr();
		}
		mergedEl.add(curr);

		return SMPL.makeInstance(new List(mergedEl));
	} else {
		throw new TypeException();
	}
    }

    public int length(){
	return getVal().length();
    }


    public SMPLObject equalTo (SMPLObject obj) throws TypeException{
	SMPLBoolean result = (SMPLBoolean) SMPL.makeInstance(false);
	if(obj instanceof SMPLList){
		SMPLList arg2 = (SMPLList) obj;
		result = (SMPLBoolean) car().equalTo(arg2.car());
		result = (SMPLBoolean) result.and(cdr().equalTo(arg2.cdr()));
	}
	return result;
	}


    public String toString(){
	return getVal().toString();
    }
}