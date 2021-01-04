import java.util.ArrayList;

public class SMPLList extends SMPLObject<List> {

    public SMPLList(String type, List val) {
	super(type, val);
    }

    public String toString(){
	return getVal().toString();
    }

    public SMPLObject car() throws TypeException {
	return getVal().getFirstEl(); // [1,[2,[3,[4,nil]]]]
    }

    public SMPLObject cdr() throws TypeException {// RETURNS A LIST!
	if (isType("Nil", getVal().getSecondEl().getType())) return getVal().getSecondEl();// [1,nill] returns a nill object
	return getRestofLst();	//((SMPLPair)getVal().getSecondEl()).car();// [1,[2,nill]]
    }

/*
	-- obsolete --

    private SMPLObject makeLst(){
	if (isType("Nil", getVal().getSecondEl().getType())) return getVal().getSecondEl();// [1,nill] returns a nill object
	SMPLPair pair = getRestofLst(); // get pair
	
	return SMPL.makeInstance(new List(((SMPLPair)getVal().getSecondEl()).getVal(),length()));// return a pair [1,[2,nill]] -> [2,nill]
    }

*/



// This method should be adjusted to check return list and not pairs - check.
    private SMPLObject getRestofLst() throws TypeException {
	if (isType("Nil", getVal().getSecondEl().getType())) return getVal().getSecondEl();// [1,nill]
	
	Pair pair = ((SMPLPair) getVal().getSecondEl()).getVal();// [1,[2,[3,[4,nil]]]] => [2,[3,[4,nill]]]
	
	ArrayList<SMPLObject> elements = getVal().getElements();

	return SMPL.makeInstance(new List(pair,length(),elements));

    }

    public int length(){
	return getVal().length();
    }


    public SMPLObject concat(SMPLObject arg1) throws TypeException{
	if(isEqualType(arg1.getType())){
		SMPLList otherlst = (SMPLList)arg1;

		ArrayList<SMPLObject> mergedEl = (ArrayList)getVal().getElements().clone(); // just to ensure that I not modify the og list
    		ArrayList<SMPLObject> otherEl = (ArrayList)otherlst.getVal().getElements().clone();
		
		mergedEl.addAll(otherEl);
		
		return SMPL.makeInstance(new List(mergedEl));
	} else {
					// [11] and [21,15,4,5,3,2,1,0]
		throw new TypeException();// [11,1,2,3,4,15,21]
	}

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