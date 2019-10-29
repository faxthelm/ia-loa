package bean;

import java.util.HashMap;

public class Policy {

    private HashMap<State, Action> finalPolicy;

    public Policy(HashMap<State, Action> finalPolicy) {
        this.finalPolicy = finalPolicy;
    }

    public HashMap<State, Action> getFinalPolicy() {
        return finalPolicy;
    }

    public void setFinalPolicy(HashMap<State, Action> finalPolicy) {
        this.finalPolicy = finalPolicy;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POLICY\n");
        finalPolicy.keySet().forEach(state -> stringBuilder
                .append(state.toString())
                .append(" | ")
                .append(finalPolicy.get(state).toString())
                .append("\n"));
        return stringBuilder.toString();
    }
}
