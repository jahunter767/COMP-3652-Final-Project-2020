import java.util.ArrayList;
import java.util.Arrays;


public abstract class SMPLObject<T>{
    private T val;    // the value that is wrapped in each subclass

    public SMPLObject(T val){
	this.val = val;
    }

    public SMPLObject() {
	this.val = null;
    }

    public T getVal() {
	return val;
    }


    public SMPLObject bitwiseAnd(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject bitwiseOr(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject bitwiseNot() throws TypeException {
	throw new TypeException();
    }


    public SMPLObject and(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject or(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject not() throws TypeException {
	throw new TypeException();
    }


    public SMPLObject lessThan(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject lessThanEq(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject greaterThanEq(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject greaterThan(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject notEqualTo(SMPLObject object) throws TypeException {
	throw new TypeException();
    }


    public SMPLObject add(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject subtract(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject multiply(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject divide(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject mod(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject pow(SMPLObject object) throws TypeException {
	throw new TypeException();
    }


    public SMPLObject car(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject cdr(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject concat(SMPLObject object) throws TypeException {
	throw new TypeException();
    }

    public SMPLObject eqv(SMPLObject object) throws TypeException{
	throw new TypeException();
    }

    public SMPLObject Substr(SMPLObject arg1, SMPLObject arg2) throws TypeException, SubstringException{
	throw new TypeException();
    }

    public abstract String toString();

}
