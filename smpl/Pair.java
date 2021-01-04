public class Pair{ // you could eventually declare pair with a type variable <T> so could dynamically change the type of pairs. i.e replace SMPLObject with T

    private SMPLObject obj1;
    private SMPLObject obj2;


    public Pair(SMPLObject Obj1, SMPLObject Obj2) {
	this.obj1 = Obj1;
	this.obj2 = Obj2;
    }


    public SMPLObject getFirstEl(){
	return this.obj1;
    }

    public SMPLObject getSecondEl(){
	return this.obj2;
    }

    public String toString(){
    return "(" + getFirstEl().toString() +
        ", " + getSecondEl().toString() + ")";
    }

}