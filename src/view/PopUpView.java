package view;

import controller.ConsoleController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.card.type.Color;

public class PopUpView {
	
	public void colorSelect(ConsoleController ctrl) {
		Stage stage = new Stage();
		stage.setTitle("Elija un Color:");
	    Button b1 = new Button("blue");       
	    Button b2 = new Button("red");       
	    Button b3 = new Button("yellow");       
	    Button b4 = new Button("green");
	    b1.setOnAction(ctrl);
	    b2.setOnAction(ctrl);
	    b3.setOnAction(ctrl);
	    b4.setOnAction(ctrl);
	    b1.setOnAction(e -> stage.close());
	    b2.setOnAction(e -> stage.close());
	    b3.setOnAction(e -> stage.close());
	    b4.setOnAction(e -> stage.close());
	    FlowPane flowPane = new FlowPane();     
	    flowPane.setHgap(25);  
	    flowPane.setMargin(b1, new Insets(20, 0, 20, 20));  
	    ObservableList list = flowPane.getChildren(); 
	    list.addAll(b1, b2, b3, b4); 
	    Scene scene = new Scene(flowPane);  
	    stage.setScene(scene); 
	    stage.show();  
	}

	public void WrongCard() {
		Stage stage = new Stage();
		stage.setTitle("Carta Equivocada");
		Button exit = new Button("Exit");
		exit.setOnAction(e -> stage.close());
		VBox layout = new VBox(10);
		layout.getChildren().add(exit);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		stage.setScene(scene);
		stage.show();
	}
}
