import java.math.BigDecimal;

public class SMPLNumber<T> extends SMPLObject{

    public SMPLNumber(T value, String NumbType) {
	super(value,NumbType);
    }

    public BigDecimal getVal(){
	String str = V.toString();
	return new BigDecimal(str);	
	}
	
}
