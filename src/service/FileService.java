package service;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.GameBoard;

/**
 * Service class responsible for importing and exporting GameBoard
 * objects as JSON files. This class encapsulates all file-related
 * operations to keep the controller clean and focused on application flow.
 *
 * @author Philipp Palm
 */
public class FileService {

    /** Jackson mapper used for JSON serialization and deserialization. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Opens a save dialog and writes the given GameBoard to a JSON file.
     * The file chooser filters for JSON files and automatically appends
     * the .json extension if the user omits it.
     *
     * @param board the game board to export
     */
    public void exportGameBoard(GameBoard board) {

        JFileChooser chooser = new JFileChooser(
            FileSystemView.getFileSystemView().getHomeDirectory()
        );
        chooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            try {
                mapper.writeValue(file, board);
            } catch (IOException  e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error while exporting the game board.",
                        "Export Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
        }
    }

    /**
     * Opens an open dialog and loads a GameBoard from a JSON file.
     * If loading fails or the user cancels the dialog, null is returned.
     *
     * @return the loaded GameBoard, or null if the import failed or was cancelled
     */
    public GameBoard importGameBoard() {

        JFileChooser chooser = new JFileChooser(
            FileSystemView.getFileSystemView().getHomeDirectory()
        );
        chooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            try {
                return mapper.readValue(file, GameBoard.class);
            } catch (IOException  e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error while importing the game board.",
                        "Import Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
        }

        return null;
    }
}