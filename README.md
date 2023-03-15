[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Coverage Status](https://coveralls.io/repos/github/creek-service/ks-aggregate-api-demo/badge.svg?branch=main)](https://coveralls.io/github/creek-service/ks-aggregate-api-demo?branch=main)
[![build](https://github.com/creek-service/ks-aggregate-api-demo/actions/workflows/build.yml/badge.svg)](https://github.com/creek-service/ks-aggregate-api-demo/actions/workflows/build.yml)
[![CodeQL](https://github.com/creek-service/ks-aggregate-api-demo/actions/workflows/codeql.yml/badge.svg)](https://github.com/creek-service/ks-aggregate-api-demo/actions/workflows/codeql.yml)

# Kafka Streams Aggregate API Tutorial

Repo containing the completed [Aggregate API tutorial](https://www.creekservice.org/ks-aggregate-api-demo)
and associated [docs](docs/README.md).

### Gradle commands

* `./gradlew` should be the go-to local command to run when developing.
              It will run `./gradlew format`, `./gradlew static` and `./gradlew check`.
* `./gradlew format` will format the code using [Spotless][spotless].
* `./gradlew static` will run static code analysis, i.e. [Spotbugs][spotbugs] and [Checkstyle][checkstyle].
* `./gradlew check` will run all checks and tests.
* `./gradlew coverage` will generate a cross-module [Jacoco][jacoco] coverage report.

[spotless]: https://github.com/diffplug/spotless
[spotbugs]: https://spotbugs.github.io/
[checkstyle]: https://checkstyle.sourceforge.io/
[jacoco]: https://www.jacoco.org/jacoco/trunk/doc/
