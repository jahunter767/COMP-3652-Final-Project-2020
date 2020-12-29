import java.util.*;
import java.math.BigDecimal;
import java.math.MathContext;


public class Evaluator implements Visitor<Environment<Object>, Object> {
    /* For this visitor, the argument passed to all visit
       methods will be the environment object that used to
       be passed to the eval method in the first style of
       implementation. */

    // allocate state here
    protected Object result;	// result of evaluation
    private Double defaultValue;
    private Class<Object> myClass;

    protected Evaluator() {
	this(Double.NaN);
    }

    public Evaluator(Double defaultVal) {
	// perform initialisations here
	this.defaultValue = defaultVal;
	myClass = Object.class;
	result = defaultValue;
    }

    public Environment<Object> getDefaultState() {
	return Environment.makeGlobalEnv(myClass);
    }

    public Object visitArithProgram(ArithProgram p, Environment<Object> env)
	throws VisitException {
	result = p.getSeq().visit(this, env);
	return result;
    }

    public Object visitStatement(Statement s, Environment<Object> env)
    throws VisitException {
	return s.getExp().visit(this, env);
    }

    public Object visitStmtSequence(StmtSequence sseq, Environment<Object> env)
	throws VisitException {
	// remember that env is the environment
	Statement s;
	ArrayList seq = sseq.getSeq();
	Iterator iter = seq.iterator();
	Object result = defaultValue;
	while(iter.hasNext()) {
	    s = (Statement) iter.next();
	    result = s.visit(this, env);
	}
	// return last value evaluated
	return result;
    }

    public Object visitStmtDefinition(StmtDefinition sd,
				      Environment<Object> env)
	throws VisitException {
	Object result;
	result = sd.getExp().visit(this, env);
	env.put(sd.getVar(), result);
	return result;
    }
	public Object visitExpCallStmt(ExpCallStmt fd, Environment<Object> env)
	throws VisitException {
	SMPLExp lst = fd.getList(); //variable holding list
	SMPLTuple tupleList = null;
	SMPLPair pairList  = null;
	SMPLExp num = null;
	LinkedList args;
	//Type-checking
	try{
	 tupleList = (SMPLTuple) lst.visit(this,env);
	 args = (LinkedList) tupleList.getLit();	 
	}catch(Exception e){
		try {
			pairList = (SMPLPair) lst.visit(this,env);
			args = (LinkedList) pairList.getLit();
		}catch(Exception ex){
			try{
				num = (SMPLExp)(new SMPLInteger(((Integer)((BigDecimal)lst.visit(this,env)).intValue())));
				LinkedList t = new LinkedList();
				t.insert((SMPLExp)num);
				args = t;
			}catch(Exception ep){
				return 0D;
			}
		}
	}
	//End-of-type-Checking - get arguments from list
	ArrayList<SMPLExp> params = new ArrayList<SMPLExp>();
	LinkedList.Node currNode = args.head;
        // Traverse through the LinkedList 
    while (currNode != null) { 
        // Add exp to arraylist
        params.add(currNode.data); 
        currNode = currNode.next; 
        }
	//create a lambda expression with args and proc
	SMPLExp p = fd.getProc(); //possibly a var or proc
	StmtProc proc = null;
	Closure var = null;
	StmtProcDefn proc1;
	//Type-checking
	try{//Procedure found
	 proc = (StmtProc) p;	
	}catch(Exception e){ 
		try {//Variable found with closure
			var = (Closure) p.visit(this,env);
			proc1 = (StmtProcDefn) var.getFunction();
			StmtProc temp1 = new StmtProc(proc1.getExps(),proc1.getStmt(),new ArrayList<SMPLExp>());
			proc = temp1;
		}catch(Exception ex){
			return 0D; //Neither return none
		}
	}
	//End-of-type-Checking
	StmtProc lambda = new StmtProc(proc.getExps(),proc.getStmt(),params);
	System.out.println(params);
	return lambda.visit(this,env);
    }
	public Object visitStmtProc(StmtProc fd, Environment<Object> env)
	throws VisitException {
	if (fd.isLambda()==true){//Lambda proc found
		//make closure
		StmtProcDefn spd = new StmtProcDefn("p",fd.getExps(),fd.getStmt(),new ArrayList<SMPLExp>());
		Double ret = (Double) spd.visit(this,env); //create closure
		ExpProcCall epc = new ExpProcCall("p",fd.getArgs());
		return epc.visit(this,env);
	}
	return 0D;
    }
    public Object visitStmtProcDefn(StmtProcDefn fd, Environment<Object> env)
	throws VisitException {
	Closure c = new Closure(fd, env);
	env.put(fd.getName(), c); //Add function defintion binding to environment
	return 0D; 
    }

