import java.io.*;
import java.net.*;

public class ServerSocketOne {
	public static ServerSocket serverSocket = null;
	public static DataInputStream dataInputStream = null;
	public static DataOutputStream dataOutputStream = null;
	public static Socket clientSocket = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			serverSocket = new ServerSocket(8888);
			System.out.print("listening 8888 port!");
			while (true) {
				clientSocket = serverSocket.accept();
				try {
					dataInputStream = new DataInputStream(
							clientSocket.getInputStream());
					String msg = dataInputStream.readUTF();
					System.out.print(msg);

					// �ж����룬������Ӧ�Ĳ���
					if (msg.equalsIgnoreCase("shutdown")) {
						Shutdown();
					} else if (msg.equalsIgnoreCase("restart")) {
						Restart();
					} else if (msg.equalsIgnoreCase("logoff")) {
						Logoff();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						dataInputStream.close();
						clientSocket.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void Shutdown() throws IOException{
		Process p = Runtime.getRuntime().exec("sutdown -s -t 60");
		System.out.println("shutdown ,60 seconds later !");
	}
	public static void Restart() throws IOException{
		Process p = Runtime.getRuntime().exec("sutdown -r -t 60");
		System.out.println("restart ,60 seconds later !");
	}
	public static void Logoff() throws IOException{
		Process p = Runtime.getRuntime().exec("sutdown -l -t 60");
		System.out.println("Logoff ,60 seconds later !");
	}
}
