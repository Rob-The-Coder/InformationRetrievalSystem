package it.unipv.compeng.model.document;

import it.unipv.compeng.model.utility.iterator.DocumentIterator.DocumentIterator;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterable;
import it.unipv.compeng.model.utility.iterator.DocumentIterator.IDatasetIterator;

import java.util.ArrayList;
public class Dataset implements IDatasetIterable{
  /********************************/
  //Attributes
  /********************************/
  private final ArrayList<Document> documents;
  /********************************/
  //Constructors
  /********************************/
  public Dataset(){
    documents = new ArrayList<>();
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  public void add(Document document){
    documents.add(document);
  }

  public void remove(int index){
    documents.remove(index);
  }

  public Document getDocument(int index){
    return documents.get(index);
  }

  public int size(){
    return documents.size();
  }

  @Override
  public Dataset clone(){
    Dataset dataset = new Dataset();
    for(Document document : documents){
      dataset.add(document.clone());
    }//end-for
    return dataset;
  }

  @Override
  public IDatasetIterator iterator(){
    return new DocumentIterator(this);
  }
  /********************************/
}
