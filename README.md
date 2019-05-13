# cures-condec-bitbucket

[![Build Status](https://travis-ci.org/cures-hub/cures-condec-bitbucket.svg?branch=master)](https://travis-ci.org/cures-hub/cures-condec-bitbucket)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bd8ee189f9e7474e850c60fa81b50d11)](https://www.codacy.com/app/UHD/cures-condec-bitbucket?utm_source=github.com&utm_medium=referral&utm_content=cures-hub/cures-condec-bitbucket&utm_campaign=Badge_Grade)
[![Codecoverage](https://codecov.io/gh/cures-hub/cures-condec-bitbucket/branch/master/graph/badge.svg)](https://codecov.io/gh/cures-hub/cures-condec-bitbucket/branch/master)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)

The CURES ConDec Bitbucket plugin provides decision knowledge support for pull requests.
A pull request and its associated feature branch are related to one (or more) JIRA issues.
The ConDec Bitbucket plugin enables developers to view decision knowledge relevant for the JIRA issue(s) directly within the pull request.
This supports the developers in keeping the documented decision knowledge and the implementation of decisions consistent.
Further, the plugin checks and enforces the completeness of the documented decision knowledge prior to merging.
That is, the branch can only be merged back to mainline, if the decision knowledge has a certain quality.
For example, the developers responsible for implementing a task must have at least one issue (decision problem) and one decision documented (will be configurable by the rationale manager in the future).

## Installation

to support the release and commit standards install commitizen:
```
npm install commitizen -g
```
Install all npm modules:
```
npm install
```

## Commits and releases

after staging the files run:
```
git cz
```


to make a standard release:
```
npm run release
```

### Prerequisites

The following prerequisites are necessary to compile the plug-in from source code:

- Java 8 JDK
- [Atlassian SDK](https://developer.atlassian.com/docs/getting-started/set-up-the-atlassian-plugin-sdk-and-build-a-project)

### Compilation via Terminal

The source code is compiled via terminal.
Navigate into the cures-condec-jira folder and run the following command:

```
atlas-mvn package
```

The .jar file is created.

Run the plug-in locally via:

```
atlas-run
```

### Download of Precompiled .jar-File

The precompiled .jar-File for the latest release can be found here: https://github.com/cures-hub/cures-condec-bitbucket/releases/latest
