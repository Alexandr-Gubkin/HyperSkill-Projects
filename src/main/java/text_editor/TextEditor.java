package text_editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    public static String loadFile(String fileName) {
        byte[] array = new byte[0];
        try {
            array = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(array);
    }

    public static void saveFile(String fileName, String text) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            byte[] strToBytes = text.getBytes();
            outputStream.write(strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert outputStream != null;
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Open_Save_Listener implements ActionListener {

        JFileChooser FileChooser;
        JTextArea TextArea;

        private void setFileChooser(JFileChooser FileChooser) {
            this.FileChooser = FileChooser;
        }

        private void setTextArea(JTextArea TextArea) {
            this.TextArea = TextArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().matches("Open")) {
                try {
                    FileChooser.setVisible(true);
                    int returnValue = this.FileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = this.FileChooser.getSelectedFile();
                        this.TextArea.setText(TextEditor.loadFile(selectedFile.getPath()));
                    }
                } finally {
                    FileChooser.setVisible(false);
                }
            } else if (e.getActionCommand().matches("Save")) {
                try {
                    FileChooser.setVisible(true);
                    int returnValue = this.FileChooser.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = this.FileChooser.getSelectedFile();
                        TextEditor.saveFile(selectedFile.getPath(), this.TextArea.getText());
                    }
                } finally {
                    FileChooser.setVisible(false);
                }
            }
        }
    }

    public static class StartSearch implements ActionListener {

        JCheckBox CheckBox;
        JTextArea TextArea;
        JTextField SearchFile;
        static String searchText;
        static Pattern pattern;
        static Matcher matcher;
        static String text;
        int index;
        int wordLength;
        int currentIndex;

        private void setCheckBox(JCheckBox CheckBox) {
            this.CheckBox = CheckBox;
        }

        private void setTextArea(JTextArea TextArea) {
            this.TextArea = TextArea;
        }

        private void setSearchFiled(JTextField SearchFiled) {
            this.SearchFile = SearchFiled;
        }

        ArrayList<Integer> indexes = new ArrayList<>();

        @Override
        public void actionPerformed(ActionEvent e) {

            text = TextArea.getText();
            searchText = SearchFile.getText();
            Thread searching = new Thread(() -> {
                switch (e.getActionCommand()) {
                    case "StartSearch":
                        indexes.clear();
                        currentIndex = 0;
                        if (CheckBox.isSelected()) {
                            pattern = Pattern.compile(searchText);
                            matcher = pattern.matcher(text);
                            while (matcher.find()) {
                                wordLength = matcher.group().length();
                                index = matcher.start();
                                indexes.add(index);
                            }
                        } else {
                            wordLength = searchText.length();
                            int startIndex = 0;
                            while (true) {
                                index = text.indexOf(searchText, startIndex);
                                if (index >= 0) {
                                    indexes.add(index);
                                    startIndex = index + wordLength;
                                } else {
                                    break;
                                }
                            }
                        }
                        if (indexes.size() > 0) {
                            index = indexes.get(0);
                            TextArea.setCaretPosition(index + wordLength);
                            TextArea.select(index, index + wordLength);
                            TextArea.grabFocus();
                        }
                        break;
                    case "Next":
                        if (indexes.size() > 1) {
                            currentIndex++;
                            if (currentIndex == indexes.size()) {
                                currentIndex = 0;
                            }
                            index = indexes.get(currentIndex);
                            TextArea.setCaretPosition(index + wordLength);
                            TextArea.select(index, index + wordLength);
                            TextArea.grabFocus();
                        }
                        break;
                    case "Previous":
                        if (indexes.size() > 1) {
                            currentIndex--;
                            if (currentIndex == -1) {
                                currentIndex = indexes.size() - 1;
                            }
                            index = indexes.get(currentIndex);
                            TextArea.setCaretPosition(index + wordLength);
                            TextArea.select(index, index + wordLength);
                            TextArea.grabFocus();
                        }
                        break;
                }
            });
            searching.start();
        }
    }

    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextArea TextArea = new JTextArea(16, 50);
        TextArea.setName("TextArea");
        // Параметры переноса слов
        TextArea.setLineWrap(true);
        TextArea.setWrapStyleWord(true);

        JTextField SearchField = new JTextField(20);
        SearchField.setName("SearchField");

        JFileChooser FileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileChooser.setName("FileChooser");
        FileChooser.setVisible(false);
        Open_Save_Listener OSL = new Open_Save_Listener();
        OSL.setFileChooser(FileChooser);
        OSL.setTextArea(TextArea);

        Icon saveIcon = new ImageIcon("src/main/java/text_editor/resources/save.png");
        JButton SaveButton = new JButton(saveIcon);
        SaveButton.setActionCommand("Save");
        SaveButton.setName("SaveButton");
        SaveButton.addActionListener(OSL);

        Icon openIcon = new ImageIcon("src/main/java/text_editor/resources/open.png");
        JButton OpenButton = new JButton(openIcon);
        OpenButton.setActionCommand("Open");
        OpenButton.setName("OpenButton");
        OpenButton.addActionListener(OSL);


        Icon startSearchIcon = new ImageIcon("src/main/java/text_editor/resources/search.png");
        JButton StartSearchButton = new JButton(startSearchIcon);
        StartSearchButton.setName("StartSearchButton");
        StartSearchButton.setActionCommand("StartSearch");

        Icon previousMatchIcon = new ImageIcon("src/main/java/text_editor/resources/left.png");
        JButton PreviousMatchButton = new JButton(previousMatchIcon);
        PreviousMatchButton.setName("PreviousMatchButton");
        PreviousMatchButton.setActionCommand("Previous");

        Icon nextMatchIcon = new ImageIcon("src/main/java/text_editor/resources/right.png");
        JButton NextMatchButton = new JButton(nextMatchIcon);
        NextMatchButton.setName("NextMatchButton");
        NextMatchButton.setActionCommand("Next");

        JCheckBox UseRegExCheckbox = new JCheckBox("Use regex");
        UseRegExCheckbox.setName("UseRegExCheckbox");
        UseRegExCheckbox.setEnabled(true);

        StartSearch srch = new StartSearch();
        srch.setCheckBox(UseRegExCheckbox);
        srch.setSearchFiled(SearchField);
        srch.setTextArea(TextArea);
        StartSearchButton.addActionListener(srch);
        NextMatchButton.addActionListener(srch);
        PreviousMatchButton.addActionListener(srch);


        // Добавим поля в окно
        Box content = new Box(BoxLayout.Y_AXIS);
        JPanel contents_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contents_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JScrollPane ScrollPane = new JScrollPane(TextArea);
        ScrollPane.setName("ScrollPane");
        contents_1.add(OpenButton);
        contents_1.add(SaveButton);
        contents_1.add(SearchField);
        contents_1.add(StartSearchButton);
        contents_1.add(PreviousMatchButton);
        contents_1.add(NextMatchButton);
        contents_1.add(UseRegExCheckbox);
        content.add(FileChooser);
        contents_2.add(ScrollPane);
        content.add(contents_1);
        content.add(contents_2);
        setContentPane(content);

        //Меню
        JMenuBar menuBar = new JMenuBar();

        JMenu File = new JMenu("File");
        File.setName("MenuFile");
        File.setMnemonic(KeyEvent.VK_F);
        JMenuItem Open = new JMenuItem("Open");
        Open.setActionCommand("Open");
        Open.setName("MenuOpen");
        Open.addActionListener(OSL);
        JMenuItem Save = new JMenuItem("Save");
        Save.setActionCommand("Save");
        Save.setName("MenuSave");
        Save.addActionListener(OSL);
        JMenuItem Exit = new JMenuItem("Exit");
        Exit.setName("MenuExit");
        Exit.addActionListener(actionEvent -> dispose());

        JMenu Search = new JMenu("Search");
        Search.setName("MenuSearch");
        Search.setMnemonic(KeyEvent.VK_F);
        JMenuItem startSearch = new JMenuItem("Start Search");
        startSearch.setName("MenuStartSearch");
        startSearch.setActionCommand("StartSearch");
        startSearch.addActionListener(srch);
        JMenuItem previousMatch = new JMenuItem("Previous Match");
        previousMatch.setName("MenuPreviousMatch");
        previousMatch.setActionCommand("Previous");
        previousMatch.addActionListener(srch);
        JMenuItem nextMatch = new JMenuItem("Next Match");
        nextMatch.setName("MenuNextMatch");
        nextMatch.setActionCommand("Next");
        nextMatch.addActionListener(srch);
        JMenuItem useRegExp = new JMenuItem("Use regular expressions");
        useRegExp.setName("MenuUseRegExp");
        useRegExp.addActionListener(e -> UseRegExCheckbox.setSelected(true));

        File.add(Open);
        File.add(Save);
        File.addSeparator();
        File.add(Exit);
        Search.add(startSearch);
        Search.add(previousMatch);
        Search.add(nextMatch);
        Search.add(useRegExp);
        menuBar.add(File);
        menuBar.add(Search);
        setJMenuBar(menuBar);

        // Выводим окно на экран
        setSize(570, 380);
        setVisible(true);
    }
}