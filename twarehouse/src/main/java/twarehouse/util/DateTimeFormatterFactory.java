/**
 * 
 */
package twarehouse.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * @author Sidronio
 *
 */
public class DateTimeFormatterFactory {
	
	public static DateTimeFormatter dateFormatters () {
		
		DateTimeFormatterBuilder formatterBuilder = 
				new DateTimeFormatterBuilder();
		
		formatterBuilder.appendPattern("dd/MM/yyyy");
		formatterBuilder.appendPattern("dd/MM/yy");
		
		return formatterBuilder.toFormatter();
	}
	
	public static DateTimeFormatter dateTimeFormatters () {
		
		DateTimeFormatterBuilder formatterBuilder = 
				new DateTimeFormatterBuilder();
		
		formatterBuilder.append(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		formatterBuilder.append(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss"));
		
		return formatterBuilder.toFormatter();
	}

}
