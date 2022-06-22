package com.zoin.rendezvous.domain.report

enum class ReportReason(val desc: String) {
    PROFIT_MAKING("영리목적/홍보성"),
    CURSING("욕설/인신공격"),
    PROVOCATIVE("음란/선정성"),
    SPAM("같은 내용 도배"),
    ETC("기타");

    companion object {
        private val DESC_CACHE: Map<String, ReportReason> =
            values().associateBy { it.desc }

        fun findByDesc(desc: String) = DESC_CACHE[desc]
    }
}
