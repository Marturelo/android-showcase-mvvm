package com.guideline.linterrules.rules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.guideline.linterrules.rules.PrefixMethodNamingIssue.ISSUE
import com.intellij.psi.PsiClassType
import org.jetbrains.uast.UMethod

class PrefixElementHandler(private val context: JavaContext) : UElementHandler() {

    private val methodCheckers = listOf(
        PrefixMethodCheckerImpl()
    )

    override fun visitMethod(node: UMethod) {
        val returnClassName = node.returnClassName()

        methodCheckers.firstOrNull { it.isMethodReturningType(returnClassName) }
            ?.checkMethod(node)
    }

    private fun PrefixMethodChecker.checkMethod(node: UMethod) =
        checkMethodNameSuffix(node.pureMethodName())
            .also { methodNameCorrect ->
                if (!methodNameCorrect) {
                    reportIssue(node)
                }
            }

    private fun reportIssue(node: UMethod) {
        context.report(
            issue = ISSUE,
            scopeClass = node,
            location = context.getNameLocation(node),
            message = """
                [Single] returning functions should be named with prefix BCP. 
                Example: BCPgetAccounts()
            """
        )
    }


    private fun UMethod.returnClassName(): String =
        (returnTypeReference?.type as? PsiClassType)?.className ?: ""

    private fun UMethod.pureMethodName() =
        name.split(KOTLIN_BYTECODE_DELIMITER)[0]

    companion object {
        private const val KOTLIN_BYTECODE_DELIMITER = "$"
    }
}