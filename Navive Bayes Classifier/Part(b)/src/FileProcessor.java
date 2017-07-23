
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FileProcessor {

	public void display (Map<String, Integer> counts){
		for( String key : counts.keySet() ){
			System.out.println(key+" "+counts.get(key));
			//mapCounter++;
		}
	}
	public int readInputFile(String inputFileName, Map<String, Integer> counts, Map<String, Integer> labels) {
		int numberOfEntities = 0;
		try {
			
			File file = new File(inputFileName);
			if( ! file.exists()){
				System.out.println(inputFileName+" does not exists.");
				System.exit(1);
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// StringBuffer stringBuffer = new StringBuffer();
			// Pattern patt = Pattern.compile("[A-Za-z][a-z]+");
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] words = line.split(" ");
				// System.out.println(words.length+": line word length");
				int labelColumn = words.length - 1;
				for (int i = 1; i <= labelColumn; i++) {
					
					counts.putIfAbsent(words[i].toLowerCase()+"_yes", 0);
					counts.putIfAbsent(words[i].toLowerCase()+"_no", 0);
				
				/*	labels.putIfAbsent(words[i].toLowerCase(), 0);	// _yes
					labels.putIfAbsent(words[i].toLowerCase(), 0);	// _no
                */					
					String newLabel = new String(words[i]);
					newLabel = newLabel.concat("_").concat(words[labelColumn]).toLowerCase();
					if (counts.containsKey(newLabel)) {
						counts.put(newLabel, counts.get(newLabel) + 1);
					} else {
						counts.put(newLabel, 1);
						System.out.println("ERROR HERE");
						
					}
				}
				// stringBuffer.append(line);
				// stringBuffer.append("\n");
				numberOfEntities++;
			}
			fileReader.close();
			// System.out.println("Contents of file:");
			// System.out.println(stringBuffer.toString());

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			
		}
			return numberOfEntities;
	}

	public void labelCleaner( Map<String, Integer> counts ){
		for( String label : counts.keySet()){
			//System.out.println("LabelCleaner :"+label);
			if( label.substring(label.length()-3, label.length() ).equalsIgnoreCase("_no")){
				break;
			}
			if( label.substring(label.length()-3, label.length() ).equalsIgnoreCase("yes")){
				break;
			}
			counts.remove(label);
		}
	}

}
