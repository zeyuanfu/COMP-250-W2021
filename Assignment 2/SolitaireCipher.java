package assignment2;


public class SolitaireCipher {
	public Deck key;
	
	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}
	
	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {

		int[] keystream = new int[size];
		
		for (int i=0; i < size; i++) {
			keystream[i] = key.generateNextKeystreamValue();
		}
		
		return keystream;
	}
		
	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {

		String intermediate = "";
		String result = "";
		
		for (int i = 0; i < msg.length(); i++) {
			char temp = msg.charAt(i);
			if (Character.isLetter(temp)) intermediate += temp;
		}
		
		intermediate = intermediate.toUpperCase();
		
		int[] keystream = getKeystream(intermediate.length());
			
		for (int i = 0; i < intermediate.length(); i++) {
			
			char toShift = intermediate.charAt(i);
			int shift = (toShift - 'A' + keystream[i]) % 26;
			char shifted = (char) ('A' + shift);
			result += shifted;
		}
		
		return result;
	}
	
	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {

		int[] keystream = getKeystream(msg.length());
		String result = "";
		
		for (int i = 0; i < msg.length(); i++) {
			
			char toShift = msg.charAt(i);
			int shift = (toShift - 'A' - keystream[i]) % 26;
			if (shift < 0) shift += 26;
			char shifted = (char) ('A' + shift);
			
			result += shifted;
		}
		
		return result;
	}
	
}
