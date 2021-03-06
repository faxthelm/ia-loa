package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import algorithms.LaoStar;
import algorithms.ValueIteration;
import utils.Parser;
import utils.ProblemManager;
import utils.ProblemUtils;

public class App {

	public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            Path p = new File(App.class.getResource(ProblemUtils.RANDOM_NAVIGATION_10).toURI()).toPath();
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