    public Object visitExpProcCall(ExpProcCall fc, Environment<Object> env)
	throws VisitException {
	// to be implemented
	StmtProcDefn function;
    Environment<Object> closingEnv;
	StmtSequence sseq;  //To be evaulated
	ArrayList<SMPLExp> params,args;
	//Extract function from closure object : Env Lookup
	Closure c = (Closure) env.get(fc.getVar());
	function = c.getFunction();
	closingEnv = env;
	//Make bindings for function call arguements
	sseq = function.getStmt();
	params = function.getExps();
	args = fc.getExp();
	ExpVar var;
	SMPLExp expLit;
	for (int i = 0; i < params.size(); i++){
	var = (ExpVar) params.get(i);
	expLit = (SMPLExp) args.get(i);
	closingEnv.put(var.getVar(), expLit.visit(this, env));	
	}
	//Evaulate function body with arg bindings
	result = sseq.visit(this,closingEnv);
	return result;
    }

    public Object visitExpAdd(ExpAdd exp, Environment<Object> env)
	throws VisitException {
	SMPLNumber val1, val2;
	val1 = new SMPLNumber(exp.getExpL().visit(this, env),"SMPLNumber");
	val2 = new SMPLNumber(exp.getExpR().visit(this, env),"SMPLNumber");
	return val1.getVal().add(val2.getVal());
    }

    public Object visitExpSub(ExpSub exp, Environment<Object> env)
	throws VisitException {
	SMPLNumber val1, val2;
	val1 = new SMPLNumber(exp.getExpL().visit(this, env),"SMPLNumber");
	val2 = new SMPLNumber(exp.getExpR().visit(this, env),"SMPLNumber");
	return val1.getVal().subtract(val2.getVal());
    }

    public Object visitExpMul(ExpMul exp, Environment<Object> env)
	throws VisitException {
	SMPLNumber val1, val2;
	val1 = new SMPLNumber(exp.getExpL().visit(this, env),"SMPLNumber");
	val2 = new SMPLNumber(exp.getExpR().visit(this, env),"SMPLNumber");
	return val1.getVal().multiply(val2.getVal());
    }

    public Object visitExpDiv(ExpDiv exp, Environment<Object> env)
	throws VisitException {
	SMPLNumber val1, val2;
	val1 = new SMPLNumber(exp.getExpL().visit(this, env),"SMPLNumber");
	val2 = new SMPLNumber(exp.getExpR().visit(this, env),"SMPLNumber");
	return val1.getVal().divide(val2.getVal(), MathContext.DECIMAL64);
    }

    public Object visitExpMod(ExpMod exp, Environment<Object> env)
	throws VisitException {
	Double val1, val2;
	val1 = (Double) exp.getExpL().visit(this, env);
	val2 = (Double) exp.getExpR().visit(this, env);
	return val1 % val2;
    }

