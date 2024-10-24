package it.unipv.compeng.model.postinglist;

import java.util.SortedSet;

public class PositionalPostingList extends PostingList{
  int docFrequency;
  SortedSet<Integer> positions;
}
