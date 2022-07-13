package com.zoin.rendezvous.domain.rendezvous.usecase

import com.zoin.rendezvous.domain.friend.repository.FriendRepository
import com.zoin.rendezvous.domain.rendezvous.repository.RendezvousRepository
import com.zoin.rendezvous.domain.user.repository.UserRepository
import java.time.LocalDateTime
import javax.inject.Named

@Named
class GetCurrentRendezvousStatusUseCase(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
    private val rendezvousRepository: RendezvousRepository,
) {
    data class Query(
        val userId: Long,
    )

    enum class CurrentStatus(desc: String) {
        SUNNY("맑은 하늘"),
        LIGHTNING("번개가 요동치는 중"),
        CLOUDY("구름만 잔뜩 낀 하늘");
    }

    fun execute(query: Query): CurrentStatus {
        val user = userRepository.findByIdExcludeDeleted(query.userId)
        val friends = friendRepository.findByUser(user).map { it.friend }
        val friendCnt = friends.size
        val rendezvousCnt =
            rendezvousRepository.countByCreatorInAndCreatedAtAfter(friends, LocalDateTime.now().minusDays(2))

        return if (rendezvousCnt > friendCnt * 0.7) {
            CurrentStatus.SUNNY
        } else if (rendezvousCnt > friendCnt * 0.5) {
            CurrentStatus.LIGHTNING
        } else {
            CurrentStatus.CLOUDY
        }
    }
}
