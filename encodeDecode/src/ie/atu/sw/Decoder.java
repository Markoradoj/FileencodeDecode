package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Decoder {
	public void decode(String csvFilePath, String outputFileName) throws Exception {
	        System.out.println("You're inside decoder");
	        System.out.println("CSV Path: " + csvFilePath);
	       
	        
	        String line;
			HashMap<Integer, String> wordsMap = new HashMap<Integer, String>(); // create a map for words
			HashMap<Integer, String> suffixMap = new HashMap<Integer, String>(); // create a map suffixes
			// example -> [123, example]
			// reads the csv file and creates word and suffix map needed for decoding
			
			try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) { // read a buffer from a

				while ((line = br.readLine()) != null) { // read the entire file
					String str[] = line.split(","); // split the columns into 2
					if (str.length == 2) { //Once the line is split, check the length is 2
						String word = str[0].replaceAll("[\\[//]]", "").trim(); // replace all brackets
						int val = Integer.parseInt(str[1].trim());

						if (word.startsWith("@@")) {
							suffixMap.put(val, word.substring(2)); // put in suffix map skip every 2 letters
						} else {
							wordsMap.put(val,word);// put in words map
						}

					}
				}
					
				//time complexity O(n)
				br.close(); // will close after reading

				System.out.println("Maps created for Encoding");
				System.out.println("my words: " + wordsMap.size());
				System.out.println("my suffix: " + suffixMap.size());

			} catch (IOException e) {
				System.err.println("error reaidng file: " + e.getMessage());
				return;
			}

	        // Takes in the encoded file and using the wordmap and suffixmap it decodes it. 
	         StringBuilder decodeValueText = new StringBuilder(); //initalize the string builder
	         try (BufferedReader reader = new BufferedReader(new FileReader(outputFileName))) { //reads the file that needs to be decoded
	        	 String [] valueFromEncryptedFile = reader.readLine().split(","); //split the encrypted val
	        	 for(int i = 0; i < valueFromEncryptedFile.length; i++) {  //runs depending on the amount of encrypted values
	        		 int numberValue = Integer.parseInt(valueFromEncryptedFile[i].trim()); //removing whitespace
	        		 
	        		 if (wordsMap.containsKey(numberValue)) { //check if the value exists in the wordmap
	        			 String word = wordsMap.get(numberValue); //this gets the key values from the map
	        			 
	        			 //peek to see if the next code is a suffix
	        			 if ((i + 1) < valueFromEncryptedFile.length){ //this checks if hte next value is a word
	        				 int nextCode = Integer.parseInt(valueFromEncryptedFile[i + 1].trim()); //this gets the next value from the encrypted
	        				 if(suffixMap.containsKey(nextCode)) { //this checks if the value does exist in the suffix map
	        					 word += suffixMap.get(nextCode); //added to the wordmap
	        					 i++; //skip its next code because of the suffix
	        				 }
	        			 }
	        		 
	        			 decodeValueText.append(word).append(" ");//adds all the words together
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Error reading encoded file: " + e.getMessage());
	            return;
	        }
	      
	        
	        // This reads the output file and writes the decoded text
	        File output = new File(outputFileName);
	        
	        // update the output file with the decoded text
	        try (PrintWriter pw = new PrintWriter(output)) { 
	        	pw.println(decodeValueText.toString().trim());
				pw.close();
				System.out.println("File decoded \n");

			} catch (IOException e) {
				System.err.println("error reading input file: " + e.getMessage());
				return;
				//time complexity O(m)
			}
		}

	}

