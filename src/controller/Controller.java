package controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.GameBoard;
import model.Model;
import view.View;

public class Controller {

    private Model model;
    private View view;

    public Controller() {
        this.model = new Model();
        this.view = new View(model,this);
    }

    public void exportGame() {

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
                ObjectMapper mapper = new ObjectMapper();

                // nur GameBoard wird gespeichert (laut Aufgabe korrekt)
                mapper.writeValue(file, model.gameBoard);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void importGame() {

        JFileChooser chooser = new JFileChooser(
            FileSystemView.getFileSystemView().getHomeDirectory()
        );

        chooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            try {
                ObjectMapper mapper = new ObjectMapper();

                GameBoard newBoard = mapper.readValue(file, GameBoard.class);

                model.setGameBoard(newBoard);

                view.createViewForGameBoard();
                view.updateView();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Controller controller = new Controller();

      
    }
}