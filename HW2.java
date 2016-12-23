/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Nathen
 */
public class HW2 extends Application {
    
    BorderPane layout;
    GridPane grid, popGrid;
    Stage stage, secStage;
    MenuBar menu;
    Menu game;
    MenuItem itmNew, itmExit;
    Label lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
    ComboBox com;
    Button btn, btn2, btn3;
    TextField textBox;
    int n, ran;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        //Creates Menu Bar
        menu = new MenuBar();
        
        //Creates Menu
        game = new Menu("Game");
        
        //Creates Menu Items
        itmNew = new MenuItem("New     ");
        itmExit = new MenuItem("Exit");
        
        //Adds Items to menu and adds menu to menu bar
        game.getItems().addAll(itmNew, itmExit);
        menu.getMenus().add(game);
        
        //Sets action for menu items
        itmNew.setOnAction(e -> handleMenuItemNew(e));
        itmExit.setOnAction(e -> handleMenuItemExit(e));
        
        layout = new BorderPane();
        layout.setTop(menu);
        
        //Sets Window
        stage.setTitle("Guess the number!");
        Scene scene = new Scene(layout, 650, 400);
        
        //Popup window
        secStage = new Stage();
        secStage.setResizable(false);

        stage.setScene(scene);
        stage.show();
        
    }
    
    //Starts new game
    private void handleMenuItemNew(ActionEvent e) {
        setup();
    }
    
    //Generates layout when new game is started
    public void setup(){
        n = 0;
        
        grid = new GridPane();
        layout.setCenter(grid);
        
        grid.setHgap(10);
        grid.setVgap(10);
        
        
        lbl1 = new Label("Select the range from 0 to");
        lbl2 = new Label("Enter your guess here:");
        lbl2.setDisable(true);
        lbl3 = new Label("You tried " + n + " times");
        lbl3.setDisable(true);
        lbl4 = new Label("");
        GridPane.setColumnSpan(lbl4, 2);
        
        btn = new Button("   Enter   ");
        btn.setDisable(true);
        btn.setOnAction(e1 -> handleEnter(e1));
        btn.setDefaultButton(true);
        textBox = new TextField("");
        textBox.setPrefWidth(4);
        textBox.setDisable(true);
        com = new ComboBox();
        com.getItems().addAll("10", "100", "1000");
        com.setOnAction(e2 -> handleCombo((ActionEvent) e2));
        
        grid.setAlignment(Pos.CENTER);
        grid.add(lbl1, 0, 0);
        grid.add(lbl2, 0, 1);
        grid.add(lbl3, 0, 2);
        grid.add(lbl4, 0, 3);
        grid.add(com, 1, 0);
        grid.add(textBox, 1, 1);
        grid.add(btn, 1, 2);
    }
    
    //Closes Program
    private void handleMenuItemExit(ActionEvent e) { 
        Platform.exit();
    }
    
    //Disables combobox and enables text field and button
    private void handleCombo(ActionEvent e) {
        lbl1.setDisable(true);
        lbl2.setDisable(false);
        lbl3.setDisable(false);
        com.setDisable(true);
        textBox.setDisable(false);
        btn.setDisable(false);
        
        String str;
        str = com.getValue().toString();
        int i = Integer.parseInt(str);
        ran = (int)(Math.random()*(i))+1;
        
    }
    
    //Starts new game and closes popup
    private void handleButtonYes(ActionEvent e) { 
        secStage.close();
        setup();
    }
    
    //Close popup and ends program
    private void handleButtonNo(ActionEvent e) { 
       secStage.close();
       Platform.exit();
    }        
    
    //Action when guess is made
    private void handleEnter(ActionEvent e) {
        int i = 0;
        Boolean flag = false;
        String str = textBox.getText();
        
        //If statement checks if input is valid
        if(!str.matches("[0-9]*")){
          lbl4.setText("The number you entered is not a number");  
        }else{
          i = Integer.parseInt(str);
          flag = true;
          n++;
          lbl3.setText("You tried " + n + " times");
        }
        
        //Outputs of valid inputs. If value is random number then
        //Pop up window is generated
        if(flag == true && i < ran){
            lbl4.setText("The number you entered is too low");
        }else if(flag == true && i > ran){
            lbl4.setText("The number you entered is too high");
        }else if(flag == true && i == ran){
            secStage.setTitle("Congratulations!");
            popGrid = new GridPane();
            popGrid.setHgap(20);
            popGrid.setVgap(20);
            btn2 = new Button("     Yes     ");
            btn2.setOnAction(e1 -> handleButtonYes(e1));
            btn3 = new Button("     No     ");
            btn3.setOnAction(e1 -> handleButtonNo(e1));
            lbl5 = new Label("Congratulations! You guessed the number using "+n+" tries.");
            lbl6 = new Label("Do you want to start the new game?");
            popGrid.setAlignment(Pos.CENTER);
            popGrid.add(lbl5, 0, 0);
            popGrid.add(lbl6, 0, 1);
            popGrid.add(btn2, 11, 2);
            popGrid.add(btn3, 12, 2);
            GridPane.setColumnSpan(lbl5, 13);
            GridPane.setColumnSpan(lbl6, 13);
            Scene scene2 = new Scene(popGrid, 500, 200);
            secStage.setScene(scene2);
            secStage.showAndWait();
        }        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
