package com.github.beothorn.clojurecalc;

public class StringFormatter {

    public static String replaceToken(String original, int tokenIndex, String tokenReplacement) {
        return original.replaceAll("\\{" + tokenIndex + "\\}", tokenReplacement);
    }

    public static String format(String str, Object[] args) {
        String result = str;
        for (int i = 0; i < args.length; i++) {
            String replaceValue = args[i].toString();
            if (args[i] instanceof Object[][]) {
                final StringBuffer cellRangeAsString = new StringBuffer();
                Object[][] cellRange = (Object[][]) args[i];
                for (int line = 0; line < cellRange.length; line++) {
                    for (int column = 0; column < cellRange[line].length; column++) {
                        final String toString = cellRange[line][column].toString();
                        cellRangeAsString.append(toString);
                        cellRangeAsString.append(" ");
                    }
                }
                final String cellRangeStringValue = cellRangeAsString.toString();
                replaceValue = cellRangeStringValue.substring(0, cellRangeStringValue.length() - 1);
            }
            result = StringFormatter.replaceToken(result, i, replaceValue);
        }
        return result;
    }
    
}
