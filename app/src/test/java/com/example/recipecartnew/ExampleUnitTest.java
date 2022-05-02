package com.example.recipecartnew;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    //Covers True
    @Test
    public void kgsToLbsTest1() {
        double actual = SettingsFragment.kgsToLbs(1.5);
        double expected = 3.3075;
        assertEquals(expected, actual,0.001);
    }

    //Covers False
    @Test
    public void kgsToLbsTest2(){
        double actual = SettingsFragment.kgsToLbs(1.5);
        double expected = 2.45;
        assertFalse(expected == actual);
    }

    //Covers True
    @Test
    public void lbsToKgsTest1() {
        double actual = SettingsFragment.lbsToKg(5.5);
        double expected = 2.4943;
        assertEquals(expected, actual,0.001);
    }

    //Covers False
    @Test
    public void lbsToKgsTest2(){
        double actual = SettingsFragment.lbsToKg(5.5);
        double expected = 2.45;
        assertFalse(expected == actual);
    }

    //Covers True
    @Test
    public void lToGallonsTest1() {
        double actual = SettingsFragment.lToGal(3.5);
        double expected = 0.9247;
        assertEquals(expected, actual,0.001);
    }

    //Covers False
    @Test
    public void lToGallonsTest2(){
        double actual = SettingsFragment.kgsToLbs(3.5);
        double expected = 1.23;
        assertFalse(expected == actual);
    }

    //Covers True
    @Test
    public void galToLTest1() {
        double actual = SettingsFragment.galToL(2.0);
        double expected = 7.57;
        assertEquals(expected, actual,0.001);
    }

    //Covers False
    @Test
    public void galToLTest2(){
        double actual = SettingsFragment.kgsToLbs(2.0);
        double expected = 4.25;
        assertFalse(expected == actual);
    }


}