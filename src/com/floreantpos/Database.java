package com.floreantpos;

import org.apache.commons.lang.StringUtils;

public enum Database {
  DERBY_SINGLE(Messages.getString("Database.DERBY_SINGLE"), //$NON-NLS-1$
      "jdbc:derby:database/derby-single/posdb", //$NON-NLS-1$
      "jdbc:derby:database/derby-single/posdb;create=true", "", //$NON-NLS-1$ //$NON-NLS-2$
      "org.apache.derby.jdbc.EmbeddedDriver", "org.hibernate.dialect.DerbyDialect"), //$NON-NLS-1$ //$NON-NLS-2$
      DERBY_SERVER(Messages.getString("Database.DERBY_SERVER"), "jdbc:derby://<host>:<port>/<db>", //$NON-NLS-1$ //$NON-NLS-2$
          "jdbc:derby://<host>:<port>/<db>;create=true", "51527", //$NON-NLS-1$ //$NON-NLS-2$
          "org.apache.derby.jdbc.ClientDriver", "org.hibernate.dialect.DerbyDialect"), //$NON-NLS-1$ //$NON-NLS-2$
          MYSQL(Messages.getString("Database.MYSQL"), //$NON-NLS-1$
              "jdbc:mysql://<host>:<port>/<db>?characterEncoding=UTF-8", //$NON-NLS-1$
              "jdbc:mysql://<host>:<port>/<db>?characterEncoding=UTF-8", "3306", //$NON-NLS-1$ //$NON-NLS-2$
              "com.mysql.jdbc.Driver", "org.hibernate.dialect.MySQLDialect"); //$NON-NLS-1$ //$NON-NLS-2$

  private String providerName;
  private String jdbcUrlFormat;
  private String jdbcUrlFormatToCreateDb;
  private String defaultPort;
  private String driverClass;
  private String hibernateDialect;

  private Database(String providerName, String jdbcURL, String jdbcURL2CreateDb, String defaultPort,
      String driverClass, String hibernateDialect) {
    this.providerName = providerName;
    this.jdbcUrlFormat = jdbcURL;
    this.jdbcUrlFormatToCreateDb = jdbcURL2CreateDb;
    this.defaultPort = defaultPort;
    this.driverClass = driverClass;
    this.hibernateDialect = hibernateDialect;
  }

  public String getConnectString(String host, String port, String databaseName) {
    String connectionURL = jdbcUrlFormat.replace("<host>", host); //$NON-NLS-1$

    if (StringUtils.isEmpty(port)) {
      port = defaultPort;
    }

    connectionURL = connectionURL.replace("<port>", port); //$NON-NLS-1$
    connectionURL = connectionURL.replace("<db>", databaseName); //$NON-NLS-1$

    return connectionURL;
  }

  public String getCreateDbConnectString(String host, String port, String databaseName) {
    String connectionURL = jdbcUrlFormatToCreateDb.replace("<host>", host); //$NON-NLS-1$

    if (StringUtils.isEmpty(port)) {
      port = defaultPort;
    }

    connectionURL = connectionURL.replace("<port>", port); //$NON-NLS-1$
    connectionURL = connectionURL.replace("<db>", databaseName); //$NON-NLS-1$

    return connectionURL;
  }

  public String getProviderName() {
    return providerName;
  }

  public String getJdbcUrlFormat() {
    return jdbcUrlFormat;
  }

  public String getDefaultPort() {
    return defaultPort;
  }

  @Override
  public String toString() {
    return this.providerName;
  }

  public String getHibernateConnectionDriverClass() {
    return driverClass;
  }

  public String getHibernateDialect() {
    return hibernateDialect;
  }

  public static Database getByProviderName(String providerName) {
    Database[] databases = values();
    for (Database database : databases) {
      if (database.providerName.equals(providerName)) {
        return database;
      }
    }

    return null;
  }

}
