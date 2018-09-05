package mail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

import mail.Driver;
import mail.HomePage;
import mail.SearchPage;;

public class FindAffectedTC{

//	@Test
//	public void main1() throws ClassNotFoundException, IOException {
	public static void main(String args[]) throws ClassNotFoundException, IOException {
		/*
		 * java.lang.annotation.Annotation[] annotations =
		 * Class.forName("mail.Sample").getAnnotations();
		 * System.out.println(annotations[0]);
		 */

		/*
		 * java.lang.annotation.Annotation[] annotations =
		 * Class.forName("mail.Sample").getAnnotations();
		 * 
		 * System.out.println(annotations[0]);
		 */

		/*
		 * System.out.println(getClasses("default package").length);
		 * 
		 * getClasses("mail")[0].getMethods();
		 * 
		 * System.out.println(getClasses("mail")[0]);
		 * 
		 * System.out.println(getClasses("mail")[0].isAnnotationPresent(Functionality.
		 * class));
		 * 
		 * Functionality func = (Functionality)
		 * getClasses("mail")[0].getAnnotation(Functionality.class);
		 * 
		 * System.out.println(func.module() );
		 */

		// System.getenv().get(key) // to get parameter from jenkins
//		String affectedstory = args[0];
		String affectedstory = args[0];
		Class[] allclass = getClasses("");
System.out.println(allclass.length);
		List<Method> functionalMethods = new ArrayList<Method>();

		List<Method> affectedMethods = new ArrayList<Method>();

		List<Class> functionalClass = new ArrayList<Class>();

		List<String> affectedFunctionality = new ArrayList<String>();

		List<Class> affectedClass = new ArrayList<Class>();

		Method[] methods = null;

		for (int i = 0; i < allclass.length; i++) {

			if (allclass[i].isAnnotationPresent(Functionality.class)) {
				functionalClass.add(allclass[i]);

				String stories[] = ((Functionality) allclass[i].getAnnotation(Functionality.class)).story();

				if (Arrays.asList(stories).contains(affectedstory.trim())) {

					affectedFunctionality.addAll(
							Arrays.asList(((Functionality) allclass[i].getAnnotation(Functionality.class)).module()));

				}

			}

			methods = allclass[i].getMethods();

			for (int j = 0; j < methods.length; j++) {

				if (methods[j].isAnnotationPresent(Functionality.class)) {

					functionalMethods.add(methods[j]);

				}

			}

		}
		System.out.println(functionalMethods);
		for (Method current : functionalMethods) {

			if (Arrays.asList(current.getAnnotation(Functionality.class).story()).contains(affectedstory.trim())) {

				affectedFunctionality.addAll(Arrays.asList(current.getAnnotation(Functionality.class).module()));

			}

		}

		for (Class fc : functionalClass) {
			List<String> affected = new ArrayList<String>();
			affected.addAll(affectedFunctionality);
			affected
					.retainAll(Arrays.asList(((Functionality) fc.getAnnotation(Functionality.class)).module()));

			if (affected.size() >= 1) {

				affectedClass.add(fc);

			}

		}

		for (Method fm : functionalMethods) {
			List<String> affected = new ArrayList<String>();
			affected.addAll(affectedFunctionality);
			affected.retainAll(Arrays.asList(fm.getAnnotation(Functionality.class).module()));

			if (affected.size() >= 1) {

				affectedMethods.add(fm);

			}

		}

		System.out.println(affectedClass);
		System.out.println(affectedMethods);
		createReports(affectedClass, affectedMethods, affectedstory);

	}

	private static Class[] getClasses(String packageName)

			throws ClassNotFoundException, IOException {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		assert classLoader != null;

		String path = packageName.replace('.', '/');

		Enumeration resources = classLoader.getResources(path);

		List<File> dirs = new ArrayList();

		while (resources.hasMoreElements()) {

			URL resource = (URL) resources.nextElement();

			dirs.add(new File(resource.getFile().toString().replace("%20", " ")));

		}
		ArrayList classes = new ArrayList();

		for (File directory : dirs) {

			classes.addAll(findClasses(directory, packageName));

		}

		return (Class[]) classes.toArray(new Class[classes.size()]);

	}

	/**
	 * 
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * 
	 * 
	 * @param directory
	 *            The base directory
	 * 
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * 
	 * @return The classes
	 * 
	 * @throws ClassNotFoundException
	 * 
	 */

	public static List findClasses(File directory, String packageName1) throws ClassNotFoundException {

		List classes = new ArrayList();

		if (!directory.exists()) {
			return classes;

		}

		File[] files = directory.listFiles();
		for (File file : files) {

			if (file.isDirectory()) {

				assert !file.getName().contains(".");

				if (packageName1.length() == 0)
					classes.addAll(findClasses(file, file.getName()));
				else
					classes.addAll(findClasses(file, packageName1 + "." + file.getName()));

			} else if (file.getName().endsWith(".class")) {
				if (packageName1.length() == 0)
					classes.add(Class.forName(file.getName().substring(0, file.getName().length() - 6)));
				else
					classes.add(Class
							.forName(packageName1 + '.' + file.getName().substring(0, file.getName().length() - 6)));

			}

		}

		return classes;

	}

	public static void createReports(List classes, List methods, String affectedstory) {
		String time = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss").format(new Date()) ;
		if(!new File("C://Reports").exists())
			new File("C://Reports").mkdir();
		File file = new File("C://Reports//AffectedModules Story "+affectedstory+"_"+time+".html");
		new File("C://Reports").mkdir();

		/*
		 * if(file.exists()){ System.out.println("File already exist"); } else{
		 */
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);

			String htmlPage = "<html><body style=’background-color:#ccc’><b><h3><center><u>The Classes and Methods which affected by this Stories are:</u></center></h3></b>";

			bufferedWriter.write(htmlPage);
			bufferedWriter.append("<strong><h4>Classes:</h4></strong>");
			bufferedWriter.append("<ul>");
			for (int i = 0; i < classes.size(); i++) {
				bufferedWriter.append("<li>" + classes.get(i) + "</li>");
			}
			bufferedWriter.append("</ul>");

			bufferedWriter.append("<strong><h4>Methods:</h4></strong>");
			bufferedWriter.append("<ul>");
			for (int i = 0; i < methods.size(); i++) {
				bufferedWriter.append("<li>" + methods.get(i) + "</li>");
			}
			bufferedWriter.append("</ul>");
			bufferedWriter.append("</body></html>");

			System.out.println("Html page created");
			bufferedWriter.flush();
			fileWriter.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
