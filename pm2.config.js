module.exports = {
    apps: [
        {
            name: 'A-Ing',
            script: 'npm run start:prod',
            env: {
                NODE_ENV: 'production'
            }
        }
    ]
};
