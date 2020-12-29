
/**
 * IR Class to represent a function definition new vers
 */
import java.util.ArrayList;
public class StmtFunDefn extends Statement {
    //String name;
    ArrayList<String> parameters;
    StmtSequence body;
    // Implement this class


    protected StmtFunDefn() {	// placeholder; can be removed eventually
	super("funDef");
    }


    protected StmtFunDefn(ArrayList<String> funcParams, StmtSequence funcBody) {	// placeholder; can be removed eventually
	super("funDef",funcBody);
	this.parameters = funcParams;
	this.body = funcBody;
	
    }


    protected StmtFunDefn(ArrayList<String> funcParams, Exp funcBody) {	// placeholder; can be removed eventually
	super("funDef", new StmtSequence(new Statement(funcBody)));
	this.parameters = funcParams;
	this.body = new StmtSequence(new Statement(funcBody));
	
    }

/*
    public String getName() {	
	return this.name;
    }
*/
    public ArrayList<String> getParams() {	
	return this.parameters;
    }

    public StmtSequence getBody() {	
	return this.body;
    }

    public <S,T> T visit(Visitor<S,T> v, S arg) throws VisitException {	
	return v.visitStmtFunDefn(this,arg);
    }



}
