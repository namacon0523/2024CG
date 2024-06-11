package enshu10;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AWTSample1 {
	public static void main(String[] args) {
		AppFrame f = new AppFrame();
		f.setSize(200,200);
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.out.println("Paint");
				System.exit(0);}});
			f.setVisible(true);
	}
}