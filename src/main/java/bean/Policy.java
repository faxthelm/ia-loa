package bean;

import java.util.HashMap;

public class Policy {

    private HashMap<State, Action> policyStatements;

    public Policy(HashMap<State, Action> policyStatements) {
        this.policyStatements = policyStatements;
    }

    public HashMap<State, Action> getPolicyStatements() {
        return policyStatements;
    }

    public void setPolicyStatements(HashMap<State, Action> policyStatements) {
        this.policyStatements = policyStatements;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POLICY\n");
        policyStatements.keySet().forEach(state -> stringBuilder
                .append(state.toString())
                .append(" | ")
                .append(policyStatements.get(state).toString())
                .append("\n"));
        return stringBuilder.toString();
    }
}
