
/**
 * Runtime representation for a procedure object (under static scoping)
 * @param T the type of values stored in the closing environment.
 */
public class Closure<T> {
    private StmtProcDefn function;
    private Environment<T> closingEnv;

    public Closure(StmtProcDefn fun, Environment<T> env) {
	function = fun;
	closingEnv = env;
    }

    public StmtProcDefn getFunction() {
	return function;
    }

    public Environment<T> getClosingEnv() {
	return closingEnv;
    }
}