    public Object visitExpLit(ExpLit exp, Environment<Object> env)
	throws VisitException {
	if (exp.getType()=="SMPLPair" || exp.getType()=="SMPLTuple"){
		LinkedList lst =  new LinkedList();
		LinkedList temp = (LinkedList) exp.getLit();
		LinkedList.Node currNode = temp.head;
		while (currNode != null) { 
            // Print the data at current node 
			
            SMPLExp e = currNode.data;
			SMPLNumber val1 = new SMPLNumber(0,"");
			try{
				val1 = new SMPLNumber(e.visit(this, env),"");
			}catch(Exception ex){
				
			}
			lst.insert(val1);
            // Go to next node 
            currNode = currNode.next; 
        }
		if(exp.getType() == "SMPLTuple"){
			lst.head = lst.reverse(lst.head);
		} 
		exp.setLit(lst); //resave evaluated tupe
		if(exp.getType()=="SMPLPair" || exp.getType()=="SMPLTuple"){
			//System.out.println(lst.toSMPLPair());
			return LinkedList.createSMPLTuple(lst);
		}
		
	}else if(exp.getType()=="SMPLVector"){
		SMPLTuple tup = null;
		SMPLPair pair = null;
		SMPLNumber val = null;
		LinkedList args = null; //holds temporary data
		LinkedList lst =  new LinkedList();
		LinkedList temp = (LinkedList) exp.getLit();
		LinkedList.Node currNode = temp.head;
		while (currNode != null) { 
            // Print the data at current node 
			
            SMPLExp e = currNode.data;
			SMPLNumber val1 = new SMPLNumber(0,"");
			try{
				tup = (SMPLTuple) e.visit(this,env);
				args = (LinkedList) tup.getLit();	 //traverse args and add to 
				args.head = args.reverse(args.head);
				LinkedList.Node currNode1 = args.head;
				while(currNode1!=null){
					SMPLExp ex = currNode1.data;
					SMPLNumber val2 = new SMPLNumber(0,"");
					try{
						val2 = new SMPLNumber(ex.visit(this, env),"");
						}catch(Exception ex3){
						}
					lst.insert(val2);
					currNode1 = currNode1.next;
				}
				}catch(Exception ep1){
				try {
					pair = (SMPLPair) e.visit(this,env);
					args = (LinkedList) pair.getLit();
					//args.head = args.reverse(args.head);
					LinkedList.Node currNode1 = args.head;
					while(currNode1!=null){
					SMPLExp ex = currNode1.data;
					SMPLNumber val2 = new SMPLNumber(0,"");
					try{
						val2 = new SMPLNumber(ex.visit(this, env),"");
						}catch(Exception ex3){
						}
					lst.insert(val2);
					currNode1 = currNode1.next;
				}
				}catch(Exception ex1){
					try{
						val = new SMPLNumber(e.visit(this, env),"");
						lst.insert((SMPLExp)val);
						}catch(Exception ep){
							return 0D;
											}
									}
								}
            currNode = currNode.next; 
        }
		lst.head = lst.reverse(lst.head);
		exp.setLit(lst); //resave evaluated tupe
		ArrayList<SMPLExp> argList = new ArrayList<SMPLExp>();
		LinkedList.Node currNode2 = lst.head;
		while(currNode2!=null){
			argList.add((SMPLExp) currNode2.data);
			currNode2 = currNode2.next;
		}
		SMPLVector s = new SMPLVector(argList);
		//System.out.println(s.getVal());
		return s;
	}
	else{
		return exp.getVal();
		}
	return null;
    }

    public Object visitExpVar(ExpVar exp, Environment<Object> env)
	throws VisitException {
	SMPLTuple t = null;
	SMPLPair p = null;
	SMPLVector v = null;
	try {
		v = (SMPLVector) env.get(exp.getVar());
		System.out.println(v.getVal());
		return v;
		
	}catch(Exception e){
		try {
			t = (SMPLTuple) env.get(exp.getVar());
			System.out.println(t.getVal());
			return t;
		}catch(Exception e1){
			try{
				 p = (SMPLPair) env.get(exp.getVar());
				 System.out.println(p.getVal());
				 return p;
			}catch(Exception e2){
				return env.get(exp.getVar());
			}
		}
	}
	
    }
	
