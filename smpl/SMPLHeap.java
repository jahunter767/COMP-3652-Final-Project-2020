public class SMPLHeap extends SMPLObject{
	
	private SMPLInteger size;
	private SMPLInteger max;
	private SMPLExp Heap[];
	
	public SMPLHeap(SMPLExp max){
		super(max,"SMPLHeap");
		this.size = new SMPLInteger(new Integer(0));
		
	}
	
	public SMPLInteger getMax(){
		return max;
	}
	
	public SMPLInteger getSize(){
		return size;
	}
	
	public SMPLExp[] getVal(){
		return Heap;
	}
}
