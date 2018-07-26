package mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.TestNG;

public class MailReader {

	public static void main(String[] args) throws IOException {

		Properties prop = new Properties();
		InputStream input = null;

		String jenkinsIP = "";
		String jenkinsUser = "";
		String jenkinsPassword = "";
		String jenkinsJob = "";
		String jenkinsPort = "";

		try {

			input = new FileInputStream("C:\\Mail_Execute\\config.properties");
			prop.load(input);
			jenkinsIP = prop.getProperty("jenkinsIP");
			jenkinsPort = prop.getProperty("jenkinsPort");
			jenkinsUser = prop.getProperty("jenkinsUser");
			jenkinsPassword = prop.getProperty("jenkinsPassword");
			jenkinsJob = prop.getProperty("jenkinsJob");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		MailReader mail = new MailReader();
		File latestMailFile = mail.getLatestFilefromDir();
		System.out.println(mail.getLatestFilefromDir());
		StringBuilder sb = new StringBuilder();
		if (latestMailFile != null) {
			boolean executeTestSuite = false;

			BufferedReader br = new BufferedReader(new FileReader(latestMailFile));
			try {

				String line = br.readLine();

				while (line != null) {

					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				// return sb.toString();
			} finally {
				br.close();
			}
			String body = sb.toString();
			String body_Closed = body.replace("\n", "");
			if (body_Closed.contains("Status:QA Closed")) {
				executeTestSuite = true;
			}

			body = body.split("JIRA")[1].split("<o:p>")[0];
			body = body.substring(body.indexOf(")") + 1).replace("\n", " ").trim();
			System.out.println(body);

			String path = "java -jar C:\\Mail_Execute\\jenkins-cli.jar -s http://" + jenkinsIP + ":" + jenkinsPort
					+ " -auth " + jenkinsUser + ":" + jenkinsPassword + " build " + jenkinsJob + " -p suiteName=\""
					+ body + "\"";
			System.out.println(path);
			// System.out.println(System.getProperty("java.class.path"));
			if (executeTestSuite) {
				Process p = Runtime.getRuntime().exec(path);
			}

			// Process p = Runtime.getRuntime().exec("java -cp
			// C:\\Kannan\\Eclipse_Workspace\\TestNGSample\\lib\\*;C:\\Kannan\\Eclipse_Workspace\\TestNGSample\\bin
			// org.testng.TestNG Regressiontesting.xml");

			/*
			 * if (executeTestSuite) { TestNG runner = new TestNG(); List<String> suitefiles
			 * = new ArrayList<String>();
			 * suitefiles.add("C:\\Kannan\\Eclipse_Workspace\\TestNGSample\\" + body + ".xml
			 * "); runner.setTestSuites(suitefiles); runner.run();
			 * 
			 * try { // latestMailFile.delete(); } catch (Exception e) {
			 * 
			 * } }
			 */

		}

	}

	private File getLatestFilefromDir() {

		String dirPath = "C:\\mailItems";
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
