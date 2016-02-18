package angier.toolkit.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生成UUID
 *
 */
public class UUID {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String uuid = new UUID().createUUID();
		System.out.println(uuid+"@"+uuid.split("-")[4]+"@"+Long.parseLong(uuid.split("-")[4], 16));
		System.out.println(uuid+"@"+uuid.substring(uuid.length()-12));
		System.out.println(Long.parseLong(uuid.substring(uuid.length()-12),16));

	}

	public String createUUID(){
		//
		// Loose interpretation of the specification DCE 1.1: Remote Procedure Call
		// described at http://www.opengroup.org/onlinepubs/009629399/apdxa.htm#tagtcjh_37
		// since JavaScript doesn't allow access to internal systems, the last 48 bits 
		// of the node section is made up using a series of random numbers (6 octets long).
		//  
		DateFormat parser = new SimpleDateFormat("yyyy-mm-dd");
		Date dg = new Date();
		try{
			dg = parser.parse("1592-10-15");
		}catch(Exception e){
			e.printStackTrace();
		}
		

		Date dc = new Date();
		long t = dc.getTime() - dg.getTime();
		String h = "";
		String tl = this.getIntegerBits(t,0,31);
		String tm = this.getIntegerBits(t,32,47);
		String thv = this.getIntegerBits(t,48,59) + "1"; // version 1, security version is 2
		String csar = this.getIntegerBits(this.rand(4095),0,7);
		String csl = this.getIntegerBits(this.rand(4095),0,7);

		// since detection of anything about the machine/browser is far to buggy, 
		// include some more random numbers here
		// if NIC or an IP can be obtained reliably, that should be put in
		// here instead.
		String n = this.getIntegerBits(this.rand(8191),0,7) + 
				this.getIntegerBits(this.rand(8191),8,15) + 
				this.getIntegerBits(this.rand(8191),0,7) + 
				this.getIntegerBits(this.rand(8191),8,15) + 
				this.getIntegerBits(this.rand(8191),0,15); // this last number is two octets long
		return tl + h + tm + h + thv + h + csar + csl + h + n;
	}
	
	public String getIntegerBits(long val,int start,int end){
		String base16 = this.returnBase(val,16);
		List<String> quadArray = new ArrayList<String>();
		String quadString = "";
		for(int i=0;i<base16.length();i++){
			quadArray.add(base16.substring(i,i+1));	
		}
		
		/*int begin = (int)Math.floor(start/4);
		int finish = (int)Math.floor(end/4);
		
		for(int i=begin;(finish>=base16.length() ? i<base16.length() : i<=finish);i++){
			quadString += quadArray.get(i);
		}
		
		if(finish>=base16.length()){
			for(int i=0;i<=finish-base16.length();i++){
				quadString += '0';
			}
		}*/
		
		for(int i=(int)Math.floor(start/4);i<=Math.floor(end/4);i++){
			try{
				quadString += quadArray.get(i);
			}catch(Exception e){
				quadString += '0';
			}
				
		}
		
		return quadString;
	}
	
	public String returnBase(long number,int base){
		//
		// Copyright 1996-2006 irt.org, All Rights Reserved.	
		//
		// Downloaded from: http://www.irt.org/script/146.htm	
		// modified to work in this class by Erik Giberti
		String output = null;
		String[] convert = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	    if (number < base)
	    	 output = convert[(int)number];
	    else {
	        double MSD = Math.floor(number / base);
	        double LSD = number - MSD*base;
	        if (MSD >= base)
	        	 output = this.returnBase((long)MSD,base) + convert[(int)LSD];
	        else 
	        	 output = convert[(int)MSD] + convert[(int)LSD];
	    }
	    return output;
	}
	
	/**
	 * 随机数
	 * @param max
	 * @return
	 */
	public long rand(int max){
		return (long)Math.floor(Math.random() * max);
	}

}
