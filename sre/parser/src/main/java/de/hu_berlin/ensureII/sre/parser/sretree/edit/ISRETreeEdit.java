package de.hu_berlin.ensureII.sre.parser.sretree.edit;

import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;

public interface ISRETreeEdit {

    public void addSubnode(SRETree tree, SRETreeNode toAdd, int parentId);
    
    public void insertSubnode(SRETree tree, SRETreeNode toInsert, int parentId, int insertPos);
    
    public void replaceNode(SRETree tree, int toReplaceId, SRETreeNode newNode);
    
    public void replaceSubtree(SRETree tree, int toReplaceId, SRETreeNode subtreeRoot);
    
    public void deleteSubtree(SRETree tree, int rootToDeleteId);
    
    public void updateRate(SRETree tree, int id, int rate);
    
    public void updateLoopProbability(SRETree tree, int id, double probability);
    
    public void addLoop(SRETree tree, int id, double probability);
    
    public void removeLoop(SRETree tree, int id);
    
    public void addChoice(SRETree tree, int id, SRETreeNode choiceToAdd);
    
    public void updateTree(SRETree tree, SRETreeNode node);
    
}
