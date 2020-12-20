function getValueFromEnvArgs(targetKeyName: string) {
    const keyNameLowerCase = targetKeyName.toLowerCase();

    if (process.env[targetKeyName]) {
        return process.env[targetKeyName];
    } else {
        for (let i = 0; i < process.argv.length; i++) {
            const [key, value] = process.argv[i].split('=').map((token) => token.replace(' ', ''));
            if (key.toLowerCase().replace('--', '') === keyNameLowerCase) {
                return value;
            }
        }
    }
}

export default getValueFromEnvArgs;
