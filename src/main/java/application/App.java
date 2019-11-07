package application;

import algorithms.ValueIteration;
import utils.Parser;
import utils.ProblemManager;
import utils.ProblemUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            Path p = new File(App.class.getResource(ProblemUtils.DETERMINISTIC_NAVIGATION_1).toURI()).toPath();
            ProblemManager.init(parser.readFile(p));

            ValueIteration valueIteration = new ValueIteration();
            System.out.println(valueIteration.calculate());

        } catch (URISyntaxException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
