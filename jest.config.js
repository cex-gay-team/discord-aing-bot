const tsconfig = require('./tsconfig.json');

// [workaround] node_modules 를 위한 * 경로 alias 가 jest 실행시 문제를 발생시킨다.
delete tsconfig.compilerOptions.paths['*'];
const moduleNameMapper = require('tsconfig-paths-jest')(tsconfig);

module.exports = {
    preset: 'ts-jest',
    testRegex: '.*spec.ts$',
    rootDir: './src',
    testEnvironment: 'node',
    moduleDirectories: ['node_modules', 'src'],
    moduleFileExtensions: ['ts', 'js'],
    moduleNameMapper
};
