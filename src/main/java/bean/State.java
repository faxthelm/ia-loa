package bean;

public class State {

    private int x;
    private int y;
    private double heuristic;
    private State parent;

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

    public double getHeuristic() { return heuristic; }

    public void setHeuristic(double heuristic) { this.heuristic = heuristic; }

    public State getParent() { return parent; }

    public void setParent(State parent) { this.parent = parent; }

    public boolean equals(State state) {
        return (this.x == state.x && this.y == state.y);
    }

    @Override
    public String toString() {
        return "at-x" + x + "y" + y;
    }
}
