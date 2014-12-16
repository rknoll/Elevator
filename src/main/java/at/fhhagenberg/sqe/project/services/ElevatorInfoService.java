package at.fhhagenberg.sqe.project.services;

import at.fhhagenberg.sqe.project.connection.ElevatorConnectionLostException;
import at.fhhagenberg.sqe.project.connection.IElevatorAdapter;
import at.fhhagenberg.sqe.project.model.Elevator;
import at.fhhagenberg.sqe.project.model.Floor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rknoll on 16/12/14.
 */
public class ElevatorInfoService {

    private IElevatorAdapter mAdapter;
    private Set<IElevatorInfoListener> mListener;

    private UpdateThread mThread;

    public ElevatorInfoService(IElevatorAdapter adapter) {
        mAdapter = adapter;
        mListener = Collections.newSetFromMap(new ConcurrentHashMap<IElevatorInfoListener, Boolean>());
    }

    public void addListener(IElevatorInfoListener listener) {
        mListener.add(listener);
    }

    public void removeListener(IElevatorInfoListener listener) {
        mListener.remove(listener);
    }

    public void start() {
        if (mThread != null && mThread.isRunning()) return;
        mThread = new UpdateThread();
        mThread.start();
    }

    public void stop() {
        if (mThread == null || !mThread.isRunning()) return;
        mThread.stop();
        mThread = null;
    }

    private class UpdateThread implements Runnable {
        private boolean mRunning;

        public void start() {
            if (mRunning) return;
            mRunning = true;
            new Thread(this).start();
        }

        public void stop() {
            if (!mRunning) return;
            mRunning = false;
        }

        public boolean isRunning() {
            return mRunning;
        }

        @Override
        public void run() {
            while (mRunning) {

                try {
                    Set<Elevator> elevatorCache = new HashSet<Elevator>();
                    for (IElevatorInfoListener l : mListener) {
                        Elevator e = l.getElevator();

                        if (elevatorCache.contains(e)) {
                            l.update();
                            continue;
                        }

                        int elevatorNumber = e.getElevatorNumber();
                        HashMap<Integer, Floor> floors = new HashMap<Integer, Floor>();

                        for (Floor f : e.getFloors()) {
                            int floorNumber = f.getFloorNumber();
                            e.setButton(f, mAdapter.getElevatorButton(elevatorNumber, floorNumber));
                            e.setService(f, mAdapter.getServicesFloors(elevatorNumber, floorNumber));
                            floors.put(floorNumber, f);
                        }

                        e.setCurrentFloor(floors.get(mAdapter.getElevatorFloor(elevatorNumber)));
                        e.setAcceleration(mAdapter.getElevatorAccel(elevatorNumber));
                        e.setCapacity(mAdapter.getElevatorCapacity(elevatorNumber));
                        e.setDirection(mAdapter.getCommittedDirection(elevatorNumber));
                        e.setDoorStatus(mAdapter.getElevatorDoorStatus(elevatorNumber));
                        e.setPosition(mAdapter.getElevatorPosition(elevatorNumber));
                        e.setSpeed(mAdapter.getElevatorSpeed(elevatorNumber));
                        e.setWeight(mAdapter.getElevatorWeight(elevatorNumber));

                        l.update();

                        elevatorCache.add(e);
                    }
                } catch (ElevatorConnectionLostException ignored) {
                    mRunning = false;
                    break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

}
