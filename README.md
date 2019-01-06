# server-event-log-processor

Log parsing to database server events code

To run, start Application as main class with path to input file as first arg.
or
Gradle application plugin "run" goal should run from project root dir using sample input from spec


Notes:
Hsqldb file is "testdb" in current dir - will need to delete if rerunning with same input else constraint violation

Uses java8 parallel streams (as threadsafe aggregator)

For large files, lack of hsqldb connection pool is potential biggest impact on performance. Memory usage should be low as long as not too far between STARTED and FINISHED.

Written TDD but only for happy path - needs error handling cases and code

Some TODOs written in code as ideas occurred during writing
