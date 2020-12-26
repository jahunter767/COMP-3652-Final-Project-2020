import java.util.ArrayList;

/**
 * IR Class to represent a function definition
 */
public class StmtFunDefn extends Statement {
    // Implement this class

    ArrayList<String> params;
    String paramOvf;

    protected StmtFunDefn(String param, Exp body) {
    super("funDef", body);
    this.params = new ArrayList<String>();
    this.paramOvf = param;
    }

    protected StmtFunDefn(ArrayList<String> parameters, Exp body) {
    super("funDef", body);
    this.params = parameters;
    this.paramOvf = null;
    }

    protected StmtFunDefn(ArrayList<String> parameters, String paramOvf, Exp body) {
    super("proc", body);
    this.params = parameters;
    this.paramOvf = paramOvf;
    }


    public ArrayList<String> getParams(){
    return this.params;
    }

    public String getParamOvf(){
    return this.paramOvf;
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
