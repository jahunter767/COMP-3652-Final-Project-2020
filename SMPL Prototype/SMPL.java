import java.lang.*;

public class SMPL {

    public SMPL(){
    }


    public static SMPLObject makeInstance(Object obj){

	try{
	
		if(Class.forName("java.lang.Number").isInstance(obj)) return new SMPLNumbers("Numbers",(Integer)obj );

	}catch(ClassNotFoundException ex) { 
		System.out.println(ex.toString());
		return null;// or make the none type

	}//finally{ } 
	return null;

    }



}