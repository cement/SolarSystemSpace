package com.cement.stlreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class STLReader {

	public static float maxX = Float.MAX_VALUE;
	public static float minX = Float.MIN_VALUE;
	public static float maxY = Float.MAX_VALUE;
	public static float minY = Float.MIN_VALUE;
	public static float maxZ = Float.MAX_VALUE;
	public static float minZ = Float.MIN_VALUE;
    
    public static float[] readStlFile(File file) throws IOException{
    	FileReader reader = new FileReader(file);
    	char[] header =new char[80];
    	reader.read(header);
    	reader.close();
    	String s = new String(header);
    	if(s.contains("solid")){
    		System.out.println(".>>>>>....readAsciiFile......");
    		return readAsciiFile(file);
    	}else{
    		System.out.println(".>>>>>....readBinaryFile......");
    		return readBinaryFile(file);
    	}
    }
    public static float[] readAsciiFile(File file) throws IOException{
		   LineNumberReader reader = new LineNumberReader(new FileReader(file));
		   reader.skip(file.length());
		   int lines = reader.getLineNumber();
		   reader.close();
		   
		   reader = new LineNumberReader(new FileReader(file));
	        String name = reader.readLine();
	        System.out.println("name:  "+name);
	        
	        int facetCount = lines/7;
	        float[] facets = new float[facetCount*9];
	       int index = 0;
	       for(int i=0;i<facetCount;i++){
	    	    if(reader.readLine()==null) break;
	        	if(reader.readLine()==null) break;
	        	for (int j = 0; j < 3; j++) {
	        		String[] ss = reader.readLine().trim().split(" ");
//	        		facets[index++] = Float.parseFloat(ss[1]);
//	        		facets[index++] = Float.parseFloat(ss[2]);
//	        		facets[index++] = Float.parseFloat(ss[3]);
	        		for (int k = 0; k < 3; k++) {
	        			facets[index++] = Float.parseFloat(ss[k+1]);
					}
				}
	        	if(reader.readLine()==null) break;
	        	if(reader.readLine()==null) break;
			}
     	reader.close();
		return facets;
	}
//    public static float[] readAsciiFile(File file) throws IOException{
//    	LineNumberReader reader = new LineNumberReader(new FileReader(file));
//    	reader.skip(file.length());
//    	int lines = reader.getLineNumber();
//    	reader.close();
//    	
//    	reader = new LineNumberReader(new FileReader(file));
//    	String name = reader.readLine();
//    	System.out.println("name:  "+name);
//    	
//    	int facetCount = lines/7;
//    	float[] facets = new float[facetCount*9];
//    	int index = 0;
//    	for(int i=0;i<facetCount;i++){
//    		if(reader.readLine()==null) break;
//    		if(reader.readLine()==null) break;
//    		for (int j = 0; j < 3; j++) {
//    			String[] ss = reader.readLine().trim().split(" ");
//    			facets[index++] = Float.parseFloat(ss[1])/100;
//    			facets[index++] = Float.parseFloat(ss[2])/100;
//    			facets[index++] = Float.parseFloat(ss[3])/100;
//    		}
//    		if(reader.readLine()==null) break;
//    		if(reader.readLine()==null) break;
//    	}
//    	reader.close();
//    	return facets;
//    }
	private static float[] readBinaryFile(File file) throws IOException{
		FileInputStream fins = new FileInputStream(file);
		byte[] header = new byte[80];
		fins.read(header);
		String name = new String(header);
		System.out.println("...>>>>>name: "+name);
		byte[] counts = new byte[4];
		fins.read(counts);
		int count = bigBytesToInt(counts, 0);
		float[] coords = new float[count*9];
		byte[] nor_b = new byte[12]; 
		byte[] ver_b = new byte[36]; 
		byte[] typ_b = new byte[2]; 
		for (int i = 0; i < count; i++) {
			fins.read(nor_b);
			fins.read(ver_b);
			for (int j = 0; j < 9; j++) {
				int temp = bigBytesToInt(ver_b, j*4);
				coords[i*9+j] = Float.intBitsToFloat(temp);
			}
            fins.read(typ_b);
		}
		fins.close();
		return coords;
	}
	
	
	public static int littleBytesToInt(byte[] ary,int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			value |= (ary[i+offset]&0xFF)<<((3-i)*8);
		}
		return value;  
	}
	public static int littleBytesToInt(byte[] ary) {
		return littleBytesToInt(ary,0);  
	}
	public static int bigBytesToInt(byte[] ary,int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			value |= (ary[i+offset]&0xFF)<<(i*8);
		}
		return value;  
	}
	public static int bigBytesToInt(byte[] ary) {
		return bigBytesToInt(ary,0);  
	}
}

