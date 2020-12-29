import java.lang.*;

public class SMPL {

    public SMPL(){ // This is an instance of the factory pattern
    }



    public static SMPLObject makeInstance(){
	return new SMPLNone();
    }


    public static SMPLObject makeInstance(Object obj){

	try{
	
		if(Class.forName("java.lang.Integer").isInstance(obj)) return new SMPLNumbers("Numbers",(Integer)obj );
		if(Class.forName("java.lang.Double").isInstance(obj)) return new SMPLNumbers("Numbers",(Double)obj );
		if(Class.forName("java.lang.String").isInstance(obj)) return new SMPLString("String",(String)obj );
		if(Class.forName("java.lang.Character").isInstance(obj)) return new SMPLCharacter("Character",(Character)obj );
		if(Class.forName("java.lang.Boolean").isInstance(obj))return new SMPLBoolean("Boolean",(Boolean)obj );
		if(Class.forName("Closure").isInstance(obj)) return new SMPLFunction("Func",(Closure)obj );
		if(Class.forName("Nil").isInstance(obj)) return new SMPLNil("Nil",(Nil)obj );
		if(Class.forName("Pair").isInstance(obj)) return new SMPLPair("Pair",(Pair)obj );
		if(Class.forName("List").isInstance(obj)) return new SMPLList("List",(List)obj );


	}catch(ClassNotFoundException ex) { 
		System.out.println(ex.toString());
		return new SMPLNone();// or make the none type

	}//finally{ } 
	return new SMPLNone(); // chnage to none type

    }



}