# Canonical GeoPackage Bundle Availability - RESTful API

## Configuration

### Infrastructure (Provisioning)

#### MySQL
1. Install MySQL on your (dev) infrastructure host
2. Login to MySQL w/ root account (using an installed MySQL client or the CLI)
3. Create `canon_gpkgbundles` database in MySQL db:
    <pre><code>create database canon_gpkgbundles;</code></pre>
4. Create an admin user for the `canon_gpkgbundles` db and grant privileges:
    1. Set/export environment variables/values:
        1. `GEOPACKAGEBUNDLE_APP_WS__MYSQL_DB_ADMIN_USERNAME` - this value will obviously be the username for the MySQL user (admin) account to be created; this env var is also used in/by Spring-Boot application.properties (Spring-Boot Application configuration, below)
        2. `GEOPACKAGEBUNDLE_APP_WS__MYSQL_DB_ADMIN_PASSWORD` - this value will obviously be the password for the MySQL user (admin) account to be created; this env var is also used in/by Spring-Boot application.properties (Spring-Boot Application configuration, below)
    2. Execute SQL to create the admin user:
        <pre><code>create user '&lt;<em>value of <b>GEOPACKAGEBUNDLE_APP_WS__MYSQL_DB_ADMIN_USERNAME</b> env var</em>&gt;'@'localhost' identified by '&lt;<em>value of <b>GEOPACKAGEBUNDLE_APP_WS__MYSQL_DB_ADMIN_PASSWORD</b> env var</em>&gt;';</code></pre>
    3. Execute SQL to grant privileges to the newly created admin user:
        <pre><code>grant all privileges on canon_gpkgbundles.* to '&lt;<em>value of <b>GEOPACKAGEBUNDLE_APP_WS__MYSQL_DB_ADMIN_USERNAME</b> env var</em>&gt;'@'localhost';</code></pre>

### Spring-Boot Application (Provisioning)

#### Spring-Boot application.properties (resource)
Set/export environment variables/values (values used in/by application.properties):
1. `GEOPACKAGEBUNDLE_APP_WS__MYSQL_DB_HOST` - e.g. `localhost` if you installed MySQL locally
2. `GEOPACKAGEBUNDLE_APP_WS__MYSQL_DB_PORT` - the default port for MySQL is `3306`, so use that value unless you changed it
3. `GEOPACKAGEBUNDLE_APP_WS__JSON_WEB_TOKEN_SECRET` - this is the seed used for Sprint Security HTTP authentication - choose any value you like (but the more random and long it is, the better)
4. `GEOPACKAGEBUNDLE_APP_WS__GPKGBUNDLEADMIN__EMAIL` - this is email address (also the user id) for the account used to execute admin functions with the API - e.g. creating new gpkgbundles
5. `GEOPACKAGEBUNDLE_APP_WS__GPKGBUNDLEADMIN__PASSWORD` - the password for the gpkgbundle admin account
