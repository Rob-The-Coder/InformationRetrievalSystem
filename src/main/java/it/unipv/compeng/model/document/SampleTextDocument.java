package it.unipv.compeng.model.document;
import java.io.*;

public class SampleTextDocument extends Document{
  /********************************/
  //Attributes
  /********************************/
  private final File sampleText;
  /********************************/
  //Constructors
  /********************************/
  public SampleTextDocument(String path, int docId){
    this(new File(path), docId);
  }

  public SampleTextDocument(File textDocument, int docId){
    if (textDocument == null || !textDocument.exists() || !textDocument.isFile()){
      throw new NullPointerException("File is invalid");
    }//end-if

    int dotIndex=textDocument.getName().lastIndexOf(".");
    if(dotIndex>=0){
      if(textDocument.getName().substring(dotIndex+1).equals("txt")){
        this.sampleText=textDocument;
        super.title=textDocument.getName();
        super.docId=docId;
      }else{
        throw new IllegalArgumentException("File is not a text file");
      }//end-if
    }else{
      throw new IllegalArgumentException("File has no extension");
    }//end-if
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public String read() throws IOException{
    BufferedReader bufferedReader=new BufferedReader(new FileReader(sampleText));
    StringBuilder text=new StringBuilder();
    String line="";

    try{
      while((line=bufferedReader.readLine()) != null){
        if(!line.isEmpty()){text.append(line).append("\n");}
      }//end-while
    }catch(IOException e){
      throw new IOException(e);
    }finally{
      bufferedReader.close();
    }//end-try-catch

    return text.toString();
  }

  @Override
  public Document clone(){
    return new SampleTextDocument(sampleText.getPath(), this.getDocId());
  }
  /********************************/
}
