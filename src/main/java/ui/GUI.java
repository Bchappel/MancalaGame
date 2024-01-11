package ui;

import javax.swing.border.Border;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import mancala.AyoRules;
import mancala.GameNotOverException;
import mancala.GameRules;
import mancala.InvalidMoveException;
import mancala.KalahRules;
import mancala.MancalaDataStructure;
import mancala.MancalaGame;
import mancala.Saver;
import mancala.Player;
import mancala.UserProfile;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;

import java.io.IOException;
import java.io.File;



public class GUI extends JFrame{

    private PositionAwareButton[][] buttons;

    private MancalaGame currentGame;
    private GameRules gameRules;
    private boolean isGameActive = false;
    private UserProfile userProfile;
    private String playerOneFn = null;
    private String playerTwoFn = null;

    public GUI(String windowTitle, int width, int height) {
        super(); // basic constructor
        setTitle(windowTitle);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        setJMenuBar(setupMenuBar());
        
    
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS)); // Vertical alignment
    
        //Bottom panel
        JTextArea textArea = new JTextArea(" Welcome to Mancala, start the game by selecting an option from the game menu tab above!");
        textArea.setEditable(false); // Make it non-editable
        textArea.setFont(new Font("Arial", Font.PLAIN, 24)); // Set the font size
        
        // Set the preferred size of the text area
        textArea.setPreferredSize(new Dimension(10, 100)); // Adjust width and height as needed

        // Add the text area to the content pane within a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(220, 120)); // Adjust width and height as needed
        contentPane.add(scrollPane, BorderLayout.CENTER);

        show();
    }

    private JMenuBar setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Game Menu
        JMenu fileMenu = new JMenu("Game Menu");
    
        JMenuItem newGame = new JMenuItem("Start New Game");
        newGame.addActionListener(event -> startNewOption());
        fileMenu.add(newGame);

        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(event -> loadGameOption());
        fileMenu.add(loadGame);

        JMenuItem saveGame = new JMenuItem("Save and Quit");
        saveGame.addActionListener(event -> saveAndQuitGameOption());
        fileMenu.add(saveGame);

        JMenuItem quitGame = new JMenuItem("Quit Without Saving");
        quitGame.addActionListener(event -> quitGameOption());
        fileMenu.add(quitGame);

        JMenuItem restartGame = new JMenuItem("Restart Game");
        restartGame.addActionListener(event -> restartGameOption());
        fileMenu.add(restartGame);

        JMenuItem exitGame = new JMenuItem("Exit Game");
        exitGame.addActionListener(event -> exitGameOption());
        fileMenu.add(exitGame);

        menuBar.add(fileMenu);
        return menuBar;
    }
    
    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide]; // array of buttons
        panel.setLayout(new GridLayout(tall, wide, 10, 10));
    
        int bottomRowNumber = 1;
        int topRowNumber = 12;
    
        for (int y = 0; y < tall; y++) {
            for (int x = 0; x < wide; x++) {
                int pitNumber = 1;
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x + 1);
    
                if (y == 0) {
                    // Top row
                    buttons[y][x].setDown(topRowNumber);
                    pitNumber = topRowNumber;
                    topRowNumber--;
                } else {
                    // Bottom row
                    buttons[y][x].setDown(bottomRowNumber);
                    buttons[y][x].setForeground(Color.BLACK);
                    pitNumber = bottomRowNumber;
                    bottomRowNumber++;
                }
                buttons[y][x].setText(Integer.toString(gameRules.getNumStones(pitNumber)));

                int finalPitNumber = pitNumber; // To use in lambda expression
    
                buttons[y][x].addActionListener(e -> makeMove(finalPitNumber));
                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

    private void makeMove(int pitNumber) {
        int total = 0;
        String[] endOptions = {"Exit Game", "Restart Game"};
        try {
            total = currentGame.move(pitNumber);        
            if(total == -1){
                if(currentGame.getBonusTurn() != true){
                    currentGame.switchPlayer();
                }
            }
            checkForBonusTurn();
            currentGame.switchPlayer();
            currentGame.isGameOver();
            if(currentGame.isGameOver() == true){                
                int endChoice = displayWinner("Winning Player is " + currentGame.getWinner() + "!", endOptions);
                if(endChoice == 0){
                    int saveChoice1 = 0;
                    saveChoice1 = askSavePlayer("Do you want to save" + currentGame.getPlayer(0).getName() + "to a profile before exiting?", currentGame.getPlayer(0).getName());
                    if(saveChoice1 == JOptionPane.YES_OPTION){
                        saveAndQuitUserProfileOption(0); //yes to save (player 1)
                    }else{
                        System.exit(0); //no to save
                    }
                    int saveChoice2 = 0;
                    saveChoice2 = askSavePlayer("Do you want to save" + currentGame.getPlayer(1).getName() + "to a profile before exiting?", currentGame.getPlayer(1).getName());
                    if(saveChoice2 == JOptionPane.YES_OPTION){
                        saveAndQuitUserProfileOption(1); //yes to save (player 1)
                    }else{
                        System.exit(0); //no to save
                    }
                }else if(endChoice == 1){
                    int saveChoice1 = 0;
                    saveChoice1 = askSavePlayer("Do you want to save"+ currentGame.getPlayer(0).getName() + "to a profile before exiting?", currentGame.getPlayer(0).getName());
                    if(saveChoice1 == JOptionPane.YES_OPTION){
                        saveAndQuitUserProfileOption(0); //yes to save (player 2)
                    }else{
                        currentGame.restartGame(); //no to save
                    }
                    int saveChoice2 = 0;
                    saveChoice2 = askSavePlayer("Do you want to save"+ currentGame.getPlayer(1).getName() + "to a profile before exiting?", currentGame.getPlayer(1).getName());
                    if(saveChoice2 == JOptionPane.YES_OPTION){
                        saveAndQuitUserProfileOption(1); //yes to save (player 2)
                    }else{
                        currentGame.restartGame();
                    }
                }
            }else if(currentGame == null){
                int endChoice = displayWinner("Tie game!", endOptions);
            }
        } catch (InvalidMoveException e) {
            throw new RuntimeException("Invalid Move");
        }
        refreshGameScreen(); //reprints the updated game screen
    }

    private void checkForBonusTurn() {
        if (currentGame.getBonusTurn()) {
            currentGame.switchPlayer();
        }
    }
    
    private void saveAndQuitGameOption(){
        String saveFileName = JOptionPane.showInputDialog("Enter The Name of the Save");

        if(currentGame != null){
            Saver.saveObject(currentGame, saveFileName);
            JOptionPane.showMessageDialog(this, "saved file to" + saveFileName, "file saved", JOptionPane.INFORMATION_MESSAGE);

            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else{
            JOptionPane.showMessageDialog(this, "Error Saving to" + saveFileName, "file not saved", JOptionPane.ERROR_MESSAGE);
            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadGameOption() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file to load from");
    
        int userSelection = fileChooser.showOpenDialog(this);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToLoad = fileChooser.getSelectedFile();
            String fileName = fileToLoad.getName();
    
            MancalaGame loadedGame = MancalaGame.loadGame(fileName);
    
            if (loadedGame != null) {
                currentGame = loadedGame; // Assign the loaded game to the currentGame field
                gameRules = loadedGame.getGameRules();
                isGameActive = true;
                refreshGameScreen();
            } else {
                throw new RuntimeException("Error Unable to Load Game");
            }
        }
    }
    
    public void gameSet(MancalaGame game){
        currentGame = game;
    }

    public void startNewOption() {
        if (!isGameActive || currentGame == null) {
            
            String playerOne = "Player One Default";
            String playerTwo = "Player Two Default";

            int pOneChoice = askLoadPlayer("Player 1, you want to load a user profile?");
            if (pOneChoice == JOptionPane.YES_OPTION) {
                playerOneFn = getProfileName();
            } else {
                playerOne = inputUserNames("first");
            }
    
            int pTwoChoice = askLoadPlayer("Player 2, you want to load a user profile?");
            if (pTwoChoice == JOptionPane.YES_OPTION) {
                playerTwoFn = getProfileName();
            } else {
                playerTwo = inputUserNames("second");
            }

            String[] customOptions = {"Kalah Rules", "Ayo Rules"};
            int ruleChoice = showCustomOptionDialog("select an game mode", customOptions);
    
            if (ruleChoice == 0) {
                isGameActive = true;
                gameRules = new KalahRules();
                currentGame = new MancalaGame(gameRules, playerOne, playerTwo, playerOneFn, playerTwoFn);
                currentGame.startNewGame();
            } else if (ruleChoice == 1) {
                isGameActive = true;
                gameRules = new AyoRules();
                currentGame = new MancalaGame(gameRules, playerOne, playerTwo, playerOneFn, playerTwoFn);
                currentGame.startNewGame();
            }
    
            refreshGameScreen();
        } else {
            JOptionPane.showMessageDialog(this, "Game already started.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int displayWinner(String message,String[] options){
        return JOptionPane.showOptionDialog(null, message, "Do you want to continue?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    private int askLoadPlayer(String message) {
        return JOptionPane.showOptionDialog(null, message, "Select a player to load", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, null,null,null);
    }

    private static int showCustomOptionDialog(String message, String[] options) {
        return JOptionPane.showOptionDialog(null, message, "Select a rule set", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public String inputUserNames(String number){
        String playerName = JOptionPane.showInputDialog("Please enter the name of the " + number + " player.");
        return playerName;
    }

    public String getProfileName() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file to load user profile from");
    
        int userSelection = fileChooser.showOpenDialog(this);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String fileName = fileToLoad.getName();
            return fileName;
        }
        return null; //Returns null if there is an interupt
    }
    
    private void saveAndQuitUserProfileOption(int playerNumber) {
        String saveFileName = JOptionPane.showInputDialog("Enter The Name of the Save");
        currentGame.saveUserProfile(currentGame.getPlayer(playerNumber), saveFileName);
    }

    public void exitGameOption(){
        System.exit(0);
    }

    private void restartGameOption(){
        if(currentGame != null){
            currentGame.restartGame();
            refreshGameScreen();
        }
    }

    private void refreshGameScreen() {
        Container contentPane = getContentPane();
        contentPane.removeAll(); // Remove all components from the content pane
    
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS)); // Vertical alignment
   
        if (isGameActive && currentGame != null) {
            JPanel sideButtonsContainer = new JPanel();
            sideButtonsContainer.setLayout(new BoxLayout(sideButtonsContainer, BoxLayout.X_AXIS));
    
            //Player 2 Store
            JButton leftButton = makeSideButton(currentGame.getPlayer(1).getName() +"'s store count is: " + gameRules.getStoreCount(2), true);
            sideButtonsContainer.add(leftButton);

            // Create button grid (2x6)
            JPanel buttonGridContainer = new JPanel();
            buttonGridContainer.setLayout(new FlowLayout());
            JPanel buttonGridPanel = makeButtonGrid(2, 6);
            buttonGridContainer.add(buttonGridPanel);
    
            //Player 1 store
            JButton rightButton = makeSideButton(currentGame.getPlayer(0).getName() +"'s store count is: " + gameRules.getStoreCount(1), false);
            sideButtonsContainer.add(Box.createHorizontalGlue()); // Push buttons to the sides
            sideButtonsContainer.add(buttonGridContainer);
            sideButtonsContainer.add(Box.createHorizontalGlue()); // Push buttons to the sides
            sideButtonsContainer.add(rightButton);

            contentPane.add(sideButtonsContainer);// Add sideButtonsContainer to the main content pane
        }
    
        JTextArea textArea = new JTextArea("Current Player" + currentGame.getCurrentPlayer() + currentGame.getPlayer(0).getUserProfile().toString() + currentGame.getPlayer(1).getUserProfile().toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setPreferredSize(new Dimension(10, 50));
    
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(220, 120));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        revalidate(); // Repaint to update GUI
        repaint();
    }
    
    private JButton makeSideButton(String buttonText, boolean isLeftButton) {
        JButton sideButton = new JButton(buttonText);
        sideButton.setPreferredSize(new Dimension(250, 50)); // Adjust size as needed
        sideButton.setEnabled(false); // Make it non-clickable
    
        // Set colors and font based on button type
        if (isLeftButton) {
            sideButton.setBackground(Color.BLUE);
            sideButton.setForeground(Color.WHITE);
            sideButton.setFont(new Font("Arial", Font.BOLD, 12));
        } else {
            sideButton.setBackground(Color.RED);
            sideButton.setForeground(Color.WHITE);
            sideButton.setFont(new Font("Arial", Font.BOLD, 12));
        }
        return sideButton;
    }
    
    private int askSavePlayer(String message, String playerName){
        return JOptionPane.showOptionDialog(null, message, playerName, JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, null,null,null);
    }

    public void quitGameOption(){
        if(currentGame != null){
            currentGame.restartGame();
            refreshGameScreen();
        }
    }
    public static void main(String[] args){
        new GUI("Mancala Game", 1440, 880);
    }
}