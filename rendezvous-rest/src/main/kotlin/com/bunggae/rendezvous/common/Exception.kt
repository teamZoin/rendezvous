package com.bunggae.rendezvous.common

class Exception: RuntimeException {
    var error: Error = Error.UNKNOWN_ERROR

    constructor(error: Error): super(error.desc) { this.error = error }
    constructor(error: Error, message: String): super(error.desc + " : " + message) { this.error = error }
}