package com.zoin.rendezvous.domain.user.repository

import com.zoin.rendezvous.domain.SoftDeletableEntityRepository
import com.zoin.rendezvous.domain.user.User

interface UserCustomRepository : SoftDeletableEntityRepository<User, Long>
