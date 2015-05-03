package com.relicum.pvpcore;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Name: FormatUtil.java Created: 28 April 2015
 *
 * @author Relicum
 * @version 0.0.1
 */
public class FormatUtil {

    public static String format(String format, Object[] objects) {
        return colorize(MessageFormat.format(format, objects));
    }

    public static String format(String format) {
        return colorize(format);

    }

    public static String formatNoColor(String format, Object[] objects) {
        return MessageFormat.format(format, objects);

    }

    public static String colorize(String string) {

        return string.replaceAll("(" + '&' + "([a-fklmnor0-9]))", '\u00A7' + "$2");
    }

    public static String implode(String implode, Object[] objects) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            sb.append(obj).append(implode);
        }
        if (objects.length > 0) {
            sb.setLength(sb.length() - implode.length());
        }
        return sb.toString();
    }

    public static String implode(String implode, String[] objects) {
        StringBuilder sb = new StringBuilder();
        for (String str : objects) {
            sb.append(str).append(implode);
        }
        if (objects.length > 0) {
            sb.setLength(sb.length() - implode.length());
        }
        return sb.toString();
    }

    public static String implode(Object[] list, String glue, String format) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            Object item = list[i];
            String str = item == null ? "NULL" : item.toString();
            if (i != 0) {
                ret.append(glue);
            }
            if (format != null) {
                ret.append(String.format(format, new Object[] { str }));
            } else {
                ret.append(str);
            }
        }
        return ret.toString();
    }

    public static String implode(Object[] list, String glue) {
        return implode(list, glue, null);
    }

    public static String implode(Collection<?> coll, String glue, String format) {
        return implode(coll.toArray(new Object[coll.size()]), glue, format);
    }

    public static String implode(Collection<?> coll, String glue) {
        return implode(coll, glue, null);
    }

    public static String implodeCommaAndDot(Collection<?> objects, String format, String comma, String and, String dot) {
        if (objects.isEmpty()) {
            return "";
        }
        if (objects.size() == 1) {
            return implode(objects, comma, format);
        }
        List ourObjects = new ArrayList<>(objects);
        String lastItem = ourObjects.get(ourObjects.size() - 1).toString();
        String nextToLastItem = ourObjects.get(ourObjects.size() - 2).toString();
        if (format != null) {
            lastItem = String.format(format, lastItem);
            nextToLastItem = String.format(format, nextToLastItem);
        }
        String merge = String.valueOf(nextToLastItem) + and + lastItem;
        ourObjects.set(ourObjects.size() - 2, merge);
        ourObjects.remove(ourObjects.size() - 1);
        return String.valueOf(implode(ourObjects, comma, format)) + dot;
    }

    public static String implodeCommaAndDot(Collection<?> objects, String comma, String and, String dot) {
        return implodeCommaAndDot(objects, null, comma, and, dot);
    }

    public static String implodeCommaAnd(Collection<?> objects, String comma, String and) {
        return implodeCommaAndDot(objects, comma, and, "");
    }

    public static String implodeCommaAndDot(Collection<?> objects, String color) {
        return implodeCommaAndDot(objects, String.valueOf(color) + ", ", String.valueOf(color) + " and ", String.valueOf(color) + ".");
    }

    public static String implodeCommaAnd(Collection<?> objects, String color) {
        return implodeCommaAndDot(objects, String.valueOf(color) + ", ", String.valueOf(color) + " and ", "");
    }

    public static String implodeCommaAndDot(Collection<?> objects) {
        return implodeCommaAndDot(objects, "");
    }

    public static String implodeCommaAnd(Collection<?> objects) {
        return implodeCommaAnd(objects, "");
    }

    public static String implodeAndFormat(String implode, Object[] objects) {
        return format(implode(implode, objects), new Object[0]);
    }

    public static String getFriendlyName(Material mat) {
        return getFriendlyName(mat.toString());
    }

    public static String getFriendlyName(EntityType entityType) {
        return getFriendlyName(entityType.toString());
    }

    public static String getFriendlyName(String string) {
        String ret = string.toLowerCase();
        ret = ret.replaceAll("_", " ");
        return WordUtils.capitalize(ret);
    }

    public static String getArticle(String string) {
        string = string.toLowerCase();
        if ((string.startsWith("a")) || (string.startsWith("e")) || (string.startsWith("i")) || (string.startsWith("o")) || (string.startsWith("u"))) {
            return "an";
        }
        return "a";
    }
}
