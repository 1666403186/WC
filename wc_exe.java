package WC_exe;




import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 文本文件操作器
 * 功能介绍
 */
public class wc_exe {

    private static List<String> fileTxt = new LinkedList<String>();   //存放文本的全部内容
    private static String lineTxt;                                    //存放文本的某一行内容

    /**
     * 根据指定路径获取文本内容
     * 并对每一行读取内容进行代码种类判断
     *
     * @param path
     * @throws IOException
     */
    private static SpecialLine GetFileContentByPath(String path) throws IOException {
        //读取指定路径对应的文件内容
        InputStream is = new FileInputStream(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        SpecialLine specialLine=new SpecialLine();
        //buffer用于缓存从文件中读取的内容
        StringBuffer buffer = new StringBuffer();

        //将文件内容按行读取，并将每一行存入fileTxt中
        while ((lineTxt = in.readLine()) != null) {


            String regxNodeBegin = "(\\S?)\\s*/\\*.*";

            String regxNodeEnd = "(.*\\*/\\s*)\\S?";

            String regxNode = "(\\s*)(\\S?)(//+).*";//使用正则表达式判断注释行

            if (lineTxt.matches(regxNodeBegin) || lineTxt.matches(regxNodeEnd)

                    || lineTxt.matches(regxNode)) {
                ++specialLine.codeLine  ;
            }
            else if (lineTxt.length() > 1) // 判断代码行

            {
                ++specialLine.codeLine;

            }
            else {
                ++specialLine.emptLine;

            }

            fileTxt.add(lineTxt);
        }
    return specialLine;
    }

    /**
     * 获取文本所有字符（包括空格）的总数
     *
     * @return
     */
    private static int GetCharacterCount() {
        int count = 0;
        for (String str : fileTxt) {
            count += str.length();
        }
        return count;
    }

    /**
     * 获取文本所有单词的总数
     *
     * @return
     */
    private static int GetWordCount() {
        int count = 0;
        for (String str : fileTxt) {
            String[] words = str.split(" ");
            count += words.length;
        }
        return count;
    }

    /**
     * 获取文本的行数
     *
     * @return
     */
    private static int GetLineCount() {
        return fileTxt.size();
    }
    public static void main(String[] args) throws IOException {


        do {

            System.out.println("请输入 [optation] [path]");
            System.out.println("如 -c 测试代码.txt");
            Scanner scanner = new Scanner(System.in);


            if (scanner.hasNextLine()) {
                String[] str = scanner.nextLine().split(" ");
                String selection = str[0];
                String path = "E:\\IDEA\\testfile1\\" + str[1];


                SpecialLine sl= GetFileContentByPath(path);

                if (selection.equals("-c"))
                    System.out.println("字符数(包括空格)是：" + GetCharacterCount());
                else if (selection.equals("-w"))
                    System.out.println("单词数是：" + GetWordCount());
                else if (selection.equals("-l"))
                    System.out.println("行数是：" + GetLineCount());
                else if (selection.equals("-a")) {
                    System.out.println("注释行数是："+ sl.nodeLine);
                    System.out.println("代码行数是："+ sl.codeLine);
                    System.out.println("空行行数是："+ sl.emptLine);
                }


            }
         else  System.out.println("找不到目标文件！请重新输入！");
        }while (true);
    }
}

