package it.unipv.compeng.model.postinglist;

import java.util.LinkedList;
import java.util.SortedSet;

public class PositionalPostingList extends PostingList{
  private class Node{
    private int docId;
    private int docFrequency;
    private SortedSet<Integer> positions;
  }

  private LinkedList<Node> postings;
}
