package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import utils.Parser;
import utils.ProblemManager;

public class App {
	public static void main(String[] args) throws InterruptedException {
		Path p = new File("src/main/resources/DeterministicGoalState/navigation_1.net").toPath();
		Parser parser = new Parser();
		try {
			ProblemManager.init(parser.readFile(p));
			System.out.println(ProblemManager.getApplicableActions(ProblemManager.getInitialState()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
