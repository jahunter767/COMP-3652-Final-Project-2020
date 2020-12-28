import java.lang.Math;

public class SMPLNumbers extends SMPLObject<Double> {    
    public SMPLNumbers(Double val) {
	super(val);
    }

	public SMPLNumbers(Integer val) {
	super(new Double(val));
    }

    public boolean isInt(){
	return getVal().intValue() == getVal().doubleValue();
    }

	public int getIntVal(){
	return getVal().intValue();
    }


    public SMPLObject bitwiseAnd(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;

		if(isInt() && obj.isInt()){
			Integer val2 = obj.getVal().intValue();
			Integer result = getVal().intValue() & val2;
			return new SMPLNumbers(result);
		}else {
			throw new TypeException();
		}
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject bitwiseOr(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;

		if(isInt() && obj.isInt()){
			Integer val2 = obj.getVal().intValue();
			Integer result = getVal().intValue() | val2;
			return new SMPLNumbers(result);
		}else {
			throw new TypeException();
		}
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject bitwiseNot() throws TypeException {
	if(isInt()){
		Integer result = ~getVal().intValue();
		return new SMPLNumbers(result);
	}else {
		throw new TypeException();
	}
    }


	public SMPLObject add(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = getVal() + val2;
		return new SMPLNumbers(result);
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject subtract(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = getVal() - val2;
		return new SMPLNumbers(result);
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject multiply(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = getVal() * val2;
		return new SMPLNumbers(result);
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject divide(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = getVal() / val2;
		return new SMPLNumbers(result);
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject mod(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		
		if(isInt() && obj.isInt()){
			Integer val2 = obj.getVal().intValue();
			Integer result = getVal().intValue() % val2;
			return new SMPLNumbers(result);
		}else {
			throw new TypeException();
		}
    }else {
		throw new TypeException();
	}
    }

	public SMPLObject pow(SMPLObject object) throws TypeException {
	if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		Double val2 = obj.getVal();
		Double result = Math.pow(getVal(), val2);
		return new SMPLNumbers(result);
    }else {
		throw new TypeException();
	}
	}


    public SMPLObject lessThan(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		double val2 = obj.getVal().doubleValue();
		return new SMPLBoolean(getVal().doubleValue() < val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject lessThanEq(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		double val2 = obj.getVal().doubleValue();
		return new SMPLBoolean(getVal().doubleValue() <= val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject equalTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		double val2 = obj.getVal().doubleValue();
		return new SMPLBoolean(getVal().doubleValue() == val2);
    }else {
		return new SMPLBoolean(false);
	}
    }

    public SMPLObject greaterThanEq(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		double val2 = obj.getVal().doubleValue();
		return new SMPLBoolean(getVal().doubleValue() >= val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject greaterThan(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		double val2 = obj.getVal().doubleValue();
		return new SMPLBoolean(getVal().doubleValue() > val2);
    }else {
		throw new TypeException();
	}
    }

    public SMPLObject notEqualTo(SMPLObject object) throws TypeException {
    if(object instanceof SMPLNumbers){
		SMPLNumbers obj = (SMPLNumbers) object;
		double val2 = obj.getVal().doubleValue();
		return new SMPLBoolean(getVal().doubleValue() != val2);
    }else {
		throw new TypeException();
	}
	}

    public SMPLObject eqv(SMPLObject object) throws TypeException {
	return new SMPLBoolean(this == object);
    }


    public String toString(){
	return "Type: SMPLNumbers\nValue: " + String.valueOf(getVal());
    }

}
