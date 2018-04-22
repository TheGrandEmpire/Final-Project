import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Final1 {
  public static void main(String[] args) {

    HashMap<String,String> frenchWords = new HashMap<String,String>();

    JFrame window = new JFrame("Final Project");
    JPanel content = new JPanel();
    content.setLayout(new BorderLayout());
    JPanel middle = new JPanel();
    middle.setLayout(new GridLayout(1,0));
    JPanel bottom = new JPanel();
    bottom.setLayout(new GridLayout(0,2));

    JTextField englishTF = new JTextField(); //set preferred size n stuff
    middle.add(englishTF);
    JTextField foreignTF = new JTextField();
    middle.add(foreignTF);
    //add foreign script (e.g. cyrillic, hanzi) option
    content.add(middle,BorderLayout.CENTER);

    JButton saveButton = new JButton("Save");
    bottom.add(saveButton);
    JButton displayButton = new JButton("Display");
    bottom.add(displayButton);
    //add search, flashcard options later
    content.add(bottom,BorderLayout.PAGE_END);

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
              frenchWords.put(key, value);
          } else {
              System.out.println("ignoring line: " + line);
          }
      }
    } catch (Exception f){
      englishTF.setText("File not found");
    }

    displayButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFrame displayWindow = new JFrame();
        JPanel displayContent = new JPanel();
        /*displayContent.setLayout(new GridLayout(0,2)); //make a scrollList later
        for(String key: frenchWords) { //goes through the entire hashmap
          displayContent.add(new JLabel(key.toString())); //prints the english word
          displayContent.add(new JLabel(frenchWords.get(key).toString())); //prints the corresponding french word
          //alphabetize later
        }*/ //this currently does not work only because this for loop doesn't work with hashmaps

        //displayContent.add(frenchWords);

        displayWindow.setContentPane(displayContent);
        displayWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayWindow.setLocation(700,200);
        displayWindow.setSize(500,300);
        displayWindow.setVisible(true);
      }
    });

    window.setContentPane(content);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocation(100,100);
    window.setSize(800,300);
    window.setVisible(true);
  }
}
