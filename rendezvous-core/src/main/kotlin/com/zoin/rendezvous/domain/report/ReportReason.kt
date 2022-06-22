package com.zoin.rendezvous.domain.report

enum class ReportReason(desc: String) {
    PROFIT_MAKING("영리목적/홍보성"),
    CURSING("욕설/인신공격"),
    PROVOCATIVE("음란/선정성"),
    SPAM("같은 내용 도배"),
    ETC("기타"),
}
