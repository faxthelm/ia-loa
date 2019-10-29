package bean;

import enums.ErrorMessages;
import exceptions.FileFormatException;
import exceptions.InvalidProbabilityValueException;

public class Action {
	private String name;
	private State fromState;
	private State toState;
	private double probability;
	private double cost;

	public Action(String name, State fromState, State toState, double probability) {
		setName(name);
		setFromState(fromState);
		setProbability(probability);
		setToState(toState);
	}
	
	

	public double getCost() {
		return cost;
	}



	public void setCost(double cost) {
		this.cost = cost;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public State getFromState() {
		return fromState;
	}

	public void setFromState(State fromState) {
		if (fromState != null) {
			this.fromState = fromState;
		}else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION);
		}

	}

	public State getToState() {
		return toState;
	}

	public void setToState(State toState) {
		if (fromState != null) {
			this.toState = toState;
		}else {
			throw new FileFormatException(ErrorMessages.FILE_FORMAT_EXCEPTION);
		}
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
		return fromState.toString()+" =("+name+":"+String.format("%.2f", probability)+")=> "+toState.toString() ;
	}

}
