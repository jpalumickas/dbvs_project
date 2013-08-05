DBVS Project in University
============

DBVS Project written in Java language, which manage fuel consuption in work.

## Configuration

```java
// File located in src/database/DatabaseConfig.java

package database;

public class DatabaseConfig
{
  public static final String USERNAME = "username";
  public static final String PASSWORD = "password";
  public static final String SERVER   = "localhost:5432";
  public static final String DATABASE = "dbvs_project";
}

```
## Usage
  
Clean builds

```sh
ant clean
```
    
Compile Java project

```sh
ant compile
```
    
Make Jar file of Java project

```sh
ant jar
```

Clean, compile, build Jar and run project
```sh
./run
```

## Copyright
Copyright (c) 2012 Justas Palumickas.
See [LICENSE][] for details.

[license]: LICENSE.md
