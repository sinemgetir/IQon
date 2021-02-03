package de.hu_berlin.ensureII.sre.parser.attributes;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.verification.ContainmentCalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.verification.InclusionWLengthCalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.verification.LengthCalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.verification.PrefixCalculator;
import de.hu_berlin.ensureII.sre.parser.attributes.verification.ProbabilityPart;
import de.hu_berlin.ensureII.sre.parser.attributes.verification.SuffixCalculator;


public class SREConfig {
	private List<Parts> parts = new LinkedList<Parts>();
	private List<ICalculator> realParts = null;
	
	public enum Parts {
		PROB_EXACT(0, new ProbabilityPart()),
		PROB_PREFIX(1, new PrefixCalculator(), PROB_EXACT),
		PROB_SUFFIX(1, new SuffixCalculator(), PROB_EXACT),
		PROB_CONTAIN(2, new ContainmentCalculator(), PROB_SUFFIX, PROB_PREFIX),
		DEPTH(0, new DepthCalculator()),
		INSTANCES(0, new InstanceCalculator()),
	//	DTMC(0, new DTMCCalculator()),
		SERIALIZE(0, new SerializePart()),
		LENGTH(0, new LengthCalculator()),
		PROB_CONTAIN_LENGTH(2, new InclusionWLengthCalculator(), PROB_CONTAIN, LENGTH);

		
		private int prio;
		private ICalculator realpart;
		private Parts[] requires;

		private Parts(int prio, ICalculator realpart, Parts... requires){
			this.prio = prio;
			this.realpart = realpart;
			this.requires = requires;
		}

		public int getPrio() {
			return prio;
		}
		
		public ICalculator getRealpart(){
			return realpart;
		}
		
		protected List<ICalculator> resolveRequirements(){
			List<ICalculator> result = new LinkedList<ICalculator>();
			for(Parts r : this.requires){
				for(ICalculator req : r.resolveRequirements()){
					if(!result.contains(req)){
						result.add(req);
					}
				}
				if(!result.contains(r.getRealpart())){
					result.add(r.getRealpart());
				}
			}
			return result;
		}
		
		public Parts[] getRequirements(){
			return requires;
		}
		
		private static Comparator<Parts> comparator = new Comparator<SREConfig.Parts>() {
			@Override
			public int compare(Parts o1, Parts o2) {
				return o1.prio-o2.prio;
			}
		};
		
		public static Comparator<Parts> getComparator(){
			return comparator;
		}
	}
	
	public SREConfig() {
		parts.add(Parts.SERIALIZE);//REQUIRED!!
	}
	
	public void activate(Parts p){
		realParts = null;
		parts.add(p);
	}
	
	public Collection<ICalculator> getParts(){
		if(realParts==null){
			realParts = new LinkedList<ICalculator>();
			Collections.sort(parts, Parts.getComparator());
			for(Parts p : parts){
				for(ICalculator req : p.resolveRequirements()){
					if(!realParts.contains(req)){
						realParts.add(req);
					}
				}
				if(!realParts.contains(p.getRealpart())){
					realParts.add(p.getRealpart());
				}
			}
		}
		return realParts;
	}
	
	public static SREConfig getAll(){
		SREConfig cfg = new SREConfig();
		for(Parts p : Parts.values()){
			cfg.activate(p);
		}
		return cfg;
	}
	
	public static SREConfig getProbs(){
		SREConfig cfg = new SREConfig();
		cfg.activate(Parts.PROB_CONTAIN);
		return cfg;
	}
	
	public static SREConfig getExact(){
		SREConfig cfg = new SREConfig();
		cfg.activate(Parts.PROB_EXACT);
		return cfg;
	}
	
	public static SREConfig getDepth(){
		SREConfig cfg = new SREConfig();
		cfg.activate(Parts.DEPTH);
		return cfg;
	}
	
//	public static UltimateSREConfig getDtmc(){
//		UltimateSREConfig cfg = new UltimateSREConfig();
//		cfg.activate(Parts.DTMC);
//		return cfg;
//	}
}
