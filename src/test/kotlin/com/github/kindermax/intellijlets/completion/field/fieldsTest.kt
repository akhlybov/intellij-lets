package com.github.kindermax.intellijlets.completion.field

import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.editor.VisualPosition
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixture4TestCase
import junit.framework.TestCase
import org.junit.Test

open class LetsLightCodeInsightTestCase :  LightPlatformCodeInsightFixture4TestCase() {

    override fun getTestDataPath(): String {
        return "src/test/resources/completion"
    }

    @Test
    fun testRootCompletion() {
        val letsFile = myFixture.copyFileToProject("/root/lets.yaml")
        myFixture.configureFromExistingVirtualFile(letsFile)
        val variants = myFixture.getCompletionVariants("/root/lets.yaml")
                ?: return TestCase.fail("completion variants must not be null")
        val expected = listOf(
            "shell",
            "commands",
            "env",
            "eval_env",
            "version",
            "mixins",
            "before"
        )

        TestCase.assertEquals(expected.sorted(), variants.sorted())
    }

    @Test
    fun testCommandCompletionWithCLetter() {
        val letsFile = myFixture.copyFileToProject("/command/lets.yaml")
        myFixture.configureFromExistingVirtualFile(letsFile)
        myFixture.editor.caretModel.primaryCaret.moveToOffset(35)
        val variants = myFixture.getCompletionVariants("/command/lets.yaml")
                ?: return TestCase.fail("completion variants must not be null")
        val expected = listOf(
            "description",
            "checksum",
            "persist_checksum",
            "cmd"
        )

        TestCase.assertEquals(expected.sorted(), variants.sorted())
    }

    @Test
    fun testDependsCompletionWorks() {
        val letsFile = myFixture.copyFileToProject("/depends/lets.yaml")
        myFixture.configureFromExistingVirtualFile(letsFile)
        myFixture.editor.caretModel.primaryCaret.moveToOffset(146)
        val variants = myFixture.getCompletionVariants("/depends/lets.yaml")
                ?: return TestCase.fail("completion variants must not be null")

        val expected = listOf(
            "hi", "lol"
        )

        TestCase.assertEquals(expected.sorted(), variants.sorted())
    }
}