public class SMPLList extends SMPLObject<List> {

    public SMPLList(List val) {
	super(val);
    }

    public SMPLObject car() throws TypeException {
	return getVal().getFirstEl(); // [1,[2,[3,[4,nil]]]]
    }

    public SMPLObject cdr() throws TypeException {
	return getRestofLst().car();//((SMPLPair)getVal().getSecondEl()).car();// [1,[2,nill]]
    }

    private SMPLObject getRestofLst() throws TypeException {//
	return (SMPLPair) getVal().getSecondEl();// [1,[2,[3,[4,nil]]]] => [2,[3,[4,nill]]]
    }

    public int length(){
	return getVal().length();
    }


    public SMPLObject equalTo (SMPLObject obj) throws TypeException{
	SMPLBoolean result = (SMPLBoolean) SMPL.makeInstance(false);
	if(obj instanceof SMPLList){
		SMPLList arg2 = (SMPLList) obj;
		result = (SMPLBoolean) car().equalTo(arg2.car());
		result = (SMPLBoolean) result.and(getRestofLst().equalTo(arg2.getRestofLst()));
	}
	return result;
	}


    public String toString(){
	return "Type: SMPLList\nValue: " + getVal();
    }
}