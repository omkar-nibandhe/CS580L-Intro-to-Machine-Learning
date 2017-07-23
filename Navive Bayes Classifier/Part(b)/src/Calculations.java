import java.util.Map;

public class Calculations {

	public double logBase2(int numerator, int denominator){
		double result = 0.0;
		if (numerator == 0 || denominator == 0){
			return 0.0;
		}
		result = Math.log((double)numerator/denominator) / Math.log(2);	
		return result;
	}
	public double calculateEntropy(int pos, int neg, Map<String, Integer> counts){
		//System.out.println(pos+" "+neg+" ");
		double result = 0.0;
		int total = pos + neg;
		double p = (double)pos;
		double n = (double)neg;
		double t = p + n;
		result = - (p/t)*logBase2(pos,total) - (n/t)*logBase2(neg,total); 
		return result;
	}
	public double calculateConditionalEntropy(String subSample, int numberofEntities,Map<String, Integer> counts){
		double results = 0.0;
		double x = (double)counts.get(subSample+"_yes") + counts.get(subSample+"_no");
		results =  (x/(double)numberofEntities) *
			calculateEntropy(counts.get(subSample+"_yes"), counts.get(subSample+"_no"), counts); 
		return results;
	}
}
