#!/usr/bin/env bash

# shellcheck disable=SC2038

find . -name "*.iml"   | xargs rm -rf
find . -name ".idea"   | xargs rm -rf
find . -name "logs"    | xargs rm -rf

find . -name ".vscode"      | xargs rm -rf
find . -name ".project"     | xargs rm -rf
find . -name ".settings"    | xargs rm -rf
find . -name ".classpath"   | xargs rm -rf
find . -name ".factorypath" | xargs rm -rf
find . -name "bin"          | xargs rm -rf

mvn clean -Dmaven.test.skip=true install -U

