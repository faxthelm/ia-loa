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

public class ValueIteration {

    private HashMap<Integer, HashMap<State, Double>> iterations;
    private Map<String, Object> problem;
    private List<State> states;
    private Policy policy;

    public ValueIteration(Map<String, Object> problem) {
        this.problem = problem;
        this.states = (List<State>) problem.get("states");
        this.iterations = new HashMap<>();
        this.policy = new Policy(new HashMap<>());
    }

    public void initialize() {
        State goal = (State) problem.get("goalstate");
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
        Map<String, List<Action>> mapActions = (Map<String, List<Action>>) problem.get("action");
        List<Action> actions = new ArrayList<>();
        Set<String> keys = mapActions.keySet();
        keys.forEach(key -> actions.addAll(mapActions.get(key)));
        List<Cost> costs = (List<Cost>) problem.get("cost");

        while (residual > 0.001) {
            System.out.print("INTERATION" + iteration + "\n");
            for (State state : states) {
                double value = calculateFunctionValue(state, actions, costs, (iteration - 1));
                values.put(state, value);
                sumValue += value;
            }
            iterations.put(iteration, values);
            residual = sumValue - sumValuePrevious;
            sumValuePrevious = sumValue;
        }

    }

    public double calculateFunctionValue(State state, List<Action> actions, List<Cost> costs, int previousIteration) {
        double minValue = Double.MAX_VALUE;
        HashMap<State, Double> previousValues = iterations.get(previousIteration);
        HashMap<Action, Double> values = new HashMap<>();
        List<Action> applicableActions = actions.stream()
                .filter(action -> action.getFromState() == state)
                .collect(Collectors.toList());
        for (Action action : applicableActions) {
            double value;
            List<Cost> cost = costs.stream().filter(costAction -> costAction.getActionName().equals(action.getName())
                    && costAction.getCurrentState().equals(action.getFromState())).collect(Collectors.toList());
            value = action.getProbability() * (cost.get(0).getCost() + previousValues.get(action.getToState()));
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
