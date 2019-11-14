package utils;

import java.util.List;
import java.util.Map;

import bean.Action;
import bean.State;

public class ProblemManager {
    private static Map<String, Object> data;

    public static void init(Map<String, Object> dataParsed) {
        data = dataParsed;
    }

    @SuppressWarnings("unchecked")
    public static List<Action> getApplicableActions(State currentState) {      
        return ((Map<String,List<Action>>) (data.get("action"))).get(currentState.toString());
    }

    public static State getInitialState() {
        return (State) data.get("initialstate");
    }

    public static State getGoalState() {
        return (State) data.get("goalstate");
    }

    public static List<State> getStates() {
        return (List<State>) data.get("states");
    }

}
