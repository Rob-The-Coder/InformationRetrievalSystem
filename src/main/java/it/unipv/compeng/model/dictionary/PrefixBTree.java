package it.unipv.compeng.model.dictionary;

import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

import java.util.Arrays;

public class PrefixBTree extends Dictionary<Term, PostingList>{
  /********************************/
  //NESTED INNER CLASS
  /********************************/
  private static class BTreeNode{
    /********************************/
    //Attributes
    /********************************/
    private final Term[] keys;
    private final PostingList[] postingLists;
    private final PrefixBTree.BTreeNode[] children;
    private int n;
    private boolean leaf;
    /********************************/
    //Constructor
    /********************************/
    public BTreeNode(int t){
      this.keys=new Term[2 * t - 1];
      this.postingLists=new PostingList[2 * t - 1];
      this.children=new PrefixBTree.BTreeNode[2 * t];
      
      this.n=0;
      this.leaf=true;
    }
    /********************************/
    //Getter/Setter
    /********************************/
    public Term getKey(int i){
      return this.keys[i];
    }

    public void setKey(Term key, int i){
      this.keys[i]=key;
    }

    public BTreeNode getChild(int i){
      return this.children[i];
    }

    public void setChild(BTreeNode bTreeNode, int i){
      this.children[i]=bTreeNode;
    }

    public PostingList getPostingList(int i){
      return this.postingLists[i];
    }
    
    public void setPostingList(PostingList postingList, int i){
      this.postingLists[i]=postingList;
    }
    
    public boolean isLeaf(){
      return leaf;
    }

    public void setLeaf(boolean leaf){
      this.leaf=leaf;
    }

    public int getN(){
      return n;
    }

    public void setN(int n){
      this.n=n;
    }
    /********************************/
    //Methods
    /********************************/
    private void traverseSubtree(){
      int i;

      for(i=0; i<getN(); i+=1){
        if(!isLeaf()){
          getChild(i).traverseSubtree();
        }//end-if
        System.out.print(getKey(i).toString() + " | ");
      }//end-for

      if(!isLeaf()){
        getChild(i).traverseSubtree();
      }//end-if
    }

    private Term computeSeparator(int t){
      int i=2;
      Term separator=this.getKey(t - 1).returnDigits(1);
      Term predecessor=this.getKey(t - 2);

      while(predecessor.compareTo(separator)>0 && separator.toString().length()<this.getKey(t - 1).toString().length()){
        separator=this.getKey(t - 1).returnDigits(i);
        i+=1;
      }//end-while
      return separator;
    }

    @Override
    public String toString(){
      return "BTreeNode{" +
        "keys=" + Arrays.toString(keys) +
        ", postingLists=" + Arrays.toString(postingLists) +
        ", children=" + Arrays.toString(children) +
        ", n=" + n +
        ", leaf=" + leaf +
        '}';
    }

    /********************************/
  }
  /********************************/
  //Attributes
  /********************************/
  BTreeNode root;
  private final int t;
  /********************************/
  //Constructor
  /********************************/
  public PrefixBTree(int t){
    this.t=t;
    root=new BTreeNode(t);
  }
  /********************************/
  //Getters/Setters
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public Term search(Term key){
    BTreeNode current=root;

    while(current != null){
      int i=0;

      while(i<current.getN() && key.compareTo(current.getKey(i))>0){
        i+=1;
      }//end-while

      if(i<current.getN() && key.compareTo(current.getKey(i)) == 0){
        return current.getKey(i);
      }//end-if

      if(current.isLeaf()){
        return null;
      }else{
        current=current.getChild(i);
      }//end-if
    }//end-while
    return null;
  }

  public void traverse(){
    System.out.print("| ");
    root.traverseSubtree();
    System.out.println();
  }

