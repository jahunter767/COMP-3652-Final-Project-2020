import java.util.*;

/**
 * new vers
 * An instance of class <code>Environment</code> maintains a
 * collection of bindings from valid identifiers to integers.
 * It supports storing and retrieving bindings, just as would
 * be expected in any dictionary.
 *
 * @author <a href="mailto:dcoore@uwimona.edu.jm">Daniel Coore</a>
 * @version 1.0
 */
public class Environment<T> {

    Environment<T> parent;
    HashMap<String, T> dictionary;
    //HashMap<String, Closure> closureDictionary = new HashMap<>(); 


    /**
     * Create a new (empty) top level Environment.
     *
     */
    public Environment() {
	parent = null;
	dictionary = new HashMap<>();
    }

    /**
     * Creates a new <code>Environment</code> instance that is
     * initialised with the given collection of bindings
     * (presented as separate arrays of names and values).
     *
     * @param ids the collection of identifiers to be bound.
     * @param values the corresponding collection of values
     * for the identifiers.  Note that the two arrays must
     * have the same length.
     */
    public Environment(Environment<T> parent, String[] ids, T[] values) {
	this.parent = parent;
	dictionary = new HashMap<>();
	for (int i = 0; i < ids.length; i++) {
	    dictionary.put(ids[i], values[i]);
	}
    }

/*
    public Environment(Environment<T> parent, String[] ids, T[] values, String[] Fids, Closure[] closures) {
	this.parent = parent;
	dictionary = new HashMap<>();
	for (int i = 0; i < ids.length; i++) {
	    dictionary.put(ids[i], values[i]);
	}

	for (int i = 0; i < Fids.length; i++) {
	    closureDictionary.put(Fids[i], closures[i]);
	}
	
    }

*/

    /**
     * Creates a new <code>Environment</code> instance that is
     * initialised with the given collection of bindings
     * (presented as separate array lists of names and values).
     *
     * @param ids the collection of identifiers to be bound.
     * @param values the corresponding collection of values
     * for the identifiers.  Note that the two lists must
     * have the same length.
     */
    public Environment(Environment<T> parent, ArrayList<String> ids,
		       ArrayList<T> values) {
	this.parent = parent;
	dictionary = new HashMap<>();
	for (int i = 0; i < ids.size(); i++) {
	    dictionary.put(ids.get(i), values.get(i));
	}
    }

/*

    public Environment(Environment<T> parent, ArrayList<String> ids,
		       ArrayList<T> values, ArrayList<String> Fids, ArrayList<Closure> closures) {
	this.parent = parent;
	dictionary = new HashMap<>();
	for (int i = 0; i < ids.size(); i++) {
	    dictionary.put(ids.get(i), values.get(i));
	}

	for (int i = 0; i < Fids.size(); i++) {
	    closureDictionary.put(Fids.get(i), closures.get(i));
	}

    }

*/

