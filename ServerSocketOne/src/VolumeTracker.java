import java.awt.*; 
import java.awt.font.*; 
import java.awt.geom.*; 
import java.awt.event.*; 
import java.text.AttributedString; 
import java.text.AttributedCharacterIterator; 
import javax.swing.*; 
import javax.swing.border.*; 
import javax.swing.table.*; 
import javax.swing.event.*; 
import java.io.*; 
public class VolumeTracker extends JPanel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String welcomeStr = "Welcome to Java Sound"; 
	public static Thread pBThread; 
	Color background = Color.white; 
	//new Color(20, 20, 20); 
	Color jfcBlue = Color.blue; 
	//new Color(204, 204, 255); 
	Color jfcDarkBlue = jfcBlue.darker(); 
	Font font24 = new Font("serif", Font.BOLD, 24); 
	Font font28 = new Font("serif", Font.BOLD, 28); 
	Font font42 = new Font("serif", Font.BOLD, 42); 
	FontMetrics fm28, fm42; 
	String errStr=null; 
	String currentName=null; 
	double duration = 100.0; 
	double seconds = 82.0; 
	boolean midiEOM, audioEOM; 
	public VolumeTracker()
	{
	fm28 = getFontMetrics(font28); 
	fm42 = getFontMetrics(font42); 
	initVolume(); 
	start();
	}

	private void initVolume()
	{
		try{
			//这一段小程序实现对VC创建程序的调用 
			Runtime rt = Runtime.getRuntime(); //Time and Date. mngPathTool(); 
			//mngPathTool类,提供了一个获取当前路径的方法 
			mngPathTool tool = new mngPathTool();
			String sexec = tool.getCurPath()+ "\\binex\\VolumeControl.exe 0"; 
			Process child = rt.exec(sexec); 
			//获取控制台输出的内容,进而获得音量的大小 
			InputStreamReader reader = new InputStreamReader(child.getInputStream()); 
			char[] chr = new char[5]; 
			reader.read(chr) ; 
			String s=""; 
			for(int i=0;i<5;i++)
			{
				if(chr[i]>='0' && chr[i]<='9') s+=chr[i];
			}
			//System.out.println(s);
			Integer nVolume = new Integer(s); 
			seconds = nVolume.intValue(); 
			child.waitFor();//这一段小程序实现对VC创建程序的调用
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}
	public void paint(Graphics g)
	{
		//画图来实现百分比Tracker 
		Graphics2D g2 = (Graphics2D) g; 
		Dimension d = getSize(); 
		g2.setBackground(background); 
		g2.clearRect(0, 0, d.width, d.height); 
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(jfcBlue); 
		double tseconds = duration-seconds; 
		if (tseconds > 0.0)
		{
			int num = 20; 
			int progress = (int) (tseconds / duration * num); 
			double hh = ((double) (d.height - 4) / (double) num); 
			double ww = (int)(d.width-4); 
			double x = 0.0; 
			for ( ; x < progress; x+=1.0) 
			{
				g2.fill(new Rectangle2D.Double(d.width-ww-2, x*hh+2, ww, hh));
				g2.fill3DRect((int)(d.width-ww-2),(int) (x*hh+2),(int) ww, (int)hh,true);
			}
			g2.setColor(jfcDarkBlue); 
			for ( ; x < num; x+=1.0)
			{
				g2.fill(new Rectangle2D.Double(d.width-ww-2, x*hh+2, ww, hh));
				g2.fill3DRect((int)(d.width-ww-2),(int) (x*hh+2),(int) ww, (int)hh,true);
　　　			}
　　		}
　	}

	public void start(){
		pBThread = new Thread(this); 
		pBThread.setName("PlaybackMonitor");
		pBThread.start();
	}

	public void stop()
	{
	if (pBThread != null) 
	{
		pBThread.interrupt();
	}
	pBThread = null; 
	}

	public void run()
	{
		while (pBThread != null)
		{
			try 
			{
				pBThread.sleep(99); 
			}
			catch (Exception e) 
			{
				break; 
			}
			repaint(); 
		}
		pBThread = null;
	}

	public void addVolume()
	{
		changeVolume(false); 
		initVolume();
	}

	public void minusVolume()
	{
	changeVolume(true); 
	initVolume(); 
	}

	//control sound volume.

	private void changeVolume(boolean bIsMinus)
	{
	try{
	Runtime rt = Runtime.getRuntime(); 
	//Sound Control mngPathTool 
	tool = new mngPathTool();
	String sexec; 
	if(bIsMinus)
	sexec= tool.getCurPath()+ "\\binex\\VolumeControl.exe 2"; 
	else 
	sexec= tool.getCurPath()+ "\\binex\\VolumeControl.exe 1"; 
	rt.exec(sexec); 
	}catch(Exception e1){e1.printStackTrace(); }
	}
}
// End VolumeTracker