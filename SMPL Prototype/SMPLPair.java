public class SMPLPair extends SMPLObject<Pair> {

    public SMPLPair(String type, Pair val) {
	super(type,val);
    }

    public String toString(){
	return "Type: " + getType() + "\nValue: " + getVal();
    }


    public SMPLObject car() throws TypeException {
	return getVal().getFirstEl();//[1,2]
    }


    public SMPLObject cdr() throws TypeException {
	return getVal().getSecondEl();
    }


    public SMPLObject Equal(SMPLObject obj) throws TypeException{
	Boolean result = false; //pair("10", 28) and pair(10,"28")
	if(isEqualType(obj.getType())){ // are they pairs?
		SMPLPair arg2 = (SMPLPair)obj;

		if(car().isEqualType(arg2.car().getType()) && cdr().isEqualType(arg2.cdr().getType())){// are the elements in each the same type?

			if(  ((SMPLBoolean)car().Equal(arg2.car())).getVal() && ((SMPLBoolean)cdr().Equal(arg2.cdr())).getVal()  ){// are the values in each the same? // pair(10,5) and pair(10,5)
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