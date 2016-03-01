/**
 * 
 */
package com.example.asu.bdi.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaoyaoworm
 * 
 */
public class CommonMethod {

	// change the long time format into the one we always use
	public String chanDateFormat(Long timeInMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date d = new Date(timeInMillis);
		String strDate = sdf.format(d);
		return strDate;
	}
	
}
