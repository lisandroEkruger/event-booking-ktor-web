package com.model

import org.jetbrains.exposed.sql.Table

object Participant : Table() {
    val id_participant = integer("id_participant").autoIncrement()
    val firstName = varchar("first_name", length = 50)
    val lastName = varchar("last_name", length = 50)
    val email = varchar("email", length = 50)
    override val primaryKey = PrimaryKey(id_participant)
}
