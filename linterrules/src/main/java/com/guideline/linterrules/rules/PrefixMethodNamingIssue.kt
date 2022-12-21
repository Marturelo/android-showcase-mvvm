package com.guideline.linterrules.rules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod

@Suppress("UnstableApiUsage")
object PrefixMethodNamingIssue {

    /**
     * The fixed id of the issue
     */
    private const val ID = "PrefixMethodNamingIssue"

    /**
     * The priority, a number from 1 to 10 with 10 being most important/severe
     */
    private const val PRIORITY = 7

    /**
     * Description short summary (typically 5-6 words or less), typically describing
     * the problem rather than the fix (e.g. "Missing minSdkVersion")
     */
    private const val DESCRIPTION = "Wrong function name prefix."

    /**
     * A full explanation of the issue, with suggestions for how to fix it
     */
    private const val EXPLANATION = """
        Function is wrongly named. Please make sure that function name matches rules described in 
        https://documentation
    """

    /**
     * The associated category, if any @see [Category]
     */
    private val CATEGORY = Category.CUSTOM_LINT_CHECKS

    /**
     * The default severity of the issue
     */
    private val SEVERITY = Severity.ERROR

    val ISSUE = Issue.create(
        ID,
        DESCRIPTION,
        EXPLANATION,
        CATEGORY,
        PRIORITY,
        SEVERITY,
        Implementation(PrefixIssueDetector::class.java, Scope.JAVA_FILE_SCOPE)
    )

    class PrefixIssueDetector : Detector(), Detector.UastScanner {
        override fun getApplicableUastTypes(): List<Class<out UElement>> =
            listOf(UMethod::class.java)

        override fun createUastHandler(context: JavaContext): UElementHandler =
            PrefixElementHandler(context)
    }
}