type TimerInfoParams = {
    username: string;
    timeout: number;
    timerName?: string;
}

type Timer = {
    createdAt: number;
    targetTime: number;
    timeout: NodeJS.Timeout;
}

type TimerMap = {[timerName: string]: Timer};

const SECOND = 1000;

export default new class TimeService {
    private timers: {[username: string]: TimerMap} = {};

    addTimer(info: TimerInfoParams, callback: () => void) {
        const {
            username, timeout, timerName = 'default'
        } = info;

        !this.timers[username] && (this.timers[username] = {});

        this.clearTimer(username, timerName);
        const targetTime = timeout * SECOND;
        this.timers[username][timerName] = {
            createdAt: Date.now(),
            targetTime,
            timeout: setTimeout(callback, targetTime)
        };
    }

    /**
     * @return 타이머가 존재했는지에 대한 여부
     */
    clearTimer(username: string, timerName = 'default'): boolean {
        !this.timers[username] && (this.timers[username] = {});
        const targetTimer = this.timers[username][timerName];

        if (targetTimer) {
            clearTimeout(targetTimer.timeout);
            delete this.timers[username][timerName];
            return true;
        } else {
            return false;
        }
    }

    getTimerList(username: string): [string, number][] {
        const timerMap = this.timers[username];

        if (!timerMap) {
            return [];
        }

        const currentTime = Date.now();
        return Object.entries(timerMap).map(([timerName, timer]) => {
            return [timerName, Math.round((timer.targetTime - (currentTime - timer.createdAt))/SECOND)];
        });
    }
}();
