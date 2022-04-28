rootProject.name = "rendezvous"
include("rendezvous-core")
include("rendezvous-rest")
include("rendezvous-data")

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.40.1"
}