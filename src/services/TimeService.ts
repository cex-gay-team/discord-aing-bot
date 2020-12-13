type TimerInfo = {
    username: string;
    timeout: number;
    timerName?: string;
}

const SECOND = 1000;

export default new class TimeService {
    private timers: {[username: string]: {[timerName: string]: NodeJS.Timeout }} = {};

    addTimer(info: TimerInfo, callback: () => void) {
        const {
            username, timeout, timerName = 'default'
        } = info;

        !this.timers[username] && (this.timers[username] = {});
        this.clearTimer(username, timerName);
        this.timers[username][timerName] = setTimeout(callback, timeout * SECOND);
    }

    /**
     * @return 타이머가 존재했는지에 대한 여부
     */
    clearTimer(username: string, timerName = 'default'): boolean {
        !this.timers[username] && (this.timers[username] = {});
        const targetTimer = this.timers[username][timerName];

        if (targetTimer) {
            clearTimeout(targetTimer);
            delete this.timers[username][timerName];
            return true;
        } else {
            return false;
        }
    }
}();
