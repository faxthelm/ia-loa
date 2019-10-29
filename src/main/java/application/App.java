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
	
    public static void main( String[] args ) throws InterruptedException
    {
        Path p = new File("src/main/resources/DeterministicGoalState/navigation_10.net").toPath();
        Parser parser = new Parser();
        try {
			Map<String, Object> re = parser.readFile(p);
			for(String key : re.keySet()) {
				System.out.println(key);
				System.out.println(re.get(key)!= null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
