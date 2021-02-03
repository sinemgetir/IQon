package de.hu_berlin.ensureII.sre.parser.attributes.verification;


import java.util.LinkedList;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.ICalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.NodeCounter;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;

public class AllCalculator implements ICalculator {
	
	private List<ICalculator> parts = new LinkedList<ICalculator>();
	public AllCalculator() {
		//Dependencies:
		//- PrefixCalculator --> ProbabilityPart
		//- SuffixCalculator --> ProbabilityPart
		//- ContainmentCalculator --> PrefixCalculator, SuffixCalculator
		parts.add(new ProbabilityPart());
		parts.add(new PrefixCalculator());
		parts.add(new SuffixCalculator());
		parts.add(new ContainmentCalculator());
		parts.add(new NodeCounter());
		//parts.add(new LengthCalculator());
		//parts.add(new InclusionWLengthCalculator());
	}

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
		for(ICalculator p : parts){
			p.concat(left, right, result);
		}
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		for(ICalculator p : parts){
			p.kleenClosure(inner, right, result);
		}
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		for(ICalculator p : parts){
			p.plusClosure(inner, right, result);
		}
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData right, SRENodeData result) {
		for(ICalculator p : parts){
			p.choiceElem(inner, right, result);
		}
	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
		for(ICalculator p : parts){
			p.choice(left, right, result);
		}
	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		for(ICalculator p : parts){
			p.initialSetupAction(nodeData);
		}
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		for(ICalculator p : parts){
			p.initialSetupProbability(nodeData);
		}
	}

	

}
