package com.service

import com.model.Notification
import com.request.NotificationRequest
import com.response.NotificationResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class NotificationService {

    suspend fun listAll(): List<NotificationResponse> = dbQuery {
        Notification.selectAll().map {
            NotificationResponse(
                id_notification = it[Notification.id_notification].toString(),
                participant_id = it[Notification.participant_id],
                message = it[Notification.message],
                sentAt = it[Notification.sentAt]
            )
        }
    }

    suspend fun createNotification(notificationRequest: NotificationRequest) {
        transaction {
            Notification.insert {
                it[participant_id] = notificationRequest.participant_id
                it[message] = notificationRequest.message
                it[sentAt] = notificationRequest.sentAt
            }[Notification.id_notification]
        }
    }

    suspend fun updateNotification(id: Int, notificationRequest: NotificationRequest) {
        dbQuery {
            Notification.update({ Notification.id_notification eq id }) {
                it[participant_id] = notificationRequest.participant_id
                it[message] = notificationRequest.message
                it[sentAt] = notificationRequest.sentAt
            }
        }
    }

    suspend fun deleteNotification(id: Int) {
        dbQuery {
            Notification.deleteWhere { Notification.id_notification eq id }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}