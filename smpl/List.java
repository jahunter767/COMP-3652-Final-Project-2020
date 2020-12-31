import java.util.*;

public class List{

    private Pair pair;
    private int len;

    //private SMPLObject nil = SMPL.makeInstance(new Nil());

    public List() { // this constructor is used by nill 
	this.pair = new Pair(SMPL.makeInstance(),
			SMPL.makeInstance(new Nil()));
	this.len = 0;
    }


    public List(ArrayList<SMPLObject> Args) {
	SMPLObject nil = SMPL.makeInstance(new Nil()); // creates new SMPLNil
	this.len = Args.size();

	Collections.reverse(Args); // reverse the order of the list
	SMPLObject rest = Args.remove(0);
	this.pair = new Pair(rest,nil); // create the last pair in the list
	for (SMPLObject obj : Args){	
		this.pair = new Pair(obj, SMPL.makeInstance(this.pair));
	}
    }


    public SMPLObject getFirstEl(){
	return this.pair.getFirstEl();
    }

    public SMPLObject getSecondEl(){
	return this.pair.getSecondEl();
    }

    public int length(){
	return this.len;
    }

	public String toString(){
	SMPLObject curr = this.pair.getFirstEl();
	if (curr instanceof SMPLNone){
		return "[]";
	}

	SMPLPair next = (SMPLPair) this.pair.getSecondEl();
	String result = "[";
	while (next instanceof SMPLPair){
		result = result + curr.toString() + ", ";
		try{
			curr = next.car();
			next = (SMPLPair) next.cdr();
		}catch (TypeException t){
			break;
		}
    }
	result = result + curr.toString() + "]";
    return result;
    }
}