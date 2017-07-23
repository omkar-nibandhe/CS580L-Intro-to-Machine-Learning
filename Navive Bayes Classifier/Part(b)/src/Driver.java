
import java.util.HashMap;
import java.util.Map;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length< 1) {
			System.out.println("Arguments expected");
			System.exit(1);
		}
		int mapCounter = 0 ;
		
		FileProcessor fp = new FileProcessor();
		Map<String, Integer> counts = new HashMap<>();
		Map<String, Integer> labels = new HashMap<>();
		int numberOfEntities = fp.readInputFile(args[0], counts, labels);
		if (numberOfEntities > 0){
			Calculations calc = new Calculations();
			fp.display(counts);
		}
		System.out.println("numberofentities= " + numberOfEntities);
	
		System.out.println(mapCounter+" MapCounter");
	}

}
