import java.awt.Graphics;
import java.awt.Graphics2D;

public interface Product {
	public void draw(Graphics g);
	public void drawConnect(City b, Graphics2D g);
}
