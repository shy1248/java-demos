package me.shy.base.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    public static void main(String[] args) {
        p("abc".matches("..."));
        Pattern pa = Pattern.compile("[a-z]{3}"); ///将正则表达式先编译，这样效率比较高
        Matcher m = pa.matcher("afz");
        p(m.matches());
        p("afz".matches("[a-z]{3}"));
        p("afzx".matches("[a-z]{3}"));
        p("a2bn3d89976hfs737".replaceAll("\\d", "-"));
        p("a2bn3d89976hfs737".replaceAll("\\d", "*"));
        p("\n");

        //初步认识 . * + ?
        p("aa".matches(".."));
        p("aa".matches("aa"));
        p("aaaa".matches("a*"));
        p("".matches("a*"));
        p("aaaaa".matches("a+"));
        p("".matches("a+"));
        p("aaaa".matches("a?"));
        p("".matches("a?"));
        p("192.168.0.34".matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"));
        p("192.168.0.aaa".matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"));
        p("192.168.0.34".matches("[0-255]\\.[0-255]\\.[0-255]\\.[0-255]"));
        p("192".matches("[0-2][0-9][0-9]"));
        p("192.168.0.34".matches("[0-2][0-9][0-9]\\.[0-2][0-9][0-9]\\.[0-2][0-9][0-9]\\.[0-2][0-9][0-9]"));
        p("\n");

        //范围
        p("a".matches("[abc]"));
        p("a".matches("[^abc]"));
        p("A".matches("[a-zA-Z]"));
        p("A".matches("[a-z][A-Z]"));
        p("A".matches("[a-z]|[A-Z]"));
        p("A".matches("[a-z[A-Z]]"));
        p("R".matches("[A-Z&&[RFG]]"));
        p("\n");

        //特殊字符
        p(" \n\t\r\f".matches("\\s{5}"));
        p("abc99999".matches("[a-z]+\\d+"));
        p("jfs&*^".matches("\\D+"));
        p(" ".matches("\\S"));
        p("s_8".matches("\\w{3}"));
        p("abc&%&9999".matches("[a-z]+[&%&]+\\d+"));
        p("\\".matches("\\\\"));   //java里\\代表着一个\,而java的正则表达式里必需用\\\\来表示一个\
        p("\n");

        //边界处理
        p("hello sir".matches("^h.*"));
        p("hello sir".matches(".*r$"));
        p("hello sir".matches("^h.*\\b.*"));
        p("hellosir".matches("^h.*\\b.*"));
        p(" \n".matches("^[\\s&&[^\\n]]*\\n$"));  //空白行的匹配
        p("\n");

        //匹配Email地址
        p("hengchen_2005@126.com".matches("^[a-zA-Z][\\w[.-]]+@[\\w[.-]]+\\.[\\w+[.-]]+"));
        p("\n");

        //常用方法

        Pattern pa1 = Pattern.compile("\\d{3,5}[a-z]{2}");
        Matcher ma1 = pa1.matcher("123aa-345678bb-901cc-345ddd-00");
        p(ma1.matches());
        ma1.reset();
        p(ma1.find());
        p(ma1.start() + "-" + ma1.end());
        p(ma1.find());
        p(ma1.start() + "-" + ma1.end());
        p(ma1.find());
        p(ma1.start() + "-" + ma1.end());
        p(ma1.find());
        p(ma1.start() + "-" + ma1.end());
        p(ma1.find());
        //p(ma1.start()+ "-" + ma1.end());  //start&end方法分别返回匹配的起始位置，此方法必需要ma1.find()为true时才能使用
        p("\n");

        p(ma1.lookingAt());
        p(ma1.lookingAt());
        p(ma1.lookingAt());
        p(ma1.lookingAt());
        p(ma1.lookingAt());
        p("\n");

        Pattern pa3 = Pattern.compile("java");
        Matcher ma3 = pa3.matcher("java Java JAva jAVa iloveJAVA you hate jaVA  hsaskf");
        while (ma3.find()) {
            p(ma3.group());
        }
        p("\n");

        Pattern pa2 = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        Matcher ma2 = pa2.matcher("java Java JAva jAVa iloveJAVA you hate jaVA  hsaskf");
        while (ma2.find()) {
            p(ma2.group());
        }
        p("\n");

        Pattern pa4 = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        Matcher ma4 = pa4.matcher("java Java JAva jAVa iloveJAVA you hate jaVA  hsaskf");
        while (ma4.find()) {
            p(ma4.replaceAll("JAVA"));
        }
        p("\n");

        Pattern pa5 = Pattern.compile("java", Pattern.CASE_INSENSITIVE);
        Matcher ma5 = pa5.matcher("java Java JAva jAVa iloveJAVA you hate jaVA  hsaskf");
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (ma5.find()) {
            i++;
            if (i % 2 == 0) {
                ma5.appendReplacement(sb, "java");
            } else {
                ma5.appendReplacement(sb, "JAVA");
            }
        }
        ma5.appendTail(sb);//此方法用于将后面不能匹配的尾巴字符加上去
        p(sb);
        p("\n");

        //分组，每个()为一组，每组有一个id，id确认：从左边开始数，第一个“（”的id为1，第二个为二，一次类推
        Pattern pa6 = Pattern.compile("(\\d{3,5})([a-z]{2})");
        Matcher ma6 = pa6.matcher("123aa-345678bb-901cc-345ddd-00");
        while (ma6.find()) {
            p(ma6.group(1));
            p(ma6.group(2));
        }

    }

    public static void p(Object o) {
        System.out.println(o);
    }
}
