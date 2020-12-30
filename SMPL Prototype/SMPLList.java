public class SMPLList extends SMPLObject<List> {

    public SMPLList(String type, List val) {
	super(type, val);
    }

    public String toString(){
	return "Type: " + getType() + "\nValue: " + getVal();
    }

    public SMPLObject car() throws TypeException {
	return getVal().getFirstEl(); // [1,[2,[3,[4,nil]]]]
    }

    public SMPLObject cdr() throws TypeException {
	if (isType("Nil", getVal().getSecondEl().getType())) return getVal().getSecondEl();// [1,nill] returns a nill object
	return makeLst();	//((SMPLPair)getVal().getSecondEl()).car();// [1,[2,nill]]
    }


    private SMPLObject makeLst(){
	if (isType("Nil", getVal().getSecondEl().getType())) return getVal().getSecondEl();// [1,nill] returns a nill object
	return SMPL.makeInstance(new List(((SMPLPair)getVal().getSecondEl()).getVal(),length()));// return a pair [1,[2,nill]] -> [2,nill]
    }




// This method should be adjusted to check return list and not pairs
    private SMPLObject getRestofLst() throws TypeException {//
	if (isType("Nil", getVal().getSecondEl().getType())) return getVal().getSecondEl();// [1,nill] 
	return (SMPLPair) getVal().getSecondEl();// [1,[2,[3,[4,nil]]]] => [2,[3,[4,nill]]]
    }

    public int length(){
	return getVal().length();
    }





    public SMPLObject Equal(SMPLObject obj) throws TypeException{
	Boolean result = false; //[1,2,"3",4] and [1,2,3,4] and [1,2,4,3]
	if(isEqualType(obj.getType())){ // are they list?
		SMPLList arg2 = (SMPLList)obj; // [1,[2,[3,[4,nil]]]] and [1,[2,[3,[4,nil]]]] => [2,[3,[4,nill]]] eg. [1,[2,[4,nil]]] and [10,nill]
		
		if(length() != arg2.length()) return SMPL.makeInstance(result);
		

		if(car().isEqualType(arg2.car().getType())){// are the elements in each the same type?

			if(  ((SMPLBoolean)getRestofLst().Equal(arg2.getRestofLst())).getVal() ){// are the values in each the same? // pair(10,5) and pair(10,5)
				result = true;
			}

			return SMPL.makeInstance(result); //

		}else{
			return SMPL.makeInstance(result);
		}

    	} else {
		
		return SMPL.makeInstance(result);
	}

    }




}