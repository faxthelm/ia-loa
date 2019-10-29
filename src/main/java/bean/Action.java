package bean;

import enums.ErrorMessages;
import exceptions.InvalidProbabilityValueException;

public class Action {
	private State fromState;
	private State toState;
	private double probability;

	public Action(State fromState, State toState, double probability) {
		setFromState(fromState);
		setProbability(probability);
		setToState(toState);
	}

	public State getFromState() {
		return fromState;
	}

	public void setFromState(State fromState) {
		if (fromState != null) {
			this.fromState = fromState;
		}else {
		}

	}

	public State getToState() {
		return toState;
	}

	public void setToState(State toState) {
		this.toState = toState;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		if (probability >= 0) {
			this.probability = probability;
		} else {
			throw new InvalidProbabilityValueException(ErrorMessages.INVALID_PROBABILITY_FOUND,
					String.valueOf(probability));
		}
	}

	@Override
	public String toString() {
		return "" ;
	}

}
