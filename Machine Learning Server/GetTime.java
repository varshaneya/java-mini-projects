/**

*/

import java.util.*;
import java.text.*;

public class GetTime
{
	 public static void main(String[] args) 
	 {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzzz");
        System.out.println( sdf.format(cal.getTime()) );
    }

}