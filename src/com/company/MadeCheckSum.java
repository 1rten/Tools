package com.company;

import java.io.PrintStream;

public class MadeCheckSum
{
    public static void main(String[] args)
    {
        String string = null;
        string = "02 3F 01 0A 01 00 02 0A 02 00 02 08 01 00 25 08 02 00 23 09 01 00 07 09 02 00 07 09 03 00 07 09 04 00 07 09 05 00 07 09 06 00 07 0C 01 00 03 0C 02 00 01 0C 03 00 01 0C 04 00 01 0C 05 00 05 06 01 00 00 06 02 00 00 05 01 00 00 05 02 00 02 05 03 00 00 06 03 00 02 06 04 00 02 0D 0B 00 00 0D 0C 00 00 0D 0D 00 03 0D 0E 00 00 0D 0F 00 00 0D 10 00 03 0D 11 00 02 0D 12 00 00 0D 13 00 03 0D 14 00 00 0D 15 00 03 0D 0A 00 00 0D 01 00 00 0D 02 00 00 0D 03 00 00 0D 04 00 00 0D 05 00 00 0D 06 00 00 0D 07 00 00 0D 08 00 00 0D 09 00 00 FE 11 AA 01 01 01 01 01 01 01 01 00 00 00 00 00 00 00 00 00 01 02 00 00 00 00 00 BB BB 00 00 00 00 BB BB 01 01 01 01 01 01 01 01 01 01 01 01 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02 02";
        String str = null;
        int j = 0;
        int sum = 0;
        String tempStr = string.replace(" ", "");
        for (int i = 0; i < tempStr.length() / 2; i++)
        {
            str = tempStr.substring(j, j + 2);
            sum += Integer.parseInt(str, 16);
            j += 2;
        }
        System.out.println(Integer.toHexString(sum));

        int mod = sum % 65535;
        int hex = (mod ^ 0xFFFFFFFF) + 1;
        System.out.println(Integer.toHexString(hex).subSequence(4, 8));
    }
}
