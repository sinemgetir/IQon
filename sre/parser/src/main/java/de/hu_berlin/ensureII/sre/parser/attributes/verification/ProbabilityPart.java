package de.hu_berlin.ensureII.sre.parser.attributes.verification;


import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.ICalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.utils.SubListStringMaker;



public class ProbabilityPart implements ICalculator {
	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		result.setSearchedMatching(left.getSearchedMatching());
		for (List<String> s : left.getProbabilityMap().keySet()) {
			result.getProbabilityMap().put(s, calculateConcat(left, right, s));
		}
	}
	
	private double calculateConcat(SRENodeData left,
			SRENodeData right, List<String> s) {
		double sum = 0;
		for (int i = 0; i <= s.size(); i++) {
			Double l = left.getProbabilityMap().get(SubListStringMaker.substring(s, 0, i));
			Double r = right.getProbabilityMap().get(SubListStringMaker.substring(s, i));
			sum +=  l * r;
		}
		return sum;
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		result.setSearchedMatching(inner.getSearchedMatching());
		double prob = right.getBasicProbability();
		for (List<String> s : inner.getProbabilityMap().keySet()) {
			result.getProbabilityMap().put(s, calculateKleenClosure(s, prob, inner));
		}
	}
	
	private Double calculateKleenClosure(List<String> s, double f,
			SRENodeData inner) {
		if (s.isEmpty()) {
			return 1 - f;
		} else {
			double sum = 0;
			for (int i = 1; i <= s.size(); i++) {
				List<String> left = SubListStringMaker.substring(s, 0, i);
				List<String> right = SubListStringMaker.substring(s, i);
				sum += f * inner.getProbabilityMap().get(left) * calculateKleenClosure(right, f, inner);
			}
			return sum;
		}
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		result.setSearchedMatching(inner.getSearchedMatching());
		double prob = right.getBasicProbability();
		for (List<String> s : inner.getProbabilityMap().keySet()) {
			result.getProbabilityMap().put(s, calculatePlusClosure(s, prob, inner));
		}
	}
	
	private Double calculatePlusClosure(List<String> s, double f,
			SRENodeData inner) {
		double sum = 0;
		for (int i = 1; i <= s.size(); i++) {
			sum += inner.getProbabilityMap().get(SubListStringMaker.substring(s, 0, i))
					* calculateKleenClosure(SubListStringMaker.substring(s, i), f, inner);
		}
		return sum;
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData rate,
			SRENodeData result) {
		result.setChoiceRate(rate.getChoiceRate());
		result.setSearchedMatching(inner.getSearchedMatching());
		result.setProbabilityMap(inner.getProbabilityMap());
	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		result.setSearchedMatching(left.getSearchedMatching());
		double sum = 0.0;
		sum += left.getChoiceRate();
		sum += right.getChoiceRate();
		for (List<String> s : SubListStringMaker.getAllPossibleSubstrings(left.getSearchedMatching())) {
			double sprob = 0.0;
			if (sum != 0.0){
				sprob += left.getProbabilityMap().get(s) * left.getChoiceRate() / sum;
				sprob += right.getProbabilityMap().get(s) * right.getChoiceRate() / sum;
				
			}
			
			result.getProbabilityMap().put(s, sprob);
		}
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		for (List<String> s : SubListStringMaker
				.getAllPossibleSubstrings(nodeData.getSearchedMatching())) {
			if (s.size() == 1 && s.get(0).equals(nodeData.getTerminal())) {
				nodeData.getProbabilityMap().put(s, 1.0);
			} else {
				nodeData.getProbabilityMap().put(s, 0.0);
			}
		}
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		//no additional action needed
	}


	

}