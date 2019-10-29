package bean;

import enums.ErrorMessages;
import exceptions.FileFormatException;

public class Cost {
	private State currentState;
	private String actionName;
	private double cost;

	public Cost() {

	}

	public Cost(State currentState, String actionName, double cost) {
		setActionName(actionName);
		setCost(cost);
		setCurrentState(currentState);
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		if (currentState != null) {
			this.currentState = currentState;
		} else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION, "state not found when reading cost");
		}
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		if (actionName != null && !actionName.trim().isEmpty()) {
			this.actionName = actionName;
		} else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION,
					"empty action name found when reading cost");
		}
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		if (cost > 0) {
			this.cost = cost;
		} else {

			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION,
					"The value of cost must be greater than 0. Found: " + cost);
		}
	}

	@Override
	public String toString() {
		return "cost " + currentState + " to " + actionName + " (" + cost + ")";
	}

}