  private void splitSeparator(BTreeNode x, int i){
    BTreeNode z=new BTreeNode(this.t);
    BTreeNode y=x.getChild(i);
    z.setLeaf(y.isLeaf());
    z.setN(this.t - 1);

    //Copying second part of y into z
    for(int j=0; j<(t - 1); j+=1){
      z.setKey(y.getKey(j + t), j);
      z.setPostingList(y.getPostingList(j + t), j);
    }//end-for

    //Copying children
    if(!y.isLeaf()){
      for(int j=0; j<t; j+=1){
        z.setChild(y.getChild(j + t), j);
      }//end-for
    }//end-if
    y.setN(this.t - 1);

    for(int j=x.getN(); j>=(i + 1); j-=1){
      x.setChild(x.getChild(j), j + 1);
    }//end-for
    x.setChild(z, i + 1);

    for(int j=x.getN() - 1; j>=i; j-=1){
      x.setKey(x.getKey(j), j + 1);
      x.setPostingList(x.getPostingList(j), j + 1);
    }//end-for
    x.setKey(y.getKey(this.t - 1), i);
    x.setPostingList(y.getPostingList(this.t - 1), i);

    x.setN(x.getN() + 1);
  }

  //X is non-full internal node.
  //i is the index such that x.getChild(i) is a full child of X, hence is to split.
  //Y is the child of x which is full and is to split.
  //Z becomes the sibling of Y, so the child of X.
  private void splitChild(BTreeNode x, int i){
    BTreeNode z=new BTreeNode(this.t);
    BTreeNode y=x.getChild(i);
    z.setLeaf(y.isLeaf());
    z.setN(this.t);

    //Calculating separator
    Term separatorKey=y.computeSeparator(this.t);

    //Copying second part of y into z, from the median onward
    for(int j=0; j<t; j+=1){
      z.setKey(y.getKey(j + t - 1), j);
      z.setPostingList(y.getPostingList(j + t - 1), j);
    }//end-for

    //Copying children
    if(!y.isLeaf()){
      for(int j=0; j<(t + 1); j+=1){
        z.setChild(y.getChild(j + t - 1), j);
      }//end-for
    }//end-if
    y.setN(this.t - 1);


    //Making space for annexing Z to X
    for(int j=x.getN(); j>=(i + 1); j-=1){
      x.setChild(x.getChild(j), j + 1);
    }//end-for
    x.setChild(z, i + 1);

    for(int j=x.getN() - 1; j>=i; j-=1){
      x.setKey(x.getKey(j), j + 1);
      x.setPostingList(x.getPostingList(j), j + 1);
    }//end-for

    x.setKey(separatorKey, i);
    x.setPostingList(y.getPostingList(t - 1), i);

    x.setN(x.getN() + 1);
  }

  private void insertNonFull(BTreeNode x, Term key, PostingList postingList) {
    int i = x.getN() - 1;

    if (x.isLeaf()) {
      while (i >= 0 && key.compareTo(x.getKey(i)) < 0) {
        x.setKey(x.getKey(i), i + 1);
        x.setPostingList(x.getPostingList(i), i + 1);
        i -= 1;
      }//end-while
      i += 1;
      x.setKey(key, i);
      x.setPostingList(postingList, i);
      
      x.setN(x.getN() + 1);
    } else {
      while (i >= 0 && key.compareTo(x.getKey(i)) < 0) {
        i -= 1;
      }//end-while
      i += 1;

      if (x.getChild(i).getN() == (2 * t - 1)) {
        //If child is a leaf, then it's a term node, hence split adding separator
        //otherwise, if child is a full node of separator we need to split
        //by pushing the median upwards.
        if (x.getChild(i).isLeaf()) {
          splitChild(x, i);
        } else {
          splitSeparator(x, i);
        }//end-if

        if (key.compareTo(x.getKey(i)) > 0) {
          i += 1;
        }//end-if
      }//end-if
      insertNonFull(x.getChild(i), key, postingList);
    }//end-if
  }

  @Override
  public void insert(Term key, PostingList postingList) {
    BTreeNode r=root;

    if(r.getN() == (2 * t - 1)){
      BTreeNode s=new BTreeNode(this.t);
      root=s;
      s.setLeaf(false);
      s.setN(0);
      s.setChild(r, 0);

      //if root node is a term node, then split it by creating a separator
      //otherwise if the root is made of separator split it by pushing the median upwards.
      if(r.isLeaf()){
        splitChild(s, 0);
      }else{
        splitSeparator(s, 0);
      }//end-if

      insertNonFull(s, key, postingList);
    }else{
      insertNonFull(r, key, postingList);
    }//end-if
  }

  /********************************/
}
