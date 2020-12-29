import java.io.*;
import java.util.*;
public class SMPLTuple extends SMPLObject{
	ArrayList<SMPLExp> exp;
	LinkedList tuple;
	public SMPLTuple(ArrayList<SMPLExp> xp){
		super(xp,"SMPLTuple");
		tuple = new LinkedList();
		for(SMPLExp e: xp){
		tuple.insert(e);
		}
		this.V = tuple;
	}
	
	public LinkedList getTuple(){
		return tuple;
	}
	public void setVal(LinkedList tuple){
		this.V = tuple;
	}
	public String getVal(){
		return tuple.toSMPLPair();
	}
	
	public SMPLExp car(){
		return tuple.findByIndex(0);
	}
	
	public SMPLExp cdr(){
		return tuple.findByIndex(1);
	}
	public void setType(String type){
		this.SMPLType = type;
	}
	
	public static SMPLExp createTuple(ArrayList<SMPLExp> exp){
		if(exp.size()==2){
			return new SMPLPair(exp.get(0),exp.get(1));
		}else{
			return new SMPLTuple(exp);
		}
		
	}
	
	
	
	
}