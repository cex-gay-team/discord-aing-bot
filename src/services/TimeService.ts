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
        this.timers[username][timerName] = setTimeout(callback, timeout * SECOND);
    }

    clearTimer(username: string, timerName = 'default') {
        !this.timers[username] && (this.timers[username] = {});
        const targetTimer = this.timers[username][timerName];
        targetTimer && clearTimeout(targetTimer);
    }
}();
