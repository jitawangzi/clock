package clock;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	JLabel stateLable;
	private static int sitMinites = 30;
	private static int standMinites = 20;
	private static final int fontSize = 60;

	private volatile boolean sitFirst = true;
	long startTime;

	Thread thread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					Clock frame = new Clock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
					thread.interrupt();
					sitFirst = true;
				} else if (e.getKeyCode() == KeyEvent.VK_F2) {
					thread.interrupt();
					sitFirst = false;
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("重置计时-坐");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				thread.interrupt();
				sitFirst = true;
			}
		});
		contentPane.add(btnNewButton, BorderLayout.NORTH);

		JButton btnNewButton_1 = new JButton("重置计时-站");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				thread.interrupt();
				sitFirst = false;
			}
		});

		contentPane.add(btnNewButton_1, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		stateLable = new JLabel("坐站状态");
		stateLable.setFont(new Font("", Font.BOLD, fontSize)); // 设置文字的字体及大小
		panel.add(stateLable);

		thread = new Thread(() -> {
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
		thread.start();
	}

	private void startSitTiming() throws InterruptedException {
		stateLable.setText("坐着");
		Thread.sleep(sitMinites * 60 * 1000);
		alert("坐了" + sitMinites + "分钟了，该站一会了");
		startTime = System.currentTimeMillis();
	}
	private void startStandTiming() throws InterruptedException {
		stateLable.setText("站着");
		Thread.sleep(standMinites * 60 * 1000);
		alert("站了" + standMinites + "分钟了，该坐一会了");
		startTime = System.currentTimeMillis();
	}
	private void alert(String msg) {
		JOptionPane message = new JOptionPane();
		// 设置文本显示效果
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(null, Font.ITALIC, fontSize)));
		message.showMessageDialog(null, msg);
	}

	public class TimePane extends JPanel {

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			// 抗锯齿
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.addRenderingHints(hints);

			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					drawTime(g2d);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		}

		private void drawTime(Graphics2D g2d) {
			int time = (int) ((System.currentTimeMillis() - startTime) / 1000);
			int m = time / 60;
			int s = time % 60;
			String ms = m == 0 ? "00" : m < 10 ? "0" + m : m + "";
			String ss = s == 0 ? "00" : s < 10 ? "0" + s : s + "";
			String text = ms + ":" + ss;
			GameVisHelper.drawText(g2d, text, 50, 50, GameVisHelper.Green);
		}
	}

}
