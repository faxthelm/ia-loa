package bean;

import java.util.HashMap;


public class Policy {

    private HashMap<String, String> policyStatements;

    public Policy(HashMap<String, String> policyStatements) {
        this.policyStatements = policyStatements;
    }

    public HashMap<String, String> getPolicyStatements() {
        return policyStatements;
    }

    public void setPolicyStatements(HashMap<String, String> policyStatements) {
        this.policyStatements = policyStatements;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POLICY\n");
        policyStatements.forEach((state, action) -> stringBuilder
        		.append(String.format("robot-%s | %s\n", state, action)));
        return stringBuilder.toString();
    }

}
