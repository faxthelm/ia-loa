package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import utils.Parser;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
        Path p = new File("src/main/resources/DeterministicGoalState/navigation_1.net").toPath();
        Parser parser = new Parser();
        try {
			Map<String, Object> re = parser.readFile(p);
			System.out.println(re);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
