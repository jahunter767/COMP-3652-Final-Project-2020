import java.util.ArrayList;

/**
 * IR Class to represent a function definition
 */
public class StmtFunDefn extends Statement {
    // Implement this class

    String name;
    ArrayList<String> params;

    protected StmtFunDefn(String functionName, ArrayList<String> parameters, Exp body) {	// placeholder; can be removed eventually
    super("funDef", body);
    this.name = functionName;
    this.params = parameters;
    }

    public String getName(){
    return this.name;
    }

    public ArrayList<String> getParameters(){
    return this.params;
    }

    public Exp getBody(){
    return super.getSubTree(0);
    }

    public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
    return v.visitStmtFunDefn(this, arg);
    }
    
    public String toString(){
    return super.getName() + ": " + getName();
    }
    
}
