package edu.gcccd.csis;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BigestFileLocatorTest
{


    @SuppressWarnings({"unchecked"})
    private static File findExtremeFile(final Path p)
    {

        final List fileList = new ArrayList();
        fileList.add(p.toFile());

        File x = null;
        File current = null;
        File[] fa = p.toFile().listFiles();

        LinkedList<Path> queue = new LinkedList();
        queue.add(p);

        if (fa != null)
        {

            do
                {
                if (fa != null)
                {

                    if (queue.peek() != null)
                    {

                        fa = queue.pollFirst().toFile().listFiles();
                        for (int i = 0; i < fa.length; i++)
                        {

                            if (fa[i].isDirectory())
                            {
                                queue.add(Paths.get(fa[i].getPath()));
                            }
                            if (fa[i].isFile()) {
                                if (x == null) x = fa[i];
                                else if (fa[i].isFile() && x.length() < fa[i].length()) x = fa[i];
                                else if (x.length() == fa[i].length()) {
                                    if (x.getPath().length() <= fa[i].getPath().length()) x = fa[i];
                                }
                            }
                        }
                    }
                }
            } while (queue.size() > 0);
        }
        return x;
    }

    @Test
    public void testExtreme() throws Exception
    {

        File f1 = null;
        File f2 = File.createTempFile("test2_", ".tmp");
        f2.deleteOnExit();

        assertEquals(f2, BigestFileLocator.extreme(f1, f2));
        assertEquals(f2, BigestFileLocator.extreme(f2, f1));




        File f3 = File.createTempFile("test3_", ".tmp");
        File f4 = File.createTempFile("test4_", ".tmp");
        f3.deleteOnExit();
        f4.deleteOnExit();

        assertEquals(f4, BigestFileLocator.extreme(f3, f4));
        assertEquals(f3, BigestFileLocator.extreme(f4, f3));


        File f5 = File.createTempFile("test5_", ".tmp");
        File f6 = File.createTempFile("test6_", ".tmp");
        String toWrite = "Hello";
        FileWriter writer = new FileWriter(f6);
        writer.write(toWrite);
        writer.close();
        f5.deleteOnExit();
        f6.deleteOnExit();
        assertEquals(BigestFileLocator.findExtremeFile(Paths.get(".")), findExtremeFile(Paths.get(".")));
    }

    @Test
    public void findExtremeFile() throws Exception
    {


        assertEquals(BigestFileLocator.findExtremeFile(Paths.get(".")), findExtremeFile(Paths.get(".")));
    }
}