## Sectors app
### Quick start
 Used technologies:
- Spring Boot for web service
- Thymeleaf for template engine
- PostgreSQL for data persistence
- Redis for session data
- Docker Compose for running postgsql + redis
- Flyway for migrations 

In order to start service you need:
1. Start postgresql and redis
2. Compile jar
3. Run jar
```shell
docker-compose up -d
# wait for services to start
mvn clean install
java -jar target/categories-0.0.1-SNAPSHOT.jar
```
4. Open browser [here](http://localhost:11500)


All configurations for spring application could be found in application.properties file.

Flyway must run this scripts automatically:
```sql
# src/main/resources/db/migrations/v1_createtable.sql
# src/main/resources/db/migrations/v2_insert.sql
```
It's recommended to read this page before jumping to another part of project
You can find additional and detailed comments in this format:
```java
/**
 * Sectors service. It contains only 1 method get sector tree(where each node contains info about sector value and description and
 * children). It's better to return sector tree in order to perform query operation on tree.
 * For view purposes tree must be converted to flatSector(according level)
 *
 */
public interface SectorsService {
    List<SectorNode> getAll();
}

```

### Implementation notes.
Generally I have used the service pattern.
Main services are:
- Sector service. Sector service responsible to return all sectors in tree view.
Where nodes include sector value(identifier) and human-readable description, children - nested sectors.
- Users service. Users service responsible to store userdata(sessionId, selected sectors, firstName) and retrieve users by session id.

All validations were performed on service side. Each service has corresponding repositories. 
All defined errors must be thrown with <code>GenericException</code> class and later can be handled for each of the cases.

If an error wasn't handled then GlobalExceptionHandler handle error.
```java
/**
 * ControllerAdvice to handle all exceptions.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler { }
```

There are two main endpoints:
- <code>GET /</code> to send html template with filled values(depending on user session)
- <code>POST /sectors </code> to process and store user data

Sectors can be nested. For example Manufacturing have nested sectors like: construction, electronics and so on.
That is why I decided to store sector's relation between each other in a database.
If sector do not have parent(root sector(manufacturing, other, service)) then in a database <code>sector.sector_value = sector.sector_parent_value</code>.
If sector has parent sector(owning) then <code>sector.sector_value_parent = parent.sector_value</code>
#### This rule must be considered before adding new sectors
Each sector also stores order integer value that define ordering between sibling sectors.

In order to build sectors trees and convert them to select tag I'm using dfs approach. More details can be found in code. 


### Tricky moments:
- Which logic must be implemented(UI side) when user selects non leaf sector? For example if I choose Food and Beverage, does it mean I have chosen all It's children automatically?
##### For current implementation I do not differ from choosing non leaf and leaf sectors.


### "Where to start optimization" notes.
- It is reasonable that sectors has defined amount and wouldn't change in size and values too often.
  but can have read heavy operations(depending on users requests). That is why it's better to store sectors in inmemory cache(for example redis).
  and perform http operations in order to retrieve them. Or better approach is to have listener in web service for data changes(for sectors part).
- Add indexes to session id field(for better users search).
- User first name and chosen sectors not sensitive data, but anyway better approach to not use session. 
- We don't get much from sql relations for user and user sectors data. Maybe nonSql dbs would be better choice?
- Use SPA (React, Angular) for user interactions and restify backend.
- CI/CD.