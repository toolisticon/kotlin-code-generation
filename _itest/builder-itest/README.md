# Kotlin Code Generation Integration tests

The repository provides both, the generation module and the test module.
As the test module depends on the generation module, we cannot provide tests using the test module inside that module.

This additional module is used to define and run all nontrivial integration tests, where we "eat our own dog food"
and use the generation tools and builders to created code and then compile and assert it is working correctly using
the test module.
