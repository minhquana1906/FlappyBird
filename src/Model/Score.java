package Model;

import java.io.*;

public class Score {
    private final String HIGH_SCORE_FILE = "src/high_score.txt";

    private int score;
    private int highScore;

    public Score(){
        this.score = 0;
        this.highScore = getHighScore();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        FileReader fr = null;
        BufferedReader bf = null;

        try{
            fr = new FileReader(HIGH_SCORE_FILE);
            bf = new BufferedReader(fr);

            return Integer.parseInt(bf.readLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(bf != null){
                    bf.close();
                }
                if(fr != null){
                    fr.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void increaseScore(){
        this.score++;
    }

    public void updateHighScore(){
        FileWriter fw = null;
        BufferedWriter bw = null;

        if(highScore < 0){
            throw new IllegalArgumentException("High score must be greater than 0");
        }
        if(score > highScore){
            this.highScore = score;
        } else {
            this.highScore = highScore;
        }
        try{
            fw = new FileWriter(HIGH_SCORE_FILE);
            bw = new BufferedWriter(fw);
            bw.write(String.valueOf(this.highScore));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try{
                if(bw != null){
                    bw.close();
                }
                if(fw != null){
                    fw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
