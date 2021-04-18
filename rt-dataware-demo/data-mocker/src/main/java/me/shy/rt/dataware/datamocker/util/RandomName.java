/**
 * @Date        : 2021-04-10 23:29:25
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A tool for random generated names.
 */

package me.shy.rt.dataware.datamocker.util;

import java.nio.charset.Charset;
import java.util.Random;

import lombok.NonNull;

public class RandomName {
    public static final String[] SURNAMES = { "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "卫", "蒋", "沈", "韩", "杨",
            "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "柏", "窦",
            "苏", "潘", "葛", "范", "彭", "鲁", "韦", "马", "苗", "凤", "方", "俞", "任", "袁", "柳", "鲍", "史", "唐", "费", "岑", "薛",
            "雷", "贺", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "卞", "齐", "康", "伍", "余", "元",
            "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹", "姚", "汪", "祁", "毛", "狄", "臧", "计", "伏", "成", "戴", "宋", "茅",
            "庞", "熊", "纪", "舒", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "尉迟", "濮阳", "淳于", "单于", "公孙", "轩辕", "令狐",
            "钟离", "宇文", "长孙", "慕容", "司徒", "司空", "南门", "呼延", "百里", "东郭", "西门", "南宫", "独孤", "南宫" };

    public static final String BOY_NAMES = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛"
            + "昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固"
            + "之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    public static final String GIRL_NAMES = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞"
            + "莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓"
            + "欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽韶涵予馨艺欣";


    public static String nextFamilyName() {
        return SURNAMES[new Random().nextInt(SURNAMES.length)];
    }

    public static String nextLastName(@NonNull String gender) {
        String nameString;
        int nameStringLength;
        if("M".equals(gender.trim().toUpperCase())) {
            nameString = BOY_NAMES;
            nameStringLength = BOY_NAMES.length();
        } else {
            nameString = GIRL_NAMES;
            nameStringLength = GIRL_NAMES.length();
        }
        int nameLenth = RandomNumeric.nextInteger(1, 2);
        int index = RandomNumeric.nextInteger(0, nameStringLength - nameLenth);
        return nameString.substring(index, index + nameLenth);
    }

    public static String getNickName(@NonNull String gender, @NonNull String lastName) {
        if (lastName.length() > 1) {
            return lastName;
        }
        if ("M".equals(gender.trim().toUpperCase())) {
            return "阿" + lastName;
        } else {
            return lastName + lastName;
        }
    }

    public String nextFullName(String gender) {
        return nextFamilyName() + nextLastName(gender);
    }

    public String nextFullName() {
        return nextFullName(new RandomWeightOption<String>("M", "F").nextPayload());
    }

    // GB2312编码范围：A1A1－FEFE，其中汉字编码范围：B0A1-F7FE。
    // GB2312编码是第一个汉字编码国家标准，由中国国家标准总局1980年发布，1981年5月1日开始使用。
    // GB2312编码共收录汉字6763个，其中一级汉字3755个，二级汉字3008个。同时，GB2312编码收录了
    // 包括拉丁字母、希腊字母、日文平假名及片假名字母、俄语西里尔字母在内的682个全角字符。
    // 分区表示
    // GB2312编码对所收录字符进行了“分区”处理，共94个区，每区含有94个位，共8836个码位。这种表示方式也称为区位码。
    // 01-09区收录除汉字外的682个字符。
    // 10-15区为空白区，没有使用。
    // 16-55区收录3755个一级汉字，按拼音排序。
    // 56-87区收录3008个二级汉字，按部首/笔画排序。
    // 88-94区为空白区，没有使用。
    // 举例来说，“啊”字是GB2312编码中的第一个汉字，它位于16区的01位，所以它的区位码就是1601。
    // 双字节编码
    // GB2312规定对收录的每个字符采用两个字节表示，第一个字节为“高字节”，对应94个区；第二个字节为“低字节”，对应94个位。
    // 所以它的区位码范围是：0101－9494。区号和位号分别加上0xA0就是GB2312编码。例如最后一个码位是9494，区号和位号分别
    // 转换成十六进制是5E5E，0x5E+0xA0＝0xFE，所以该码位的GB2312编码是FEFE。
    // GB2312编码范围：A1A1－FEFE，其中汉字的编码范围为B0A1-F7FE，第一字节0xB0-0xF7（对应区号：16－87），第二个字节0xA1-0xFE（对应位号：01－94）。
    public static String nextChineseCharacter() {
        int highPosition, lowPostion;
        Random random = new Random();
        // 区号和位号分别加上 0xA0 就是GB2312编码
        // 高位字节，代表区码，从 16 区开始，有效区位为 87-16
        highPosition = 0xA0 + 16 + Math.abs(random.nextInt(87 - 16));
        // 低位字节，代表位码，每区含有94个位
        lowPostion = 0xA0 + Math.abs(random.nextInt(94));
        // 一个字符占用2个字节
        byte[] bytes = new byte[2];
        bytes[0] = new Integer(highPosition).byteValue();
        bytes[1] = new Integer(lowPostion).byteValue();
        return new String(bytes, Charset.forName("GB2312"));
    }

}
