# Quartz Demo Project

This project demonstrates the use of Quartz Scheduler with a SQL Server database. The application is configured to run a simple job using Quartz, a popular job scheduling library in Java.

## Project Structure

- **quartz-demo/src/main/resources/quartz.properties**: Configuration file for Quartz Scheduler.
- **quartz-demo/src/main/java/com/example**: Contains Java classes for job scheduling and execution.
- **quartz-demo/pom.xml**: Maven configuration file for managing project dependencies.

## Configuration Details

### quartz.properties

This file contains the configuration settings for the Quartz Scheduler. Below is a detailed explanation of each configuration:

- **org.quartz.scheduler.instanceName**: Sets the name of the scheduler instance. This is useful for identifying the scheduler in a clustered environment.

  ```properties
  org.quartz.scheduler.instanceName=MyClusteredScheduler
  ```

- **org.quartz.scheduler.instanceId**: Specifies the instance ID. Setting it to `AUTO` allows Quartz to automatically generate an ID.

  ```properties
  org.quartz.scheduler.instanceId=AUTO
  ```

- **org.quartz.jobStore.class**: Defines the class used for job storage. `JobStoreTX` is used for transactional job storage.

  ```properties
  org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
  ```

- **org.quartz.jobStore.driverDelegateClass**: Specifies the delegate class for SQL Server. This is crucial for database-specific operations.

  ```properties
  org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.MSSQLDelegate
  ```

- **org.quartz.jobStore.dataSource**: Indicates the data source name used by the job store.

  ```properties
  org.quartz.jobStore.dataSource=myDS
  ```

- **org.quartz.jobStore.tablePrefix**: Sets the prefix for Quartz tables in the database.

  ```properties
  org.quartz.jobStore.tablePrefix=QRTZ_
  ```

- **org.quartz.jobStore.isClustered**: Enables clustering, allowing multiple instances to share the same job store.

  ```properties
  org.quartz.jobStore.isClustered=true
  ```

- **org.quartz.dataSource.myDS.driver**: Specifies the JDBC driver for SQL Server.

  ```properties
  org.quartz.dataSource.myDS.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
  ```

- **org.quartz.dataSource.myDS.URL**: The JDBC URL for connecting to the SQL Server database.

  ```properties
  org.quartz.dataSource.myDS.URL=jdbc:sqlserver://localhost:1433;databaseName=quartz
  ```

- **org.quartz.dataSource.myDS.user**: Database username for authentication.

  ```properties
  org.quartz.dataSource.myDS.user=yourusername
  ```

- **org.quartz.dataSource.myDS.password**: Database password for authentication.

  ```properties
  org.quartz.dataSource.myDS.password=yourpassword
  ```

- **org.quartz.dataSource.myDS.maxConnections**: Maximum number of database connections in the pool.

  ```properties
  org.quartz.dataSource.myDS.maxConnections=5
  ```

- **org.quartz.threadPool.class**: Specifies the thread pool class used by Quartz.

  ```properties
  org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
  ```

- **org.quartz.threadPool.threadCount**: Number of threads in the thread pool.

  ```properties
  org.quartz.threadPool.threadCount=10
  ```

- **org.quartz.threadPool.threadPriority**: Priority of the threads in the pool.
  ```properties
  org.quartz.threadPool.threadPriority=5
  ```

## Running the Project

1. Ensure you have a SQL Server instance running and accessible.
2. Update the `quartz.properties` file with your database credentials.
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   java -jar target/quartz-demo-1.0-SNAPSHOT.jar
   ```

The server will start on port 8080, and you can interact with the job scheduling API.

## License

This project is licensed under the MIT License.

- Install Database for Quartz use these scripts
  [Install Database](https://github.com/quartz-scheduler/quartz/tree/main/quartz/src/main/resources/org/quartz/impl/jdbcjobstore)
