rootProject.name = "rendezvous"
include("rendezvous-core")
include("rendezvous-rest")
include("rendezvous-data")
include("rendezvous-infra")
include("rendezvous-util")

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.40.1"
}