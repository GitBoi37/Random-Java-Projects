package snake;

public class Point {
	int x;
	int y;
	public Point() {
		x = 0;
		y = 0;
	}
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(double x, double y) {
		this.x = (int)x;
		this.y = (int)y;
	}
	public void randomize(int startX, int endX, int startY, int endY) {
		x = (int)(Math.random() *(endX - startX)) + startX;
		y = (int)(Math.random() *(endY - startY)) + startY;
		while(y > endY) {
			y-=10;
		}
	}
	public void rectify() {
		x = x/10*10;
		y = y/10*10;
		if(x/10%10 % 2 != 0) {
			x+= 10;
		}
		if(y/10%10 % 2 != 0) {
			y+= 10;
		}
	}
}
