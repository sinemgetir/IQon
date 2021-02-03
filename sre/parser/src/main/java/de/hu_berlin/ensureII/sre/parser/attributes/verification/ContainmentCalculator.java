package de.hu_berlin.ensureII.sre.parser.attributes.verification;


import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.ICalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;
import de.hu_berlin.ensureII.sre.parser.utils.SubListStringMaker;


public class ContainmentCalculator implements ICalculator {

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		for (List<String> s : left.getContainmentMap().keySet()) {
			result.getContainmentMap().put(s, calculateConcat(left, right, s));
		}
	}

	private Double calculateConcat(SRENodeData left, SRENodeData right, List<String> s) {
		double sumA = 0;
		for (int i = 1; i < s.size(); i++) {
			Double l = left.getSuffixMap().get(SubListStringMaker.substring(s, 0, i));
			Double r = right.getPrefixMap().get(SubListStringMaker.substring(s, i));
			sumA += l * r;
		}
		double sumB = left.getContainmentMap().get(s);
		double sumC = right.getContainmentMap().get(s);
		return sumA+ sumB*(1-sumC)+(1-sumB)*sumC+sumB*sumC; //+ sumB + sumC - sumA * sumB - sumB * sumC - sumA * sumC + sumA * sumB * sumC;
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		double prob = right.getBasicProbability();
		for (List<String> s : inner.getContainmentMap().keySet()) {
			double containmentProb = calculateKleenClosure(s, prob, inner, result);
			result.getContainmentMap().put(s, containmentProb);
		}
	}

	private Double calculateKleenClosure(List<String> s, double f, SRENodeData inner, SRENodeData result) {
		if (s.isEmpty()) {
			return 1.0;
		} else {
			double sum = 0, sumInner = 0;
			
//			sum += inner.getContainmentMap().get(s);
			for (int i = 1; i < s.size(); i++) {
//				for (int j = i; j <= s.size(); j++) {
//					List<String> left = SubListStringMaker.substring(s, 0, i);
//					List<String> middle = SubListStringMaker.substring(s, i, j);
//					List<String> right = SubListStringMaker.substring(s, j);
//					sum += inner.getSuffixMap().get(left)*f * result.getProbabilityMap().get(middle) / (1 - f)
//							* inner.getPrefixMap().get(right) *f;
//				}
				List<String> left = SubListStringMaker.substring(s, 0, i);
				List<String> right = SubListStringMaker.substring(s, i);
				sum += result.getSuffixMap().get(left) *result.getPrefixMap().get(right)/(1-f);
			}
			if (inner.getContainmentMap().get(s)>0) {
				sumInner = inner.getContainmentMap().get(s)*f;
			}
			//sumInner = inner.getContainmentMap().get(s)*f;
			return sumInner+sum;
			//-sum*sumInner;
		}
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
		result.setChoiceRate(rate.getBasicProbability());
		result.setContainmentMap(inner.getContainmentMap());
	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		double sum = 0.0;
		sum += left.getChoiceRate();
		sum += right.getChoiceRate();
		for (List<String> s : SubListStringMaker.getAllPossibleSubstrings(left.getSearchedMatching())) {
			double sprob = 0.0;
			sprob += left.getContainmentMap().get(s) * left.getChoiceRate() / sum;
			sprob += right.getContainmentMap().get(s) * right.getChoiceRate() / sum;
			result.getContainmentMap().put(s, sprob);
		}
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		for (List<String> s : SubListStringMaker.getAllPossibleSubstrings(nodeData.getSearchedMatching())) {
			if (s.isEmpty()) {
				nodeData.getContainmentMap().put(s, 1.0);
			}
			if (s.size() == 1 && s.get(0).equals(nodeData.getTerminal())) {
				nodeData.getContainmentMap().put(s, 1.0);
			} else{
				nodeData.getContainmentMap().put(s, 0.0);
			}
		}
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		// empty
	}
	

}
