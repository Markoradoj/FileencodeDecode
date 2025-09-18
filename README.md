# FileencodeDecode

This is a console-based application that allows the user to input a .txt
file containing strings. It uses an encondings-10000.csv file to map words
or suffixes to numeric values, which are then written back to the file.
The application uses two HashMaps: one for mapping words and another for
suffixes. I use Hashmaps Because they allow for efficient retrieval of
values based on keys, which are used to store data at specific locations.
The key is used to retrieve its corresponding value by location within the
HashMap <br/>
To Run
Place the jar and its contents in the folder and go to the directory in
the Command Line. To run java -cp ./dsa.jar ie.atu.sw.Runner.
Before you run, you will need input txt file that contains any Strings
from the CSV file otherwise it will prompt an error.
Navigate the console using numbers on with the corresponding number that
you wish to choose. <br/>
To setup your CSV, press 1 to specify the CSV file
Do the same with input.txt and its output.
If you have the contents specified, then you can press 4 to encode will
encode the txt file and generates the output.
The decoding will rewrite the output file and gets its String back.
Features <br/>
• Allow the file to be encoded.
This will display the encoded text on display in the command line
and it will also create an output.txt file with its numeric values <br/>
• Allow the file to be decoded.
This will convert the output file that originally has encrypted
values to the original String format
• Runner
Creates a main menu to select and specify the files and allowing to
encode or decode the files.
