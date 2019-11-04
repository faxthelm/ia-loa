package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.Action;
import bean.Cost;
import bean.State;
import enums.ErrorMessages;
import exceptions.CorruptedDataException;

public class ProblemManager {
	private static Map<String, Object> data;

	public static void init(Map<String, Object> dataParsed) {
		data = dataParsed;
	}

	@SuppressWarnings("unchecked")
	public static List<Action> getApplicableActions(State currentState) {
		List<Action> applicableActions = new ArrayList<Action>();
		Map<String, List<Action>> actions = null;
		if (data.get("action") instanceof Map) {
			actions = (Map<String, List<Action>>) data.get("action");
		} else {
			throw new CorruptedDataException(ErrorMessages.CORRUPTED_DATA_ERROR, data.get("action").toString());
		}

		for (String key : actions.keySet()) {
			for (Action action : actions.get(key)) {
				if (action.getFromState().equals(currentState)) {
					applicableActions.add(action);
				}
			}
		}
		
		setApplicableCosts(applicableActions);
		return applicableActions;
	}
	
	@SuppressWarnings("unchecked")
	private static void setApplicableCosts(List<Action> applicableActions){
		List<Cost> costs = null;
		if (data.get("cost") instanceof List) {
			costs = (List<Cost>) data.get("cost");
		} else {
			throw new CorruptedDataException(ErrorMessages.CORRUPTED_DATA_ERROR, data.get("cost").toString());
		}
		for(Action action : applicableActions) {
			for(Cost cost : costs) {
				if(cost.getCurrentState().equals(action.getFromState())){
					action.setCost(cost.getCost());
				}
			}
		}
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
