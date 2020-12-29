public class PrintLn extends SMPLExp {
	Boolean newLine = false;
	SMPLExp exp;

    public PrintLn(SMPLExp xp, Boolean flag) {
	super(xp.toString());
	newLine = flag;
	exp = xp;
    }

    public Boolean getFlag(){
		return newLine;
	}

    public String toString() {
	return exp.toString();
    }
	
	public SMPLExp getExp(){
		return exp;
	}
	
	
	public <S, T> T visit(Visitor<S,T> v, S arg) throws VisitException {
	return v.visitPrintLn(this, arg);
    }
	
}