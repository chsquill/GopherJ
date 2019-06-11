package cs622.ui;

import java.io.File;
import java.io.IOException;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.generator.GopherJGenerator;
import cs622.generator.Result;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * GopherJ user interface.
 * 
 * Provides basic functionality to select a json file, generate code and print
 * the output to the UI.
 * 
 */
public class GopherJUi extends Application {

	// generator for code generation
	private GopherJGenerator generator = new GopherJGenerator();

	// UI components
	private Button fileButton;
	private Button copyButton;
	private Button generateButton;
	private Button saveOutputButton;
	private Button readOutputButton;
	private Button clearButton;
	private TextField textField;
	private TextArea textArea;
	private Label messageLabel;

	// main method
	public static void main(String[] args) {
		launch(args);
	}

	// main UI method
	public void start(Stage mainStage) {

		// create generator
		generator = new GopherJGenerator();
		generator.setWriteOutputToDisk(false);

		// initialize the ui components
		fileButton = new Button("Choose Json File");
		copyButton = new Button("Copy");
		generateButton = new Button("Generate");
		saveOutputButton = new Button("Save Output");
		readOutputButton = new Button("Read Output");
		clearButton = new Button("Clear");
		textField = new TextField();
		textField.setMinWidth(400);
		textArea = new TextArea();
		messageLabel = new Label();

		// define actions for buttons
		fileButton.setOnAction(event -> chooseFileAction(mainStage));
		copyButton.setOnAction(event -> copyAction());
		generateButton.setOnAction(event -> generateAction());
		saveOutputButton.setOnAction(event -> saveOutputResultAction(mainStage));
		readOutputButton.setOnAction(event -> readOutputResultAction(mainStage));

		// clear text fields / areas
		clearButton.setOnAction(event -> {
			textArea.clear();
			textField.clear();
			messageLabel.setText("");
		});

		// layout for the UI

		// top layout
		FlowPane topPane = new FlowPane();
		topPane.setHgap(10);
		topPane.setPadding(new Insets(10));
		topPane.getChildren().add(fileButton);
		topPane.getChildren().add(textField);
		topPane.getChildren().add(generateButton);
		topPane.getChildren().add(clearButton);

		// bottom layout
		FlowPane bottomPane = new FlowPane();
		bottomPane.setPadding(new Insets(10));
		bottomPane.setHgap(10);
		bottomPane.getChildren().add(copyButton);
		bottomPane.getChildren().add(saveOutputButton);
		bottomPane.getChildren().add(readOutputButton);
		bottomPane.getChildren().add(messageLabel);

		// main scene layout
		BorderPane pane = new BorderPane();
		pane.setTop(topPane);
		pane.setCenter(textArea);
		pane.setBottom(bottomPane);

		// main scene
		Scene scene = new Scene(pane, 800, 500);
		mainStage.setScene(scene);
		mainStage.setTitle("GopherJ Code Generator");
		mainStage.show();
	}

	// choose a file for parsing
	private void chooseFileAction(Stage mainStage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File fileSelected = fileChooser.showOpenDialog(mainStage);
		textField.setText(fileSelected.getAbsolutePath());
		messageLabel.setText("File chosen");
		textArea.clear();
	}

	// copy action to copy output to clip-board
	private void copyAction() {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(textArea.getText());
		clipboard.setContent(content);
		messageLabel.setText("Copied");
	}

	// generate code or display error to UI
	private void generateAction() {
		try {
			Document doc = new JsonDocument();
			doc.readInputFromFile(textField.getText());
			textArea.setText(generator.generate(doc));
			messageLabel.setText("Code Generated");
		} catch (Exception e) {
			messageLabel.setText("Error generating code");
			textArea.setText(e.getMessage());
		}
	}

	// save the output to a Result displaying the saved file path and
	// saved in a directory chosen by user
	private void saveOutputResultAction(Stage mainStage) {
		Document doc = new JsonDocument();
		String jsonInput = doc.readContentsFromFile(textField.getText());
		if (doc.validInput(jsonInput) && !textArea.getText().isEmpty()) {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Save Result Directory");
			File directorySelected = directoryChooser.showDialog(mainStage);
			try {
				String resultPath = generator.storeParseResult(jsonInput, textArea.getText(),
						String.format("%s%sgopherj_%s.dat", directorySelected.getAbsolutePath(), File.separator,
								System.currentTimeMillis()));
				messageLabel.setText("Result saved to: " + resultPath);
			} catch (IOException e) {
				messageLabel.setText("Faild to save results");
			}

		} else {
			messageLabel.setText("Faild to save results");
		}
	}

	// display to ui the input and output of a saved Result
	private void readOutputResultAction(Stage mainStage) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Result File");
			File fileSelected = fileChooser.showOpenDialog(mainStage);
			Result resultPath = generator.readParseResult(fileSelected.getAbsolutePath());
			textArea.clear();
			textArea.setText("Input\n-------\n\n" + resultPath.getDocumentInput() + "\n\nOutput\n-------\n\n"
					+ resultPath.getJavaOutput());
			messageLabel.setText("Loaded results");
		} catch (ClassNotFoundException e) {
			messageLabel.setText("Failed to find valid object");
		} catch (IOException e) {
			messageLabel.setText("Failed to read results");
		}
	}
}