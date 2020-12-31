import java.util.*;
public class Vector{

    private ArrayList<SMPLObject> vect;

    public Vector() { // this constructor is used by nill 
	this.vect = new ArrayList<SMPLObject>();
    }


    public Vector(ArrayList<SMPLObject> Args) {
    this.vect = Args;
	}


    public SMPLObject get(int i){
	return this.vect.get(i);
    }

    public void set(int i, SMPLObject el){
	this.vect.set(i, el);
    }

    public int length(){
	return this.vect.size();
    }

    public String toString(){
    String result = "[:";
    SMPLObject el;
    int i;
    for (i = 0; i < this.vect.size()-1; i ++){
        el = this.vect.get(i);
        result = result + el.toString() + ", ";
    }
    el = this.vect.get(i);
    result = result + el.toString() + ":]";
    return result;
    }
}