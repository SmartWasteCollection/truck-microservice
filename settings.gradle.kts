rootProject.name = "truck-microservice"
include("app")

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.0.12"
}

gitHooks {
    file(".git/hooks").mkdirs()
    commitMsg {
        conventionalCommits()
    }
    preCommit {
        tasks("ktlintCheck")
    }
    createHooks()
}
