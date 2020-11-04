package db.changelog

databaseChangeLog {
    changeSet(id: 'create-account-table', author: 'Fred Policarpo') {
        createTable(tableName: 'accounts', schemaName: 'public') {
            column(name: 'id', type: 'int', autoIncrement: 'true') {
                constraints(nullable: false, primaryKey: true)
            }

            column(name: 'document_number', type: 'varchar(50)') {
                constraints(nullable: false, unique: true)
            }

            column(name: "version", type: "int") {
                constraints(nullable: false)
            }
            column(name: 'created_at', type: 'timestamp') {
                constraints(nullable: false)
            }
            column(name: 'updated_at', type: 'timestamp')
            column(name: 'deleted_at', type: 'timestamp')
        }
    }

    changeSet(id: 'create-transactions-table', author: 'Fred Policarpo') {
        createTable(tableName: 'transactions', schemaName: 'public') {
            column(name: 'id', type: 'int', autoIncrement: 'true') {
                constraints(nullable: false, primaryKey: true)
            }

            column(name: 'account_id', type: 'int') {
                constraints(nullable: false, foreignKeyName: "fk_transacions_accounts", references: "accounts(id)")
            }

            column(name: 'operation_type', type: 'int') {
                constraints(nullable: false)
            }

            column(name: "amount", type: "money") {
                constraints(nullable: false)
            }

            column(name: 'event_date', type: 'timestamp') {
                constraints(nullable: false)
            }

            column(name: 'created_at', type: 'timestamp') {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: 'add-column-credit-limit-to-account-table', author: 'Fred Policarpo') {
        addColumn(tableName: 'accounts', schemaName: 'public') {
            column(name: 'credit_limit', type: 'money') {
                constraints(nullable: false)
            }
        }
    }
}