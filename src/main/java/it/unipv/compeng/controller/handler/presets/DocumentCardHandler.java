package it.unipv.compeng.controller.handler.presets;

import it.unipv.compeng.model.document.Document;
import it.unipv.compeng.view.presets.DocumentCard;

/***********************************************************/
//CLASS DocumentCardHandler PER MVC ARCHITECTURE, HANDLES ALL THE
//USER INTERACTION WITH THE DocumentCard view
/***********************************************************/
public class DocumentCardHandler {
  /********************************/
  //Attributes
  /********************************/
  private final DocumentCard documentCard;
  private final Document document;
  /********************************/
  //Constructors
  /********************************/
  public DocumentCardHandler(DocumentCard documentCard, Document document) {
    this.documentCard = documentCard;
    this.document = document;
    initComponents();
  }
  /********************************/
  //Methods
  /********************************/
  private void initComponents(){
    this.documentCard.setTitle(document.getTitle());
    this.documentCard.setDescription("This is the "+document.getDocId()+" document with " + document.getScore() + " points.");
  }
  /********************************/
}
