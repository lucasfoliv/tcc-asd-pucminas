package db.changelog

databaseChangeLog {
    changeSet(id: "01_CREATE_AUTHORITY_TABLE", author: "GISA") {
        createTable(tableName: "authority") {
            column(name: "id", type: "uuid", autoIncrement: false) {
                constraints(primaryKey: true, primaryKeyName: "pk_authority", nullable: false)
            }
            column(name: "name", type: "varchar(50)") {
                constraints(nullable: false, unique: true, uniqueConstraintName: "uk_authority_name")
            }
            column(name: "optlock", type: "bigint") {
                constraints(nullable: false)
            }
            column(name: "created_at", type: "datetime") {
                constraints(nullable: false)
            }
            column(name: "updated_at", type: "datetime") {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: "02_CREATE_PROFILE_TABLE", author: "GISA") {
        createTable(tableName: "profile") {
            column(name: "id", type: "uuid", autoIncrement: false) {
                constraints(primaryKey: true, primaryKeyName: "pk_profile", nullable: false)
            }
            column(name: "name", type: "varchar(50)") {
                constraints(nullable: false, unique: true, uniqueConstraintName: "uk_profile_name")
            }
            column(name: "status", type: "varchar(10)") {
                constraints(nullable: false)
            }
            column(name: "optlock", type: "bigint") {
                constraints(nullable: false)
            }
            column(name: "created_at", type: "datetime") {
                constraints(nullable: false)
            }
            column(name: "updated_at", type: "datetime") {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: "03_CREATE_PROFILE_AUTHORITY_TABLE", author: "GISA") {
        createTable(tableName: "profile_authority") {
            column(name: "profile_id", type: "uuid") {
                constraints(foreignKeyName: "fk_profile_authority", references: "profile(id)", nullable: false)
            }
            column(name: "authority_id", type: "uuid") {
                constraints(foreignKeyName: "fk_authority_profile", references: "authority(id)", nullable: false)
            }
        }
    }

    changeSet(id: "04_CREATE_USER_TABLE", author: "GISA") {
        createTable(tableName: "user") {
            column(name: "id", type: "uuid", autoIncrement: false) {
                constraints(primaryKey: true, primaryKeyName: "pk_user", nullable: false)
            }
            column(name: "email", type: "varchar") {
                constraints(nullable: false, unique: true, uniqueConstraintName: "uk_user_email")
            }
            column(name: "secret", type: "varchar(100)") {
                constraints(nullable: false)
            }
            column(name: "status", type: "varchar(15)") {
                constraints(nullable: false)
            }
            column(name: "profile_id", type: "uuid") {
                constraints(foreignKeyName: "fk_user_profile", references: "profile(id)", nullable: false)
            }
            column(name: "optlock", type: "bigint") {
                constraints(nullable: false)
            }
            column(name: "created_at", type: "datetime") {
                constraints(nullable: false)
            }
            column(name: "updated_at", type: "datetime") {
                constraints(nullable: false)
            }
        }
    }
}
