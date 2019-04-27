package dataAccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileOperations {
	
	public static String readPages(String nameOfFile) {
		String text = "";
		final String directory = System.getProperty("user.dir");
		String filePath = directory + "\\" + nameOfFile;
		try {
			text = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return text;
	}

}
