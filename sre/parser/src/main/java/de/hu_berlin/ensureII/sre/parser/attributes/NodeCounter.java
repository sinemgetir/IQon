package de.hu_berlin.ensureII.sre.parser.attributes;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;

public class NodeCounter implements ICalculator {

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		int count = left.getConcatCount()+right.getConcatCount()+1;
		result.setConcatCount(count);
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		int count = inner.getKleeneCount()+1;
		result.setKleeneCount(count);
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		int count = inner.getPlusClosureCount()+1;
		result.setPlusClosureCount(count);
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData right, SRENodeData result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		int count = left.getChoiceCount() + right.getChoiceCount()+1;
		result.setChoiceCount(count);
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		nodeData.setActionCount(1);
		nodeData.setChoiceCount(0);
		nodeData.setConcatCount(0);
		nodeData.setKleeneCount(0);
		nodeData.setPlusClosureCount(0);
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		// TODO Auto-generated method stub

	}

}
