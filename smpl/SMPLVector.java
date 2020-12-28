import java.io.*;
import java.util.*;
public class SMPLVector extends SMPLObject{
	ArrayList<SMPLExp> exp;
	LinkedList vector;
	public SMPLVector(ArrayList<SMPLExp> xp){
		super(xp,"SMPLVector");
		vector = new LinkedList();
		for(SMPLExp e: xp){
		vector.insert(e);
		}
		this.V = vector;
	}
	public LinkedList getTuple(){
		return vector;
	}
	public void setVal(LinkedList vector){
		this.V = vector;
	}
	public String getVal(){
		return vector.toSMPLVector();
	}
	
}
		