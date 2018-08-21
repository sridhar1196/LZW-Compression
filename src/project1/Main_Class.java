package project1;
//********************************************************************************************************************************************//
// Name: Sridhar Ramesh Babu
// Description: LZW Compression
// Input: Encoder/Decoder Input_File_Name Bit_length
// Output: Either it will be Encoded or Decoded file
//********************************************************************************************************************************************//
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Main_Class {

	public static void main(String[] args) {
		System.out.println("You input: " + args[0] + ", " + args[1] + " and " + args[2]);
		String type = args[0];
		int bit_len = Integer.parseInt(args[2]);
		int MAX_TABLE_SIZE = (int) Math.pow(2, bit_len);
		if(type.equals("Encoder")) {
			String rev = new StringBuilder(args[1]).reverse().toString().split("\\.")[0];
			StringBuilder build = new StringBuilder();
			if(rev.equals("txt")) {
				System.out.println("Maximum table size:" + MAX_TABLE_SIZE);
				File input1 = new File(args[1]);
				BufferedReader reader = null;
				try {
					System.out.println(args[1]);
				    reader = new BufferedReader(new FileReader(args[1]));
				    String text = null;
				    while ((text = reader.readLine()) != null) {
				        build.append(text);
				    }
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				} catch (IOException e) {
				    e.printStackTrace();
				} finally {
				    try {
				        if (reader != null) {
				            reader.close();
				        }
				    } catch (IOException e) {
				    }
				}
				if(build != null) {
					String chg = build.toString();
					HashMap<String,Integer> hm = new HashMap<String,Integer>();
				    for(int i = 0; i < 256; i++) {
				    	hm.put(String.valueOf((char) i), i);
				    }
				    String cal = "";
				    String filePath = input1.getPath();
				    String fileName = input1.getName();
				    int index = filePath.lastIndexOf("\\");
				    if(index != -1) {
				    	filePath = filePath.substring(0, index);
				    } else {
				    	filePath = System.getProperty("user.dir");
				    }
				    index = fileName.lastIndexOf(".");
				    fileName = fileName.substring(0, index);
				    File output1 = new  File( filePath + "\\" + fileName + ".lzw");
				    if (!output1.exists()) {
				    	try {
							output1.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
			        }
				    OutputStreamWriter bw;
					try {
						bw = new OutputStreamWriter(new FileOutputStream(output1),"UTF-16BE");
						for(int i = 0; i<chg.length();i++) {
							String symbol = String.valueOf(chg.charAt(i));
							if(hm.containsKey(cal + symbol)) {
								cal = cal + symbol;
							} else {
								bw.write(hm.get(cal));
								if(hm.size() < MAX_TABLE_SIZE) {
									int size = hm.size();
									hm.put(cal + symbol, size);
								}
								cal = symbol;
							}
						}
						if(!(cal.isEmpty())) {
							bw.write(hm.get(cal));
						}
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}


				} 
			} else {
				System.out.println("Input file format is not in txt");
			}
			
		} else if (type.equals("Decoder")) {
			String rev = new StringBuilder(args[1]).reverse().toString().split("\\.")[0];
			ArrayList<Integer> build = new ArrayList<Integer>();
			if(rev.equals("wzl")) {
				System.out.println("Maximum table size:" + MAX_TABLE_SIZE);
				File input1 = new File(args[1]);
				InputStreamReader reader = null;
				try {
				    reader = new InputStreamReader(new FileInputStream(input1), "UTF-16BE");
				    int text;

				    while ((text = reader.read()) != -1) {
				    	build.add(text);
				    }
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				} catch (IOException e) {
				    e.printStackTrace();
				} finally {
				    try {
				        reader.close();
				    } catch (IOException e) {
				    }
				}
				if(build.size() > 0) { 
					HashMap<Integer,String> hm = new HashMap<Integer,String>();
				    for(int i = 0; i < 256; i++) {
				    	hm.put(i, String.valueOf((char) i));
				    }
				    String cal = "";
				    String filePath = input1.getPath();
				    String fileName = input1.getName();
				    int index = filePath.lastIndexOf("\\");
				    if(index != -1) {
				    	filePath = filePath.substring(0, index);
				    } else {
				    	filePath = System.getProperty("user.dir");
				    }
				    index = fileName.lastIndexOf(".");
				    fileName = fileName.substring(0, index);
				    File output1 = new  File( filePath + "\\" + fileName + "_decoded.txt");
				    if (!output1.exists()) {
				    	try {
							output1.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
			        }
				    FileWriter fw;
					try {
						fw = new FileWriter(output1.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						int chg = build.get(0);
						cal = hm.get(chg);
						bw.write(cal);
						for(int i = 1;i<build.size();i++) {
							int code = build.get(i);
							String newStr = "";
							if (hm.containsKey(code)){
								newStr = hm.get(code);
							} else {
								newStr = cal + String.valueOf(cal.charAt(0));
							}
							
							bw.write(newStr);
							if(hm.size()<MAX_TABLE_SIZE) {
								hm.put(hm.size(), cal + String.valueOf(newStr.charAt(0)));
							}
							cal = newStr;
						}
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else {
					String filePath = input1.getPath();
				    String fileName = input1.getName();
				    int index = filePath.lastIndexOf("\\");
				    if(index != -1) {
				    	filePath = filePath.substring(0, index);
				    } else {
				    	filePath = System.getProperty("user.dir");
				    }
				    index = fileName.lastIndexOf(".");
				    fileName = fileName.substring(0, index);
				    File output1 = new  File( filePath + "\\" + fileName + "_decoded.txt");
				    if (!output1.exists()) {
				    	try {
							output1.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
			        }
				    FileWriter fw;
					try {
						fw = new FileWriter(output1.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write("");
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			} else {
				System.out.println("Input file format is not in lzw");
			}
		} else {
			System.out.println("Program can perform either Encoder or Decoder");
		}
	}

}
