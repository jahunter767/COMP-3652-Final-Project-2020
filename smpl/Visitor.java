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

    public T visitStmtPrint(StmtPrint p, S arg) throws VisitException;
    public T visitStmtPrintln(StmtPrintln p, S arg) throws VisitException;


    public T visitStmtDefinition(StmtDefinition sd, S arg) throws VisitException;

    public T visitStmtAssignment(StmtAssignment sa, S arg) throws VisitException;


    public T visitStmtFunDefn(StmtFunDefn fd, S arg) throws VisitException;

    public T visitExpFunCall(ExpFunCall fc, S arg) throws VisitException;
    public T visitExpCall(ExpCall fc, S arg) throws VisitException;

    // expressions
    public T visitExpIf(ExpIf ifStmt, S arg) throws VisitException;
    public T visitExpCase(ExpCase c, S arg) throws VisitException;
    public T visitExpClause(ExpClause c, S arg) throws VisitException;

    public T visitExpLet(ExpLet l, S arg) throws VisitException;
    public T visitExpBind(ExpBind b, S arg) throws VisitException;

    public T visitExpAdd(ExpAdd exp, S arg) throws VisitException;
    public T visitExpSub(ExpSub exp, S arg) throws VisitException;
    public T visitExpMul(ExpMul exp, S arg) throws VisitException;
    public T visitExpDiv(ExpDiv exp, S arg) throws VisitException;
    public T visitExpMod(ExpMod exp, S arg) throws VisitException;

    public T visitExpLit(ExpLit exp, S arg) throws VisitException;
    public T visitStringExp(StringExp exp, S arg) throws VisitException;

    public T visitSubstr(Substr exp, S arg) throws VisitException;
    public T visitEqv(Eqv exp, S arg) throws VisitException;
    public T visitEqual(Equal exp, S arg) throws VisitException;

    public T visitExpPair(ExpPair exp, S arg) throws VisitException;
    public T visitExpList(ExpList exp, S arg) throws VisitException;
    public T visitExpListConcat(ExpListConcat exp, S arg) throws VisitException;

    public T visitCar(Car exp, S arg) throws VisitException;
    public T visitCdr(Cdr exp, S arg) throws VisitException;

    public T visitisPair(isPair exp, S arg) throws VisitException;

    public T visitExpVar(ExpVar exp, S arg) throws VisitException;


    public T visitExpPow(ExpPow exp, S arg) throws VisitException;

    public T visitExpRead(ExpRead r, S arg) throws VisitException;
    public T visitExpReadInt(ExpReadInt r, S arg) throws VisitException;


    public T visitExpVector(ExpVector exp, S arg) throws VisitException;
    public T visitSize(Size exp, S arg) throws VisitException;
    public T visitExpGetVectEl(ExpGetVectEl exp, S arg) throws VisitException;
    public T visitExpPopVectEl(ExpPopVectEl exp, S arg) throws VisitException;
    public T visitExpSetVectEl(ExpSetVectEl exp, S arg) throws VisitException;




    // bitwise expressions
    public T visitExpBitwiseNot(ExpBitwiseNot exp, S arg) throws VisitException;
    public T visitExpBitwiseAnd(ExpBitwiseAnd exp, S arg) throws VisitException;
    public T visitExpBitwiseOr(ExpBitwiseOr exp, S arg) throws VisitException;

    // comparator expressions
    public T visitExpLess(ExpLess exp, S arg) throws VisitException;
    public T visitExpLessEq(ExpLessEq exp, S arg) throws VisitException;
    public T visitExpEqual(ExpEqual exp, S arg) throws VisitException;
    public T visitExpGreaterEq(ExpGreaterEq exp, S arg) throws VisitException;
    public T visitExpGreater(ExpGreater exp, S arg) throws VisitException;
    public T visitExpNotEqual(ExpNotEqual exp, S arg) throws VisitException;

    //public T visitExpComparator(ExpComparator exp, S arg) throws VisitException;

    // boolean expressions
    public T visitExpNot(ExpNot exp, S arg) throws VisitException;
    public T visitExpAnd(ExpAnd exp, S arg) throws VisitException;
    public T visitExpOr(ExpOr exp, S arg) throws VisitException;

}
