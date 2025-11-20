package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final double INITIAL_BALANCE = 0;
    private static final double AMOUNT = 100;
    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, INITIAL_BALANCE);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(INITIAL_BALANCE, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);
        assertEquals(AMOUNT, bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactionsCount());

        bankAccount.chargeManagementFees(mRossi.getUserID());

        // 100 - (5 + 0.1*1) = 94.9
        assertEquals(94.9, bankAccount.getBalance(), 0.0001);
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -50);
            fail("Withdrawing a negative amount should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // OK: expected
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        // saldo = 0, si prova a prelevare 50
        try {
            bankAccount.withdraw(mRossi.getUserID(), 50);
            fail("Withdrawing more than the balance should have thrown an exception");
        } catch (IllegalArgumentException e) {
            // OK: expected
            assertEquals(0, bankAccount.getBalance());
        }
    }
}
