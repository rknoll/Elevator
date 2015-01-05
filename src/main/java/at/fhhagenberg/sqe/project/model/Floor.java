package at.fhhagenberg.sqe.project.model;

/**
 * A representation for a Floor
 */
public class Floor extends ListenableModel {
    /* All possible Properties */
    public static final String PROP_BUTTON_UP = "buttonUp";
    public static final String PROP_BUTTON_DOWN = "buttonDown";

    /* Constant Class Members */
    private final int mFloorNumber;
    private final String mDescription;

    /* Dynamic Members */
    private boolean mButtonUp;
    private boolean mButtonDown;

    /**
     * Constructor for a Floor, identified by it's Floor Number and a Human readable representation
     *
     * @param floorNumber The Floor Number
     * @param description The Human readable representation, e.g. "Lobby"
     */
    public Floor(int floorNumber, String description) {
        mFloorNumber = floorNumber;
        mDescription = description;
    }

    public void setButtonUp(boolean buttonUp) {
        boolean oldValue = mButtonUp;
        mButtonUp = buttonUp;
        pcs.firePropertyChange(PROP_BUTTON_UP, oldValue, mButtonUp);
    }

    public void setButtonDown(boolean buttonDown) {
        boolean oldValue = mButtonDown;
        mButtonDown = buttonDown;
        pcs.firePropertyChange(PROP_BUTTON_DOWN, oldValue, mButtonDown);
    }

    public boolean isButtonUp() {
        return mButtonUp;
    }

    public boolean isButtonDown() {
        return mButtonDown;
    }

    public int getFloorNumber() {
        return mFloorNumber;
    }

    public String getDescription() {
        return mDescription;
    }
}
