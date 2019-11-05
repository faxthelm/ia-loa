package bean;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


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
    	policyStatements = orderPolicy();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POLICY\n");
        policyStatements.keySet().forEach(state -> stringBuilder
                .append(state.toString())
                .append(" | ")
                .append(policyStatements.get(state))
                .append("\n"));
        return stringBuilder.toString();
    }
    
    private HashMap<State, String> orderPolicy() {
    	Comparator<State> comparator = (State s1, State s2) -> ( (s1.getX() > s2.getX()) | ((s1.getX() == s2.getX()) & (s1.getY() > s2.getY())) ) ? 1 : -1;
    	return policyStatements.entrySet().stream()
                                            .sorted(Map.Entry.<State,String>comparingByKey(comparator))
                                            .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e1,LinkedHashMap::new));
    	
    }
}
