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
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JPanel content = new JPanel();
    content.setLayout(new BorderLayout());
    JPanel top = new JPanel();
    top.setLayout(new GridLayout(0,3));
    JPanel middle = new JPanel();
    middle.setLayout(new GridLayout(2,0));
    JPanel bottom = new JPanel();
    bottom.setLayout(new GridLayout(2,2));

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
    JButton langSelectionButton = new JButton("Language Selection");
    bottom.add(langSelectionButton);
    content.add(bottom,BorderLayout.PAGE_END);

    JButton returnButton = new JButton("Return");
    returnButton.setSize(200,50);

    searchButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String value = engToFrn.get(searchField.getText().trim());
        if(value != null) {
          translationLabel.setText(value);
        } else if(frnToEng.get(searchField.getText().trim()) != null) {
          String translation = frnToEng.get(searchField.getText().trim());
          translationLabel.setText(translation);
        } else {
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

    readDoc();

    displayButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JPanel displayContent = new JPanel(new GridLayout(0,1));
        JPanel displayPanel = new JPanel(new GridBagLayout());
        setDisplayPanelContent(displayPanel);
        displayContent.add(displayPanel);
        displayContent.add(returnButton);
        splitPane.setRightComponent(displayContent);
        splitPane.setDividerLocation(0.6);
      }
    });

    flashcardButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JPanel flashcardContent = new JPanel(new GridLayout(0,1));
        JPanel flashcardPanel = new JPanel();
        setFlashcardPanelContent(flashcardPanel);
        flashcardContent.add(flashcardPanel);
        flashcardContent.add(returnButton);
        splitPane.setRightComponent(flashcardContent);
        splitPane.setDividerLocation(0.6);
      }
    });

    langSelectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JPanel langSelectionContent = new JPanel(new GridLayout(0,1));
        langSelectionContent.setLayout(new GridLayout(0,1));
        JPanel langSelectionPanel = new JPanel();
        setLangSelectionPanelContent(langSelectionPanel);
        langSelectionContent.add(langSelectionPanel);
        langSelectionContent.add(returnButton);
        splitPane.setRightComponent(langSelectionContent);
        splitPane.setDividerLocation(0.6);
      }
    });

    returnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        splitPane.setDividerLocation(0.9999);
      }
    });

    splitPane.setLeftComponent(content);
    window.setContentPane(splitPane);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocation(100,100);
    window.setSize(800,300);
    window.setVisible(true);
  }

  /* Reads the document provided.
   * By default, this document is foreignWords.txt.
   * It contains English words and their French translations.
  */
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

  /* Sets the content of the display panel.
   * This panel then becomes the right component of the JSplitPane.
   * @param displayPanel the display panel
  */
  static void setDisplayPanelContent(JPanel displayPanel) {
       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.PAGE_START;
       c.weightx = 0;

       JLabel label1 = new JLabel("English Words : French Words");
       displayPanel.add(label1, c);

       c.anchor = GridBagConstraints.PAGE_END;
       c.fill = GridBagConstraints.BOTH;
       c.weightx = 1;
       c.gridy = 1;
       c.ipady = 8;
       c.weighty = .8;
       JScrollPane scoller = getScroller(engToFrn);

       displayPanel.add(getScroller(engToFrn), c);
   }

  /* Sets the content of the flashcard panel.
   * This panel then becomes the right component of the JSplitPane.
   * @param flashcardPanel the flashcard panel
  */
  static void setFlashcardPanelContent(JPanel flashcardPanel) {
    flashcardPanel.setLayout(new GridLayout(0,1));
    flashcardPanel.add(new JLabel("<html><h1>Coming soon!</h1></html>"));
  }

  /* Sets the content of the language selection panel.
   * This panel then becomes the right component of the JSplitPane.
   * @param langSelectionPanel the language selection panel
  */
  static void setLangSelectionPanelContent(JPanel langSelectionPanel) {
    JRadioButton frenchButton = new JRadioButton("English-French");
    langSelectionPanel.add(frenchButton);
    /*If we expand upon this program, frenchButton will have an ActionListener setting the language to French, and other buttons for other languages.*/
    langSelectionPanel.add(new JLabel("<html>Users will soon be able to store vocabulary for more languages.</html>"));
  }

  /* Creates a scroller for the displayContent panel from the contents of the hashmap.
  * @param map the HashMap of the words to be displayed in the scroller
  * @return engScroller the scroller to be displayed
  */
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
