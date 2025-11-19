# TornadoFX v2 (Gradle)

A JavaFX framework for Kotlin (Java 17+)

This (experimental) branch is gradle based.
Current state: TODO

* Kotlin target is now 1.7.20 & Java target is Java 17
* JavaFX version is 22
* JUnit5 with additional vintage engine for unit testing
* Tests running on classpath ignoring javas module system 
* no support for OSGI bundle

## Features

* Supports both MVC, MVP and their derivatives
* Dependency injection
* Type safe GUI builders
* Type safe CSS builders
* First class FXML support
* Async task execution
* EventBus with thread targeting
* Hot reload of Views and Stylesheets
* Zero config, no XML, no annotations

## Requirements

* JDK 17+
* [Kotlin 1.7+](https://kotlinlang.org/)
* [JavaFX 22+](https://openjfx.io/)

## Installation

In repositories block add

```
maven {
    url = uri("https://oss.sonatype.org/content/repositories/snapshots")
}
```

In dependencies block add
```
implementation("no.tornado:tornadofx:2.0.0-SNAPSHOT")
```

## Feature Requests and Bugs

Please use the GitHub [issues](https://github.com/edvin/tornadofx2/issues) for all feature requests and bugs.

## Maintainers

* [Edvin Syse](https://github.com/edvin)
* [GoToTags](https://gototags.com/) / [Craig Tadlock](https://www.linkedin.com/in/ctadlock/)
