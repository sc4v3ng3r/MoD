package com.example.scavenger.mod;

import java.util.Comparator;

/**
 * Created by scavenger on 13/05/16.
 */
public class WordComparator implements Comparator<Word> {
  @Override
  public int compare(Word w1, Word w2) {
    return w1.getWord().compareTo(w2.getWord());
  }
}
