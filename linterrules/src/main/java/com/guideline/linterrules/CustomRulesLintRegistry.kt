package com.guideline.linterrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.guideline.linterrules.rules.PrefixMethodNamingIssue

class CustomRulesLintRegistry : IssueRegistry() {
    override val issues = listOf(PrefixMethodNamingIssue.ISSUE)

    override val api: Int = CURRENT_API

}
