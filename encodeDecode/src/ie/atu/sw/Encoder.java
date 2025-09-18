package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Encoder {
	public void encode(String csvFilePath, String inputFilePath, String outputFileName) throws Exception {
		System.out.println("You're inside encoder");
		System.out.println("This is the CSV File Path: " + csvFilePath);
		System.out.println("This is the the Input File path " + inputFilePath);
		String line;
		HashMap<String, Integer> wordsMap = new HashMap<String, Integer>(); // create a map for words
		HashMap<String, Integer> suffixMap = new HashMap<String, Integer>(); // create a map suffixes
//		example -> [example, 123]
		// reads the csv file and creates word and suffix map needed for encoding 
		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) { // read a buffer from a

			while ((line = br.readLine()) != null) { // read the entire file
				String str[] = line.split(","); // split the columns into 2
				if (str.length == 2) { // get the length of contents of CSV (its columns)
					String word = str[0].replaceAll("[\\[//]]", "").trim(); // remove brackets
					int val = Integer.parseInt(str[1].trim());

					if (word.startsWith("@@")) {
						suffixMap.put(word.substring(2), val); // put in suffix map skip every 2 letters
					} else {
						wordsMap.put(word, val);// put in words map
					}

				}
			}

			br.close(); // will close after reading
			
			//Complexity is o(n), the n times is the number of lines within the CSV file
			System.out.println("Maps created for Encoding");
			System.out.println("my words: " + wordsMap.size());
			System.out.println("my suffix: " + suffixMap.size());

		} catch (IOException e) {
			System.err.println("error reaidng file: " + e.getMessage());
			return;
		}

		// this reads the input file and encodes the values in it. by using the word and suffix map.
		List<Integer> encodeWords = new ArrayList<>();
		try (BufferedReader fr = new BufferedReader(new FileReader(inputFilePath))) {
			String inputText = fr.readLine(); // reads the file
			fr.close(); // closes file

			if (inputFilePath != null) { // if files not empty
				for (String word : inputText.split(" ")) { // split the two columns in the file
					if (wordsMap.containsKey(word)) { //if the wordmap contains the word from the input file
						encodeWords.add(wordsMap.get(word)); //This will add to encode words finding words without suffixes
						System.out.println("Full Words I have founds so far this is their code" + encodeWords);
					} else {
						// this will turn true once the root word and suffix is together
						for (String suffix : suffixMap.keySet()) {
							if (word.endsWith(suffix)) {
								// checking the word minus the suffix
								String wordNoSuffix = word.substring(0, word.length() - suffix.length());
								if (wordsMap.containsKey(wordNoSuffix)) { 
									encodeWords.add(wordsMap.get(wordNoSuffix));
									encodeWords.add(suffixMap.get(suffix));
									System.out.println(
											"Rootword + Suffix I have founds so far this is their code" + encodeWords);
									break; // leaves the loop
									//time complexity O(m*s) every word would need to be checked on every suffix
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.err.println("error reading input file: " + e.getMessage());
			return;
		}
		File output = new File(outputFileName);

		if (!output.exists()) {
			output.createNewFile(); // if it does not exists, create a file

		}
		//this writes the encoded output to a file 
		try (PrintWriter pw = new PrintWriter(output)) { //declare a printwriter and create it from the output
			for (int i = 0; i < encodeWords.size(); i++) { //get the entire encrypted values
				pw.print(encodeWords.get(i));
				if (i < encodeWords.size()) {
					pw.print(",");//prints out the commas in each of the values

				}
			}

			pw.close(); //closes the file once it finishes
			System.out.println("File Encoded \n");

		} catch (IOException e) {
			System.err.println("error reading input file: " + e.getMessage());
			return;
			//Running time is O(m) est -0.001ms
		}
	}
}
