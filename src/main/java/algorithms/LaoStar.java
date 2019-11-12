package algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import bean.Action;
import bean.Policy;
import bean.State;
import bean.StateComparator;
import utils.ProblemManager;

public class LaoStar {

    private HashMap<String, List<Action>> possibleActions;
    private List<State> states;
    private Policy policy;
    private Policy tempPolicy;
    private HashMap<String, State> visitedStates;
    //TODO definir estrutura dos grafos
    final PriorityQueue<State> fringe;
    //private graph2;
    private Set<State> greedyGraph;
    private HashMap<String, Double> valuesV;

    public LaoStar() {
        this.states = ProblemManager.getStates();
        this.policy = new Policy(new HashMap<>());
        this.tempPolicy = new Policy(new HashMap<>());
        this.possibleActions = ProblemManager.generatePossibleActions();
        this.visitedStates = new HashMap<>();
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

        while (!fringe.isEmpty()) {
            // EXPAND AND INITIALIZE HEURISTIC
            System.out.println("EXPAND AND INITIALIZE");
            State currentState = fringe.poll();
            System.out.println("Current state: " + currentState.toString());
            visitedStates.put(currentState.toString(), currentState);

            Set<String> expandedStates = new HashSet<>();
            List<Action> applicableActions = possibleActions.get(currentState.toString());
            applicableActions.forEach(action -> {
                State newState = action.getToState();
                if(!expandedStates.contains(newState.toString())) {
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
            if(currentState.toString().equals(ProblemManager.getGoalState().toString())) {
                break;
            }
        }

        //OUTPUT THE FINAL GREEDY GRAPH AS THE POLICY
        greedyGraph.forEach(state -> {
            policy.getPolicyStatements().put(state.toString(), tempPolicy.getPolicyStatements().get(state.toString()));
        });
        // END
        Long algorithmTime = System.currentTimeMillis() - begin;

        StringBuilder result = new StringBuilder();
        result.append("\nALGORITHM TIME: ").append(algorithmTime).append(" ms\n\n")
                .append("LAST VALUE ITERATION\n");
        greedyGraph.forEach(state -> {
            result.append("V(")
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
        List<Action> applicableActions = possibleActions.get(state.toString());
        for (Action action : applicableActions) {
            double value = action.getProbability()
                    * (action.getCost() + previousValues.get(action.getToState().toString()));
            values.merge(action.getName(), value, (a, b) -> (a + b));
        }
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            if (minValue > entry.getValue()) {
                minValue = entry.getValue();
                tempPolicy.getPolicyStatements().put(state.toString(), entry.getKey());
            }
        }
        return minValue;
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
                    stringBuilder.append(" N ");
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
