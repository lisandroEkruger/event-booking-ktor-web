package com.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object Event : Table() {
    val id_event: Column<Int> = integer("id_event").autoIncrement()
    val name: Column<String> = varchar("name", length = 50)
    val date: Column<LocalDate> = date("date")
    override val primaryKey = PrimaryKey(id_event)
}

