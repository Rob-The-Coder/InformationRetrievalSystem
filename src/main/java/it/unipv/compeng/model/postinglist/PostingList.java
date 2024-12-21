package it.unipv.compeng.model.postinglist;

import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.VariableByteCode;

import java.util.ArrayList;
import java.util.Collections;

public abstract class PostingList{
  /********************************/
  //NESTED INNER CLASS
  /********************************/
  protected static class Node implements Comparable<Node>{
    /********************************/
    //Attributes
    /********************************/
    protected final Integer docId;
    protected Double termDocCount;
    /********************************/
    //Constructor
    /********************************/
    public Node(Integer docId){
      this.docId=docId;
      this.termDocCount=0.0;
    }

    public Node(Integer docId, Double termDocCount){
      this.docId=docId;
      this.termDocCount=termDocCount;
    }
    /********************************/
    //Getter/Setter
    /********************************/
    public Integer getDocId(){
      return docId;
    }

    public Double getTermDocCount(){
      return termDocCount;
    }
    /********************************/
    //Methods
    /********************************/
    public void incrementTermDocFrequencies(Double termDocFrequencies){
      this.termDocCount+=termDocFrequencies;
    }

    @Override
    public int compareTo(Node o){
      if(this.docId.equals(o.getDocId())){
        return 0;
      }else if(this.docId<o.getDocId()){
        return -1;
      }else{
        return 1;
      }//end-if
    }

    @Override
    public boolean equals(Object obj){
      Node n=(Node)obj;
      return this.docId.equals(n.getDocId());
    }
    /********************************/
  }
  /********************************/
  //Attributes
  /********************************/
  protected transient ArrayList<Node> nodes;
  protected VariableByteCode compressedDocIds;
  protected VariableByteCode compressedTermDocCounts;
  protected transient double scoreWeight;
  /********************************/
  //Constructor
  /********************************/
  public PostingList(){
    this.nodes=new ArrayList<>();
    this.compressedDocIds=new VariableByteCode(true);
    this.compressedTermDocCounts=new VariableByteCode(false);
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  public int insertDocId(Term t){
    Node node=new Node(t.getDocId(), scoreWeight);
    int pos=Collections.binarySearch(nodes, node);

    if(pos<0){
      nodes.add(Math.abs(pos+1), node);
    }else if(nodes.get(pos).equals(node)){
      nodes.get(pos).incrementTermDocFrequencies(scoreWeight);
    }//end-if

    return pos;
  }

  public abstract void addToPostingList(Term t);

  public int[] docIdsToArray(){
    return compressedDocIds.toArray();
  }

  public int[] termDocFrequenciesToArray(){
    return compressedTermDocCounts.toArray();
  }
  /********************************/
}
