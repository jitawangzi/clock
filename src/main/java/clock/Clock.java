package clock;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;


public class Clock extends JFrame {

	private JPanel contentPane;
	private volatile JLabel stateLable;
	private static int sitMinites = 30;
	private static int standMinites = 20;
	private static final int fontSize = 60;

	private volatile boolean sitFirst = true;
	private volatile long startTime;
	private volatile boolean isSitting;

	Thread timeThread;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Clock frame = new Clock();
			frame.setVisible(true);
		});
	}

	/**
	 * Create the frame.
	 */
	public Clock() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F1) {
					timeThread.interrupt();
					sitFirst = true;
				} else if (e.getKeyCode() == KeyEvent.VK_F2) {
					timeThread.interrupt();
					sitFirst = false;
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1470, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("重置计时-坐");
		btnNewButton.addActionListener(e -> {
			timeThread.interrupt();
			sitFirst = true;
		});
		contentPane.add(btnNewButton, BorderLayout.NORTH);

		JButton btnNewButton_1 = new JButton("重置计时-站");
		btnNewButton_1.addActionListener(e -> {
			timeThread.interrupt();
			sitFirst = false;
		});

		contentPane.add(btnNewButton_1, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		stateLable = new JLabel();
		stateLable.setFont(new Font("", Font.BOLD, fontSize)); // 设置文字的字体及大小
		panel.add(stateLable);

		timeThread = new Thread(() -> {
			while (true) {
				try {
					if (sitFirst) {
						startSitTiming();
						startStandTiming();
					} else {
						startStandTiming();
						startSitTiming();
					}
				} catch (InterruptedException e1) {
//					e1.printStackTrace();
				}
			}
		});
		timeThread.start();
		new Thread(() -> {
			while (true) {
				try {
					status();
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
//					e1.printStackTrace();
				}
			}
		}).start();
	}

	private void startSitTiming() throws InterruptedException {
//		stateLable.setText("坐着");
		isSitting = true;
		startTime = System.currentTimeMillis();
		Thread.sleep(sitMinites * 60 * 1000);
		alert("坐了" + sitMinites + "分钟了，该站一会了");
	}
	private void startStandTiming() throws InterruptedException {
		isSitting = false;
		startTime = System.currentTimeMillis();
//		stateLable.setText("站着");
		Thread.sleep(standMinites * 60 * 1000);
		alert("站了" + standMinites + "分钟了，该坐一会了");
	}
	private void alert(String msg) {
		JOptionPane message = new JOptionPane();
		// 设置文本显示效果
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(null, Font.ITALIC, fontSize)));
		message.showMessageDialog(null, msg);
	}

	private void status() {
		if (startTime == 0) {
			return;
		}
		int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
		int m = time / 60;
		int s = time % 60;
		String ms = m == 0 ? "00" : m < 10 ? "0" + m : m + "";
		String ss = s == 0 ? "00" : s < 10 ? "0" + s : s + "";
		String text = ms + ":" + ss;
		String status = (isSitting ? "坐着 " : "站着 ") + text;
		stateLable.setText(status);
	}
}
