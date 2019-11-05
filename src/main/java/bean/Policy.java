package bean;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


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
        policyStatements.keySet().forEach(state -> stringBuilder
                .append(state.toString())
                .append(" | ")
                .append(policyStatements.get(state))
                .append("\n"));
        return stringBuilder.toString();
    }
    
    public void createMap() {
    	
    }
}