	public Object visitCompareL(CompareL exp,Environment<Object> env) 
	throws VisitException {
	Boolean r;
	if (exp.getExpR().visit(this, env) instanceof Boolean){
		exp.setSubTree(1,CompareG.nextVal);
	}
	SMPLNumber val1 = new SMPLNumber(exp.getExpL().visit(this, env),"");
	SMPLNumber val2 = new SMPLNumber(exp.getExpR().visit(this, env),"");
	r = val1.getVal().doubleValue() < val2.getVal().doubleValue(); 
	CompareG.nextVal = exp.getExpL();
	return r;
	}

	public Object visitCompareLE(CompareLE exp, Environment<Object> env) 
	throws VisitException {
	Boolean r;
	if (exp.getExpR().visit(this, env) instanceof Boolean){
		exp.setSubTree(1,CompareG.nextVal);
	}
	SMPLNumber val1 = new SMPLNumber(exp.getExpL().visit(this, env),"");
	SMPLNumber val2 = new SMPLNumber(exp.getExpR().visit(this, env),"");
	r = val1.getVal().doubleValue() <= val2.getVal().doubleValue(); 
	CompareG.nextVal = exp.getExpL();
	return r;
	}
	public Object visitCompareE(CompareE exp, Environment<Object> env) 
	throws VisitException {
	Boolean r;
	if (exp.getExpR().visit(this, env) instanceof Boolean){
		exp.setSubTree(1,CompareG.nextVal);
	}
	SMPLNumber val1 = new SMPLNumber(exp.getExpL().visit(this, env),"");
	SMPLNumber val2 = new SMPLNumber(exp.getExpR().visit(this, env),"");
	r = val1.getVal().doubleValue() == val2.getVal().doubleValue(); 
	CompareG.nextVal = exp.getExpL();
	return r;
	}
	public Object visitCompareNE(CompareNE exp, Environment<Object> env) 
	throws VisitException {
	Boolean r;
	if (exp.getExpR().visit(this, env) instanceof Boolean){
		exp.setSubTree(1,CompareG.nextVal);
	}
	SMPLNumber val1 = new SMPLNumber(exp.getExpL().visit(this, env),"");
	SMPLNumber val2 = new SMPLNumber(exp.getExpR().visit(this, env),"");
	r = val1.getVal().doubleValue() != val2.getVal().doubleValue(); 
	CompareG.nextVal = exp.getExpL();
	return r;
	}
	
	public Object visitCompareG(CompareG exp, Environment<Object> env) 
	throws VisitException {
	Boolean r;
	if (exp.getExpR().visit(this, env) instanceof Boolean){
		exp.setSubTree(1,CompareG.nextVal);
	}
	SMPLNumber val1 = new SMPLNumber(exp.getExpL().visit(this, env),"");
	SMPLNumber val2 = new SMPLNumber(exp.getExpR().visit(this, env),"");
	r = val1.getVal().doubleValue() > val2.getVal().doubleValue(); 
	CompareG.nextVal = exp.getExpL();
	return r;
	}
	
	public Object visitCompareGE(CompareGE exp, Environment<Object> env) 
	throws VisitException {
	Boolean r;
	if (exp.getExpR().visit(this, env) instanceof Boolean){
		exp.setSubTree(1,CompareG.nextVal);
	}
	SMPLNumber val1 = new SMPLNumber(exp.getExpL().visit(this, env),"");
	SMPLNumber val2 = new SMPLNumber(exp.getExpR().visit(this, env),"");
	r = val1.getVal().doubleValue() >= val2.getVal().doubleValue(); 
	CompareG.nextVal = exp.getExpL();
	return r;
	}
	
	public Object visitStmtAnd(StmtAnd exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	Boolean flag = true;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	flag = flag & (Boolean) val1 & (Boolean) val2;
	return flag;
	}
	
	public Object visitStmtOr(StmtOr exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	Object val2;
	Boolean flag = false;
	val1 = (Object) exp.getExpL().visit(this, env);
	val2 = (Object) exp.getExpR().visit(this, env);
	flag = flag | (Boolean) val1 | (Boolean) val2;
	return flag;
	}
	
	public Object visitStmtNot(StmtNot exp, Environment<Object> env) 
	throws VisitException {
	Object val1;
	val1 = (Object) exp.getExpL().visit(this, env);
	if ((Boolean) val1 == true){
		return false;
	}else {
		return true;
	}
	}
	
