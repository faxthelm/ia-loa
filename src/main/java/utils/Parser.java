package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Action;
import bean.Cost;
import bean.State;
import enums.ErrorMessages;
import exceptions.FileFormatException;

public class Parser {
	public State readState(String state) {
		State s = new State();
		if (state != null && !state.trim().isEmpty() && state.trim().length() > 9) {
			String data = state.trim().substring(9, state.trim().length());
			String[] x_and_y = data.split("y");
			s.setX(Integer.valueOf(x_and_y[0].replace("x", "")));
			s.setY(Integer.valueOf(x_and_y[1]));
		} else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION, "\"" + state + "\"");
		}
		return s;
	}

	public Action readAction(String actionName, String actionString) {
		if (actionString != null && !actionString.trim().isEmpty()) {
			String[] bits = actionString.trim().split(" ");
			State fromState = readState(bits[0]);
			State toState = readState(bits[1]);
			double probability = Double.valueOf(bits[2]);
			return new Action(actionName, fromState, toState, probability);
		} else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION, actionString);
		}
	}

	public Cost readCost(String costString) {
		if (costString != null && !costString.trim().isEmpty()) {
			String[] bits = costString.split(" ");
			State currentState = readState(bits[0]);
			String actionName = bits[1];
			double cost = Double.valueOf(bits[2]);
			return new Cost(currentState, actionName, cost);
		} else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION, costString);
		}
	}

	public Map<String, Object> readFile(Path path) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (path != null) {
			List<String> lines = Files.readAllLines(path);
			String key = "";
			String line = "states";
			String actionName = "";
			Map<String, List<Action>> mapActions = new HashMap<String, List<Action>>();
			List<Action> actions = null;
			List<Cost> costs = new ArrayList<Cost>();
			List<State> states = new ArrayList<State>();
			int i = 0;
			do {
				if (line.isEmpty()) {
					i++;
					continue;
				}
				if (key.isEmpty()) {
					key = line;
					if (line.contains("action")) {
						String[] bits = line.split(" ");
						key = bits[0];
						actionName = bits[1];
						actions = new ArrayList<Action>();
					}
					i++;
					continue;
				}

				if (line.contains("end" + key)) {
					key = "";
					if (!actionName.isEmpty()) {
						mapActions.put(actionName, actions);
					}
					actionName = "";
					i++;
					continue;
				}

				switch (key) {
				case "states":
					String[] stringStates = line.split(",");
					for (String stringState : stringStates) {
						State state = readState(stringState);
						states.add(state);
					}
					break;
				case "action":
					Action action = readAction(actionName, line);
					actions.add(action);
					break;
				case "cost":
					Cost cost = readCost(line);
					costs.add(cost);
					break;
				case "initialstate":
					State initialState = readState(line);
					map.put(key, initialState);
					break;
				case "goalstate":
					State goalState = readState(line);
					map.put(key, goalState);
					break;
				}
				i++;
			} while (!(line = lines.get(i)).equals("Grid:"));
			map.put("states", states);
			map.put("cost", costs);
			map.put("action", mapActions);
		} else {
			throw new FileNotFoundException();
		}

		return map;
	}

}
