package com.bitris.graphics;

import com.bitris.logic.Score;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class GameOver {
    
    private static final AudioClip GAME_OVER_SOUND = new AudioClip(GameOver.class.
            getResource("/com/bitris/resources.audio/gameOver.wav").toExternalForm());
    private static final AudioClip NEXT_LEVEL_SOUND = new AudioClip(GameOver.class.
            getResource("/com/bitris/resources.audio/nextLevel.wav").toExternalForm());
    
    private static Label text = new Label();
    private static Label text2 = new Label();
    private static Label text3 = new Label();
    
    public static void setup(Label label)           { text = label; }
    public static void nextLevelSetup(Label label)  { text2 = label; }
    public static void scoreSetup(Label label)      { text3 = label; }
    
    private static int USE_PREF_SIZE = 100;
    private static int MAXROW = 10;
    private static int MAXCOL = 2;
    private static String[][] LeaderBoard;
    
    private static String username;
    
    public static void newGame()            { text.setText(""); }
    public static void gameOver()           { GAME_OVER_SOUND.play();   text.setText("GAME OVER!"); addName(); }
    public static void nextLevel()          { NEXT_LEVEL_SOUND.play();  text2.setText("Level " + Score.getLevelNumber()); }
    public static void updateScore()        { NEXT_LEVEL_SOUND.play();  text3.setText("" + Score.getScore()); }
    
    public static String getLeaderBoardIJ(int i, int j) { return LeaderBoard[i][j]; }

    private static void addName(){
        
        final Stage nameStage = new Stage();
        final TextField textField = new TextField();
        final Button submitButton = new Button("Submit");
        submitButton.setTranslateX(60);
        submitButton.setTranslateY(0);
        submitButton.setDefaultButton(true);
        submitButton.setOnAction((ActionEvent event) -> {
            username = textField.getText();
            nameStage.close();
            readFile();
            writeToFile();
        });
        textField.setMinHeight(TextField.USE_PREF_SIZE);
        final VBox layout = new VBox(10);
        nameStage.setTitle("Enter your name");
        submitButton.setStyle(
        "    -fx-font-size: 15px;\n" +
        "    -fx-font-family: \"Karmatic Arcade\";\n" +
        "    -fx-text-fill: #77300d;\n" +
        "    -fx-padding: 0 0 15 0;\n" +
        "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
        "    -fx-background-radius: 8;\n" +
        "    -fx-background-color: \n" +
        "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
        "        #9d4024,\n" +
        "        #d86e3a,\n" +
        "        radial-gradient(center 50% 50%, radius 100%, #ff9d47, #c54e2c);\n" +
        "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");
        layout.setStyle("-fx-background-color: whitesmoke;");

        layout.getChildren().addAll(
          textField, 
          submitButton
        );

        nameStage.setScene(new Scene(layout, 200, 100));
        nameStage.show();

        
    }
    
    /*reads the current leaderboard*/
    public static void readFile(){
    
        LeaderBoard = new String[MAXROW][MAXCOL];
        String fileName = "test/leaderboard.txt";
        String line;
        String[] temp = new String[100];

        try {

            FileReader fileReader = new FileReader(fileName);

            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                int i = 0;
                while((line = bufferedReader.readLine()) != null) {

                    temp[i] = new String();
                    temp[i] = line;
                    i++;
                }
            }         
        }
        catch(FileNotFoundException ex) {
                System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
                System.out.println("Error reading file '" + fileName + "'");                  
        }

        int i, j, k = 0;

        for (i = 0; i < MAXROW; i++) {
            for (j = MAXCOL - 1; j >= 0; j--) {
                LeaderBoard[i][j] = new String();
                LeaderBoard[i][j] = temp[k++];
            }
        }
    }
    
    /* write the score, new position and username of the player 
    & switch position if necessary */
    private static void writeToFile(){
        
        String fileName = "test/leaderboard.txt";

        try {
            
            FileWriter fileWriter = new FileWriter(fileName);
            /*if score > than existing score, replace it*/
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                int cnt = 0, i = 0;
                boolean added = false;
                while (cnt < MAXROW) {
                    int score;
                    String name;
                    if (Integer.parseInt(LeaderBoard[i][1]) < Score.getScore() && !added) {
                        score = Score.getScore();
                        name = username;
                        added = true;
                    } else {
                        score = Integer.parseInt(LeaderBoard[i][1]);
                        name = LeaderBoard[i][0];
                        i++;
                    }
                    bufferedWriter.write("" + score);
                    bufferedWriter.newLine();
                    bufferedWriter.write(name);
                    bufferedWriter.newLine();
                    cnt++;
                }
            }
        }
        catch(IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

}
