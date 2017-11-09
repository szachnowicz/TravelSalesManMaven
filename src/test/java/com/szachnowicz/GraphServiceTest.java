package com.szachnowicz;

import com.szachnowicz.graphs.Graph;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GraphServiceTest {
    @Test
    public void testSwap() throws Exception {


        int tab[] = {1, 2, 3};
        for (int i : tab) {
            System.out.print(i);
        }

//        GraphService.swap(0, 1, tab);

        for (int i : tab) {
            System.out.print(i);
        }

    }

    @Test
    public void testRevert() throws Exception {


        int tab[] = {1, 2, 3, 4, 5,7};
        for (int i : tab) {
            System.out.print(i);
        }
        System.out.println();
//        GraphService.revertArray(tab);

        for (int i : tab) {
            System.out.print(i);
        }


    }

    @Test
    public void testPrint() throws Exception {
        int tab[] = {1, 2, 3,4};

        final GraphService graphService = new GraphService();
        final List<int[]> permute = graphService.permute(tab);

        assertEquals(permute.size(),24);

    }
}