package at.fhhagenberg.sqe.project.model;

/**
 * Created by rknoll on 15/12/14.
 */
public class Floor {

    private int mFloorNumber;
    private String mDescription;

    private boolean mButtonUp;
    private boolean mButtonDown;

    public Floor(int floorNumber, String description) {
        mFloorNumber = floorNumber;
        mDescription = description;
    }

    public void setButtonUp(boolean buttonUp) {
        mButtonUp = buttonUp;
    }

    public void setButtonDown(boolean buttonDown) {
        mButtonDown = buttonDown;
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
