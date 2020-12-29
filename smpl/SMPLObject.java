public abstract class SMPLObject<T> extends ExpLit{


    public SMPLObject(T value, String Type) {
	super(value.toString());
	this.V = value;
	this.SMPLType = Type; 
    }
	
	public String getType(){
		return SMPLType;
	}

    public abstract T getVal();
	
	
    
}