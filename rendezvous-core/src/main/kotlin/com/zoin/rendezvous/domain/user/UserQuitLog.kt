package com.zoin.rendezvous.domain.user

import com.zoin.rendezvous.base.JpaBaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user_quit_log")
class UserQuitLog(
    @ManyToOne
    val user: User,
    @Enumerated(EnumType.STRING)
    val reason: QuitReason,
    val etcDescription: String?,
): JpaBaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserQuitLog

        if (user != other.user) return false
        if (reason != other.reason) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = user.hashCode()
        result = 31 * result + reason.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserQuitLog(user=$user, reason=$reason, etcDescription='$etcDescription', id=$id)"
    }
}

enum class QuitReason(val desc: String) {
    NOTHING_TO_PARTICIPATE_IN("참여할 번개가 없어요"),
    NO_NEW_PERSON("새로운 사람을 만나기 어려워요"),
    UNPLEASANT_EXPERIENCE("불쾌한 경험을 했어요"),
    NOTHING_TO_GATHER("번개를 올리지 않게 돼요"),
    FREQUENT_ERROR("앱에 오류가 자주 발생해요"),
    LESS_USER("앱을 사용하는 주변 사람들이 많지 않아요"),
    USELESSNESS("앱에 잘 접속하지 않아요"),
    ETC("기타");

    companion object {
        private val DESC_CACHE: Map<String, QuitReason> =
            values().associateBy { it.desc }

        fun findByDesc(desc: String) = DESC_CACHE[desc]
    }
}
