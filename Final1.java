import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
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
    //add search, display, flashcard options later
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

    window.setContentPane(content);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocation(100,100);
    window.setSize(800,300);
    window.setVisible(true);
  }
}
