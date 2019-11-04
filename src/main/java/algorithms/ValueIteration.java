package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bean.Action;
import bean.Cost;
import bean.Policy;
import bean.State;
import utils.ProblemManager;

public class ValueIteration {

    private HashMap<Integer, HashMap<State, Double>> iterations;
    private List<State> states;
    private Policy policy;

    public ValueIteration() {
        this.states = ProblemManager.getStates();
        this.iterations = new HashMap<>();
        this.policy = new Policy(new HashMap<>());
    }

    public void initialize() {
        State goal = ProblemManager.getGoalState();
        HashMap<State, Double> arbitraryValues = new HashMap<>();
        for (State state : states) {
            Double val = (double) (Math.abs(goal.getX() - state.getX()) + Math.abs(goal.getY() - state.getY()));
            arbitraryValues.put(state, val);
        }
        iterations.put(1, arbitraryValues);
    }

    public void calculate() {
        initialize();
        double residual = Double.MAX_VALUE;
        double sumValue = 0.0;
        double sumValuePrevious = 0.0;
        int iteration = 2;
        HashMap<State, Double> values = new HashMap<>();

        while (residual > 0.001) {
            System.out.print("INTERATION" + iteration + "\n");
            for (State state : states) {
                double value = calculateFunctionValue(state, (iteration - 1));
                values.put(state, value);
                sumValue += value;
            }
            iterations.put(iteration, values);
            residual = sumValue - sumValuePrevious;
            sumValuePrevious = sumValue;
            iteration++;
        }

    }

    public double calculateFunctionValue(State state, int previousIteration) {
        double minValue = Double.MAX_VALUE;
        HashMap<State, Double> previousValues = iterations.get(previousIteration);
        HashMap<Action, Double> values = new HashMap<>();
        List<Action> applicableActions = ProblemManager.getApplicableActions(state);
        for (Action action : applicableActions) {
            double value;
            System.out.println(previousIteration);
            System.out.println(action.getProbability());
            System.out.println(action.getCost());
            // TODO: Valor vindo nulo sendo que está no hash, entender porque e resolver
            System.out.println(previousValues.get(action.getToState()));
            value = action.getProbability() * (action.getCost() + previousValues.get(action.getToState()));
            values.merge(action, value, (a, b) -> (a + b));
        }
        for (Map.Entry<Action, Double> entry : values.entrySet()) {
            if (minValue > entry.getValue()) {
                minValue = entry.getValue();
                policy.getPolicyStatements().put(state, entry.getKey());
            }
        }
        System.out.print("V(" + state.toString() + ") = " + minValue + "\n");
        return minValue;
    }

    public Policy getPolicy() {
        return policy;
    }
}
