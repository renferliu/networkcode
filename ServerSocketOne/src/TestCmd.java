import java.io.File;
import java.io.IOException;


public class TestCmd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Runtime time = Runtime.getRuntime();
		try {
			 time.exec("d:\\Program Files (x86)\\Notepad++\\notepad++.exe");
			// time.exec("D:\Program Files (x86)\Notepad++\notepad++.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
