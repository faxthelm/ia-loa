package application;

import algorithms.LaoStar;
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
            Path p = new File(App.class.getResource(ProblemUtils.DETERMINISTIC_NAVIGATION_10).toURI()).toPath();
            ProblemManager.init(parser.readFile(p));
            
           ValueIteration valueIteration = new ValueIteration();
           valueIteration.calculate();

//            LaoStar laoStar = new LaoStar();
//            System.out.println(laoStar.calculate());

        } catch (URISyntaxException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
