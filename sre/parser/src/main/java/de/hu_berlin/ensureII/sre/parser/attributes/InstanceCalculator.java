package de.hu_berlin.ensureII.sre.parser.attributes;

import java.util.LinkedList;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;

public class InstanceCalculator implements ICalculator {

	private static final double REPEAT_THRESHOLD = 0.2;

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		for (List<String> inleft : left.getInstances()) {
			for (List<String> inright : right.getInstances()) {
				List<String> in = new LinkedList<String>();
				in.addAll(inleft);
				in.addAll(inright);
				result.getInstances().add(in);
			}
		}
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		for (int i = 0; i < howMany(right.getBasicProbability()); i++) {
			for (List<String> inst : inner.getInstances()) {
				List<String> inr = new LinkedList<String>();
				for (int j = 0; j <= i; j++) {
					inr.addAll(inst);
				}
				result.getInstances().add(inr);
			}
		}
	}

	private int howMany(double prob) {
		if (prob < REPEAT_THRESHOLD) {
			return 0;
		}
		return 1 + howMany(prob * prob);
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		for (int i = -1; i < howMany(right.getBasicProbability()); i++) {
			for (List<String> inst : inner.getInstances()) {
				List<String> inr = new LinkedList<String>();
				for (int j = -1; j <= i; j++) {
					inr.addAll(inst);
				}
				result.getInstances().add(inr);
			}
		}
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		result.getInstances().addAll(inner.getInstances());
	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		result.getInstances().addAll(left.getInstances());
		result.getInstances().addAll(right.getInstances());
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		List<String> in = new LinkedList<String>();
		in.add(nodeData.getTerminal());
		nodeData.getInstances().add(in);
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
	}
	
	
	

}
