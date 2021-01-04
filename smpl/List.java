import java.util.*;
public class List{

    private Pair pair;
    private SMPLObject el;
    private ArrayList<SMPLObject> elements;
    private int len;

    //private SMPLObject nil = SMPL.makeInstance(new Nil());

    public List() { // this constructor is used by nill 
	this.el = SMPL.makeInstance();
	this.pair = new Pair(el,el);
	this.len = 0;
	this.elements = new ArrayList<SMPLObject>();

    }

    public List(Pair pair, int length, ArrayList<SMPLObject> els) { // this constructor is when creating a sub list from an existing list
	this.el = SMPL.makeInstance();
	this.pair = pair;
	this.len = length - 1;
	this.elements = (ArrayList)els.clone();
	this.elements.remove(0);
    }


    public List(ArrayList<SMPLObject> Args) {
	this.elements = (ArrayList)Args.clone();
	this.len = Args.size();
	SMPLObject nil = SMPL.makeInstance(new Nil()); // creates new SMPLNil

	if(Args.size() == 1){ // create pair with nill
		this.el = Args.get(0);
		this.pair = new Pair(el, nil);

	} else {
		// Building the sequence of pairs in reverse
		
		Collections.reverse(Args); // reverse the order of the list
		this.el = Args.get(0); // get the last element
		Args.remove(0);// remove the last element
		
		Pair element = new Pair(el,nil); // create the last pair in the list
		Pair next = null;
		
		for (SMPLObject obj : Args){
			
			next = new Pair(obj,SMPL.makeInstance(element));
			element = next;
		}
		
		this.pair = element; // a sequence of pairs 
	
	}

    }


    public SMPLObject getFirstEl(){
	return this.pair.getFirstEl(); // [Val,another pair in the form of a SMPLPair] -> return val
    }

    public SMPLObject getSecondEl(){
	return this.pair.getSecondEl(); // [Val,another pair] -> return another pair or nill SMPL instance if empty
    }


    public ArrayList<SMPLObject> getElements(){
	return this.elements; // returns the elements of the list
    }

    public int length(){
	return this.len;
    }

public String toString(){
	SMPLObject curr = this.pair.getFirstEl();
	if (curr instanceof SMPLNone){
		return "[]";
	}

	String result = "[";
	try{
		SMPLPair next = (SMPLPair) this.pair.getSecondEl();
		while (next instanceof SMPLPair){
			result = result + curr.toString() + ", ";
			try{
				curr = next.car();
				next = (SMPLPair) next.cdr();
			}catch (TypeException t){
				break;
			}
		}
	}catch (ClassCastException c){
		result = result + curr.toString() + "]";
	}
	return result;
    }




}