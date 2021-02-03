package de.hu_berlin.ensureII.sre.parser.attributes.verification;

import de.hu_berlin.ensureII.sre.parser.attributes.ICalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.data.SRENodeData;

public class LengthCalculator implements ICalculator {

	@Override
	public void concat(SRENodeData left, SRENodeData right, SRENodeData result) {
	//	System.out.println(LengthCalculator.class.getName()+ " Concat method");

		result.setN(left.getN());
		for (int i = 0; i <= left.getN(); i++) {
//			if (i==0) {
//				result.getLengthMap().put(i, 0.0);
//			}else{
			Double res = calculateConcat(left, right, i);
			result.getLengthMap().put(i, res);
			//}
		}
		
	}

	private Double calculateConcat(SRENodeData left, SRENodeData right, int i) {
		double sum = 0;
		boolean already = false;
		for (int j = 0; j <= i; j++) {
			Double l = left.getLengthMap().get(j);
			Double r = right.getLengthMap().get(i-j);
			if (j == i-j) {
				if (already == false) {
					sum = sum+(l * r);
				}
				already = true;
			}else{
			sum = sum + (l * r);
			}
			//if(sum >= 1.0) return 1.0;
		}	
		return sum;
	}

	@Override
	public void kleenClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
	//	System.out.println(LengthCalculator.class.getName()+ " Kleene method");

		double prob = right.getBasicProbability();
		int n = inner.getN();
		result.setN(n);
		for (int i = 0; i <= n; i++) {
			if (i == 0) {
				result.getLengthMap().put(0, 1-prob);
			}
				else{
					result.getLengthMap().put(i, calculateKleenClosure(i, prob, inner, result));
				}
			}
			
		}
		
			//result.getLengthMap().put(i, Math.pow(prob, i));
			
			//result.getLengthMap().put(i, calculateKleenClosure(i, prob, inner, result));
	
	

	private Double calculateKleenClosure(int i, double f, SRENodeData inner, SRENodeData result) {
		if (f == 1.0) return inner.getLengthMap().get(i);
//		if (i == 0) {
//			return (1-f);
//		}else{
		if (i==1) {
			return inner.getLengthMap().get(1) * f * (1-f);
		}else{
			double sum = 0.0;
			for (int j = 1; j <= i/2; j++) {
				if ( i % j == 0) {
//					if ( j == i/j){
						sum += inner.getLengthMap().get(j) * Math.pow(f, i/j) * (1-f);
						if (sum == 0.0) sum += inner.getLengthMap().get((i/j)) * Math.pow(f, j) * (1-f);



//					}else{
//					sum += inner.getLengthMap().get(j) * Math.pow(f, i/j) * (1-f);
//					sum += inner.getLengthMap().get((i/j)) * Math.pow(f, j) * (1-f);
//					 }// result.getLengthMap().get(j)/(1-f) * inner.getLengthMap().get(i-j)*f;

				}
			
			}
//			for (int j = 0; j < i; j++) {
//				sum += result.getLengthMap().get(j)/(1-f) * inner.getLengthMap().get(i-j)*f;
//			}
			return sum ;
		//}
			}
	}

	@Override
	public void plusClosure(SRENodeData inner, SRENodeData right, SRENodeData result) {
		result.setN(inner.getN());
		double prob = right.getBasicProbability();
		for (int i = 1; i <= inner.getN(); i++) {
			
			result.getLengthMap().put(i, Math.pow(prob, i));
			//result.getLengthMap().put(i, calculateKleenClosure(i, prob, inner, result));
			
		}			
		
	}

	private Double calculatePlusClosure(int i, double f, SRENodeData inner, SRENodeData result) {
		double sum = inner.getLengthMap().get(i);
		for (int j = 1; j <= i; j++) {
			sum += result.getLengthMap().get(j)/(1-f) * inner.getLengthMap().get(i-j)*f;
		}
		return sum;
	}

	@Override
	public void choiceElem(SRENodeData inner, SRENodeData rate, SRENodeData result) {
	//	System.out.println(LengthCalculator.class.getName()+ " Choice element method");

		result.setChoiceRate(rate.getChoiceRate());
		result.setLengthMap(inner.getLengthMap());
		result.setN(inner.getN());

	}

	@Override
	public void choice(SRENodeData left, SRENodeData right, SRENodeData result) {
	//	System.out.println(LengthCalculator.class.getName()+ " Choice method");

		double sum = 0.0;
		sum += left.getChoiceRate();
		sum += right.getChoiceRate();
		result.setN(right.getN());
	

		for (int i = 0; i <= left.getN(); i++) {
			double sprob = 0.0;
			if (sum == 0.0) {
				result.getLengthMap().put(i, 0.0);
			}
			else{
			sprob += left.getLengthMap().get(i) * left.getChoiceRate() / sum;
			sprob += right.getLengthMap().get(i) * right.getChoiceRate() / sum;
			result.getLengthMap().put(i, sprob);
			}
		}

	}

	@Override
	public void initialSetupAction(SRENodeData nodeData) {
		nodeData.getLengthMap().put(1, 1.0);
		nodeData.getLengthMap().put(0, 0.0);
		for (int i = 2; i <= nodeData.getN(); i++) {
			nodeData.getLengthMap().put(i, 0.0);
		}
		
	}

	@Override
	public void initialSetupProbability(SRENodeData nodeData) {
		// TODO Auto-generated method stub
		
	}

}
