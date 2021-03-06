Programming Project1: LZW Compression

Program Design:
---------------------------------------------------------------------------------------------------------------------------------
This program in created for encoding ASCII characters into UTF-16BE. Then decoding the UTF-16BE file into ASCII characters. 
The process to be performed, input file name & path and bit length is passed as input to program from CMD prompt. Based on the 
given input either Encoding or Decodng will happen. To write encoded file, UTF-16BE format is used and output file is writtened
using OutputStreamWriter. The encoded file which is in formal UTF-16BE is read using InputStreamReader.

Encoding pseudocode:
MAX_TABLE_SIZE=2(bit_length) //bit_length is number of encoding bits
initialize TABLE[0 to 255] = code for individual characters
STRING = null
while there are still input symbols:
	SYMBOL = get input symbol
	if STRING + SYMBOL is in TABLE:
		STRING = STRING + SYMBOL
	else:
		output the code for STRING
		If TABLE.size < MAX_TABLE_SIZE: // if table is not full
			add STRING + SYMBOL to TABLE // STRING + SYMBOL now has a code
	STRING = SYMBOL
output the code for STRING
               
Decoding Pseudocode:

MAX_TABLE_SIZE=2(bit_length)
initialize TABLE[0 to 255] = code for individual characters
CODE = read next code from encoder
STRING = TABLE[CODE]
output STRING
while there are still codes to receive:
	CODE = read next code from encoder
	if TABLE[CODE] is not defined: // needed because sometimes the
		NEW_STRING = STRING + STRING[0] // decoder may not yet have code!
	else:
		NEW_STRING = TABLE[CODE]
	output NEW_STRING
	if TABLE.size < MAX_TABLE_SIZE:
		add STRING + NEW_STRING[0] to TABLE
	STRING = NEW_STRING

This Program is for encoding a set of ascii characters and for decoding the encoded characters. The Program takes a 
set of asccii characters as input for Encoding , encodes the set of characters and stores them in a file. For decoding the input will
be a file that consists of encoded characters , the output of decoding will be a set of ascii characters.

Data Structure Design:
--------------------------------------------------------------------------------------------------------------------------------------

The main Data Structure used in this program is HashMap for saving the newly created code appart from the inital 256 ASCII characters. 
HashMap has no fixed length. But inorder to check the limit of new created code and consuming too much of memory, HashMap size is 
maintanied below 2^(bit length) where bit length is given as input to the program. 

I am using different HashTables for Encoding and Decoding.
*** HashTable for Encoding has strings(ASCII Characters) as keys and integers(ASCII Codes) as values. ***
*** HashTable for Decoding has integers as keys(ASCII Codes) and strings (ASCII Characters) as values. ***

Breakdown of file:
---------------------------------------------------------------------------------------------------------------------------------------
The program has 2 different process,
1. Encoding: 

a) Read the input file word by word
b) Encode the symbol read from file
c) Write the output in new file name (<Input File Name without extension> + “.lzw”) using formal UTF-16BE

2. Decoding:

a) Read the input file word by word
b) Decode the number read from file
c) Write the output in new file name (<File Name without extension> + "_decoded.txt") in normal .txt format

Programming Language
---------------------------------------------------------------------------------------------------------------------------------------
Java.
Compiler Version 8.0

Instruction to Run the Program:
---------------------------------------------------------------------------------------------------------------------------------------
1. Compile the module Main_Class.java once.
example: javac project1\Main_Class.java

2. Run the program with 3 arguments(1. Encoder/Decoder, 2. File Name, 3. Bit length)

Encoder: Encoder <File Name> <Bit Length>

Decoder: Decoder <Encoded File Name> <Bit Length>

Example: 
Encoder Input: java project1.Main_Class Encoder input2.txt 12

Decoder Input: java project1.Main_Class Decoder input2.lzw 12

3. Output file will be created in the following format. 

Encoding: <Input File Name without extension> + “.lzw”

Decoding: <File Name without extension> + "_decoded.txt"

Summary of things on how input should be given to the program:
------------------------------------------------------------------------------------------------------------------------------------------
1. For Encoding, give input file in txt format and input should be in one line
2. For Decoding, give input file in UTF-16BE format which is generated by encoder. 
3. Input file to Encoder should contain characters between ASCII from 0 to 255.
4. Input file should not be a empty file.
5. Input file path should be given properly and file should be present in the given path.
6. Bit length should be less than or equal to 16
