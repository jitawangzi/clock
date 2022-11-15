package clock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class GameVisHelper {

    private GameVisHelper(){}

    public static final Color Red = new Color(0xF44336);
    public static final Color Pink = new Color(0xE91E63);
    public static final Color Purple = new Color(0x9C27B0);
    public static final Color DeepPurple = new Color(0x673AB7);
    public static final Color Indigo = new Color(0x3F51B5);
    public static final Color Blue = new Color(0x2196F3);
    public static final Color LightBlue = new Color(0x03A9F4);
    public static final Color Cyan = new Color(0x00BCD4);
    public static final Color Teal = new Color(0x009688);
    public static final Color Green = new Color(0x4CAF50);
    public static final Color LightGreen = new Color(0x8BC34A);
    public static final Color Lime = new Color(0xCDDC39);
    public static final Color Yellow = new Color(0xFFEB3B);
    public static final Color Amber = new Color(0xFFC107);
    public static final Color Orange = new Color(0xFF9800);
    public static final Color DeepOrange = new Color(0xFF5722);
    public static final Color Brown = new Color(0x795548);
    public static final Color Grey = new Color(0x9E9E9E);
    public static final Color BlueGrey = new Color(0x607D8B);
    public static final Color Black = new Color(0x000000);
    public static final Color White = new Color(0xFFFFFF);

    public static void strokeCircle(Graphics2D g, int x, int y, int r){

        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g.draw(circle);
    }

    public static void fillCircle(Graphics2D g, int x, int y, int r){

        Ellipse2D circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g.fill(circle);
    }

    public static void strokeRectangle(Graphics2D g, int x, int y, int w, int h){

        Rectangle2D rectangle = new Rectangle2D.Double(x, y, w, h);
        g.draw(rectangle);
    }

    public static void fillRectangle(Graphics2D g, int x, int y, int w, int h){

        Rectangle2D rectangle = new Rectangle2D.Double(x, y, w, h);
        g.fill(rectangle);
    }
	/** 
	 * 用颜色填充满某个矩形
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param color
	 * @param strokeWidth
	 */
	public static void fillRectangle(Graphics2D g, int x, int y, int w, int h, Color color, int strokeWidth) {
		Color cur = g.getColor();
		setColor(g, color);

		Rectangle2D rectangle = new Rectangle2D.Double(x + strokeWidth, y + strokeWidth, w - strokeWidth, h - strokeWidth);
		g.fill(rectangle);
		setColor(g, cur);
	}
	/** 
	 * 填充矩形的中心一部分
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param color
	 * @param strokeWidth
	 */
	public static void fillRectangleInside(Graphics2D g, int x, int y, int w, int h, Color color, int strokeWidth) {
		Color cur = g.getColor();
		setColor(g, color);
		int nx = x + strokeWidth + (w - strokeWidth * 2) / 5;
		int ny = y + strokeWidth + (h - strokeWidth * 2) / 5;
		Rectangle2D rectangle = new Rectangle2D.Double(nx, ny, (w - 2 * strokeWidth) * 0.6, (h - 2 * strokeWidth) * 0.6);
		g.fill(rectangle);
		setColor(g, cur);
	}

    public static void setColor(Graphics2D g, Color color){
        g.setColor(color);
    }

	public static void setStrokeWidth(Graphics2D g, int w) {
        int strokeWidth = w;
        g.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public static void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    public static void putImage(Graphics2D g, int x, int y, String imageURL){

        ImageIcon icon = new ImageIcon(imageURL);
        Image image = icon.getImage();

        g.drawImage(image, x, y, null);
    }

    public static void drawText(Graphics2D g, String text, int centerx, int centery){

		drawText(g, text, centerx, centery, null);
    }
	public static void drawText(Graphics2D g, String text, int centerx, int centery, Color color) {

		if (text == null)
			throw new IllegalArgumentException("Text is null in drawText function!");

		FontMetrics metrics = g.getFontMetrics();
		int w = metrics.stringWidth(text);
		int h = metrics.getDescent();
		if (color != null) {
			Color cur = g.getColor();
			g.setColor(color);

			g.drawString(text, centerx - w / 2, centery + h);
			g.setColor(cur);
		} else {
			g.drawString(text, centerx - w / 2, centery + h);
		}
	}
	public static void drawTextWithFont(Graphics2D g, String text, int centerx, int centery, int fontSize) {

		if (text == null)
			throw new IllegalArgumentException("Text is null in drawText function!");
		g.setFont(new Font(null, Font.PLAIN, fontSize));
		drawText(g, text, centerx, centery);
	}

//	public static JOptionPane createJOptionPane() {
//		JOptionPane pane = new JOptionPane();
//		// 设置文本显示效果
//		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(null, Font.ITALIC, PropertiesUtil.getInt(
//				"messageFontSize"))));
//		return pane;
//	}
}