    /**
     * Create an instance of a global environment suitable for
     * evaluating an program.
     *
     * @return the <code>Environment</code> created.
     */
    public static <b> Environment<b> makeGlobalEnv (Class<b> cls) {
	Environment<b> result =  new Environment<b>();
	// add definitions for any primitive procedures or
	// constants here

	// ----- BUILTIN METHOD FOR SUBSTR -----
	ArrayList<String> parameters = new ArrayList<String>();
	parameters.add("string");
	parameters.add("start");
	parameters.add("end");
	StmtSequence body = new StmtSequence(new Statement(new Substr(new ExpVar("string"),new ExpVar("start"),new ExpVar("end"))));
	StmtFunDefn fd = new StmtFunDefn(parameters, body);
	Closure closure = new Closure(fd,result);
	SMPLObject substr = SMPL.makeInstance(closure);
	result.put("substr",(b) substr);

	// ----- BULTIN METHOD FOR EQV? -----
	ArrayList<String> Eqvparameters = new ArrayList<String>();
	Eqvparameters.add("eqvobj1");
	Eqvparameters.add("eqvobj2");
	body = new StmtSequence(new Statement(new Eqv(new ExpVar("eqvobj1"),new ExpVar("eqvobj2"))));
	fd = new StmtFunDefn(Eqvparameters, body);
	closure = new Closure(fd,result);
	SMPLObject eqv = SMPL.makeInstance(closure);
	result.put("eqv?", (b) eqv);

	// ----- BULTIN METHOD FOR EQUAL? -----
	ArrayList<String> equalParameters = new ArrayList<String>();
	equalParameters.add("equalobj1");equalParameters.add("equalobj2");
	body = new StmtSequence(new Statement(new Equal(new ExpVar("equalobj1"),new ExpVar("equalobj2"))));
	fd = new StmtFunDefn(equalParameters, body);
	closure = new Closure(fd,result);
	SMPLObject equal = SMPL.makeInstance(closure);
	result.put("equal?", (b) equal);


	// ----- BULTIN METHOD FOR PAIR -----
	ArrayList<String> pairParameters = new ArrayList<String>();
	pairParameters.add("pairObj1");pairParameters.add("pairObj2");
	body = new StmtSequence(new Statement(new ExpPair(new ExpVar("pairObj1"),new ExpVar("pairObj2"))));
	fd = new StmtFunDefn(pairParameters, body);
	closure = new Closure(fd,result);
	SMPLObject pair = SMPL.makeInstance(closure);
	result.put("pair", (b) pair);


	// ----- BULTIN METHOD FOR PAIR? -----
	ArrayList<String> ispairParameters = new ArrayList<String>();
	ispairParameters.add("ispairObj");
	body = new StmtSequence(new Statement(new isPair(new ExpVar("ispairObj"))));
	fd = new StmtFunDefn(ispairParameters, body);
	closure = new Closure(fd,result);
	SMPLObject ispair = SMPL.makeInstance(closure);
	result.put("pair?", (b) ispair);

	// ----- BULTIN METHOD FOR CAR -----
	ArrayList<String> carParameters = new ArrayList<String>();
	carParameters.add("carObj");
	body = new StmtSequence(new Statement(new Car(new ExpVar("carObj"))));
	fd = new StmtFunDefn(carParameters, body);
	closure = new Closure(fd,result);
	SMPLObject car = SMPL.makeInstance(closure);
	result.put("car", (b) car);


	// ----- BULTIN METHOD FOR CDR -----
	ArrayList<String> cdrParameters = new ArrayList<String>();
	cdrParameters.add("cdrObj");
	body = new StmtSequence(new Statement(new Cdr(new ExpVar("cdrObj"))));
	fd = new StmtFunDefn(cdrParameters, body);
	closure = new Closure(fd,result);
	SMPLObject cdr = SMPL.makeInstance(closure);
	result.put("cdr", (b) cdr);



	// ----- BULTIN METHOD FOR LIST -----
	// to be implemented when functions can accept any number of inputs
	
	return result;
    }

    /**
     * Store a binding for the given identifier to the given
     * int within this environment.
     *
     * @param id the name to be bound
     * @param value the value to which the name is bound.
     */
    public void put(String id, T value) {
	dictionary.put(id, value);
    }

/*
    public double putClosure(String Fid, Closure closure) {
	closureDictionary.put(Fid, closure);
	return 1000D;
    }

*/ 

    /**
     * Return the int associated with the given identifier.
     *
     * @param id the identifier.
     * @return the int associated with the identifier in
     * this environment.
     * @exception Exception if <code>id</code> is unbound
     */

    public T get(String id) throws UnboundVarException {
	T result = dictionary.get(id);
	if (result == null)
	    if (parent == null)
		throw new UnboundVarException(id);
	    else
		return parent.get(id);
	else
	    return result;
    }

/*
    public Closure getClosure(String Fid) throws UnboundVarException {
	Closure result = closureDictionary.get(Fid);
	if (result == null)
	    if (parent == null)
		throw new UnboundVarException(Fid);
	    else
		return parent.getClosure(Fid);
	else
	    return result;
    }

*/


// ------ FROM JASON's CODE --------

    public void update(String id, T value) throws UnboundVarException {
	T result = dictionary.get(id);
	if (result == null){
	    if (parent == null){
    		throw new UnboundVarException(id);
        }else{
            parent.update(id, value);
        }
    }else{
        dictionary.replace(id, value);
    }
    }


//----------------------------------

    /**
     * Create a string representation of this environment.
     *
     * @return a string of all the names bound in this
     *         environment.
     */
    public String toString() {
	StringBuffer result = new StringBuffer();

	Iterator iter = dictionary.keySet().iterator();
	while (iter.hasNext()) {
	    result = result.append(iter.next().toString());
	}
	return result.toString();
    }

}
