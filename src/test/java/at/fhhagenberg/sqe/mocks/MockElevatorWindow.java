package at.fhhagenberg.sqe.mocks;

import at.fhhagenberg.sqe.project.ui.IElevatorWindow;
import org.springframework.stereotype.Component;

/**
 * Created by rknoll on 08/01/15.
 */
@Component
public class MockElevatorWindow implements IElevatorWindow {
    private boolean mIsVisible;

    @Override
    public void setVisible(boolean visible) {
        mIsVisible = visible;
    }

    @Override
    public boolean isVisible() {
        return mIsVisible;
    }
}
