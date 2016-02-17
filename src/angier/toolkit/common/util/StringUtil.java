package angier.toolkit.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class StringUtil {
    public static String isoToGBK(String s) {
        if(s==null)
            return "";
        try {
            return new String(s.getBytes("ISO-8859-1"), "GBK").trim();
        } catch (Exception e) {
            return s;
        }
    }
    
    public static String isoToUTF(String s) {
        if(s==null)
            return "";
        try {
            return new String(s.getBytes("ISO-8859-1"), "UTF-8").trim();
        } catch (Exception e) {
            return s;
        }
    }
    
    public static String gbkToISO(String s) {
        if(s==null)
            return "";
        try {
            return new String(s.getBytes("GBK"), "ISO-8859-1").trim();
        } catch (Exception e) {
            return s;
        }
    }
    
    public static String utfToISO(String s) {
        if(s==null)
            return "";
        try {
            return new String(s.getBytes("UTF-8"), "ISO-8859-1").trim();
        } catch (Exception e) {
            return s;
        }
    }
    
    public static String notNull(String s) {
        if(s==null)
            return "";
        else
            return s.trim();
    }
    public static String setHtmlTag(String input){
    	input=notNull(input);
    	input=input.replaceAll("<","&lt;");
	  	input=input.replaceAll(">","&gt;");
		input=input.replaceAll("\r","");
		input=input.replaceAll("\n","<br>");
		return input;
    }
    public static String getHtmlTag(String input){ 
    	input=notNull(input);
    	input=input.replaceAll("<br>","\r\n");
		input=input.replaceAll("&lt;","<");
  		input=input.replaceAll("&gt;",">");
		return input;
    }    
    /**
     * Escape SQL tags, ' to ''; \ to \\.
     * @param input string to replace
     * @return string
     */
    public static String escapeSQLTags(String input) {
      if (input == null || input.length() == 0) {
        return input;
      }
      StringBuffer buf = new StringBuffer();
      char ch = ' ';
      for (int i = 0; i < input.length(); i++) {
        ch = input.charAt(i);
        if (ch == '\\') {
          buf.append("\\");
        }
        else if (ch == '\'') {
          buf.append("\'");
        }
        else {
          buf.append(ch);
        }
      }
      return buf.toString();
    }
    public static String  escape(String src){
    	int i;  
    	char j;  
    	StringBuffer tmp = new StringBuffer();
    	tmp.ensureCapacity(src.length()*6);
    	for (i=0;i<src.length() ;i++ ){
    		j = src.charAt(i);
    		if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
    			tmp.append(j);
    		else if (j<256){
    			tmp.append( "%" );
    			if (j<16)
    				tmp.append( "0" );
    			tmp.append( Integer.toString(j,16) );
    		}else{
    			tmp.append( "%u" );
    			tmp.append( Integer.toString(j,16) );
    		}  
    	}  
    	return tmp.toString();
    }
    public static String  unescape(String src){
    	StringBuffer tmp = new StringBuffer();
    	tmp.ensureCapacity(src.length());
    	int  lastPos=0,pos=0;  char ch;
    	while (lastPos<src.length()){
    		pos = src.indexOf("%",lastPos);
    		if (pos == lastPos){
    			if (src.charAt(pos+1)=='u'){
    				ch = (char)Integer.parseInt(src.substring(pos+2,pos+6),16);
    				tmp.append(ch);
    				lastPos = pos+6;
    			}else{
    				ch = (char)Integer.parseInt(src.substring(pos+1,pos+3),16);
    				tmp.append(ch);
    				lastPos = pos+3;
    			}
    		}else{
    			if (pos == -1){
    				tmp.append(src.substring(lastPos));
    				lastPos=src.length();
    			}else{
    				tmp.append(src.substring(lastPos,pos));
    				lastPos=pos;
    			}
    		}
    	}
    	return tmp.toString(); 
    }

    public static String getPartString(String str,int len) throws UnsupportedEncodingException {
    	byte b[];
    	int counterOfDoubleByte = 0;
    	b = str.getBytes("GBK");
    	if(b.length <= len)
    		return str;
    	for(int i = 0; i < len; i++){
    		if(b[i] < 0)
    			counterOfDoubleByte++;
    	}
    	if(counterOfDoubleByte % 2 == 0)
    		return new String(b, 0, len, "GBK") + "...";
    	else
    		return new String(b, 0, len - 1, "GBK") + "...";
    } 
    public static int getStringLength(String str) throws UnsupportedEncodingException{
    	byte b[];
    	b = str.getBytes("GBK");
    	return b.length;
    }
    public static String htmlEncode(String txt){
    	  txt = txt.replaceAll("&","&amp;");
    	  txt = txt.replaceAll("&amp;amp;","&amp;");
    	  txt = txt.replaceAll("&amp;quot;","&quot;");
    	  txt = txt.replaceAll("\"","&quot;");
    	  txt = txt.replaceAll("&amp;lt;","&lt;");
    	  txt = txt.replaceAll("<","&lt;");
    	  txt = txt.replaceAll("&amp;gt;","&gt;");
    	  txt = txt.replaceAll(">","&gt;");
    	  txt = txt.replaceAll("&amp;nbsp;","&nbsp;");
    	  //txt = txt.replaceAll("'","&rsquo;");
    	  //txt = txt.replaceAll("&amp;rsquo;","&rsquo;");
    	  return txt;
    	}

    public static String unHtmlEncode(String txt){
    	  txt = txt.replaceAll("&amp;","&");
    	  txt = txt.replaceAll("&quot;","\"");
    	  txt = txt.replaceAll("&lt;","<");
    	  txt = txt.replaceAll("&gt;",">");
    	  txt = txt.replaceAll("&nbsp;"," ");
    	  return txt;
    	}
		public static String praseSSNumberToImgDicEnc(String sSSNumber)
		{
			String sImg="";
			if(!sSSNumber.equals("")&&!sSSNumber.equals("none")&&!sSSNumber.equals("NULL"))
			{
				try
				{
					sImg += sSSNumber.substring(0,2) + "/";
					sImg += sSSNumber.substring(2,5) + "/";
					sImg += sSSNumber.substring(5,sSSNumber.length());
				}catch(Exception e )//try
				{
					sImg="";
				}
			}
			return sImg;
		}
		public static String praseSSNumberToImgDiclg(String sSSNumber)
		{
			String sImg="";
			if(!sSSNumber.equals("")&&!sSSNumber.equals("none")&&!sSSNumber.equals("NULL"))
			{
				try
				{
					sImg += sSSNumber.substring(0,2) + "/";
					sImg += sSSNumber.substring(2,5) + "/";
					sImg += sSSNumber.substring(5,sSSNumber.length()) + "lg";
				}catch(Exception e )//try
				{
					sImg="";
				}
			}
			return sImg;
		}
		public static String markColor(String sw,String sTemp,String sColor)
		{
			String sReturn="";
			int i=0,j=0;
			int iTempLength = sTemp.length();
			int iLengthS1 = sw.length();
			String sTemp1=sw.toLowerCase();
			String sTemp2 = sTemp.toLowerCase();
			while(true)
			{
				i=sTemp2.indexOf(sTemp1,j);
				if(i==-1)
				{
					sReturn += sTemp.substring(j,iTempLength);
					break;
				}
				sReturn += sTemp.substring(j,i) + "<font color=\""+sColor+"\">"+ sTemp.substring(i,i+iLengthS1) + "</font>";

				j= i + iLengthS1;
				if(j>iTempLength)
					j = iTempLength;
			}
			return sReturn;
		}
	    public static String parseMoney(String price){
			price=price.replaceAll("￥","");
			price=price.replaceAll("元","");
			price=price.replaceAll("元","");
			price=price.replaceAll("\\$","");
			price=price.replaceAll("\\＄","");
			price=price.replaceAll("\\(","");
			price=price.replaceAll("\\)","");
			price=price.replaceAll(";","");
			price=price.replaceAll("CNY","");
	    	  return price;
	    	}		
	    //返回保留两位小数的浮点数
	    public static String getFloat(double price){
	      return (new DecimalFormat("0.00").format(price));
	    }	    
	    //返回保留两位小数的浮点数
	    public static String getMoneyString(String price){
	      return (new DecimalFormat("0.00").format(getMoneyDouble(price)));
	    }	
	    //返回保留两位小数的浮点数
	    public static double getMoneyDouble(String price){
	    	price=parseMoney(price);
	    	double dPrice=0;
	    	try{
	    		dPrice=Double.parseDouble(price);
	    	}catch(Exception ex){}
	      return dPrice;
	    }	
	    public static String getStaticDxNumber(String dxNumber)
		{
			String sReturn="";
			try{
				sReturn += dxNumber.substring(0,3) + "/";
				sReturn += dxNumber.substring(3,6) + "/";
				sReturn += dxNumber.substring(6,9) + "/";
				sReturn += dxNumber.substring(9,dxNumber.length());
			}catch(Exception e){}
			return sReturn;
		}
	    //从挂接信息中得到单位的挂接数字，现在挂接较多，可能会大于2位。
	    public static int getFiguresFromCode(String comyChar,String Code)
		{
			int Fig=0;
			try{
				try{
				Code=Code.substring(Code.indexOf(comyChar)+1, Code.indexOf(comyChar)+4);
				}catch(Exception e){Code=Code.substring(Code.indexOf(comyChar)+1);}
				int times=0;
				while(Fig==0 && times<3){
					try{
						Fig=Integer.parseInt(Code);
					}catch(Exception e){
						Fig=0;
						Code=Code.substring(0,Code.length()-1);
						times++;
					}
				}
			}catch(Exception e){}
			return Fig;
		}
	    /**
	     * 字符转int类型
	     * @param str
	     * @return
	     */
	    public static int strToInt(String str){
	    	int num = 0;
	    	if(str==null){
	    		str = "";
	    	}
	    	try{
	    		num = Integer.parseInt(str);
	    	}catch(Exception e){
	    		num = 0;
	    	}
	    	return num;
	    }
	    
	    /**
	     * 字符转int类型
	     * @param str
	     * @return
	     */
	    public static long strToLong(String str){
	    	long num = 0;
	    	if(str==null){
	    		str = "";
	    	}
	    	try{
	    		num = Long.parseLong(str);
	    	}catch(Exception e){
	    		num = 0;
	    	}
	    	return num;
	    }
	    
	    public static String objectToStr(Object obj){
			String str = "";
			if(obj==null){
				str = "";
			}else{
				str = obj.toString();
			}
			if(str.equals("null")){
				str = "";
			}
			return str;
		}
		/**
		 * 对象转int类型
		 * @param obj
		 * @return
		 */
		public static int objectInt(Object obj){
			int num = 0;
			if(obj == null){
				num = 0;
			}else{
				try{
					num = Integer.parseInt(obj.toString());
				}catch(Exception e){
					num = 0;
				}
			}
			return num;
		}
		
		/**
		 * 对象转int类型
		 * @param obj
		 * @return
		 */
		public static long objectToLong(Object obj){
			long num = 0;
			if(obj == null){
				num = 0;
			}else{
				try{
					num = Long.parseLong(obj.toString());
				}catch(Exception e){
					num = 0;
				}
			}
			return num;
		}
		
		/**
		 * 对象转 double类型
		 * @param obj
		 * @return
		 */
		public static double objectToDou(Object obj){
			double num = 0.00;
			if(obj == null){
				num = 0.00;
			}else{
				try{
					num = Double.parseDouble(obj.toString());
				}catch(Exception e){
					num = 0.00;
				}
			}
			return num;
		}
		/**
		 * 字符转双精度
		 * @param str
		 * @return
		 */
		public static double strToDou(String str){
			double num = 0.00;
	    	if(str==null){
	    		str = "";
	    	}
	    	try{
	    		num = Double.parseDouble(str);
	    	}catch(Exception e){
	    		num = 0.00;
	    	}
	    	return num;
		}
		/**
		 * 字符转浮点
		 * @param str
		 * @return
		 */
		public static float strToFlo(String str){
			float num = 0.0f;
	    	if(str==null){
	    		str = "";
	    	}
	    	try{
	    		num = Float.parseFloat(str);
	    	}catch(Exception e){
	    		num = 0.0f;
	    	}
	    	return num;
		}
		
		/**
		 * 期刊的高级检索。关键词组合
		 * @param sw
		 * @param field
		 * @param select
		 * @return
		 */
		public static String makeSearchStr(String[] sw,String[] field,String[] select,String stime,String etime,String checkbox){
			String con = "";
			for(int i = 0;i<5;i++){
				if(!sw[i].equals("")){
					if(con.equals("")){
						if(field[i].equals("2")){
							con = con + " (标题=" + sw[i] + ")";
						}else if(field[i].equals("3")){
							con = con + " (作者=" + sw[i] + ")";
						}else if(field[i].equals("4")){
							con = con + " (刊名=" + sw[i] + ")";
						}else if(field[i].equals("5")){
							con = con + " (关键词=" + sw[i] + ")";
						}else{
							con = con + " (全部字段=" + sw[i] + ")";
						}
					}else{
						//关键词之间的关系
						if(select[i].equals("1")){
							con = con + " and";
						}else if(select[i].equals("2")){
							con = con + " or";
						}else if(select[i].equals("3")){
							con = con + " not";
						}
						//检索关键词
						if(field[i].equals("2")){
							con = con + " (标题=" + sw[i] + ")";
						}else if(field[i].equals("3")){
							con = con + " (作者=" + sw[i] + ")";
						}else if(field[i].equals("4")){
							con = con + " (刊名=" + sw[i] + ")";
						}else if(field[i].equals("5")){
							con = con + " (关键词=" + sw[i] + ")";
						}else{
							con = con + " (全部字段=" + sw[i] + ")";
						}
					}
				}
			}
			if(!stime.equals("-1")&&!etime.equals("-1")&&checkbox.equals("checkbox")){
				if(con.equals("")){
					con = stime+"<日期<"+etime;
				}else{
					con = con + " and (" + stime+"<日期<"+etime+")";
				}
			}
			return con;
		}
		
		public static String replaceEnToCh(String str){
			String islv = "相关词组.*";
			str = str.replaceAll(islv, "");
			System.out.println(str);
	    	String[] strs = str.split(",| ");
	    	str = "";
	    	for(int i = 0;i<strs.length;i++){
	    		System.out.println(strs[i]);
	    		//str = str + "<a href = 'http://test.duxiu.com/goqw.jsp?sw="+strs[i]+"&channel='>"+strs[i]+"</a> ";
	    	}
			return str;
		}
		
		public static String makeKeyWords(String regex,String keyword,String servlet,String channel,String field){
			String keyherf = "";
			String[] keys = keyword.split(regex,0);
			boolean fenhao = true;   //判断关键词之间是否加分号
			try{
				for(int i = 0;i<keys.length;i++){
					if(!keys[i].equals("")){
						if(fenhao){
							keyherf += "<a href = '"+servlet+"?sw="+java.net.URLDecoder.decode(keys[i],"GBK")+"&channel="+channel+"&Field="+field+"' target = '_blank'>"+keys[i]+"</a>";
							fenhao = false;
						}else{
							keyherf += ";<a href = '"+servlet+"?sw="+java.net.URLDecoder.decode(keys[i],"GBK")+"&channel="+channel+"&Field="+field+"' target = '_blank'>"+keys[i]+"</a>";
						}
					}
				}
			}catch(Exception e){
				System.out.println("duxiu StringUtil makeKeyWords error:"+e.getMessage());
			}
			
			return keyherf;
		}
		
		public static String getEncExStr(String str){
			if(str == null) return "";
			if(!str.equals("")){
				str="["+str+"]";
			}
			return str;
		}
		
		/**
		 * 处理一下字符串，过滤一些通配符。
		 * @param sw
		 * @return
		 */
		public static String  filterSw(String sw){
			String  islv="\\||\\!|\\@|\\#|\\$|\\%|\\^|\\&|\\*|\\(|\\)|\\_|\\+|\\-|\\}|\\{|\\/|\\.|\\,|\\?|\\>|\\<|\\~|\\、|\\】|\\【|\\‘|\\；|\\’|\\、|\\。|\\，|\\￥|\\……|\\）|\\（";//检索词需要过滤的特殊字符
			sw=sw.replaceAll(islv,"");
			return sw;
		}
		public static int getIntFromMap(int ibYear,int ieYear,HashMap <String ,Integer>cole){
			int cout=0;
			Integer temp=0;
			for(;ibYear<=ieYear;ibYear++){
				if((temp=cole.get(String.valueOf(ibYear)))!=null)
				cout+=temp;
			}
			return cout;
		}
		
		public static String TransactSQLInjection(String sql) {  
			return sql.replaceAll(".*([';]+|(--)+).*", " ");  
		}
		
		public static String stringTojson(String s) {
			if (s == null)
				return "";
			@SuppressWarnings("unused")
			char cha = '\\';
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				switch (ch) {
				case '"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '/':
					sb.append("\\/");
					break;
				default:
					if (ch >= '\u0000' && ch <= '\u001F') {
						String ss = Integer.toHexString(ch);
						sb.append("\\u");
						for (int k = 0; k < 4 - ss.length(); k++) {
							sb.append('0');
						}
						sb.append(ss.toUpperCase());
					} else {
						sb.append(ch);
					}
				}
			}
			return sb.toString();
		}
		
		/**
		 * 判断字符串是否是整数
		 */
		public static boolean isInteger(String value) {
			try {
				Integer.parseInt(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		/**
		 * 判断字符串是否是浮点数
		 */
		public static boolean isDouble(String value) {
			try {
				Double.parseDouble(value);
				if (value.contains("."))
					return true;
				return false;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		/**
		 * 判断字符串是否是数字
		 */
		public static boolean isNumber(String value) {
			return isInteger(value) || isDouble(value);
		}
}

