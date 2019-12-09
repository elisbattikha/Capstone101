package edu.gcccd.csis;

import java.io.*;
import java.nio.file.*;
import java.util.LinkedList;

public class BigestFileLocator
{

    public static void main(final String[] args)
    {


        final Path path = Paths.get(args.length < 1 ? "." : args[0]);//

        final File ex = findExtremeFile(path);
        System.out.printf("Starting at : %s, the largest file was found here:\n%s\n its size is: %d\n",
                path.toAbsolutePath().toString(),//

                ex.getAbsolutePath(),

                ex.length());//
    }


    static File extreme(final File f1, final File f2)//
    {

        if (f1 == null) {

            return f2;
        }
        if (f2 == null) {

            return f1;
        }
        if (f1.length() > f2.length()) {

            return f1;//
        }
        return f2;//
    }

    static File findExtremeFile(final Path p)//
    {

        File x = null;
        File current = null;
        final File[] fa = p.toFile().listFiles();//
        if (fa != null) {
            for (int i = 0; i < fa.length; i++)//
            {

                if (fa[i].isDirectory())//
                {

                    current = findExtremeFile(Paths.get(fa[i].getPath()));//
                    if (x == null) x = current;
                    else if (current != null && x.length() < current.length()) x = current;
                    else if (current != null && x.length() == current.length()) //
                    {

                        if (x.getPath().length() <= current.getPath().length()) x = current;
                    }
                }
            }

            for (int i = 0; i < fa.length; i++) //
            {

                if (fa[i].isFile())//
                {

                    if (x == null) x = fa[i];
                    else if (x.length() < fa[i].length()) x = fa[i];//
                    else if (x.length() == fa[i].length())//
                    {

                        if (x.getPath().length() <= fa[i].getPath().length()) x = fa[i];//
                    }
                }
            }
        }
        return x;
    }
}
