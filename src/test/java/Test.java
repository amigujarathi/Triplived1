import java.util.regex.Pattern;


public class Test {

	public static void main(String[] args) {
		
		Pattern p =  Pattern.compile("^[1|-][2|-][3|-][4|-][5|-][6|-][7|-]$");
		System.out.println(p.matcher("1234567").matches());
		System.out.println(p.matcher("-------").matches());
		System.out.println(p.matcher("------7").matches());
		System.out.println(p.matcher("1------").matches());
		System.out.println(p.matcher("1-----7").matches());
	}
}
