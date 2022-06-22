package com.zoin.rendezvous.domain.report

import com.zoin.rendezvous.domain.rendezvous.Rendezvous
import com.zoin.rendezvous.domain.user.User
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "rendezvous_report")
class RendezvousReport(
    @ManyToOne val rendezvous: Rendezvous,
    @ManyToOne val reporter: User,
    reportReason: ReportReason,
    etcDescription: String? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Enumerated(value = EnumType.STRING)
    var reportReason: ReportReason? = reportReason

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RendezvousReport

        if (rendezvous != other.rendezvous) return false
        if (reporter != other.reporter) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rendezvous.hashCode()
        result = 31 * result + reporter.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "RendezvousReport(rendezvous=$rendezvous, reporter=$reporter, id=$id)"
    }
}
