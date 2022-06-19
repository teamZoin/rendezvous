package com.zoin.rendezvous.domain.user.usecase

import com.zoin.rendezvous.domain.user.User
import com.zoin.rendezvous.domain.user.repository.UserRepository
import javax.inject.Named

@Named
class SearchUserByServiceIdUseCase(
    private val userRepository: UserRepository,
) {
    data class Query(
        val searchIdInput: String,
    )
    fun execute(query: Query): List<User> {
        return userRepository.findByServiceIdContaining(query.searchIdInput)
    }
}
