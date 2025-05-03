package com.model

import org.jetbrains.exposed.sql.Table

object Venue : Table() {
    val id_venue = integer("id_venue").autoIncrement()
    val name = varchar("name", length = 50)
    val address = varchar("address", length = 50)
    val capacity = bool("capacity")
    override val primaryKey = PrimaryKey(id_venue)
}