package cs622.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Javafx1 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage mainStage) {
		// StackPane pane = new StackPane();
		// Button firstButton = new Button("First Button");
		// pane.getChildren().add(firstButton);
		// Scene scene = new Scene(pane, 200, 100);
		// mainStage.setTitle("One button in a pane");
		// mainStage.setScene(scene);
		// mainStage.show();

		StackPane pane = new StackPane();
		//
		// Circle circ = new Circle();
		// circ.setRadius(70);
		// circ.setStroke(Color.GREEN);
		// circ.setFill(new Color(0.7, 0.7, 0.7, 0.2));
		// pane.getChildren().add(circ);
		//
		// Label la = new Label("My Circle");
		// la.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 10));
		// pane.getChildren().add(la);
		//

		TextArea textArea = new TextArea();

		VBox vbox = new VBox(textArea);

		Scene scene = new Scene(vbox, 200, 100);
		mainStage.setScene(scene);
		mainStage.show();

		// Scene scene = new Scene(pane);
		// mainStage.setTitle("Font & Color");
		// mainStage.setScene(scene);
		// mainStage.show();

		// file chooser
		// FileChooser fileChooser = new FileChooser();
		// fileChooser.setTitle("Open Resource File");
		// fileChooser.showOpenDialog(mainStage);
	}
}