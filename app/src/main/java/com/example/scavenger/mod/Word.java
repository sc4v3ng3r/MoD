package com.example.scavenger.mod;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by scavenger on 10/05/16.
 */
public class Word implements Parcelable{

  private String m_word = new String();
  private ArrayList<String> m_translateList = new ArrayList<String>();

  Word(String word, String translate){
    setWord(word);
    m_translateList.add(translate);
  }

  Word(Word word){
    m_word = new String(word.m_word);
    m_translateList = (ArrayList<String>) word.m_translateList.clone();
  }

  Word(String word){
    setWord(word);
  }

  Word(){}

  Word(String word, ArrayList<String> translateList){
    m_word = word;
    m_translateList = translateList;
  }

  public void setWord(String word) { m_word = word;}

  public String getWord(){ return m_word;}

  public void setTranslate(ArrayList<String> translate){ m_translateList = translate; }

  public void addTranslate(String translate){ m_translateList.add(translate); }


  public ArrayList<String> getTranslatesList()  { return m_translateList; }

  public String getTranslateStrings(){
    String translates = "";

    for(String s: m_translateList)
      translates+=s;

    return translates;
  }

  public boolean hasTranslates() { return (!m_translateList.isEmpty()); }

  protected Word(Parcel in) {
    m_word = in.readString();
    m_translateList = in.createStringArrayList();
  }

  public static final Creator<Word> CREATOR = new Creator<Word>() {
    @Override
    public Word createFromParcel(Parcel in) {
      return new Word(in);
    }

    @Override
    public Word[] newArray(int size) {
      return new Word[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(m_word);
    parcel.writeStringList(m_translateList);
  }
}
