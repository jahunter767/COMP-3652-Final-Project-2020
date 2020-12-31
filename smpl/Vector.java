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
    for (SMPLObject el: this.vect){
        result = result + el.toString();
    }
    result = result + ":]";
    return result;
    }
}