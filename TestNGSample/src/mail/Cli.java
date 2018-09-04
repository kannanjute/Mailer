package mail;

import java.io.IOException;

@Functionality(module= {"login"},story= {"1234"})
public class Cli {

	@Functionality(module= {"login"},story= {"1234"})
	public  void executeJenkinsCLI() throws IOException {
		// TODO Auto-generated method stub
		
		
System.out.println(System.getProperty("user.dir")+"\\lib");
System.out.println(System.getProperty("java.class.path"));

String path = "java -jar "+System.getProperty("java.class.path")+"\\lib\\jenkins-cli.jar -s http://10.153.86.127:8080 -auth admin:admin build Sample -p suiteName=regression";

Process p = Runtime.getRuntime().exec(path);
	}

}

