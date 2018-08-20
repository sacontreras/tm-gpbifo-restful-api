1. Create initial pom.xml with Spring Initializr (start.spring.io), with dependencies:
    - `Web`
    - `JPA`
    - `Security`
    - `HATEOAS`
2. Check/add additional maven (pom.xml) deps versions from mvnrepository.com:
    - JSON Web Token Support For The JVM (groupid: "io.jsonwebtoken", artifactid: "jjwt"")
    - ModelMapper (groupid: "org.modelmapper", artifactid: "modelmapper")
3. Set "YOUR" values in application.properties resources file
4. Install MySQL locally (for debugging stand-alone jar ws) if it does not exist
5. Create `canon_gpkgbundles` database in MySQL (w/ root MySQL account)
6. Create an admin user account in MySQL for the `canon_gpkgbundles` db:
    `create user '<VALUE OF spring.datasource.username from application.properties>'@'localhost' identified by '<VALUE OF spring.datasource.password from application.properties>';`
7. Grant all privileges to this user account on the `canon_gpkgbundles` db:
    `grant all privileges on canon_gpkgbundles.* to '<VALUE OF spring.datasource.username from application.properties>'@'localhost';`
