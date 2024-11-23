package it.unipv.compeng.model.utility.iterator.DocumentIterator;

import it.unipv.compeng.model.document.Dataset;
import it.unipv.compeng.model.document.Document;
public class DocumentIterator implements IDatasetIterator{
  /********************************/
  //Attributes
  /********************************/
  private final Dataset dataset;
  private int position;
  /********************************/
  //Constructors
  /********************************/
  public DocumentIterator(Dataset dataset){
    this.dataset=dataset;
    this.position=-1;
  }
  /********************************/
  //Getter/Setter
  /********************************/
  @Override
  public int getPosition(){
    return this.position;
  }
  /********************************/
  //Methods
  /********************************/
  @Override
  public Document next(){
    if (!hasNext()) {
      System.out.println(this + " Has no more documents to iterate");
      return null;
    }//end-if

    position+=1;
    return dataset.getDocument(position);
  }

  @Override
  public boolean hasNext(){
    return (position+1)<dataset.size();
  }
  /********************************/
}
