package mail;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class MailReader {

	public static void main(String[] args) throws IOException {
		
		MailReader mail = new MailReader();
		File latestMailFile =  mail.getLatestFilefromDir();
		System.out.println(mail.getLatestFilefromDir());
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(latestMailFile));
	    try {
	       
	        String line = br.readLine();

	        while (line != null) {
	        	
	        	sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
//	        return sb.toString();
	    } finally {
	        br.close();
	    }
		String body = sb.toString();
		body = body.split("JIRA")[1].split("<o:p>")[0];
		body = body.substring(body.indexOf(")")+1).replace("\n", " ").trim();
		System.out.println(body);
		
		//Process p = Runtime.getRuntime().exec("java -cp C:\\Kannan\\Eclipse_Workspace\\TestNGSample\\lib\\*;C:\\Kannan\\Eclipse_Workspace\\TestNGSample\\bin org.testng.TestNG Regressiontesting.xml");
	    
		TestNG runner=new TestNG();
		// Create a list of String 
		List<String> suitefiles=new ArrayList<String>();
		// Add xml file which you have to execute
		suitefiles.add("C:\\Kannan\\Eclipse_Workspace\\TestNGSample\\"+body+".xml");
		// now set xml file for execution
		runner.setTestSuites(suitefiles);
		// finally execute the runner using run method
		runner.run();

		try {
			latestMailFile.delete();
		}
		catch(Exception e) {
			
		}
		
		
		

	}


private File getLatestFilefromDir(){
    
	String dirPath ="C:\\mailItems";
	File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        return null;
    }

    File lastModifiedFile = files[0];
    for (int i = 1; i < files.length; i++) {
       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
           lastModifiedFile = files[i];
       }
    }
    return lastModifiedFile;
}

}
