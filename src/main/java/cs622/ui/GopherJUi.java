package cs622.ui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs622.db.DataStore;
import cs622.db.ResultRecord;
import cs622.document.Document;
import cs622.document.DocumentManager;
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
	private GopherJGenerator generator = GopherJGenerator.getInstance(false);

	// document manager
	private DocumentManager manager = new DocumentManager();

	// UI components
	private Button directoryButton;
	private Button fileButton;
	private Button copyButton;
	private Button generateButton;
	private Button saveOutputButton;
	private Button readOutputButton;
	private Button clearButton;
	private Button viewAuditButton;
	private TextField textField;
	private TextArea textArea;
	private Label messageLabel;

	// main method
	public static void main(String[] args) {
		launch(args);
	}

	// main UI method
	public void start(Stage mainStage) {

		// initialize the ui components
		directoryButton = new Button("Choose Directory");
		fileButton = new Button("Choose Json File");
		copyButton = new Button("Copy");
		generateButton = new Button("Generate");
		saveOutputButton = new Button("Save Output");
		readOutputButton = new Button("Read Output");
		clearButton = new Button("Clear");
		viewAuditButton = new Button("View Audit");
		textField = new TextField();
		textField.setMinWidth(400);
		textArea = new TextArea();
		messageLabel = new Label();

		// define actions for buttons
		directoryButton.setOnAction(event -> chooseDirectoryAction(mainStage));
		fileButton.setOnAction(event -> chooseFileAction(mainStage));
		copyButton.setOnAction(event -> copyAction());
		generateButton.setOnAction(event -> generateAction());
		saveOutputButton.setOnAction(event -> saveOutputResultAction(mainStage));
		readOutputButton.setOnAction(event -> readOutputResultAction(mainStage));
		viewAuditButton.setOnAction(event -> viewAuditAction(mainStage));

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
		topPane.getChildren().add(directoryButton);
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
		bottomPane.getChildren().add(viewAuditButton);
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

	// choose a directory for parsing
	private void chooseDirectoryAction(Stage mainStage) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Open Resource Directory");
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File fileSelected = chooser.showDialog(mainStage);
		if (fileSelected == null)
			return;
		textField.setText(fileSelected.getAbsolutePath());
		messageLabel.setText("Directory chosen");
		textArea.clear();
	}

	// choose a file for parsing
	private void chooseFileAction(Stage mainStage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File fileSelected = fileChooser.showOpenDialog(mainStage);
		if (fileSelected == null)
			return;
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
			List<String> results = manager.process(textField.getText());
			StringBuffer buffer = new StringBuffer();
			for (String result : results) {
				if (buffer.length() != 0) {
					buffer.append("\n\n###########\n\n");
				}
				buffer.append(result);
			}
			textArea.setText(buffer.toString());
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
		String jsonInput = manager.readContentsFromFile(textField.getText());
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
		} catch (Exception e) {
			messageLabel.setText("Failed to read results");
		}
	}

	// display to ui the audit results from DataSource
	private void viewAuditAction(Stage mainStage) {
		try {
			textArea.clear();
			// get the data store if it was created
			if (DataStore.getInstance() != null) {
				List<ResultRecord> records = DataStore.getInstance().fetchResultRecords();

				StringBuffer buf = new StringBuffer();
				// concatenate the audit records for display to ui
				for (ResultRecord resultRecord : records) {
					buf.append(resultRecord + "\n");
				}
				textArea.setText(buf.toString());
			}
			messageLabel.setText("Loaded audit results");
		} catch (Exception e) {
			messageLabel.setText("Failed to retrieve audit results");
		}
	}
}