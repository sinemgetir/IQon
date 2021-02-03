package de.hu_berlin.ensureII.sre.parser.attributes;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;

public class DepthCalculator implements ICalculator {

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		calculateDepth(left,right,result);
	}

	private void calculateDepth(SRENodeData left, SRENodeData right,
			SRENodeData result) {
		result.setDepth(Math.max(left.getDepth(), right.getDepth())+1);
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		calculateDepth(inner, right, result);
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		calculateDepth(inner, right, result);
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		result.setDepth(inner.getDepth());
	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		calculateDepth(left, right, result);
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		nodeData.setDepth(0);
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		//nothing
	}
	
}
