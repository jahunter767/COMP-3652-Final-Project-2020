import java.util.ArrayList;
import java.lang.*;

public class SMPL {

    public SMPL(){ // This is an instance of the factory pattern
    }

    public static SMPLObject makeInstance(ExpLit lit){
	String type = lit.getType();

	try{
		if (type == "number") return new SMPLNumbers((Double) lit.getVal());
		if (type == "boolean") return new SMPLBoolean((Boolean) lit.getVal());
		if (type == "character") return new SMPLCharacter((Character) lit.getVal());
		if (type == "string") return new SMPLString((String) lit.getVal());
		if (type == "nill") return new SMPLNil();
		if (type == "none") return new SMPLNone();
	}catch(ClassNotFoundException ex) { 
		System.out.println(ex.toString());
		return null;// or make the none type
	}//finally{ } 
	return null;
    }

	public static SMPLObject makeInstance(ExpLit lit, ArrayList<SMPLObject> seq){
	String type = lit.getType();

	try{
		if ((type == "pair") || (type == "list")) return new SMPLPair(seq);
		if (type == "vector") return new SMPLVector(seq);
	}catch(ClassNotFoundException ex) { 
		System.out.println(ex.toString());
		return null;// or make the none type
	}//finally{ } 
	return null;
    }

    public static SMPLObject makeInstance(Closure c){
	return new SMPLFunction(c);
	}

}