	public Object visitStmtIfDefn(StmtIfDefn exp, Environment<Object> env) 
	throws VisitException {
	SMPLExp p = exp.getPredicate();
	StmtSequence c = exp.getConsequent();
	Statement a = exp.getAlternative();
	Boolean flag = (Boolean) p.visit(this, env);
	if (flag == true){
		result = (Double) c.visit(this,env);
	}else{
		result = (Double) a.visit(this,env);
	}
	
	return result;
	}
	
	public Object visitStmtCaseDefn(StmtCaseDefn exp, Environment<Object> env) 
	throws VisitException {
	ArrayList<Pair> clauses = exp.getClauses();
	Boolean flag = false;
	Boolean r;
	int i = 0;
	for(int c=0; c<clauses.size();c++){
		r = (Boolean) clauses.get(c).getPred().visit(this,env) ;
		if( r == true){
			flag =true;
			i = c;
			break;
		}
	}
	if(flag == true){
	return clauses.get(i).getConseq().visit(this,env);
	}
	else{
		return false;
	}
	}
	
	public Object visitSubVector(SubVector exp, Environment<Object> env) 
	throws VisitException {
	LinkedList subVector = new LinkedList();
	Pair SubV = exp.getSubVectorExp();
	SMPLNumber c = new SMPLNumber(SubV.getPred().visit(this, env),""); //can either be var or raw lit
	StmtProc proc = null;
	StmtProc p = null;
	Closure cs = null;
	try{
		p = (StmtProc) SubV.getConseq();
		proc = p;                                           //Proc
	}catch(Exception e){
		try{
			cs = (Closure) SubV.getConseq().visit(this,env); //variable
			StmtProc temp = new StmtProc(cs.getFunction().getExps(),cs.getFunction().getStmt(),new ArrayList<SMPLExp>());
			proc = temp;
		}catch(Exception ex){
			return 0D;
		}
	}
	int b = ((BigDecimal) c.visit(this,env)).intValue();
	for(int i = 0; i<b; i++){
		ArrayList<SMPLExp> args = new ArrayList<SMPLExp>();
		args.add(new SMPLInteger(i));
		StmtProc eval = new StmtProc(proc.getExps(),proc.getStmt(),args);
		subVector.insert((SMPLExp)(new SMPLInteger(((Integer)((BigDecimal)eval.visit(this,env)).intValue()))));
	}
	System.out.println(subVector.toSMPLVector());
	return LinkedList.createSMPLTuple(subVector);
	}
	public Object visitTupleOperations(TupleOperations exp, Environment<Object> env) 
	throws VisitException {
	 SMPLTuple tup = null;
	 SMPLPair pair = null;
	 SMPLVector vect = null;
	 String type = null ;
	 String op = null;
	 Boolean variableFound = false;
	 ExpVar var = null;
		 try{
			 tup = (SMPLTuple) exp.getTup().visit(this, env);
			 type = "SMPLTuple";
			 variableFound = true;
			 var = (ExpVar) exp.getTup();
		 }catch(Exception e){
			  try{
				  pair = (SMPLPair) exp.getTup().visit(this, env);
				  type = "SMPLPair";
				  variableFound = true;
				  var = (ExpVar) exp.getTup();
			  }catch(Exception e1){
				  try{
					  vect = (SMPLVector) exp.getTup().visit(this, env);
					  type = "SMPLVector";
					  variableFound = true;
					  var = (ExpVar) exp.getTup();
				  }catch(Exception e2){
					  try{
						  tup = (SMPLTuple) exp.getTup();
						  type = "SMPLTuple";
					  }catch(Exception e3){
						  try{
							  pair = (SMPLPair) exp.getTup();
							  type = "SMPLPair";
						  }catch(Exception e4){
							  try{
							  vect = (SMPLVector) exp.getTup();
							  type = "SMPLVector";
							  }catch(Exception e5)
							  {
								  op = exp.getOp();
							  }
						  }
					  }
				  }
				  
			  }
		 }
		 //Check for var
			if (var == null){
				variableFound = false;
			}else
			{
				variableFound = true;
			}
		 //End of Check
		 SMPLNumber c = null; //expression : index or value
		 op = exp.getOp();   // operation 
		 if(op == "get"){
			if (type == "SMPLVector"){
				c = new SMPLNumber(exp.getVal().visit(this, env),"");
				return vect.getTuple().findByIndex(((BigDecimal)c.getVal()).intValue());
			} else if(type=="SMPLPair"){
				c = new SMPLNumber(exp.getVal().visit(this, env),"");
				System.out.println(pair.getVal());
				return pair.getTuple().findByIndex(((BigDecimal)c.getVal()).intValue());
			}
		 }else if(op == "set"){
			 if(type == "SMPLVector"){
				 if(variableFound==true){//set variable return variable or vector
					 c = new SMPLNumber(exp.getVal().visit(this, env),"");
					 vect.insert((SMPLExp) c);
					 env.put(var.getVar(),vect);
					 return vect.getVal();
				 }else{//return new vector
					 c = new SMPLNumber(exp.getVal().visit(this, env),"");
					 LinkedList rev = vect.getTuple();
					 rev.head = rev.reverse(rev.head);
					 vect.setVal(rev);
					 vect.insert((SMPLExp) c); 
					 return vect.getVal();
				 }
			 }
		 }else if(op =="size"){
			  LinkedList vectt =null;
			 if(type=="SMPLVector"){
				 vectt = vect.getTuple();
				 if(variableFound==true){
					 LinkedList.Node currNode = vectt.head;
						Integer count = new Integer(0);
						while(currNode!=null){
							currNode = currNode.next;
							count = count + 1;
						}
						return new SMPLInteger(count);
						//return vect.getVal();
				 }else{
						LinkedList.Node currNode = vectt.head;
						Integer count = new Integer(0);
						while(currNode!=null){
							currNode = currNode.next;
							count = count + 1;
						}
						LinkedList rev = vect.getTuple();
						rev.head = rev.reverse(rev.head);
						vect.setVal(rev);
						return new SMPLInteger(count);
						//return vect.getVal();
				 }
				 
			 }
				
		 }
		 
		 return op;
	}
	public Object visitPrintLn(PrintLn exp, Environment<Object> env) 
	throws VisitException {
	if(exp.getFlag() == false){
		System.out.print(exp.getExp().visit(this,env));
	}else{
		System.out.println(exp.getExp().visit(this,env));

	}
	return 0D;
	}
	
