package com.canknow.cbp.base.threading.timers;

import com.canknow.cbp.base.exceptions.CbpException;
import com.canknow.cbp.base.threading.RunnableBase;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
@Data
public class CanknowTimer extends RunnableBase {
    private int period;
    public TimerTask elapsed;
    private Timer _taskTimer;
    private boolean runOnStart;
    private volatile boolean _performingTasks;

    public CanknowTimer() {
        _taskTimer = new Timer();
    }

    @Override
    public void start() {
        if (period <= 0) {
            throw new CbpException("Period should be set before starting the timer!");
        }
        super.start();

        _taskTimer.schedule(new TimerCallBackTask(), runOnStart ? 0 : period);
    }

    @Override
    public void stop() {
        _taskTimer.cancel();
        super.stop();
    }

    @Override
    public void waitToStop() {
        // todo
        super.waitToStop();
    }

    private class TimerCallBackTask extends TimerTask {

        @Override
        public void run() {
            if (!getIsRunning() || _performingTasks) {
                return;
            }
            _performingTasks = true;

            try {
                if (elapsed != null) {
                    elapsed.run();
                }
            }
            catch (Exception ignored){

            }
            finally {
                _performingTasks = false;

                if (getIsRunning()) {
                    _taskTimer.schedule(new TimerCallBackTask(), period);
                }
            }
        }
    }
}
