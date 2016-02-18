package angier.toolkit.common.util;

import java.util.Random;

public class StringRandom {

	/**
	 * 生成随机数字和字母,
	 * @param length
	 * @return
	 */
	public static String getStringRandom(int length) {
		
		String val = "";
		Random random = new Random();
		
		//参数length，表示生成几位随机数
		for(int i = 1; i <= length; i++) {
			
			String charOrNum = random.nextInt(i) % 2 == 0 ? "char" : "num";
			if(i==length){
				charOrNum = "num";
			}
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char)(random.nextInt(26) + temp);
			 
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	/**
	 * 定生成给定参数区间的随机字符串
	 * @param min
	 * @param max
	 * @return
	 */
	public static String getStringRandom4Fixed(int min,int max){
		return getStringRandom((new Random().nextInt(max))%(max-min+1)+min);
	}

}