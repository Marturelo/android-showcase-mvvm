package com.guideline.linterrules.rules

class PrefixMethodCheckerImpl : PrefixMethodChecker() {
    override val returnClassNames: List<String> = listOf("Void")
    override val prefix: List<String> = listOf("BCP")

    override val message: String = """
        [ReturnType] returning functions should be named with suffix BCP. 
        Example: BCPremoveAccount()
    """
}