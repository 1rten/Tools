package com.company;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class HexToBytes
{
    public static byte[] HexStringToBytes(String hexstr)
    {
        String str = hexstr.replace(" ", "");
        byte[] b = new byte[str.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++)
        {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = ((byte)(Parse(c0) << 4 | Parse(c1)));
        }
        return b;
    }

    private static int Parse(char c)
    {
        if (c >= 'a') {
            return c - 'a' + 10 & 0xF;
        }
        if (c >= 'A') {
            return c - 'A' + 10 & 0xF;
        }
        return c - '0' & 0xF;
    }

    public static void main(String[] args)
    {
        String hexStr = null;
        hexStr = "5a4332000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001";
        byte[] by = HexStringToBytes(hexStr);
        try
        {
            String store_name = new String(by, "GB2312");
            System.out.print(store_name.replace(" ", ""));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
}
