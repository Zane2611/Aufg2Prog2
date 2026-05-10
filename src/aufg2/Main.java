package aufg2;

import controller.Controller;

/**
 * Application entry point.
 * Initialises the controller, which in turn sets up
 * the model and view according to the MVC pattern.
 *
 * @author Philipp Palm
 */
public class Main {

    /**
     * Starts the application by creating a new Controller instance.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Controller();
    }
}