import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.RuntimeException.*;

public class Final2 {

  private static HashMap<String,String> engToFrn = new HashMap<String,String>();
  private static HashMap<String,String> frnToEng = new HashMap<String,String>();

  public static void main(String[] args) {
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



    JFrame flashcardWindow = new JFrame("Flashcards");
    JPanel flashcardContent = new JPanel();
    java.util.ArrayList<String> keys = new ArrayList<String>(engToFrn.keySet());
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
        //  translationLabel.setText("Word not found.");
          JFrame errorFrame = new JFrame("Error");
          JPanel errorContent = new JPanel();
          JOptionPane.showMessageDialog(errorFrame,"Word not found.","Inane error",JOptionPane.ERROR_MESSAGE);
          errorFrame.setContentPane(errorContent);
          errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          errorFrame.setSize(500,300);
          errorFrame.setVisible(false);
        }
      }
    });

    readDoc();

    displayButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setdisplayWindowContent();
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

    flashcardWindow.setContentPane(flashcardContent);
    flashcardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    flashcardWindow.setLocation(700,200);
    flashcardWindow.setSize(500,300);
    flashcardWindow.setVisible(false);
  }

  static void readDoc(){
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
      System.out.println("File not found");
    }
  }

  static void setdisplayWindowContent(){
       JFrame displayWindow = new JFrame("List of Words");
       JPanel miniPanel = new JPanel(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.PAGE_START;
       c.weightx = 0;

       JLabel label1 = new JLabel("English Words : French Words");
       miniPanel.add(label1, c);

       c.anchor = GridBagConstraints.PAGE_END;
       c.fill = GridBagConstraints.BOTH;
       c.weightx = 1;
       c.gridy = 1;
       c.ipady = 8;
       c.weighty = .8;
       JScrollPane scoller = getScroller(engToFrn);

       miniPanel.add(getScroller(engToFrn), c);

       displayWindow.setVisible(true);
       displayWindow.setContentPane(miniPanel);
       displayWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       displayWindow.setLocation(700,200);
       displayWindow.setSize(300,300);
       displayWindow.setVisible(true);
   }


  static JScrollPane getScroller(HashMap<String,String> map){
    String[] engWords = new String[engToFrn.size()];
    int y = 0;
    for(HashMap.Entry<String, String> entry : map.entrySet()) {
      engWords[y] = entry.getKey().toString() + " : " + entry.getValue().toString();
      y++;
    }
    JList engList = new JList(engWords);
    engList.setLayoutOrientation(JList.VERTICAL);
    JScrollPane engScroller = new JScrollPane(engList);
    return engScroller;
  }

}
