//Yegor "QzK" Kozhevnikov
public class Interjack {
	
	private char key;
	
	public Interjack (char keyN){
		key = keyN;
	}
	
	public void changeKey(char newKey){
		key = newKey;
	}
	
	private char getKey(){
		return key;
	}
	
	public String encrypt(String input){
		String output = "";
		for (int x = 1; x < input.length() + 1; x++){
			if (x % 2 == 0){
				output += (int) input.charAt(x - 1) + key + ".";
			} else if (x % 3 == 0){
				output += (int) input.charAt(x - 1) + key + key + ".";
			} else if (x % 4 == 0){
				output += (int) input.charAt(x - 1) - key;
			} else {
				output += (int) input.charAt(x - 1) + ".";
			}
		}
		
		//cuts off last delimeter
		output = output.substring(0, output.length() - 1);
		
		return output;
	}
	
	public String decrypt(String input){
		//adds a delimeter at the end
		input += ".";
		
		String output = "";
		String curPort = "";
		int startpoint = 0;
		int markpoint = 0;
		int delimeters = 0;

		//counts how many characters to decrypt
		for (int x = 0; x < input.length(); x++){
			if (input.charAt(x) == '.'){
				delimeters++;
			}
		}
		
		//does decrypting
		for (int x = 1; x <= delimeters; x++){
			while (input.charAt(markpoint) != '.'){
				markpoint++;
			}
			curPort = input.substring(startpoint, markpoint);
			if (x % 2 == 0){
				output += (char) (Integer.parseInt(curPort) - key);
			} else if (x % 3 == 0){
				output += (char) (Integer.parseInt(curPort) - key - key);
			} else if (x % 4 == 0){
				output += (char) (Integer.parseInt(curPort) + key);
			} else {
				output += (char) Integer.parseInt(curPort);
			}
			startpoint = markpoint + 1;
			markpoint += 2;
		}
		
		return output;
	}
	
	public static void main(String args[]){
		Interjack jack = new Interjack('Ü');
		System.out.println(jack.encrypt("hello"));
		System.out.println(jack.decrypt(jack.encrypt("hello")));
	}
}
