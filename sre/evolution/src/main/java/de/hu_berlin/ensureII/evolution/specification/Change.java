package de.hu_berlin.ensureII.evolution.specification;
import java.util.List;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
public abstract class Change {
 public enum ModelType {
  SRE, PA
 }
 
 public abstract boolean addTask(int sourceId, String event2add1, String event2add2, int targetId);
 public abstract boolean addTask(int sourceId, String event2add, int targetId);
 public abstract boolean changePath(int sourceId,int oldTargetId,int newTargetId);
    public abstract boolean updateDistribution(int sourceId, List<Tuple<Integer, Double>> targetIdsAndProbabilities);
    public abstract boolean loop2choice(int stateId4loop,int targetId);
    public abstract boolean choice2loop(int stateId4loop,int targetId);
    public abstract boolean addAlternation(int sourceId,int targetId2add, double prob);
    public abstract boolean removeAlternation(int sourceId,int targetId2remove);
    public abstract boolean addSelfLoop(int stateId, double prob);
    public abstract boolean removeSelfLoop(int stateId);
    public abstract boolean reorderEvents(int sourceId1, String event1, int targetId1,int sourceId2, String event2, int targetId2);
    public abstract boolean moveEvents(int sourceId, String action, int step);
    public abstract ModelType getModelType();
   
 public static class PatternRunner {
  
  public static void applyChange(Change model) {
  }
 }
}