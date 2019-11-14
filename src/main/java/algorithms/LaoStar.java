package algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import bean.Action;
import bean.Policy;
import bean.State;
import bean.StateComparator;
import utils.ProblemManager;

public class LaoStar {

    private List<State> states;
    private Policy policy;
    private Policy tempPolicy;
    private Set<String> expandedStates;
    final PriorityQueue<State> fringe;
    private Set<State> greedyGraph;
    private HashMap<String, Double> valuesV;

    public LaoStar() {
        this.states = ProblemManager.getStates();
        this.policy = new Policy(new HashMap<>());
        this.tempPolicy = new Policy(new HashMap<>());
        this.expandedStates = new HashSet<>();
        this.fringe = new PriorityQueue<>(11, new StateComparator());
        this.greedyGraph = new HashSet<>();
        this.valuesV = new HashMap<>();
    }

    public String calculate() {
        long begin = System.currentTimeMillis();
        State initialState = ProblemManager.getInitialState();
        Double heuristicInitial = calculateHeuristic(initialState);
        initialState.setHeuristic(heuristicInitial);
        valuesV.put(initialState.toString(), heuristicInitial);
        fringe.add(initialState);
        expandedStates.add(initialState.toString());

        while (!fringe.isEmpty()) {
            // EXPAND AND INITIALIZE HEURISTIC
            System.out.println("EXPAND AND INITIALIZE");
            State currentState = fringe.poll();
            System.out.println("Current state: " + currentState.toString());

            List<Action> applicableActions = ProblemManager.getApplicableActions(currentState);
            applicableActions.forEach(action -> {
                State newState = action.getToState();
                if (!expandedStates.contains(newState.toString())) {
                    Double heuristic = calculateHeuristic(newState);
                    newState.setHeuristic(heuristic);
                    newState.setParent(currentState);
                    expandedStates.add(newState.toString());

                    valuesV.put(newState.toString(), heuristic);
                    fringe.add(newState);
                }
            });
            // END
            // COMPUTE GREEDY GRAPH
            System.out.println("COMPUTE GREEDY GRAPH");
            greedyGraph = new HashSet<>();
            greedyGraph.add(currentState);
            State aux = currentState;
            while (aux.getParent() != null) {
                greedyGraph.add(aux.getParent());
                aux = aux.getParent();
            }
            // END
            System.out.println("CALCULATE VI");
            calculateVI();
            if (currentState.toString().equals(ProblemManager.getGoalState().toString())) {
                break;
            }
        }

        //OUTPUT THE FINAL GREEDY GRAPH AS THE POLICY
        for (State state : greedyGraph) {
            if (state.getParent() != null) {
                List<Action> applicableActions = ProblemManager.getApplicableActions(state.getParent());
                for (Action action : applicableActions) {
                    if (action.getToState().toString().equals(state.toString())) {
                        policy.getPolicyStatements().put(state.getParent().toString(), action.getName());
                        break;
                    }
                }
            }
        }
        // END
        Long algorithmTime = System.currentTimeMillis() - begin;

        StringBuilder result = new StringBuilder();
        result.append("\nALGORITHM TIME: ").append(algorithmTime).append(" ms\n\n")
                .append("LAST VALUE ITERATION\n");
        greedyGraph.forEach(state -> {
            result.append("V(robot-")
                    .append(state)
                    .append(") = ")
                    .append(valuesV.get(state.toString()))
                    .append("\n");
        });
        result.append("\n")
                .append(policy.toString());

        return result.toString() + createMap();
    }

    private Double calculateHeuristic(State currentState) {
        // TODO: ver qual heurística vai ser utilizada
        State goal = ProblemManager.getGoalState();
        return (double) (Math.abs(goal.getX() - currentState.getX()) + Math.abs(goal.getY() - currentState.getY()));
    }

    private void calculateVI() {
        double residual = Double.MAX_VALUE;
        double sumValue = 0.0;
        double sumValuePrevious = 0.0;
        int iteration = 2;
        HashMap<String, Double> values = new HashMap<>();
        HashMap<Integer, HashMap<String, Double>> iterations = new HashMap<>();
        iterations.put(1, valuesV);

        while (residual > 0.001) {
            for (State state : greedyGraph) {
                double value = calculateFunctionValue(iterations.get(iteration - 1), state);
                valuesV.put(state.toString(), value);
                sumValue += value;
            }
            iterations.put(iteration, valuesV);
            residual = sumValue - sumValuePrevious;
            sumValuePrevious = sumValue;
            sumValue = 0;
            iteration++;
        }
    }

    private Double calculateFunctionValue(HashMap<String, Double> previousValues, State state) {
        double minValue = Double.MAX_VALUE;
        HashMap<String, Double> values = new HashMap<>();
        for (Action action : ProblemManager.getApplicableActions(state)) {
            double value = action.getProbability()
                    * (action.getCost() + previousValues.get(action.getToState().toString()));
            values.merge(action.getName(), value, (a, b) -> (a + b));
        }
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            if (minValue > entry.getValue()) {
                minValue = entry.getValue();
            }
        }
        return minValue;
    }

    private String createMap() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nGRID WITH OPTIMAL POLICY: \n");
        int gridSize = 20;
        for (int y = gridSize; y > 0; y--) {
            for (int x = 1; x <= gridSize; x++) {
                String state = "at-x" + x + "y" + y;
                String action = policy.getPolicyStatements().get(state);
                if (action == null) {
                    stringBuilder.append(" 0 ");
                } else if (action.equals("move-east")) {
                    stringBuilder.append(" > ");
                } else if (action.equals("move-west")) {
                    stringBuilder.append(" < ");
                } else if (action.equals("move-south")) {
                    stringBuilder.append(" V ");
                } else if (action.equals("move-north")) {
                    stringBuilder.append(" ^ ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
