package utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
 
import data.Construction;

public class FileManager {
	 public static final String FILE_NAME = "Construction.fem";
	 
	 public void writeLibraryToFile(Construction con) {
	        try(
	        FileOutputStream fos = new FileOutputStream(FILE_NAME);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	                ) {
	             
	            oos.writeObject(con);
	             
	        } catch (FileNotFoundException e) {
	            System.err.println("Nie odnaleziono pliku " + FILE_NAME);
	        } catch (IOException e) {
	            System.err.println("Błąd podczas zapisu danych do pliku " + FILE_NAME);
	        }
	    }
	 public Construction readLibraryFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
	        Construction Construction = null;
	        try(
	        FileInputStream fis = new FileInputStream(FILE_NAME);
	        ObjectInputStream ois = new ObjectInputStream(fis);
	                ) {
	             
	        	Construction = (Construction)ois.readObject();
	             
	        } catch (FileNotFoundException e) {
	            System.err.println("Nie odnaleziono pliku " + FILE_NAME);
	            throw e;
	        } catch (IOException e) {
	            System.err.println("Błąd podczas zapisu danych do pliku " + FILE_NAME);
	            throw e;
	        } catch (ClassNotFoundException e) {
	            System.err.println("Nieprawidłowy format pliku");
	            throw e;
	        }
	         
	        return Construction;
	    }
	}


