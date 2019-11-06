package algorithms;

import bean.Action;
import bean.Policy;
import bean.State;
import utils.ProblemManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueIteration {

    private HashMap<Integer, HashMap<String, Double>> iterations;
    private List<State> states;
    private Policy policy;
    private HashMap<String, List<Action>> possibleActions;

    public ValueIteration() {
        this.states = ProblemManager.getStates();
        this.iterations = new HashMap<>();
        this.policy = new Policy(new HashMap<>());
        this.possibleActions = ProblemManager.generatePossibleActions();
    }

    public void initialize() {
        State goal = ProblemManager.getGoalState();
        HashMap<String, Double> arbitraryValues = new HashMap<>();
        for (State state : states) {
            Double val = (double) (Math.abs(goal.getX() - state.getX()) + Math.abs(goal.getY() - state.getY()));
            arbitraryValues.put(state.toString(), val);
        }
        iterations.put(0, arbitraryValues);
    }

    public String calculate() {
        long begin = System.currentTimeMillis();
        initialize();
        double residual = Double.MAX_VALUE;
        double sumValue = 0.0;
        double sumValuePrevious = 0.0;
        int iteration = 1;
        HashMap<String, Double> values = new HashMap<>();

        while (residual > 0.001) {
            for (State state : states) {
                double value = calculateValueFunction(state, (iteration - 1));
                values.put(state.toString(), value);
                sumValue += value;
            }
            iterations.put(iteration, values);
            residual = sumValue - sumValuePrevious;
            sumValuePrevious = sumValue;
            sumValue = 0;
            iteration++;
        }
        Long algorithmTime = System.currentTimeMillis() - begin;
        StringBuilder result = new StringBuilder();
        result.append("\nALGORITHM TIME: ").append(algorithmTime).append(" ms\n\n")
                .append("LAST VALUE ITERATION\n");
        HashMap<String, Double> lastIteration = iterations.get(iteration - 1);
        lastIteration.keySet().forEach(state -> result
                .append("V(")
                .append(state)
                .append(") = ")
                .append(lastIteration.get(state))
                .append("\n"));
        return result.toString();

    }

    public double calculateValueFunction(State state, int previousIteration) {
        double minValue = Double.MAX_VALUE;
        HashMap<String, Double> previousValues = iterations.get(previousIteration);
        HashMap<String, Double> values = new HashMap<>();
        List<Action> applicableActions = possibleActions.get(state.toString());
        for (Action action : applicableActions) {
            double value = action.getProbability()
                    * (action.getCost() + previousValues.get(action.getToState().toString()));
            values.merge(action.getName(), value, (a, b) -> (a + b));
        }
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            if (minValue > entry.getValue()) {
                minValue = entry.getValue();
                policy.getPolicyStatements().put(state.toString(), entry.getKey());
            }
        }
        // System.out.print("V(" + state.toString() + ") = " + minValue + "\n");
        return minValue;
    }

    public Policy getPolicy() {
        return policy;
    }

    public String createMap() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "GRID WITH OPTIMAL POLICY: \n");
        int gridSize = 20;
        for(int y = gridSize; y>0; y--) {
            for(int x = 1 ; x<=gridSize; x++) {
                String state = "at " + x + "-" + y;
                String action = policy.getPolicyStatements().get(state);
                if(action == null) {
                    stringBuilder.append(" P ");
                } else if (action.equals("move-east")) {
                        stringBuilder.append(" > ");
                } else if (action.equals("move-west")) {
                    stringBuilder.append(" < ");
                } else if (action.equals("move-south")) {
                    stringBuilder.append(" V ");
                } else if (action.equals("move-north")){
                    stringBuilder.append(" ^ ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
