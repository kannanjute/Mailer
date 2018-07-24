package mail;

import java.io.IOException;

public class Cli {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
System.out.println(System.getProperty("user.dir")+"\\lib");

String path = "java -jar "+System.getProperty("user.dir")+"\\lib\\jenkins-cli.jar -s http://10.153.86.127:8080 -auth admin:admin build Sample -p suiteName=regression";

Process p = Runtime.getRuntime().exec(path);
	}

}
