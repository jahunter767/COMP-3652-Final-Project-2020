public abstract class ExpLit extends Exp {

    protected ExpLit(String lit) {
	super(lit);
    }

    public abstract <L> getVal();
    public abstract boolean isEquivTo(ExpLit L);
    public abstract boolean isEqualTo(ExpLit L);

}
