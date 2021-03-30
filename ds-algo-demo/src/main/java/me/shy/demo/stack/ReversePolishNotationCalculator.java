/**
 * @Date        : 2021-02-17 16:29:53
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 使用逆波兰表达式（也叫后缀表达式）实现的简易版计算器
 *
 * 1.给定一个算术的中缀表达式，计算出其后缀表达式；
 * 2.根据后缀表达式求值算法进行表达式计算；
 *
 * 支持的操作符为：+、-、*、/、( 和 )
 * 支持的操作数为非负数
 *
 */
package me.shy.demo.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

public class ReversePolishNotationCalculator {

    private static final Set<String> VALID_SYMBOLS = new HashSet<>();

    static {
        Arrays.asList(
                new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "+", "-", "*", "/", "(", ")" })
                .forEach((s) -> {
                    VALID_SYMBOLS.add(s);
                });
    }

    public static void main(String[] args) {
        // ReversePolishNotationCalculator calculator = new ReversePolishNotationCalculator();
        // String expr = "5 * (3+6/2) + 2*(8*(11.5/7.5) -2)";
        // List<String> symbols = calculator.exprToList(expr);
        // System.out.println(symbols);
        // symbols = calculator.toReversePolishNatation(symbols);
        // System.out.println(symbols);
        // System.out.println(calculator.calculate(symbols));

        Scanner in = new Scanner(System.in);
        boolean loop = true;
        System.err.println(
                "############################################################################################################");
        System.out.println(
                "# Simple Polish Notation Calculator                                                                        #");
        System.out.printf(
                "# All suport symbols: [ +  -  *  /  (  )  and nonnegative number ]                                         #");
        System.out.println();
        System.err.println(
                "############################################################################################################");

        while (loop) {
            System.out.print("[Please input a expression or <exit> to exit] : ");
            try {
                String input = in.nextLine();
                if ("".equals(input.trim())) {
                    continue;
                }

                if("EXIT".equals(input.toUpperCase())) {
                    in.close();
                    loop = false;
                    System.out.println("Exit!");
                    break;
                }
                ReversePolishNotationCalculator calculator = new ReversePolishNotationCalculator();
                List<String> symbols = calculator.exprToList(input);
                // System.out.println(symbols);
                symbols = calculator.toReversePolishNatation(symbols);
                // System.out.println(symbols);
                System.out.printf("\n%s = %f", input, calculator.calculate(symbols));
            } catch (RuntimeException e) {
                System.err.println("[ERROR]: " + e.getMessage());
            }
            System.out.println();
        }
    }

    /**
     * 将给定的中缀表达式转换成操作符与操作数的列表，如果表达式非法，则直接抛出异常
     * @param expr 要转换的中缀表达式
     * @return 转化后的列表
     */
    public List<String> exprToList(String expr) {
        // 去掉空白字符
        expr = expr.replaceAll("\\s", "");
        // 待返回的列表
        List<String> symbols = new ArrayList<>();
        // 字符索引
        int index = 0;
        // 记录括号出现的次数，当出现左括号时加一，出现右括号时减一
        // 当整个变大时遍历完毕后，如果该值不为0，表示括号不平衡
        int brackets = 0;
        // 缓存连续的数字
        StringBuilder keeppingNumbers = new StringBuilder();
        String curSymbol = null;
        String nxtSymbol = null;

        while (index < expr.length()) {
            curSymbol = getSymbol(expr, index);
            nxtSymbol = getSymbol(expr, index + 1);
            // 校验符号是否合法
            if (!VALID_SYMBOLS.contains(curSymbol)) {
                throw new RuntimeException(
                        String.format("Invalid symbol: %s, expression: %s at index %d.", curSymbol, expr, index));
            }

            if (Pattern.matches("[\\.0-9]", curSymbol)) { // 当前字符是数字或者小数点，后面还有可能是数字
                // 往后搜索，直到尾部或者非数字和小数点结束
                int j = 0;
                while ((null != (nxtSymbol = getSymbol(expr, index + j))) && Pattern.matches("[\\.0-9]", nxtSymbol)) {
                    keeppingNumbers.append(nxtSymbol);
                    j++;
                }
                // 当数字结束时，保存数字并清空 StringBuilder
                curSymbol = keeppingNumbers.toString();
                keeppingNumbers.delete(0, keeppingNumbers.length());
                // 进一步检查数字格式是否合法
                try {
                    Double.parseDouble(curSymbol);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(
                            String.format("Invalid number: %s, expression: %s at index %d.", curSymbol, expr, index));
                }
                if (Pattern.matches("[\\(]", nxtSymbol)) {
                    throw new RuntimeException(String.format("Invalid symbol: %s, expression: %s at index %d.",
                            nxtSymbol, expr, index + 1));
                }
                // 跳过搜索过的内容
                index = index + j - 1;
            } else if (Pattern.matches("[\\+\\-\\*/]", curSymbol)) { // 如果当前字符是操作符，那么下一个字符不能是操作符、小数点以及右括号
                if (Pattern.matches("[\\+\\-\\*/\\.\\)]", nxtSymbol)) {
                    throw new RuntimeException(String.format("Invalid symbol: %s, expression: %s at index %d.",
                            nxtSymbol, expr, index + 1));
                }
            } else if (Pattern.matches("[\\(]", curSymbol)) { // 如果当前字符是左括号，那么下一个字符不能是操作符、小数点以及右括号
                if (Pattern.matches("[\\.\\+\\-\\*/\\)]", nxtSymbol)) {
                    throw new RuntimeException(String.format("Invalid symbol: %s, expression: %s at index %d.",
                            nxtSymbol, expr, index + 1));
                }
                // 记录括号出现的次数
                brackets++;
            } else { // 如果当前字符是右括号，那么下一个字符不能是小数点，数字以及左括号
                if (Pattern.matches("[\\.0-9\\(]", nxtSymbol)) {
                    throw new RuntimeException(String.format("Invalid symbol: %s, expression: %s at index %d.",
                            nxtSymbol, expr, index + 1));
                }
                // 右括号出现，消除一对括号
                brackets--;
            }
            symbols.add(curSymbol);
            index++;
        }
        // 判断括号是否平衡
        if (brackets != 0) {
            throw new RuntimeException(String.format("Not closed expression: %s.", expr));
        }

        return symbols;
    }

    /**
     * 将给定的中缀表达式转换为逆波兰表达式
     * 1. 初始化2个栈：操作符s1和结果栈s2；
     * 2. 从左至右扫描中缀表达式；
     * 3. 遇到操作数时，将其压入s2；
     * 4. 遇到操作符时，将其与s1栈顶的运算符进行比较：
     *    1. 如果s1为空，或者栈顶运算符为左括号，或者优先级比栈顶运算符优先级高，则直接将此运算符压入s1；
     *    2. 若是同优先级或者更低的优先级，将s1栈顶运算符弹出并压入到s2中，再次转入**4.1**，与s1中新的栈顶元算符比较；
     * 5. 遇到括号时：
     *    1. 如果是左括号，直接将其压入s1；
     *    2. 如果是右括号，则依次弹出s1顶的运算符，并压入s2，直到遇到左括号为止，然后将这一对括号丢弃；
     * 6. 重复步骤**2-5**，直到表达式的最右边；
     * 7. 将s1中剩余的运算符依次弹出并压入s2；
     * 8. 依次弹出s2中的元素并输出，**结果的逆序**即为对应中缀表达式的后缀表达式。
     *
     * **注意**：因为s2这个栈在整个操作中没有弹栈操作，而且最后的结果还要逆序输出，因此实际中第二个栈完全可以使用数组或者List来代替。
     *
     * @param symbols 要转换的中缀表达式列表
     * @return 逆波兰表达式的列表
     */
    public List<String> toReversePolishNatation(List<String> symbols) {
        // 存放结果的列表
        List<String> items = new ArrayList<>();
        // 存放操作符的栈
        Stack<String> temp = new Stack<>();
        for (String symbol : symbols) {
            if (Pattern.matches("[0-9\\.]+", symbol)) {
                items.add(symbol);
            } else if (Pattern.matches("[\\+\\-\\*/]", symbol)) {
                while(!(temp.empty() || "(".equals(temp.peek()) || getOperatorPriority(symbol, temp.peek()) == 1)){
                    items.add(temp.pop());
                }
                temp.push(symbol);
            } else if (Pattern.matches("[\\(]", symbol)) {
                temp.push(symbol);
            } else {
                while (!Pattern.matches("[\\(]", temp.peek())) {
                    items.add(temp.pop());
                }
                temp.pop();
            }
        }
        while(!temp.empty()) {
            items.add(temp.pop());
        }
        return items;
    }

    /**
     * 逆波兰表达式求值
     * 扫描表达式，遇到数字时，将数字压入栈，遇到运算符时，弹出栈顶的2个数，用运算符对它们做相应的计算，
     * 并将结果压入栈中，重复上述过程直到表达式最右端，最后运算得出的结果即为表达式的值。
     *
     * @param symbols 包含了逆波兰表达式的列表
     * @return 表达式的值
     */
    public double calculate(List<String> symbols) {
        double result = 0;
        Stack<String> temp = new Stack<>();
        for (String symbol: symbols){
            if (Pattern.matches("[0-9\\.]+", symbol)) {
                temp.push(symbol);
            } else {
                double num1 = Double.parseDouble(temp.pop());
                double num2 = Double.parseDouble(temp.pop());

                if ("+".equals(symbol)) {
                    result = num2 + num1;
                } else if ("-".equals(symbol)) {
                    result = num2 - num1;
                } else if ("*".equals(symbol)) {
                    result = num2 * num1;
                } else {
                    result = num2 / num1;
                }
                temp.push(Double.toString(result));
            }
        }
        return result;
    }

    /**
     * 获取表达式中的第 index 位
     * @param expr 原始表达式
     * @param index 索引，从 0 开始
     * @return 返回 index 位置上的字符串，如果已到原始表达式的尾部，返回空字符串
     */
    private String getSymbol(String expr, int index) {
        try {
            String symbol = expr.charAt(index) + "";
            return symbol;
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    /**
     * 判断两个操作符的优先级
     * @param o1
     * @param o2
     * @return 如果运算符 o1 的优先级低于 o2，返回 -1；如果运算符 o1 的优先级高于 o2，返回 1；否则返回 0
     */
    private int getOperatorPriority(String o1, String o2) {
        if (Pattern.matches("[\\+\\-]", o1) && Pattern.matches("[\\*/]", o2)) {
            return -1;
        } else if (Pattern.matches("[\\*/]", o1) && Pattern.matches("[\\+\\-]", o2)) {
            return 1;
        } else {
            return 0;
        }
    }
}
