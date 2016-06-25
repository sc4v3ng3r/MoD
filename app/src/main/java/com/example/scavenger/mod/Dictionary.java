package com.example.scavenger.mod;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by scavenger on 10/05/16.
 */


public class Dictionary extends BaseAdapter{
  private Context m_context;
  private ArrayList<Word> m_wordList;

  Dictionary(Context context, ArrayList<Word> objects){
    super();
    m_context = context;
    m_wordList = objects;
  }

  @Override
  public int getCount() { return m_wordList.size();}

  @Override
  public Word getItem(int position) { return m_wordList.get(position); }

  @Override
  public long getItemId(int position) { return  position; }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    Word word = m_wordList.get(position);

    ViewHolder viewHolder = null;

    if (view == null) {
      view = LayoutInflater.from(m_context).inflate(R.layout.word_layout, null);

      viewHolder = new ViewHolder();

      viewHolder.m_logoView = (ImageView) view.findViewById(R.id.logoView);
      viewHolder.m_wordView = (TextView) view.findViewById(R.id.wordView);
      viewHolder.m_translateView = (TextView) view.findViewById(R.id.translateView);
      view.setTag(viewHolder);
    }

    else {
      viewHolder = (ViewHolder) view.getTag();
    }

    viewHolder.m_logoView.setImageResource(R.drawable.list_icon);
    viewHolder.m_wordView.setText(word.getWord());
    viewHolder.m_translateView.setText(word.getTranslateStrings());
    return view;
  }

  public void add(Word word){
    m_wordList.add(word);
    Collections.sort(m_wordList, new WordComparator());
    notifyDataSetChanged();
  }

  public void add(Word word, int index){
    m_wordList.remove(index);
    m_wordList.add(word);
    Collections.sort(m_wordList, new WordComparator());
    notifyDataSetChanged();

  }

  static class ViewHolder{
    private ImageView m_logoView;
    private TextView m_wordView, m_translateView;
  }

  public Word remove(int index ) {
    Word obj = m_wordList.remove(index);
    Collections.sort(m_wordList, new WordComparator());
    notifyDataSetChanged();
    return obj;
  }


}
/*
public class Dictionary extends ArrayAdapter<Word>{
  private Context m_context;
  public static final String DICTIONARY_DATA_TO_PARCEL = "dictionary_parceleble_data";

  // Contexto      Lista com objetos
  public Dictionary(Context context, ArrayList<Word> objects) {
    Mas pode existir um construtor que antes do arrayList venha com o Id do layout!
    super(context, R.layout.word_layout, objects);
    m_context = context;
  }

  public Dictionary(Context context, int layoutId, ArrayList<Word> objects){
    super(context, layoutId, objects);
    m_context = context;
  }


  @Override
  public View getView(int position, View view, ViewGroup parent){

    LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View myView = inflater.inflate(R.layout.word_layout, parent, false);


    TextView wordView = (TextView) myView.findViewById(R.id.wordView);
    TextView translateView = (TextView) myView.findViewById(R.id.translateView);
    ImageView logo = (ImageView) myView.findViewById(R.id.logoView);

    logo.setImageResource(R.drawable.list_icon);
    wordView.setText( getItem(position).getWord() );
    translateView.setText(getItem(position).getTranslateStrings());

    return myView;
  }

  @Override
  public void add(Word w1){
    super.add(w1);
    this.sort( new WordComparator());
  }

}
*/