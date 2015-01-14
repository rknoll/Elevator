package at.fhhagenberg.sqe.mocks;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.services.IService;
import org.springframework.stereotype.Component;

/**
 * Created by rknoll on 14/01/15.
 */
@Component
public class DummyService implements IService {

    private int mRefreshed;
    private boolean mFireException;

    public int getRefreshCount() {
        return mRefreshed;
    }

    public void setRefreshCount(int refreshCount) {
        mRefreshed = refreshCount;
    }

    public void setFireException() {
        mFireException = true;
    }

    @Override
    public void refresh() throws ElevatorConnectionLostException {
        ++mRefreshed;
        if (mFireException) throw new ElevatorConnectionLostException();
    }
}
