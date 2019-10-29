package utils;

import java.util.List;

import bean.Action;
import bean.State;
import enums.ErrorMessages;
import exceptions.FileFormatException;

public class Parser {
	public State readState(String state) {
		State s = new State();
		if (state != null && !state.trim().isEmpty() && state.trim().length() > 9) {
			String data = state.trim().substring(9, state.length());
			String[] x_and_y = data.split("y");
			s.setX(Integer.valueOf(x_and_y[0].replace("x", "")));
			s.setY(Integer.valueOf(x_and_y[1]));
		} else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION, "\"" + state + "\"");
		}
		return s;
	}
	
	public List<Action> readAction(String actionString){
	return null;
	}
	
	
}
