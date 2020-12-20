function parseDateByHMS(args: string[]): number {
    const fullString = args.join();

    // 첫번째 그룹의 매칭결과와 같지 않으면 0
    const hour = parseInt((fullString.match(/(\d{1,2})[h시]/) || [])[1]) || 0;
    const minute = parseInt((fullString.match(/(\d{1,2})[m분]/) || [])[1]) || 0;
    const second = parseInt((fullString.match(/(\d{1,2})[s초]/) || [])[1]) || 0;

    return hour * 3600 + minute * 60 + second;
}

function parseDateSplitColon(args: string[]): number {
    const targetString = args[0];

    if (targetString.match(/^[:\d]+$/)) {
        const numbers = (targetString.match(/:?\d{1,2}/g) || []).map((target) => target.replace(':', ''));

        const second = parseInt(numbers.pop() || '') || 0;
        const minute = parseInt(numbers.pop() || '') || 0;
        const hour = parseInt(numbers.pop() || '') || 0;

        return hour * 3600 + minute * 60 + second;
    }
    return 0;
}

/**
 * 타이머 입력은 세가지 방식을 따른다.
 *
 * 1. 초만 입력하기
 * 2. 1h, 2m, 3s 와 같이 세가지의 값을 입력하기 (어떤 조합도 가능하며, 무조건 순서는 hms 순이다.)
 * 3. hh:mm:ss 로 입력하기
 *
 * @param args 위 셋중 하나
 * @throws InvalidFormatError
 * @return {number} seconds
 * @private
 */
export default function formatDate(args: string[]): number {
    const maybeSeconds = args[0].match(/^\d+$/)?.[0];
    if (maybeSeconds && Number.isInteger(parseInt(maybeSeconds))) {
        return parseInt(maybeSeconds);
    }

    const colonSplitSeconds = parseDateSplitColon(args);
    if (colonSplitSeconds !== 0) {
        return colonSplitSeconds;
    }

    const messageSplitSeconds = parseDateByHMS(args);
    if (messageSplitSeconds !== 0) {
        return messageSplitSeconds;
    }

    throw new Error('포맷이 안맞아용');
}
