package controller;

import model.Connection;
import model.GameBoard;
import model.Model;
import service.FileService;
import view.View;

/**
 * Main controller coordinating interactions between the model,
 * the view and file operations handled by the FileService.
 * Delegates JSON import and export to the FileService
 * to keep the MVC structure clean and focused.
 *
 * @author Philipp Palm
 */
public class Controller {

    /** The central application model. */
    private final Model model;

    /** The graphical user interface. */
    private final View view;

    /** Service responsible for JSON import and export. */
    private final FileService fileService;

    /**
     * Creates a new controller and initialises the model, view and file service.
     */
    public Controller() {
        this.model = new Model();
        this.view = new View(model, this);
        this.fileService = new FileService();
    }

   
    /**
     * Exports the current game board to a JSON file via the FileService.
     */
    public void exportGame() {
        fileService.exportGameBoard(model.getGameBoard());
    }

    /**
     * Imports a game board from a JSON file via the FileService.
     * If a valid board is loaded, the model is updated, all connection
     * colors are reset to the first color, and the view is rebuilt.
     */
    public void importGame() {
        GameBoard newBoard = fileService.importGameBoard();

        if (newBoard != null) {
            model.setGameBoard(newBoard);
            Connection.resetColorIndex();
            view.createViewForGameBoard();
            view.updateView();
        }
    }
}