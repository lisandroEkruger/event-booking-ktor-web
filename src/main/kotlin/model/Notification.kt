package com.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Notification: Table() {
    val id_notification: Column<Int> = integer("id_notification").autoIncrement()
    val participant_id: Column<Int> = reference("participant_id", Participant.id_participant)
    val message: Column<String> = varchar("message", length = 50)
    val sentAt: Column<String> = varchar("sent_at", length = 50)
    override val primaryKey = PrimaryKey(id_notification)
}