package db.changelog

databaseChangeLog {
    changeSet(id: 'create-account-table', author: 'fred') {
        createTable(tableName: 'accounts', schemaName: 'public') {
            column(name: 'id', type: 'int', autoIncrement: 'true') {
                constraints(nullable: false, primaryKey: true)
            }

            column(name: 'document_number', type: 'varchar(50)') {
                constraints(nullable: false, unique: true)
            }
            column(name: 'create_at', type: 'timestamp') {
                constraints(nullable: false)
            }
            column(name: 'updated_at', type: 'timestamp')
            column(name: 'deleted_at', type: 'timestamp')
        }
    }
}