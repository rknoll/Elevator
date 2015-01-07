package at.fhhagenberg.sqe.project.model;

/**
 * A representation for a Floor
 */
public class Floor extends ListenableModel {

    /**
     * Property Name for the Button Up State
     */
    public static final String PROP_BUTTON_UP = "buttonUp";
    /**
     * Property Name for the Button Down State
     */
    public static final String PROP_BUTTON_DOWN = "buttonDown";

    /**
     * The Floor Number
     */
    private final int mFloorNumber;
    /**
     * The human readable Description
     */
    private final String mDescription;
    /**
     * The human readable short Description
     */
    private final String mShortDescription;
    /**
     * The Button Up State
     */
    private boolean mButtonUp;
    /**
     * The Button Down State
     */
    private boolean mButtonDown;

    /**
     * Constructor for a Floor, identified by it's Floor Number and a Human readable representation
     *
     * @param floorNumber      The Floor Number
     * @param description      The Human readable representation, e.g. "Lobby" or "Floor 2"
     * @param shortDescription A short representation to be used inside Elevators, e.g. "L" or "2"
     */
    public Floor(int floorNumber, String description, String shortDescription) {
        mFloorNumber = floorNumber;
        mDescription = description;
        mShortDescription = shortDescription;
    }

    /**
     * Set the new Button Up State.
     *
     * @param buttonUp The new Button Up State to be set
     */
    public void setButtonUp(boolean buttonUp) {
        boolean oldValue = mButtonUp;
        mButtonUp = buttonUp;
        pcs.firePropertyChange(PROP_BUTTON_UP, oldValue, mButtonUp);
    }

    /**
     * Set the new Button Down State.
     *
     * @param buttonDown The new Button Down State to be set
     */
    public void setButtonDown(boolean buttonDown) {
        boolean oldValue = mButtonDown;
        mButtonDown = buttonDown;
        pcs.firePropertyChange(PROP_BUTTON_DOWN, oldValue, mButtonDown);
    }

    /**
     * Gets the Button Up State.
     *
     * @return The Button Up State
     */
    public boolean isButtonUp() {
        return mButtonUp;
    }

    /**
     * Gets the Button Down State.
     *
     * @return The Button Down State
     */
    public boolean isButtonDown() {
        return mButtonDown;
    }

    /**
     * Gets the Floor Number.
     *
     * @return The Floor Number
     */
    public int getFloorNumber() {
        return mFloorNumber;
    }

    /**
     * Gets the Human Readable Description of this Floor.
     *
     * @return The Human Readable Description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Gets the Human Readable Short Description of this Floor.
     *
     * @return The Human Readable Short Description
     */
    public String getShortDescription() {
        return mShortDescription;
    }
}
