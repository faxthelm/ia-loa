package bean;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {

    public StateComparator() {
    }

    @Override
    public int compare(State s1, State s2) {
        return Double.compare(s1.getHeuristic(), s2.getHeuristic());
    }
}
