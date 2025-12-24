import java.util.ArrayList;
import java.math.*;

public class PRNG {
	private Double curr = null;
	private ArrayList<Integer> list;
	private ArrayList<Long> evenBitsList;
	private ArrayList<Long> oddBitsList;

	private double h = 1e-7;
	
	public PRNG (int run) {
		list = new ArrayList<Integer>();
		for (int i=0; i<run; i++) {
			list.add(newtonianChaos());
		}
	}
	
	private int newtonianChaos() {
		if (curr == null) {
			curr = (double) (System.currentTimeMillis()%100)/100;
		} else if (Math.abs(curr) > 1e150 || Math.abs(curr) < 1e-15) {
			long bits = Double.doubleToRawLongBits(curr);
			long evenBits = 0;
			long oddBits = 0;

			for (int i = 0; i < 32; i++) {
			    evenBits |= ((bits >> (2 * i)) & 1L) << i;
			    oddBits |= ((bits >> (2 * i + 1)) & 1L) << i;
			}
			
			if (evenBits > oddBits) {

				curr = (double) evenBits / Math.max(oddBits, 1);  // avoid division by 0.
			} else {
			    curr = (double) oddBits / Math.max(evenBits, 1);  // avoid division by 0.
			}
		}
		
		double y1 = curr*curr+1;
		
		double a = 2*curr;
		curr = -y1/a+curr;
		return (int) Math.abs(curr*1e6)%6+1;
	}
	
	public ArrayList<Integer> getList(){
		return list;
	}
	
	public ArrayList<Long> getEvenBits() {
		return evenBitsList;
	}
	
	public ArrayList<Long> getOddBits() {
		return oddBitsList;
	}
	
	public static void main(String[] args) {
		PRNG yes = new PRNG(2000);
		ArrayList<Integer> l = yes.getList();
		int length = l.size();	
		for (int i=0; i<length; i++) {
			System.out.println(l.get(i));
		}
	}
}
