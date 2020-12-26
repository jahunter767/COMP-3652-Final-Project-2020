import java.util.ArrayList;
import java.lang.*;

public class SMPL {

    public SMPL(){ // This is an instance of the factory pattern
    }

    public static SMPLObject makeInstance(ExpLit lit){
	return makeInstance(lit.getType(), lit.getVal());
	}
	
	public static SMPLObject makeInstance(String type, Object val){
	if (type == "number") return new SMPLNumbers((Double) val);
	if (type == "boolean") return new SMPLBoolean((Boolean) val);
	if (type == "character") return new SMPLCharacter((Character) val);
	if (type == "string") return new SMPLString((String) val);
	if (type == "nill") return new SMPLNil();
	if (type == "none") return new SMPLNone();
	if ((type == "pair") || (type == "list")) return new SMPLPair((ArrayList<SMPLObject>) val);
	if (type == "vector") return new SMPLVector((ArrayList<SMPLObject>) val);
	if (type == "function") return new SMPLFunction((Closure) val);
	return null;
    }
}
