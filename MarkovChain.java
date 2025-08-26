import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class MarkovChain extends JFrame {

    Map<String, List<String>> markovChain = new HashMap<String, List<String>>();
    Random rand = new Random();

    private JTextField textInput;
    private JTextArea textOutput;
    private JButton sendButton;

    public MarkovChain(){
        setTitle("Простое окошко");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,400);
        setLocationRelativeTo(null);
        createUI();
    }

    public void createUI(){

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel inputPanel = new JPanel(new BorderLayout(10,10));
        inputPanel.add(new JLabel("Введите текст"), BorderLayout.WEST);

        textInput = new JTextField();

        inputPanel.add(textInput, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        sendButton = new JButton("Send");

        buttonPanel.add(sendButton);


        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        textOutput = new JTextArea();

        textOutput.setEditable(false);
        textOutput.setLineWrap(true);
        textOutput.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                generateMarkovChain(10);
            }
        });

    }




    public static void main(String[] args) {

        MarkovChain markovChain1 = new MarkovChain();
        markovChain1.setVisible(true);
        try {
            Thread.sleep(30000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        markovChain1.TeachMarkov("/Users/aganiyaz/Desktop/Transactions/FileForMarkov2.txt");
        System.out.println(markovChain1.generateMarkovChain(30));
    }


    public void TeachMarkov (String input) {
        try{
            String text = new String (Files.readAllBytes(Paths.get(input)));
            String[] words = text.toLowerCase().split("\\s+");

            for (int i=0; i<words.length-1; i++) {
                String currentWord = words[i].replaceAll("[^a-zA-Za-яА-Я]", " ");
                String nextWord = words[i+1].replaceAll("[^a-zA-Za-яА-Я]", " ");

                if (currentWord.isEmpty() || nextWord.isEmpty()) {
                    continue;
                }

                markovChain.putIfAbsent(currentWord, new ArrayList<String>());
                markovChain.get(currentWord).add(nextWord);
            }
        } catch (IOException e) {
            System.out.println(" Exception: " + e.getMessage());
        }

    }

    public String generateMarkovChain (int nWords) {
        List<String> keyList = new ArrayList<>(markovChain.keySet());
        String currentWord = "мешала";


        StringBuilder result = new StringBuilder();
        result.append(currentWord).append(" ");

        for (int i=0; i<nWords; i++) {

            List<String> possibleWords = markovChain.get(currentWord);

            if (possibleWords.isEmpty()|| possibleWords==null) {
                currentWord = keyList.get(rand.nextInt(keyList.size()));
                possibleWords = markovChain.get(currentWord);
            }

            String nextWord = possibleWords.get(rand.nextInt(possibleWords.size()));
            result.append(nextWord).append(" ");

            currentWord = nextWord;
        }

        return result.toString();
    }

}
