import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.RuntimeException.*;

public class Final2 {
  public static void main(String[] args) {

    HashMap<String,String> engToFrn = new HashMap<String,String>();
    HashMap<String,String> frnToEng = new HashMap<String,String>();

    JFrame window = new JFrame("Final Project");
    JPanel content = new JPanel();
    content.setLayout(new BorderLayout());
    JPanel top = new JPanel();
    top.setLayout(new GridLayout(0,3));
    JPanel middle = new JPanel();
    middle.setLayout(new GridLayout(2,0));
    JPanel bottom = new JPanel();
    bottom.setLayout(new GridLayout(0,3));

    JTextField searchField = new JTextField();
    top.add(searchField);
    JButton searchButton = new JButton("Search");
    top.add(searchButton);
    JLabel translationLabel = new JLabel("");
    top.add(translationLabel);
    content.add(top,BorderLayout.PAGE_START);

    JLabel englishLabel = new JLabel("English:");
    JLabel foreignLabel = new JLabel("French:");
    JTextField englishTF = new JTextField(); //set preferred size n stuff
    middle.add(englishLabel);
    middle.add(englishTF);
    JTextField foreignTF = new JTextField();
    middle.add(foreignLabel);
    middle.add(foreignTF);
    //add foreign script (e.g. cyrillic, hanzi) option
    content.add(middle,BorderLayout.CENTER);

    JButton saveButton = new JButton("Save");
    bottom.add(saveButton);
    JButton displayButton = new JButton("Display");
    bottom.add(displayButton);
    JButton flashcardButton = new JButton("Flashcards");
    bottom.add(flashcardButton);
    content.add(bottom,BorderLayout.PAGE_END);

    JFrame displayWindow = new JFrame("Display");
    JPanel displayContent = new JPanel();
    displayContent.setLayout(new GridLayout(0,2)); //@TODO make a scrollList
    for(HashMap.Entry<String, String> entry : engToFrn.entrySet()) {
      displayContent.add(new JLabel(entry.getKey().toString())); //displays the english word
      displayContent.add(new JLabel(entry.getValue().toString())); //displays the corresponding french word
      //@TODO alphabetize
    }

    JFrame flashcardWindow = new JFrame("Flashcards");
    JPanel flashcardContent = new JPanel();
    java.util.List<String> keys = new ArrayList<String>(engToFrn.keySet());
    JLabel flashcardLabel = new JLabel();
    try {
      String flashcardKey = keys.get((int)(Math.random()*keys.size()));
      flashcardLabel.setText(flashcardKey);
    } catch (IndexOutOfBoundsException iOOBE) {
      flashcardLabel.setText("No words have been stored.");
    }
    flashcardContent.add(flashcardLabel);
    //@TODO: add mouselistener to show translation, next/previous buttons

    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String eng = englishTF.getText();
        String forn = foreignTF.getText();
        //write to file
        try {
          FileWriter writer = new FileWriter("foreignWords.txt", true);
          writer.write(eng + ":" + forn);
          writer.write("\r\n");   // write new line
          writer.close();
        } catch (IOException i) {
          i.printStackTrace();
        }
      }
    });

    searchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String value = engToFrn.get(searchField.getText().trim());
        if(value != null) {
          translationLabel.setText(value);
        } else if(frnToEng.get(searchField.getText().trim()) != null) {
          String translation = frnToEng.get(searchField.getText().trim());
          translationLabel.setText(translation);
        } else {
          translationLabel.setText("Word not found.");
        }
      }
    });

    String line;
    try{
      BufferedReader reader = new BufferedReader(new FileReader("foreignWords.txt"));
      while ((line = reader.readLine()) != null)
      {
          String[] parts = line.split(":", 2);
          if (parts.length >= 2)
          {
              String key = parts[0];
              String value = parts[1];
              engToFrn.put(key,value);
              frnToEng.put(value,key);
          } else {
              System.out.println("ignoring line: " + line);
          }
      }
    } catch (Exception f){
      englishTF.setText("File not found");
    }

    displayButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        displayWindow.setVisible(true);
      }
    });

    flashcardButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        flashcardWindow.setVisible(true);
      }
    });

    window.setContentPane(content);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocation(100,100);
    window.setSize(800,300);
    window.setVisible(true);

    displayWindow.setContentPane(displayContent);
    displayWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    displayWindow.setLocation(700,200);
    displayWindow.setSize(500,300);
    displayWindow.setVisible(false);

    flashcardWindow.setContentPane(flashcardContent);
    flashcardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    flashcardWindow.setLocation(700,200);
    flashcardWindow.setSize(500,300);
    flashcardWindow.setVisible(false);
  }
}