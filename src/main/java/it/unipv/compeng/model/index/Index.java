package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.Dictionary;
import it.unipv.compeng.model.dictionary.PrefixBTree;
import it.unipv.compeng.model.indexer.RunnableIndexingStrategy;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.preprocessing.PorterStringPreprocessor;
import it.unipv.compeng.model.preprocessing.Preprocessor;
import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterable;

import java.io.Serializable;

public abstract class Index implements Serializable{
  /********************************/
  //Attributes
  protected Dictionary<Term, PostingList> dictionary;
  protected transient RunnableIndexingStrategy strategy;
  protected Preprocessor preprocessor;
  /********************************/
  //Constructor
  /********************************/
  public Index(Dictionary<Term, PostingList> dictionary, Preprocessor preprocessor) {
    this.dictionary = dictionary;
    this.preprocessor = preprocessor;
  }

  public Index(){
    this.dictionary=new PrefixBTree(2);
    this.preprocessor=new PorterStringPreprocessor();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public RunnableIndexingStrategy getStrategy(){
    return strategy;
  }

  public void setDataset(IDatasetIterable dataset){
    this.preprocessor.setDataset(dataset);
  }

  public Preprocessor getPreprocessor(){
    return preprocessor;
  }

  public void setStrategy(RunnableIndexingStrategy strategy){
    this.strategy = strategy;
  }
  /********************************/
  //Methods
  /********************************/
  public abstract PostingList getPostingList(Term t);

  public PostingList getPostingList(String s){
    System.out.println(getCorrectTerm(s));
    return getPostingList(getCorrectTerm(s));
  }

  public abstract void addToDictionary(Term t);

  public void traverseDictionary(){
    this.dictionary.traverse();
  }

  protected Term getCorrectTerm(String s){
    return preprocessor.process(s);
  }
  /********************************/
}
