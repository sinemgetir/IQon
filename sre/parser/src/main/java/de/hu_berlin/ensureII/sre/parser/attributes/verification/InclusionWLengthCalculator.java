package de.hu_berlin.ensureII.sre.parser.attributes.verification;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hu_berlin.ensureII.sre.parser.attributes.ICalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.utils.SubListStringMaker;


public class InclusionWLengthCalculator implements ICalculator {

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		//System.out.println(InclusionWLengthCalculator.class.getName()+ " Concat method");
		//for (List<String> s : left.getContainmentMap().keySet()) {
	
//		List<String> s = left.getSearchedMatching();
//
//			result.getInclusionWlengthMap().put(s, 0, 0.0);
//			//result.getInclusionWlengthMap().put(s, 1, 0.0);
//			for (int i = 1; i <= left.getN(); i++) {
//				if (i <s.size()){
//					result.getInclusionWlengthMap().put(s, i, 0.0);
//
//				}else{
//				Double res = calculateConcat(left, right,s, i,result);
//				result.getInclusionWlengthMap().put(s, i, res);
//					}
//		//	}
//			
//		}
		for (List<String> s : left.getPrefixMap().keySet()) {
			if (s.isEmpty()) {
				result.getPrefixMap().put(s, 1.0);
			}else
				for (int i = 1; i <= left.getN(); i++) {
					Double res = calculateConcat(left, right,s, i,result);
					result.getInclusionWlengthMap().put(s, i, res);
				}
		}
		
	
	}

	private Double calculateConcat(SRENodeData left, SRENodeData right, List<String> s, int index, SRENodeData result) {
		//geometric series
		double sum = left.getPrefixMap().get(s);
		//sum += left.getPrefixMap().get(s);
		//String ch = s.get(0);
		for(int i = 1; i<=index; i++){
			//List<String> leftString = SubListStringMaker.substring(s, 0, i);
			//List<String> rightString = SubListStringMaker.substring(s, i);
			Double l = (1-left.getProbabilityMap().get(s));
			Double r = right.getPrefixMap().get(s);
			sum = r;

	//		sum +=  l. * r;
			if (r == 1) return r;
			else sum =+ l*r;
			//else {sum = r * ((1-Math.pow(l, index+1))/(1-r));
			}
		return sum; 

		}
			
			
	

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		//System.out.println(InclusionWLengthCalculator.class.getName()+ " Kleene method");

		double prob = right.getBasicProbability();
	//	for (List<String> s : inner.getContainmentMap().keySet()) {
		List<String> s = inner.getSearchedMatching();
		result.getInclusionWlengthMap().put(s,0,0.0);

		for (int i = 1; i <= inner.getN(); i++) {

			double probResult = calculateKleenClosure(s, prob, inner, i, result);
			result.getInclusionWlengthMap().put(s, i,probResult);
			}
	//	}
	}

	private Double calculateKleenClosure(List<String> s, double f, SRENodeData inner, int index, SRENodeData result) {
		Double sum = inner.getInclusionWlengthMap().get(s, index);
		//List<String> ch = s.get(0);
		System.out.println(s);

		if (s.isEmpty()) {
			return 1.0;
		} else {
			for(int i = 1; i<=index; i++){

			Double  left = (1-result.getProbabilityMap().get(s));
			Double  right = inner.getPrefixMap().get(s)*f;
			sum = right;
			
			sum =+ right * left;
			
			}
		}
		
		return sum; 

	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		double prob = right.getBasicProbability();
		for (List<String> s : inner.getContainmentMap().keySet()) {
			result.getContainmentMap().put(s, calculatePlusClosure(s, prob, inner, result));
		}
	}

	private Double calculatePlusClosure(List<String> s, double f, SRENodeData inner, SRENodeData result) {
		double sum = 0;
		sum += inner.getContainmentMap().get(s);
		for (int i = 0; i <= s.size(); i++) {
			for (int j = i; j <= s.size(); j++) {
				List<String> left = SubListStringMaker.substring(s, 0, i);
				List<String> middle = SubListStringMaker.substring(s, i, j);
				List<String> right = SubListStringMaker.substring(s, j);
				sum += inner.getSuffixMap().get(left) * result.getProbabilityMap().get(middle) / (1 - f)
						* inner.getPrefixMap().get(right);
			}
		}
		return sum;
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData rate, SRENodeData result) {
		//System.out.println(InclusionWLengthCalculator.class.getName()+ " choice element method");

		result.setChoiceRate(rate.getChoiceRate());
		result.setInclusionWlengthMap(inner.getInclusionWlengthMap());   
		result.setN(inner.getN());

	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
	//	System.out.println(InclusionWLengthCalculator.class.getName()+ " choice method");

		double sum = 0.0;
		sum += left.getChoiceRate();
		sum += right.getChoiceRate();
		result.setN(right.getN());
		
	

		for (int i = 1; i <= left.getN(); i++) {
			
			double sprob = 0.0;
			
		//	for (List<String> s : left.getContainmentMap().keySet()) {
			List<String> s = left.getSearchedMatching();
					if (sum == 0.0) {
						result.getInclusionWlengthMap().put(s, i,0.0);
					}
				else{
					sprob += left.getInclusionWlengthMap().get(s,i) * left.getChoiceRate() / sum;
					sprob += right.getInclusionWlengthMap().get(s,i) * right.getChoiceRate() / sum;
					result.getInclusionWlengthMap().put(s,i, sprob);
				}
			//}
		}

	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		for (List<String> s : SubListStringMaker.getAllPossibleSubstrings(nodeData.getSearchedMatching())) {
			nodeData.getInclusionWlengthMap().put(s, 0, 0.0);
			
			if (s.size() == 1 && s.get(0).equals(nodeData.getTerminal())) {
				nodeData.getInclusionWlengthMap().put(s, 1, 1.0);
			} else{
				nodeData.getInclusionWlengthMap().put(s, 1, 0.0);
			}
			for (int i = 2; i <= nodeData.getN(); i++) {
				nodeData.getInclusionWlengthMap().put(s, i, 0.0);
			}
			
		}
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		// empty
	}
	

}
