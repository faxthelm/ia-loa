package application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import algorithms.ValueIteration;
import utils.Parser;
import utils.ProblemManager;
import utils.ProblemUtils;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Parser parser = new Parser();
        try {
            Path p = new File(App.class.getResource(ProblemUtils.DETERMINISTIC_NAVIGATION_1).toURI()).toPath();
            ProblemManager.init(parser.readFile(p));
            //System.out.println(ProblemManager.getApplicableActions(ProblemManager.getInitialState()));
            ValueIteration valueIteration = new ValueIteration();
            
            String timeAndV = valueIteration.calculate();
            StringBuilder result = new StringBuilder();
            result.append(timeAndV).append("\n")
                    .append(valueIteration.getPolicy().toString());
            System.out.println(result.toString());
            

        } catch (URISyntaxException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
