import java.io.*;
//import java.lang.*;
//import java.util.stream.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
public class ZipReader {
	FileInputStream zippedFile;
	BufferedInputStream buffer;
	ZipInputStream zip;
	ZipEntry anEntry;
	ZipReader(){
	try{
		zippedFile = new FileInputStream("D:\\Development\\Java\\workspace\\Lesson 16\\src\\ZipArchive.zip");
		buffer = new BufferedInputStream(zippedFile);
		zip = new ZipInputStream(buffer);
		boolean checkNextEntry=true;
		do{
			anEntry = zip.getNextEntry();
			if (anEntry!=null){
				System.out.println("\""+anEntry.getName()+"\""+(anEntry.isDirectory()?" directory":" file") + " found in zip");
			}
			else{
				checkNextEntry=false;
			}
		}
		while (checkNextEntry);
	}
	catch(Exception e){
		System.out.println("Some error with zip-processing happened: "+e.getMessage());
	}
	finally{
		try{
			if (zip!=null) zip.close();
			if (buffer!=null) buffer.close();
			if (zippedFile!=null) zippedFile.close();
		}
		catch (IOException e){
			System.out.println("Some error with stream closing happened: "+e.getMessage());
		}
	}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ZipReader();

	}

}
