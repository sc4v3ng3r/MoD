package com.example.scavenger.mod;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static android.widget.Toast.*;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends ListActivity implements TextView.OnEditorActionListener, DialogInterface.OnDismissListener{

  private EditText m_wordEdit, m_translateEdit;
  private Button m_addButton;
  private Dictionary m_dictionary;
  private String DATA_FILE_NAME = "dictionary.txt";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_main);

    settingUiElements();
    settingFilters();

    /*Aqui vai ter que verificar se nao ha dados
    * anterioris mantidos na activity, para entao instanciar um
    * novo dictionary!*/

    m_dictionary  = new Dictionary(this, new ArrayList<Word>());
    setListAdapter(m_dictionary);

    try{
      load();
    } catch (IOException e){
      Log.i("NGVL", e.getMessage() + "\n" + e.getStackTrace());
    }


  }

  @Override
  protected void onSaveInstanceState(Bundle instanceState){
    super.onSaveInstanceState(instanceState);
    //instanceState.putParcelable();

  }

  private void settingUiElements(){

    m_wordEdit = (EditText) findViewById(R.id.wordEdit);
    m_translateEdit = (EditText) findViewById(R.id.translateEdit);

    m_addButton = (Button) findViewById(R.id.add_button);

    m_addButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

        String word = m_wordEdit.getText().toString();
        String translates = m_translateEdit.getText().toString();

        if (dataValidation(word)){
          m_dictionary.add( new Word(word, translates));
          try {
            save();
          } catch (IOException e){
            Log.i("NGVL", "ERROR CALLING SAVE IN MAIN_ACTIVITY" + e.getMessage());
          }
          m_wordEdit.setText("");
          m_translateEdit.setText("");
        }
      }
    });

  }

  private void settingFilters(){

    m_wordEdit.setFilters( new InputFilter[] { new InputFilter() {
      @Override
      public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        if (source.toString().matches( "[\\p{Alpha} [ ]+ ]+") ) {
          //Log.i("NGVL", source.toString());
          return source;
        }
        //Log.i("NGVL", "retornando null");
        return "";
      }
    }});

    m_translateEdit.setFilters(new InputFilter[]{ new InputFilter() {
      @Override
      public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {

        if (source.toString().matches("[\\p{Alpha} [ ],+ ]+"))
          return source;

        return "";
      }
    }} );
  }

  private boolean dataValidation(String word){

    if (word.isEmpty())
      return false;

    return true;
  }

  @Override
  public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
    return false;
  }

  @Override
  protected void onListItemClick(ListView listView, View view, int position, long id){
    super.onListItemClick(listView, view, position, id);
    /*Chamar uma dialog que funcione*/
  }



  private void save() throws IOException {
    try {
      FileOutputStream out = openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
      PrintWriter writer = new PrintWriter(out);

      for(int i=0; i < m_dictionary.getCount(); i++){
        Word data = m_dictionary.getItem(i);
        writer.println(data.getWord());
        writer.println(data.getTranslateStrings());
      }
      writer.flush();
      writer.close();
      out.close();

    } catch (FileNotFoundException fileNotFound){
      Log.i("NGVL", "ERROR IN SAVE() MainActivity\n" + fileNotFound.getMessage() + "\n" +
        fileNotFound.getCause());
    }
  }

  private void load() throws IOException{

    try {
      FileInputStream input = openFileInput(DATA_FILE_NAME);
      BufferedReader reader = new BufferedReader( new InputStreamReader(input));
      StringBuilder newString = new StringBuilder();
      String word;

      while ( (word = reader.readLine())!=null ){
        String translate = reader.readLine();
        m_dictionary.add( new Word(word, translate) );
      }

      reader.close();
      input.close();
    } catch (FileNotFoundException fileNotFound){
      Log.i("NGVL", "ERROR IN LOAD() MainActivity\n" + fileNotFound.getMessage() + "\n" +
        fileNotFound.getCause());
    }

  }

  @Override
  public void onDismiss(DialogInterface dialogInterface) {
  }

}
