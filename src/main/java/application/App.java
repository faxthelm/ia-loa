package application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

import algorithms.ValueIteration;
import utils.ProblemUtils;
import utils.Parser;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        try {
            Path p = new File(App.class.getResource(ProblemUtils.RANDOM_NAVIGATION_1).toURI()).toPath();
            Parser parser = new Parser();
            Map<String, Object> re = parser.readFile(p);
            for (String key : re.keySet()) {
                System.out.println(key);
                System.out.println(re.get(key) != null);
            }
            ValueIteration valueIteration = new ValueIteration(re);
            valueIteration.calculate();
            System.out.println(valueIteration.getPolicy().toString());
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
