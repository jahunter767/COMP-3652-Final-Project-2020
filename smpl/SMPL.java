import java.lang.*;

public class SMPL {

    public SMPL(){ // This is an instance of the factory pattern
    }


    public static SMPLObject makeInstance(){
	return new SMPLNone();
    }

    public static SMPLObject makeInstance(Object obj){
	if(obj instanceof Closure) return new SMPLFunction((Closure) obj);
	if(obj instanceof Vector) return new SMPLVector((Vector) obj);
	if(obj instanceof List) return new SMPLList((List) obj);
	if(obj instanceof Pair) return new SMPLPair((Pair) obj);
	if(obj instanceof Nil) return new SMPLNil();
	if(obj instanceof Integer) return new SMPLNumbers((Integer) obj);
	if(obj instanceof Double) return new SMPLNumbers((Double) obj);
	if(obj instanceof Boolean)return new SMPLBoolean((Boolean) obj);
	if(obj instanceof Character) return new SMPLCharacter((Character) obj);
	if(obj instanceof String) return new SMPLString((String) obj);
	
	return new SMPLNone(); // chnage to none type
    }
}