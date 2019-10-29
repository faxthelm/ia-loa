package bean;

public class State {
	
	private int x;
	private int y;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(State state) {
		return (this.x == state.x && this.y == state.y);
	}
	
	@Override
	public String toString() {
		return "at "+x+"-"+y;
	}
}
