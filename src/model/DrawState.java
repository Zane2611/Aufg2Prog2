package model;

/**
 * Represents the current drawing state of the model.
 *
 * @author Philipp Palm
 */
public enum DrawState {

    /** No interaction is currently taking place. */
    IDLE,

    /** The user is actively drawing a new connection. */
    DRAWING,

    /** The user has clicked on a cell that belongs to an existing connection. */
    SELECTED,

    /** The last interaction was invalid and is being ignored. */
    INVALID
}