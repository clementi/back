package com.tenfactorial.back;

import com.tenfactorial.back.exceptions.StackUnderflowException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class EvaluatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Evaluator evaluator;

    @Before
    public void setUp() {
        evaluator = new Evaluator();
    }

    @Test
    public void testNumbersAreJustPushedOntoTheStack() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(5, 4, 3, 2, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 3 4 5")));
    }

    @Test
    public void testTwoNumbersCanBeAdded() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(3),
                evaluator.evaluateProgram(Collections.singletonList("1 2 +")));
    }

    @Test
    public void testErrorIfAdditionAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("+"));
    }

    @Test
    public void testErrorIfAdditionAttemptedWithOneNumberOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("1 +"));
    }

    @Test
    public void testTwoNumbersCanBeSubtracted() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(-1),
                evaluator.evaluateProgram(Collections.singletonList("3 4 -")));
    }

    @Test
    public void testErrorIfSubtractionAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("-"));
    }

    @Test
    public void testErrorIfSubtractionAttemptedWithOneNumberOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("1 -"));
    }

    @Test
    public void testTwoNumbersCanBeMultiplied() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(8),
                evaluator.evaluateProgram(Collections.singletonList("2 4 *")));
    }

    @Test
    public void testErrorIfMultiplicationAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("*"));
    }

    @Test
    public void testErrorIfMultiplicationAttemptedWithOneNumberOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("1 *"));
    }

    @Test
    public void testTwoNumbersCanBeDivided() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(4),
                evaluator.evaluateProgram(Collections.singletonList("12 3 /")));
    }

    @Test
    public void testThatIntegerDivisionIsUsed() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(2),
                evaluator.evaluateProgram(Collections.singletonList("8 3 /")));
    }

    @Test
    public void testErrorIfDividingByZero() throws StackUnderflowException {
        expectedException.expect(ArithmeticException.class);
        evaluator.evaluateProgram(Collections.singletonList("4 0 /"));
    }

    @Test
    public void testErrorIfDivisionAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("/"));
    }

    @Test
    public void testErrorIfDivisionAttemptedWithOneNumberOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("1 /"));
    }

    @Test
    public void testCombinedAdditionAndSubtraction() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(-1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 + 4 -")));
    }

    @Test
    public void testCombinedMultiplicationAndDivision() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(2),
                evaluator.evaluateProgram(Collections.singletonList("2 4 * 3 /")));
    }

    @Test
    public void testDupCopiesAValueOnTheStack() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 dup")));
    }

    @Test
    public void testDupCopiesTopValueOnTheStack() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(2, 2, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 dup")));
    }

    @Test
    public void testErrorIfDuplicatingAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("dup"));
    }

    @Test
    public void testDropRemovesTheTopValueOnTheStackIfItIsTheOnlyOne() throws StackUnderflowException {
        assertEquals(
                Collections.emptyList(),
                evaluator.evaluateProgram(Collections.singletonList("1 drop")));
    }

    @Test
    public void testDropRemovesTheTopValueOnTheStackIfItIsNotTheOnlyOne() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 drop")));
    }

    @Test
    public void testErrorIfDroppingAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("drop"));
    }

    @Test
    public void testSwapSwapsTheTopTwosValueOnTheStackIfTheyAreTheOnlyOnes() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 2),
                evaluator.evaluateProgram(Collections.singletonList("1 2 swap")));
    }

    @Test
    public void testSwapSwapsTheTopTwosValueOnTheStackIfTheyAreNotTheOnlyOnes() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(2, 3, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 3 swap")));
    }

    @Test
    public void testErrorIfSwappingAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("swap"));
    }

    @Test
    public void testErrorIfSwappingAttemptedWithOneNumberOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("1 swap"));
    }

    @Test
    public void testOverCopiesTheSecondElementIfThereAreOnlyTwo() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 2, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 over")));
    }

    @Test
    public void testOverCopiesTheSecondElementIfThereAreMoreThanTwo() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(2, 3, 2, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 3 over")));
    }

    @Test
    public void testErrorIfOveringAttemptedWithNothingOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("over"));
    }

    @Test
    public void testErrorIfOveringAttemptedWithOneNumberOnTheStack() throws StackUnderflowException {
        expectedException.expect(StackUnderflowException.class);
        evaluator.evaluateProgram(Collections.singletonList("1 over"));
    }

    @Test
    public void testUserDefinedOperatorsCanConsistOfBuiltInOperators() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 1, 1),
                evaluator.evaluateProgram(Arrays.asList(": dup-twice dup dup ;", "1 dup-twice")));
    }

    @Test
    public void testUserDefinedOperatorsAreEvaluatedInTheCorrectOrder() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(3, 2, 1),
                evaluator.evaluateProgram(Arrays.asList(": countup 1 2 3 ;", "countup")));
    }

    @Test
    public void testCanRedefineAUserDefinedOperator() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 1, 1),
                evaluator.evaluateProgram(Arrays.asList(": foo dup ;", ": foo dup dup ;", "1 foo")));
    }

    @Test
    public void testCanOverrideBuiltInWordOperators() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 1),
                evaluator.evaluateProgram(Arrays.asList(": swap dup ;", "1 swap")));
    }

    @Test
    public void testCanOverrideBuiltInArithmeticOperators() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(12),
                evaluator.evaluateProgram(Arrays.asList(": + * ;", "3 4 +")));
    }

    @Test
    public void testCanUseDifferentWordsWithTheSameName() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(6, 5),
                evaluator.evaluateProgram(Arrays.asList(": foo 5 ;", ": bar foo ;", ": foo 6 ;", "bar foo")));
    }

    @Test
    public void testCanDefineWordThatUsesWordWithTheSameName() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(11),
                evaluator.evaluateProgram(Arrays.asList(": foo 10 ;", ": foo foo 1 + ;", "foo")));
    }

    @Test
    public void testCannotRedefineNumbers() throws StackUnderflowException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Cannot redefine numbers");

        evaluator.evaluateProgram(Collections.singletonList(": 1 2 ;"));
    }

    @Test
    public void testErrorIfEvaluatingAnUndefinedOperator() throws StackUnderflowException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No definition available for operator \"foo\"");

        evaluator.evaluateProgram(Collections.singletonList("foo"));
    }

    @Test
    public void testDupIsCaseInsensitive() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 1, 1, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 DUP Dup dup")));
    }

    @Test
    public void testDropIsCaseInsensitive() throws StackUnderflowException {
        assertEquals(
                Collections.singletonList(1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 3 4 DROP Drop drop")));
    }

    @Test
    public void testSwapIsCaseInsensitive() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 4, 3, 2),
                evaluator.evaluateProgram(Collections.singletonList("1 2 SWAP 3 Swap 4 swap")));
    }

    @Test
    public void testOverIsCaseInsensitive() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 2, 1, 2, 1),
                evaluator.evaluateProgram(Collections.singletonList("1 2 OVER Over over")));
    }

    @Test
    public void testUserDefinedWordsAreCaseInsensitive() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 1, 1, 1),
                evaluator.evaluateProgram(Arrays.asList(": foo dup ;", "1 FOO Foo foo")));
    }

    @Test
    public void testDefinitionsAreCaseInsensitive() throws StackUnderflowException {
        assertEquals(
                Arrays.asList(1, 1, 1, 1),
                evaluator.evaluateProgram(Arrays.asList(": SWAP DUP Dup dup ;", "1 swap")));
    }
}
