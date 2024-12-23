package it.unipv.compeng.model.postinglist;

import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.VariableByteCode;

import java.io.*;
import java.util.ArrayList;

/***********************************************************/
//CLASS PositionalPostingList IS A CONCRETE IMPLEMENTATION OF A
//POSTING LIST IN WHICH FOREACH DOCID ALL THE TERM
//POSITIONS ARE SPECIFIED
/***********************************************************/
public class PositionalPostingList extends PostingList implements Serializable {
  /********************************/
  //NESTED CLASS
  /********************************/
  private static class Node{
    /********************************/
    //Attributes
    /********************************/
    private transient final ArrayList<Integer> positions;
    /********************************/
    //Constructor
    /********************************/
    public Node(){
      this.positions=new ArrayList<>();
    }
  }
  /********************************/
  //Attributes
  /********************************/
  private final transient ArrayList<Node> postings;
  private ArrayList<VariableByteCode> compressedPositions;
  /********************************/
  //Constructor
  /********************************/
  public PositionalPostingList(){
    super();
    postings=new ArrayList<>();
    compressedPositions=new ArrayList<>();
  }
  /********************************/
  //Methods
  /********************************/
  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException{
    for(PostingList.Node node:super.nodes){
      super.compressedDocIds.add(node.getDocId());
      super.compressedTermDocCounts.add(node.getTermDocCount().intValue());
    }//end-for

    out.writeObject(compressedDocIds);
    out.writeObject(compressedTermDocCounts);

    for(Node node:postings){
      VariableByteCode tmp=new VariableByteCode(true);
      for(Integer i:node.positions){
        tmp.add(i);
      }//end-for-each
      compressedPositions.add(tmp);
    }//end-for-each

    out.writeObject(compressedPositions);
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
    super.compressedDocIds = (VariableByteCode) in.readObject();
    super.compressedTermDocCounts= (VariableByteCode) in.readObject();
    this.compressedPositions= (ArrayList<VariableByteCode>) in.readObject();
  }

  @Override
  public void addToPostingList(Term t){
    Node node=new Node();
    int pos=insertDocId(t);

    if(pos<0){
      node.positions.add(t.getTermPositionInDocument());
      postings.add(Math.abs(pos+1), node);
    }else if(postings.get(pos).equals(node)){
      postings.get(pos).positions.add(t.getTermPositionInDocument());
    }//end-if
  }
  /********************************/
}