	public Object visitEqualityOperations(EqualityOperations exp, Environment<Object> env) 
	throws VisitException {
	SMPLExp x,y;
	ExpLit z,w;
	if(exp.getOp()=="equal?"){
	return new SMPLBoolean(new Boolean(exp.getObj1().visit(this,env).equals(exp.getObj2().visit(this,env))));
	}else{
	return new SMPLBoolean(new Boolean(exp.getObj1().visit(this,env)==exp.getObj2().visit(this,env)));
	}
	}
	public Object visitLetStmt(LetStmt exp, Environment<Object> env) 
	throws VisitException {
	ArrayList<Pair> bindings = exp.getBindings();
	for(Pair b : bindings){
		env.put(((ExpVar)b.getPred()).getVar(),b.getConseq().visit(this,env));
	}
	return exp.getBody().visit(this,env);
	}
	
	public Object visitSliceOperations(SliceOperations exp, Environment<Object> env) 
	throws VisitException {
	String s = null;
	SMPLString s1 = null;
	ExpVar v;
	try{
		s = (String) exp.getObj().visit(this,env);
	}catch(Exception e){
		s1 = (SMPLString) exp.getObj(); 
	}
	int start = ((BigDecimal)exp.getInit().visit(this,env)).intValue();
	int end =   ((BigDecimal)exp.getEnd().visit(this,env)).intValue();
	
	return new SMPLString(new String(s.substring(start,end)));
	}
	
}
