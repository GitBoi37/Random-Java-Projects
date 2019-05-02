import java.awt.Color;
import java.awt.color.*;
public class Dot {
	int x;
	int y;
	int size;
	Color color = Color.RED;
	public Dot() {
		x = 250;
		y = 250;
		size = 10;
	}
	public Dot(int a, int b, int c) {
		x = a;
		y = b;
		size = c;
	}
	public Dot(int a, int b) {
		x = a;
		y = b;
		size = 10;
	}
	public Dot(int a, int b, int c, Color d) {
		x = a;
		y = b;
		size = c;
		color = d;
	}
}
