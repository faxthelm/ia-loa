package bean;

import java.util.HashMap;

public class Policy {

    private HashMap<State, String> policyStatements;

    public Policy(HashMap<State, String> policyStatements) {
        this.policyStatements = policyStatements;
    }

    public HashMap<State, String> getPolicyStatements() {
        return policyStatements;
    }

    public void setPolicyStatements(HashMap<State, String> policyStatements) {
        this.policyStatements = policyStatements;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POLICY\n");
        policyStatements.keySet().forEach(state -> stringBuilder
                .append(state.toString())
                .append(" | ")
                .append(policyStatements.get(state))
                .append("\n"));
        return stringBuilder.toString();
    }
}
