# cures-condec-bitbucket

[![Continuous integration](https://github.com/cures-hub/cures-condec-bitbucket/actions/workflows/maven.yml/badge.svg)](https://github.com/cures-hub/cures-condec-bitbucket/actions/workflows/maven.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c0de598008234d36ab296260848a2701)](https://www.codacy.com/gh/cures-hub/cures-condec-bitbucket/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cures-hub/cures-condec-bitbucket&amp;utm_campaign=Badge_Grade)
[![Codecoverage](https://codecov.io/gh/cures-hub/cures-condec-bitbucket/branch/master/graph/badge.svg)](https://codecov.io/gh/cures-hub/cures-condec-bitbucket/branch/master)
[![GitHub contributors](https://img.shields.io/github/contributors/cures-hub/cures-condec-bitbucket.svg)](https://github.com/cures-hub/cures-condec-bitbucket/graphs/contributors)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)

The ConDec Bitbucket plugin provides decision knowledge support for pull requests.
A pull request and its associated feature branch are related to one (or more) Jira issues.
The ConDec Bitbucket plugin enables developers to view decision knowledge relevant for the Jira issue(s) directly within the pull request.
This supports the developers in keeping the documented decision knowledge and the implementation of decisions consistent.
Further, the plugin checks and enforces the completeness of the documented decision knowledge prior to merging.
That is, the branch can only be merged back to mainline, if the decision knowledge has a certain quality.
For example, the developers responsible for implementing a task must have at least one issue (decision problem) and one decision documented (will be configurable by the rationale manager in the future).

## Installation

### Prerequisites

The following prerequisites are necessary to compile the plug-in from source code:

- Java 11 JDK
- [Atlassian SDK](https://developer.atlassian.com/docs/getting-started/set-up-the-atlassian-plugin-sdk-and-build-a-project)

### Compilation via Terminal

The source code is compiled via terminal.
Navigate into the cures-condec-bitbucket folder and run the following command:

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

## Usage

### Application Link between Bitbucket and Jira
To share decision knowledge between Jira and Bitbucket, they need to be linked via an [application link](https://confluence.atlassian.com/adminjiraserver/using-applinks-to-link-to-other-applications-938846918.html).
The authentication type needs to be **OAuth (impersonation)**.

### Pull Requests

The ConDec Bitbucket plugin integrates in the pull request page.
The merge check can be enabled for single repositories.
![Configuration of the merge check](https://github.com/cures-hub/cures-condec-bitbucket/raw/master/doc/repository_settings.png)
*Configuration possibility of the decision knowledge completeness merge check in the repository settings* 

The branch can only be merged if at least one decision problem and one decision is documented for every associated Jira issue.

![Bitbucket ConDec plug-in](https://github.com/cures-hub/cures-condec-bitbucket/raw/master/doc/merge_check_tooltip.png)
*Pull request view with disabled merge possibility* 

![Bitbucket ConDec plug-in](https://github.com/cures-hub/cures-condec-bitbucket/raw/master/doc/merge_check_detail.png)
*Pull request view with detailed documentation hint* 

## Contribution

### General Development Setup
Install all npm modules:

```
npm install
```

#### Commits and Releases

to support the release and commit standards you may install commitizen globally:

```
npm install commitizen -g
```
and after staging the files run:

```
git cz
```

or use it locally via:

```
npm run commit
```


to make a standard release:

```
npm run release
```

#### Setup of Coding Styling

##### esLint
if npm install doesn't show you the esLint errors try the following:

first install eslint to get linting errors shown globally

```
npm install eslint -g
```

then:

```
eslint --init
```

which will create a .eslintrc file
now some code should be appear with errors like unused variables etc
eventually some Eslint plugin has to be installed for your editor of choice

##### Prettier
Next we want the code to autoformat on save.
For this we use Prettier, but with the rules from esLint and the defined rules from the .prettierc file.
This step depends on the editor but for visual studio code you don't need to install the prettier plugin.
just use the following settings:

```
{
    "editor.formatOnSave": true,
    "[javascript]": {
        "editor.formatOnSave": false,
    },
    "eslint.autoFixOnSave": true,
    "eslint.validate": [
        "javascript"
    ],
}
```

This ensures that javascript files are formated with esLint and not with the editor.

#### PreCommit Hook
Husky should ensure that only correctly linted code will be commited and pushed.
This also works when commiting with commitizen.
The pre-commit hook does only throw an error if one exists.
To display potential errors run:

```
npm run lint:js
```

and to fix them:

```
npm run lint:fix
```

The fixed files have to be staged again before commiting.