// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.awt.*;
import java.awt.event.*;

/** A program that uses all the standard AWT components */
public class AllComponents extends Frame implements ActionListener {
  TextArea textarea;  // Events messages will be displayed here.

  /** Create the whole GUI, and set up event listeners */
  public AllComponents(String title) {
    super(title);  // set frame title.

    // Arrange to detect window close events
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });

    // Set a default font
    this.setFont(new Font("SansSerif", Font.PLAIN, 12));

    // Create the menubar.  Tell the frame about it.
    MenuBar menubar = new MenuBar();
    this.setMenuBar(menubar);

    // Create the file menu.  Add to menubar.
    Menu file = new Menu("File");
    menubar.add(file);

    // Create two items for the file menu, setting their label, shortcut,
    // action command and listener.  Add them to File menu.
    // Note that we use the frame itself as the action listener
    MenuItem open = new MenuItem("Open", new MenuShortcut(KeyEvent.VK_O));
    open.setActionCommand("open");
    open.addActionListener(this);
    file.add(open);
    MenuItem quit = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_Q));
    quit.setActionCommand("quit");
    quit.addActionListener(this);
    file.add(quit);

    // Create Help menu; add an item; add to menubar
    // Display the help menu in a special reserved place.
    Menu help = new Menu("Help");
    menubar.add(help);
    menubar.setHelpMenu(help);

    // Create and add an item to the Help menu
    MenuItem about = new MenuItem("About", new MenuShortcut(KeyEvent.VK_A));
    about.setActionCommand("about");
    about.addActionListener(this);
    help.add(about);

    // Now that we've done the menu, we can begin work on the contents of
    // the frame.  Assign a BorderLayout manager with margins for this frame.
    this.setLayout(new BorderLayout(10, 10));

    // Create two panels to contain two columns of components.  Use our custom
    // ColumnLayout layout manager for each.  Add them on the west and
    // center of the frame's border layout
    Panel column1 = new Panel();
    column1.setLayout(new ColumnLayout(5, 10, 2, ColumnLayout.LEFT));
    this.add(column1, "West");
    Panel column2 = new Panel();
    column2.setLayout(new ColumnLayout(5, 10, 2, ColumnLayout.LEFT));
    this.add(column2, "Center");

    // Create a panel to contain the buttons at the bottom of the window
    // Give it a FlowLayout layout manager, and add it along the south border
    Panel buttonbox = new Panel();
    buttonbox.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
    this.add(buttonbox, "South");

    // Create pushbuttons and add them to the buttonbox
    Button okay = new Button("Okay");
    Button cancel = new Button("Cancel");
    buttonbox.add(okay);
    buttonbox.add(cancel);

    // Handle events on the buttons
    ActionListener buttonlistener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textarea.append("You clicked: " +
                        ((Button)e.getSource()).getLabel() + "\n");
      }
    };
    okay.addActionListener(buttonlistener);
    cancel.addActionListener(buttonlistener);

    // Now start filling the left column.
    // Create a 1-line text field and add to left column, with a label
    TextField textfield = new TextField(15);
    column1.add(new Label("Name:"));
    column1.add(textfield);

    // Handle events on the TextField
    textfield.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textarea.append("Your name is: " +
                         ((TextField)e.getSource()).getText() + "\n");
      }
    });
    textfield.addTextListener(new TextListener() {
      public void textValueChanged(TextEvent e) {
        textarea.append("You have typed: " +
                         ((TextField)e.getSource()).getText() + "\n");
      }
    });

    // Create a dropdown list or option menu of choices
    Choice choice = new Choice();
    choice.addItem("red");
    choice.addItem("green");
    choice.addItem("blue");
    column1.add(new Label("Favorite color:"));
    column1.add(choice);

    // Handle events on this choice
    choice.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        textarea.append("Your favorite color is: " + e.getItem() + "\n");
      }
    });

    // Create checkboxes, and group them in a CheckboxGroup to give them
    // "radio button" behavior.
    CheckboxGroup checkbox_group = new CheckboxGroup();
    Checkbox[] checkboxes = new Checkbox[3];
    checkboxes[0] = new Checkbox("vanilla", checkbox_group, false);
    checkboxes[1] = new Checkbox("chocolate", checkbox_group, true);
    checkboxes[2] = new Checkbox("strawberry", checkbox_group, false);
    column1.add(new Label("Favorite flavor:"));
    for(int i = 0; i < checkboxes.length; i++) column1.add(checkboxes[i]);

    // Handle events on the checkboxes
    ItemListener checkbox_listener = new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        textarea.append("Your favorite flavor is: " +
                        ((Checkbox)e.getItemSelectable()).getLabel() + "\n");
      }
    };
    for(int i = 0; i < checkboxes.length; i++)
      checkboxes[i].addItemListener(checkbox_listener);

    // Create a list of choices.
    List list = new List(4, true);
    list.addItem("Java"); list.addItem("C"); list.addItem("C++");
    list.addItem("Smalltalk"); list.addItem("Lisp");
    list.addItem("Modula-3"); list.addItem("Forth");
    column1.add(new Label("Favorite languages:"));
    column1.add(list);
    // Handle events on this list
    list.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        textarea.append("Your favorite languages are: ");
        String[] languages = ((List)e.getItemSelectable()).getSelectedItems();
        for(int i = 0; i < languages.length; i++) {
          if (i > 0) textarea.append(",");
          textarea.append(languages[i]);
        }
        textarea.append("\n");
      }
    });

    // Create a multi-line text area in column 2
    textarea = new TextArea(6, 40);
    textarea.setEditable(false);
    column2.add(new Label("Messages"));
    column2.add(textarea);

    // Create a scrollpane that displays portions of a larger component
    ScrollPane scrollpane = new ScrollPane();
    scrollpane.setSize(300, 150);
    column2.add(new Label("Scrolling Window"));
    column2.add(scrollpane);

    // Create a custom MultiLineLabel with a really big font and make it
    // a child of the ScrollPane container
    String message =
      "/*************************************************\n" +
      " * AllComponents.java                            *\n" +
      " * Written by David Flanagan                     *\n" +
      " * Copyright (c) 1997 by O'Reilly & Associates   *\n" +
      " *                                               *\n" +
      " *************************************************/\n";
    MultiLineLabel biglabel =  new MultiLineLabel(message);
    biglabel.setFont(new Font("Monospaced", Font.BOLD + Font.ITALIC, 24));
    scrollpane.add(biglabel);
  }

  /** This is the action listener method that the menu items invoke */
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command.equals("quit")) {
      YesNoDialog d = new YesNoDialog(this, "Really Quit?",
                                      "Are you sure you want to quit?",
                                      "Yes", "No", null);
      d.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (e.getActionCommand().equals("yes")) System.exit(0);
          else textarea.append("Quit not confirmed\n");
        }
      });
      d.show();
    }
    else if (command.equals("open")) {
      FileDialog d = new FileDialog(this, "Open File", FileDialog.LOAD);
      d.show();  // display the dialog and block until answered
      textarea.append("You selected file: " + d.getFile() + "\n");
      d.dispose();
    }
    else if (command.equals("about")) {
      InfoDialog d = new InfoDialog(this, "About",
                                "This demo was written by David Flanagan\n" +
                                "Copyright (c) 1997 O'Reilly & Associates");
      d.show();
    }
  }

  public static void main(String[] args) {
    Frame f = new AllComponents("AWT Demo");
    f.pack();
    f.show();
  }
}
