import java.util.*;
public class List{

    private Pair pair;
    private SMPLObject el;
    private int len;

    //private SMPLObject nil = SMPL.makeInstance(new Nil());

    public List() { // this constructor is used by nill 
	this.el = SMPL.makeInstance();
	this.pair = new Pair(el,el);
	this.len = 0;

    }

    public List(Pair pair, int length) { // this constructor is when creating a new list from an existing list
	this.el = SMPL.makeInstance();
	this.pair = pair;
	this.len = length - 1;

    }


    public List(ArrayList<SMPLObject> Args) {
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

    public int length(){
	return this.len;
    }

}