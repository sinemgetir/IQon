package de.hu_berlin.ensureII.sre.parser.attributes;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;

public class SerializePart implements ICalculator {

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		result.setSerialized(" ("+left.getSerialized()+":"+right.getSerialized()+") ");
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		result.setSerialized(" ("+inner.getSerialized()+"*["+right.getSerialized()+"]) ");
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		result.setSerialized(" ("+inner.getSerialized()+"#["+right.getSerialized()+"]) ");
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData right,
			SRENodeData result) {
		result.setSerialized(inner.getSerialized()+"["+right.getSerialized()+"]");
	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		result.setSerialized(" ("+left.getSerialized()+" + "+right.getSerialized()+") ");
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		nodeData.setSerialized(nodeData.getTerminal());
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		if(nodeData.getBasicProbability() % 1 == 0){
			nodeData.setSerialized(""+(int)nodeData.getBasicProbability());
		}else{
			nodeData.setSerialized(nodeData.getBasicProbability()+"");
		}
	}


}
