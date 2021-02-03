package de.hu_berlin.ensureII.sre.parser.attributes.verification;


import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.ICalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.utils.SubListStringMaker;



public class SuffixCalculator implements ICalculator {

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		for (List<String> s : left.getSuffixMap().keySet()) {
			if (s.isEmpty()) {
				result.getSuffixMap().put(s, 1.0);
			}else
			result.getSuffixMap().put(s, calculateConcat(left, right, s));
		}
	}

	private Double calculateConcat(SRENodeData left, SRENodeData right, List<String> s) {
		double sum = 0;
		sum += right.getSuffixMap().get(s);
		for(int i = 1; i<=s.size(); i++){
			List<String> leftString = SubListStringMaker.substring(s, 0, i);
			List<String> rightString = SubListStringMaker.substring(s, i);
			Double l = left.getSuffixMap().get(leftString);
			Double r = right.getProbabilityMap().get(rightString);
			
			sum +=  l * r;
		}
		return sum;
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		double prob = right.getBasicProbability();
		for (List<String> s : inner.getSuffixMap().keySet()) {
			if (s.isEmpty()) {
				result.getSuffixMap().put(s, 1.0);
			}else
			result.getSuffixMap().put(s, calculateKleenClosure(s, prob, inner, result));
		}
	}

	private Double calculateKleenClosure(List<String> s, double f, SRENodeData inner, SRENodeData result) {
		
		if (f ==1.0) return 0.0;
		if (s.isEmpty()) {
			return 1.0;
		} else {
			double sum = 0;

			for (int i = 1; i <= s.size(); i++) {
				List<String> left = SubListStringMaker.substring(s, 0, i);
				List<String> right = SubListStringMaker.substring(s, i);
				sum += result.getProbabilityMap().get(right) * inner.getSuffixMap().get(left) * f;
			}
			return sum;
		}
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		double prob = right.getBasicProbability();
		for (List<String> s : inner.getSuffixMap().keySet()) {
			if (s.isEmpty()) {
				result.getSuffixMap().put(s, 1.0);
			}else
			result.getSuffixMap().put(s, calculatePlusClosure(s, prob, inner, result));
		}
	}

	private Double calculatePlusClosure(List<String> s, double f, SRENodeData inner, SRENodeData result) {
		double sum = 0;
		sum += inner.getSuffixMap().get(s);
		for (int i = 1; i <= s.size(); i++) {
			List<String> left = SubListStringMaker.substring(s, 0, i);
			List<String> right = SubListStringMaker.substring(s, i);
			sum += result.getProbabilityMap().get(right) * inner.getSuffixMap().get(left) *f;
		}
		return sum;
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData rate, SRENodeData result) {
		result.setChoiceRate(rate.getBasicProbability());
		result.setSuffixMap(inner.getSuffixMap());
	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		double sum = 0.0;
		sum += left.getChoiceRate();
		sum += right.getChoiceRate();
		for (List<String> s : SubListStringMaker.getAllPossibleSubstrings(left.getSearchedMatching())) {
			double sprob = 0.0;
			if (s.isEmpty()) {
				result.getSuffixMap().put(s, 1.0);
			}
			sprob += left.getSuffixMap().get(s) * left.getChoiceRate() / sum;
			sprob += right.getSuffixMap().get(s) * right.getChoiceRate() / sum;
			result.getSuffixMap().put(s, sprob);
				
		}
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		for (List<String> s : SubListStringMaker
				.getAllPossibleSubstrings(nodeData.getSearchedMatching())) {
			
			if (s.size() == 1 && s.get(0).equals(nodeData.getTerminal())) {
				nodeData.getSuffixMap().put(s, 1.0);
			} else{
				nodeData.getSuffixMap().put(s, 0.0);
			}
			if (s.isEmpty()) {
				nodeData.getSuffixMap().put(s, 1.0);
			}
		}
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		// empty
	}
	

}
