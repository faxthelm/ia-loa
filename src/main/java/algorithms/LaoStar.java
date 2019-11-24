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

    private Policy policy;
    private Set<String> expandedStates;
    private Set<State> statesInPolicy;
    final PriorityQueue<State> fringe;
    private Set<State> greedyGraph;
    private HashMap<String, Double> valuesV;

    public LaoStar() {
        this.policy = new Policy(new HashMap<>());
        this.expandedStates = new HashSet<>();
        this.statesInPolicy = new HashSet<>();
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
            State currentState = fringe.poll();
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
            greedyGraph.add(currentState);
            State aux = currentState;
            while (aux.getParent() != null) {
                greedyGraph.add(aux.getParent());
                aux = aux.getParent();
            }

            calculateVI(currentState);
        } 
        Long algorithmTime = System.currentTimeMillis() - begin;
        
        State statePath = ProblemManager.getInitialState();
        while (!statePath.toString().equals(ProblemManager.getGoalState().toString())) {
            List<Action> applicableActions = ProblemManager.getApplicableActions(statePath);
            Action bestAction = null;
            Double minCost = Double.MAX_VALUE;
            for (Action action : applicableActions) {
//              System.err.println(action+" - "+valuesV.get(action.getToState().toString()));
                if (action.getFromState().toString().equals(statePath.toString()) && !action.getToState().toString().equals(statePath.toString())) {
//                   System.err.println("(IN) "+action+" - "+valuesV.get(action.getToState().toString()));
                    if(valuesV.get(action.getToState().toString()) != null && valuesV.get(action.getToState().toString()) < minCost) {
                        minCost = valuesV.get(action.getToState().toString());
                        bestAction = action;
                    }
                }
            }
            
//          System.out.println("DONE state "+statePath+" action chosen "+bestAction);
            policy.getPolicyStatements().put(statePath.toString(), bestAction.getName());
            statePath = bestAction.getToState();
//          System.out.println(statePath.toString());
        }

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

        return result.toString();
    }

    private Double calculateHeuristic(State currentState) {
        // TODO: ver qual heurística vai ser utilizada
        State goal = ProblemManager.getGoalState();
        return (double) (Math.abs(goal.getX() - currentState.getX()) + Math.abs(goal.getY() - currentState.getY()));
    }

    private void calculateVI(State currentState) {
        double residual = Double.MAX_VALUE;
        double sumValue = 0.0;
        double sumValuePrevious = calculateHeuristic(currentState);
        HashMap<String, Double> iteration = new HashMap<>(valuesV);

        while (residual > 0.001) {
            for (State state : greedyGraph) {
                double value = calculateFunctionValue(iteration, state);
                if(state.equals(ProblemManager.getGoalState())) value = 0;
                valuesV.put(state.toString(), value);
                sumValue += value;
            }
            iteration = valuesV;
            residual = Math.abs(sumValue - sumValuePrevious);
            sumValuePrevious = sumValue;
            sumValue = 0;
        }
    }

    private Double calculateFunctionValue(HashMap<String, Double> previousValues, State state) {
        double minValue = Double.MAX_VALUE;
        HashMap<String, Double> values = new HashMap<>();
        HashMap<Action, Double> valuesA = new HashMap<>();
        for (Action action : ProblemManager.getApplicableActions(state)) {
            double value = action.getProbability()
                    * (action.getCost() + previousValues.get(action.getToState().toString()));
            values.merge(action.getName(), value, (a, b) -> (a + b));
            valuesA.merge(action, value, (a, b) -> (a + b));
        }
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            if (minValue > entry.getValue()) {
                minValue = entry.getValue();
                //policy.getPolicyStatements().put(state.toString(), entry.getKey());
            }
        }
//        for (Map.Entry<Action, Double> entry : valuesA.entrySet()) {
//            if (minValue > entry.getValue()) {
//                minValue = entry.getValue();
//                policyLao.getPolicyStatements().put(state, entry.getKey());
//            }
//        }
        
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
