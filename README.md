# shutdown-hook-error
Sample app to reproduce shutdown hook errors

- Deploy the app. 
- Stop the version.

The shutdown hook should trigger and add 1 task to a task queue (you can pause the queue to verify) and log.
However the shutdown hook is triggered but no task is added.

# Observed logs:
```
10:33:07.290 com.test.Test <clinit>: Shutdown Hook - Set
10:36:29.330 com.test.Test$1 shutdown: Shutdown Hook - Start
```
# deployment
```
mvn clean gcloud:deploy -Dgcloud.gcloud_project=shutdown-hook-error -Dgcloud.version=1 -Dgcloud.gcloud_directory=$GCLOUD_SDK_PATH -Dgcloud.verbosity=debug -Dgcloud.log_level=debug
```