package de.hu_berlin.ensureII.sre.parser.attributes;

import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;

public interface ICalculator {

	void concat(SRENodeData left, SRENodeData right, SRENodeData result);

	void kleenClosure(SRENodeData inner, SRENodeData right, SRENodeData result);

	void plusClosure(SRENodeData inner, SRENodeData right, SRENodeData result);

	void choiceElem(SRENodeData inner, SRENodeData right, SRENodeData result);

	void choice(SRENodeData left, SRENodeData right, SRENodeData result);

	void initialSetupAction(SRENodeData nodeData);

	void initialSetupProbability(SRENodeData nodeData);
	
	//void propogateChoiceRate(SRENodeData left, SRENodeData rate, SRENodeData result);

	
	
}
