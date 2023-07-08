package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;


public class HelloController {

    @FXML
    private Button Player1;
    @FXML
    private Button Player2;
    @FXML
    private Button Start_Game;
    @FXML
    private ImageView roller;
    @FXML
    private ImageView token_1;
    @FXML
    private ImageView token_2;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView exit;

    private Stage stage;
    private Scene scene;


    private Player p1;
    private Player p2;
    private int dice_result;
    private Board board;
    private Snake snake;
    private Ladder ladder;

    @FXML
    public void game_started(MouseEvent mouseEvent) {
        Start_Game.setText("Game Started");
        exit.setDisable(false);

        board=new Board();
        snake=new Snake();
        ladder=new Ladder();

        p1=new Player("Player1");
        p2=new Player("Player2");
        Player1.setDisable(false);
        Player2.setDisable(true);

        Start_Game.setDisable(true);
    }

    public void roll_dice(){
        File rol = new File("src/main/resources/com/example/demo/dice-roll.gif");
        roller.setImage(new Image(rol.toURI().toString()));

        dice_result = Randomnumber.Randomnumber();
        File file = new File("src/main/resources/com/example/demo/dice "+dice_result+".PNG");
        roller.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    public void Roll1(MouseEvent event) throws InterruptedException {
        Player1.setDisable(false);
        roll_dice();
        Player1.setDisable(true);
        Player2.setDisable(false);

        move_token1(event);
    }

    @FXML
    public void Roll2(MouseEvent event) throws InterruptedException {
        Player2.setDisable(false);
        roll_dice();
        Player2.setDisable(true);
        Player1.setDisable(false);

        move_token2(event);
    }

    public void winner(Player player){
        File file;
        File file1;

        if(player == p1){
            file = new File("src/main/resources/com/example/demo/p1.png");
            file1 = new File("src/main/resources/com/example/demo/win 2.gif");
        }
        else{
            file = new File("src/main/resources/com/example/demo/p2.png");
            file1 = new File("src/main/resources/com/example/demo/win 2.gif");
        }

        image1.setImage(new Image(file.toURI().toString()));
        image1.setDisable(false);

        image2.setImage(new Image(file1.toURI().toString()));
        image2.setDisable(false);

        DropShadow drop = new DropShadow();
        image1.setEffect(drop);
        image2.setEffect(drop);

        drop.setBlurType(BlurType.THREE_PASS_BOX);
        drop.setWidth(220.31);
        drop.setHeight(215.92);
        drop.setRadius(108.56);
        drop.setSpread(0.46);
    }

    public void move_token1(MouseEvent mouseEvent) throws InterruptedException {
        // if player1 is lock and got 6, he will unlock
        if((!p1.unlock_status()) && (dice_result==6)){
            p1.set_player_status();
            Player1.setDisable(false);
            Player2.setDisable(true);
            return;
        }

        if(p1.unlock_status()){
            p1.move_player(p1.get_position() + dice_result);

            if(p1.get_position()>=101){
                p1.move_player(p1.get_position() - dice_result);
            }
            else if(p1.get_position()==100){
                token_1.setLayoutX(board.get_x_coordinate(p1.get_position()-1));
                token_1.setLayoutY(board.get_y_coordinate(p1.get_position()-1));

                Player1.setDisable(true);
                Player2.setDisable(true);

                winner(p1);
            }
            else{
                token_1.setLayoutX(board.get_x_coordinate(p1.get_position()-1));
                token_1.setLayoutY(board.get_y_coordinate(p1.get_position()-1));

                int snk=snake.snake_bite(p1.get_position());
                if(snk != -1){
                    p1.move_player(snk);
                    token_1.setLayoutX(board.get_x_coordinate(p1.get_position()-1));
                    token_1.setLayoutY(board.get_y_coordinate(p1.get_position()-1));
                }

                int ladd=ladder.ladder_climb(p1.get_position());
                if(ladd != -1){
                    p1.move_player(ladd);
                    token_1.setLayoutX(board.get_x_coordinate(p1.get_position()-1));
                    token_1.setLayoutY(board.get_y_coordinate(p1.get_position()-1));
                }

                if(dice_result==6){
                    Player1.setDisable(false);
                    Player2.setDisable(true);
                }
            }
        }
    }

    public void move_token2(MouseEvent mouseEvent) throws InterruptedException {
        // if player2 is lock and got 6, he will unlock
        if((!p2.unlock_status()) && (dice_result==6)){
            p2.set_player_status();
            Player2.setDisable(false);
            Player1.setDisable(true);
            return;
        }

        if(p2.unlock_status()){
            p2.move_player(p2.get_position() + dice_result);

            if(p2.get_position()>=101){
                p2.move_player(p2.get_position() - dice_result);
            }
            else if(p2.get_position()==100){
                token_2.setLayoutX(board.get_x_coordinate(p2.get_position()-1));
                token_2.setLayoutY(board.get_y_coordinate(p2.get_position()-1));

                Player1.setDisable(true);
                Player2.setDisable(true);

                winner(p2);
            }
            else{
                token_2.setLayoutX(board.get_x_coordinate(p2.get_position()-1));
                token_2.setLayoutY(board.get_y_coordinate(p2.get_position()-1));

                int snk=snake.snake_bite(p2.get_position());
                if(snk != -1){
                    p2.move_player(snk);
                    token_2.setLayoutX(board.get_x_coordinate(p2.get_position()-1));
                    token_2.setLayoutY(board.get_y_coordinate(p2.get_position()-1));
                }

                int ladd=ladder.ladder_climb(p2.get_position());
                if(ladd != -1){
                    p2.move_player(ladd);
                    token_2.setLayoutX(board.get_x_coordinate(p2.get_position()-1));
                    token_2.setLayoutY(board.get_y_coordinate(p2.get_position()-1));
                }

                if(dice_result==6){
                    Player2.setDisable(false);
                    Player1.setDisable(true);
                }
            }
        }
    }

    public void exit_button(MouseEvent mouseEvent) throws IOException {
        Start_Game.setText("Start Game");
        File rol = new File("src/main/resources/com/example/demo/dice-roll.gif");
        roller.setImage(new Image(rol.toURI().toString()));

        Start_Game.setDisable(true);
        Player1.setDisable(true);
        Player2.setDisable(true);

        token_1.setLayoutX(13);
        token_1.setLayoutY(740);
        token_2.setLayoutX(13);
        token_2.setLayoutY(795);

        PerspectiveTransform per = new PerspectiveTransform();
        image1.setEffect(per);
        image2.setEffect(per);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Start_interface.fxml")));
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


class Randomnumber {
    public static int Randomnumber(){
        Random rand = new Random();
        int code1 = rand.nextInt(6)+1;
        return code1;
    }
}


class Board{
    private int[] coordinate_xaxis;
    private int[] coordinate_yaxis;
    private int size;
    public Board(){
        this.size=100;
        coordinate_xaxis=new int[size];
        coordinate_yaxis=new int[size];

        int row_num=0;
        boolean row_it=true;
        int col_num=777;
        int diff=80;

        int ind=0;
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(row_it){
                    row_num+=diff;
                }
                else{
                    row_num-=diff;
                }
                coordinate_xaxis[ind]=row_num;
                coordinate_yaxis[ind]=col_num;

                ind++;
            }
            if(row_it){
                row_it=false;
                row_num+=diff;
            }
            else{
                row_it=true;
                row_num-=diff;
            }
            col_num-=diff;
        }
    }
    public int get_x_coordinate(int x){
        return coordinate_xaxis[x];
    }
    public int get_y_coordinate(int y){
        return coordinate_yaxis[y];
    }
}


class Player {
    private String name;
    private int position;
    private boolean player_status;
    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.player_status=false;
    }
    public String get_name() {
        return name;
    }
    public int get_position() { return position; }
    public void move_player(int reach) {
        position = reach;
    }
    public boolean unlock_status(){
        return player_status;
    }
    public void set_player_status(){
        this.player_status=true;
    }
}


class Snake{
    private int[] snake_head;
    private int[] snake_tail;
    private int num_of_snakes;
    public Snake(){
        this.num_of_snakes=5;
        this.snake_head= new int[]{17, 64, 89, 95, 99};
        this.snake_tail= new int[]{7,60,26,75,78};
    }
    public int snake_bite(int player_pos){
        for(int i=0;i<num_of_snakes;i++){
            if(player_pos==snake_head[i]) {
                return snake_tail[i];
            }
        }
        return -1;
    }
}


class Ladder{
    private int[] ladder_start;
    private int[] ladder_end;
    private int num_of_ladders;
    public Ladder(){
        this.num_of_ladders=7;
        this.ladder_start= new int[]{4, 9, 20, 28, 40, 51, 63};
        this.ladder_end= new int[]{14, 31, 38, 84, 59, 67, 81};
    }
    public int ladder_climb(int player_pos){
        for(int i=0;i<num_of_ladders;i++){
            if(player_pos==ladder_start[i]) {
                return ladder_end[i];
            }
        }
        return -1;
    }
}
