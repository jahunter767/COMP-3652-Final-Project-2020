/**
 * The generic Visitor interface for the Arithmetic parser
 * example.
 * @param <S> The type of the information needed by the visitor
 * @param <T> The type of result returned by the visitor 
 */
public interface Visitor<S, T> {

    // to facilitate generic constructors
    public S getDefaultState();

    // program
    public T visitArithProgram(ArithProgram p, S arg) throws VisitException;

    // statements
    public T visitStatement(Statement exp, S arg) throws VisitException ;
    public T visitStmtSequence(StmtSequence exp, S arg) throws VisitException ;
    public T visitStmtDefinition(StmtDefinition sd, S arg) throws VisitException;

    public T visitStmtProcDefn(StmtProcDefn fd, S arg) throws VisitException;
    public T visitExpProcCall(ExpProcCall fc, S arg) throws VisitException;
	public T visitStmtProc(StmtProc fc, S arg) throws VisitException;
	public T visitExpCallStmt(ExpCallStmt fc, S arg) throws VisitException;
	public T visitSubVector(SubVector fc, S arg) throws VisitException;



    // expressions
    public T visitExpAdd(ExpAdd exp, S arg) throws VisitException;
    public T visitExpSub(ExpSub exp, S arg) throws VisitException;
    public T visitExpMul(ExpMul exp, S arg) throws VisitException;
    public T visitExpDiv(ExpDiv exp, S arg) throws VisitException;
    public T visitExpMod(ExpMod exp, S arg) throws VisitException;
    public T visitExpVar(ExpVar exp, S arg) throws VisitException;
	public T visitExpLit(ExpLit exp, S arg) throws VisitException;
	
	//Relational or Compare expressions
	public T visitCompareL(CompareL exp, S arg) throws VisitException;
	public T visitCompareLE(CompareLE exp, S arg) throws VisitException;
	public T visitCompareG(CompareG exp, S arg) throws VisitException;
	public T visitCompareGE(CompareGE exp, S arg) throws VisitException;
	public T visitCompareE(CompareE exp, S arg) throws VisitException;
	public T visitCompareNE(CompareNE exp, S arg) throws VisitException;
	//Boolean
	public T visitStmtNot(StmtNot exp, S arg) throws VisitException;
	public T visitStmtAnd(StmtAnd exp, S arg) throws VisitException;
	public T visitStmtOr(StmtOr exp, S arg) throws VisitException;
	//IF Statement
	public T visitStmtIfDefn(StmtIfDefn exp, S arg) throws VisitException;
	public T visitStmtCaseDefn(StmtCaseDefn exp, S arg) throws VisitException;
	//SMPL Objects
}
