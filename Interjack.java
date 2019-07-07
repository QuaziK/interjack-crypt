//Yegor "QuaziK" Kozhevnikov
public class Interjack {

	private char key;

	// only constructor to create with a base key
	public Interjack(char keyN) {
		key = keyN;
	}

	// change key
	public void changeKey(char newKey) {
		key = newKey;
	}

	// retrieve key
	private char getKey() {
		return key;
	}

	// encrypt input string with the set key
	public String encrypt(String input) {
		String output = "";
		// check if input string is empty halting the function
		if (input == null || input.equals("")) {
			return output;
		}
		// go through each character in passed string encrypting each one,
		// adding delimeters to separate encrypted characters
		for (int x = 1; x < input.length() + 1; x++) {
			if (x % 2 == 0) {
				output += (int) input.charAt(x - 1) + key + ".";
			} else if (x % 3 == 0) {
				output += (int) input.charAt(x - 1) + key + key + ".";
			} else if (x % 4 == 0) {
				output += (int) input.charAt(x - 1) - key;
			} else {
				output += (int) input.charAt(x - 1) - key - key + ".";
			}
		}

		// cuts off last delimeter
		output = output.substring(0, output.length() - 1);

		return output;
	}

	public String decrypt(String input) {
		// checks if input is empty to prevent further crashes
		if (input == null || input.equals("")) {
			return "";
		}

		// adds a delimeter at the end to prevent crash
		input += ".";

		String output = "";
		String curPort = "";
		int startpoint = 0;
		int markpoint = 0;
		int delimeters = 0;

		// counts how many characters to decrypt
		for (int x = 0; x < input.length(); x++) {
			if (input.charAt(x) == '.') {
				delimeters++;
			}
		}

		// does decrypting while there are more characters to decrypt
		for (int x = 1; x <= delimeters; x++) {
			while (input.charAt(markpoint) != '.') {
				markpoint++;
			}
			curPort = input.substring(startpoint, markpoint);
			if (x % 2 == 0) {
				output += (char) (Integer.parseInt(curPort) - key);
			} else if (x % 3 == 0) {
				output += (char) (Integer.parseInt(curPort) - key - key);
			} else if (x % 4 == 0) {
				output += (char) (Integer.parseInt(curPort) + key);
			} else {
				output += (char) (Integer.parseInt(curPort) + key + key);
			}
			startpoint = markpoint + 1;
			markpoint += 2;
		}

		return output;
	}

	public static void main(String args[]) {
		Interjack jack = new Interjack('U');
		System.out.println(jack.encrypt(""));
		System.out.println(jack.decrypt(jack.encrypt("")));
	}
}
