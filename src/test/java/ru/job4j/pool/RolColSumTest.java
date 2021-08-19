package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RolColSumTest {

    @Test
    public void whenSeqExecute() {
        int[][] arrayStart = {
                {1, 2},
                {3, 4}
        };
        RolColSum.Sums one = new RolColSum.Sums();
        RolColSum.Sums two = new RolColSum.Sums();
        one.setRowSum(3);
        one.setColSum(4);
        two.setRowSum(7);
        two.setColSum(6);
        RolColSum.Sums[] expected = {
                one,
                two
        };
        RolColSum.Sums[] result = RolColSum.Sums.sum(arrayStart);
        assertThat(expected[0].getRowSum(), is(result[0].getRowSum()));
    }

    @Test
    public void whenAsyncExecute() throws ExecutionException, InterruptedException {
        int[][] arrayStart = {
                {1, 2},
                {3, 4}
        };
        RolColSum.Sums one = new RolColSum.Sums();
        RolColSum.Sums two = new RolColSum.Sums();
        one.setRowSum(3);
        one.setColSum(4);
        two.setRowSum(7);
        two.setColSum(6);
        RolColSum.Sums[] expected = {
                one,
                two
        };
        RolColSum.Sums[] result = RolColSum.Sums.asyncSum(arrayStart);
        assertThat(expected[0].getRowSum(), is(result[0].getRowSum()));
    }
}